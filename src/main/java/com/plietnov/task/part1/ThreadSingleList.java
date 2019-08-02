package com.plietnov.task.part1;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSingleList extends Thread implements PrimeBase {

    private int upTo;
    private ConcurrentLinkedQueue<Integer> result;
    private static final int START_VALUE = 2;

    public ThreadSingleList(int upTo, Collection<Integer> result) {
        this.upTo = upTo;
        this.result = (ConcurrentLinkedQueue<Integer>) result;
    }
    private static final AtomicInteger cursor = new AtomicInteger(START_VALUE);

    @Override
    public void run() {
        int currentValue;
        while ((currentValue = cursor.getAndAdd(1)) <= upTo) {
             addToResultListIfPrime(currentValue, result);
        }
    }
}