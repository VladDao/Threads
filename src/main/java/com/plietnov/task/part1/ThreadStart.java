package com.plietnov.task.part1;

import com.plietnov.task.Main;
import com.plietnov.task.factory.ThreadFactoryImpl;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadStart {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final String TIME = ", time= ";
    private static final String SINGLE_LIST = "Single list,  threads = ";
    private static final String SEPARATE_LIST = "Separate list,  threads = ";
    private static final String SEPARATE_LIST_AND_RANGE = "Separate list and range,  threads = ";
    private static final String MILLISECONDS = "ms";
    private int maxValue;
    private int numberOfThread;
    private ThreadFactory threadFactory = new ThreadFactoryImpl();

    public ThreadStart(int numberOfThread, int maxValue) {
        this.maxValue = maxValue;
        this.numberOfThread = numberOfThread;
    }

    public void startSingleList() {
        Queue<Integer> arr = new ConcurrentLinkedQueue<>();
        Thread thread = new ThreadSingleList(maxValue, arr);
        long start = threadStart(thread);
        LOGGER.info(SINGLE_LIST + numberOfThread + TIME +
                (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + MILLISECONDS));
    }

    public void startSeparateList() {
        Queue<Integer> arr = new ConcurrentLinkedQueue<>();
        Thread thread = new ThreadSeparateList(maxValue, arr);
        long start = threadStart(thread);
        LOGGER.info(SEPARATE_LIST + numberOfThread + TIME +
                (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + MILLISECONDS));
    }

    public void startSeparateListAndSeparateRange() {
        Queue<Integer> arr = new ConcurrentLinkedQueue<>();
        Thread thread = new ThreadSeparateListRange(numberOfThread, maxValue, arr);
        long start = threadStart(thread);
        LOGGER.info(SEPARATE_LIST_AND_RANGE + numberOfThread + TIME +
                (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start) + MILLISECONDS));
    }

    private long threadStart(Runnable r) {
        Thread[] threads = new Thread[numberOfThread];
        for (int i = 0; i < numberOfThread; i++) {
            threads[i] = threadFactory.newThread(r);
        }
        long start = System.nanoTime();
        Arrays.stream(threads).forEach(Thread::start);
        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.interrupt();
                LOGGER.error(e);
            }
        });
        return start;
    }
}
