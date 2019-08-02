package com.plietnov.task.part2;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceStart {

    private static final Logger LOGGER = Logger.getLogger(ExecutorServiceStart.class);
    private static final String POOL_OF_THREAD_SEPARATE_LIST = "Pool of thread separate list,  threads = ";
    private static final String POOL_OF_THREAD_SINGLE_LIST = "Pool of thread single list,  threads = ";
    private static final String MILLISECONDS = "ms";
    private static final String TIME = ", time= ";
    public static final int START_VALUE = 2;

    private int maxValue;
    private int numberOfThread;

    public ExecutorServiceStart(int numberOfThread, int maxValue) {
        this.maxValue = maxValue;
        this.numberOfThread = numberOfThread;
    }

    public void poolThreadSingleList() throws ExecutionException, InterruptedException {
        Queue<Integer> result = new ConcurrentLinkedQueue<>();
        Future f = null;
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThread);
        long start = System.nanoTime();
        for (int i = 0; i < numberOfThread; i++) {
            f = pool.submit(new PrimeSingleList(maxValue, result));
        }
        f.get();
        pool.shutdown();
        LOGGER.info(POOL_OF_THREAD_SINGLE_LIST + numberOfThread + TIME +
                (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + MILLISECONDS));
    }

    public void poolThreadSeparateList() throws ExecutionException, InterruptedException {
        List<Integer> result = new LinkedList<>();
        List<Future<List<Integer>>> f;
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThread);
        List<Callable<List<Integer>>> callableList = initData(maxValue);
        long start = System.nanoTime();
        f = pool.invokeAll(callableList);
        for (Future<List<Integer>> integerFuture : f) {
            result.addAll(integerFuture.get());
        }
        pool.shutdown();
        LOGGER.info(POOL_OF_THREAD_SEPARATE_LIST + numberOfThread + TIME +
                (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + MILLISECONDS));
    }

    private List<Callable<List<Integer>>> initData(int maxValue) {
        List<Callable<List<Integer>>> callableList = new ArrayList<>();
        int step = maxValue / numberOfThread;
        AtomicInteger cursor = new AtomicInteger(START_VALUE);
        for (int i = 0; i < numberOfThread; i++) {
            int startValue = cursor.getAndAdd(step);
            int endValue = cursor.get();
            if (i == numberOfThread - 1) {
                endValue = maxValue;
            }
            callableList.add(new PrimeSeparateList(startValue, endValue));
        }
        return callableList;
    }
}
