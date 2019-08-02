package com.plietnov.task.part1;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSeparateList extends Thread implements PrimeBase {

    private static final int START_VALUE = 2;
    private int upTo;
    private ConcurrentLinkedQueue<Integer> result;


    public ThreadSeparateList(int upTo, Collection<Integer> result) {
        this.upTo = upTo;
        this.result = (ConcurrentLinkedQueue<Integer>) result;
    }
    private static final AtomicInteger cursor = new AtomicInteger(START_VALUE);

    @Override
    public void run() {
        LinkedList<Integer> list = new LinkedList();
        int currentValue;
        while ((currentValue = cursor.getAndAdd(1)) <= upTo) {
            addToResultListIfPrime(currentValue, list);
        }
        result.addAll(list);
    }
}