package com.plietnov.task.part1;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSeparateListRange extends Thread implements PrimeBase {

    private static final int START_VALUE = 2;
    private int maxValue;
    private ConcurrentLinkedQueue<Integer> result;
    private int step;


    public ThreadSeparateListRange(int step, int maxValue, Collection<Integer> result) {
        this.step = step;
        this.maxValue = maxValue;
        this.result = (ConcurrentLinkedQueue<Integer>) result;
    }
    private static AtomicInteger cursor = new AtomicInteger(START_VALUE);

    @Override
    public void run() {
        int range = maxValue / step;
        int first = cursor.getAndAdd(range);
        int last = first + range;
        if (last + range > maxValue) {
            last = maxValue;
        }
        LinkedList<Integer> list = new LinkedList();
        for (int i = first; i < last; i++) {
            addToResultListIfPrime(i, list);
        }
        result.addAll(list);
    }
}