package com.plietnov.task.part2;

import com.plietnov.task.part1.PrimeBase;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeSingleList implements Runnable, PrimeBase {

    private int upTo;
    private ConcurrentLinkedQueue<Integer> result;

    public PrimeSingleList(int upTo, Queue<Integer> result) {
        this.upTo = upTo;
        this.result = (ConcurrentLinkedQueue<Integer>) result;
    }

    private static final AtomicInteger cursor = new AtomicInteger(2);

    @Override
    public void run() {
        int currentValue;
        while ((currentValue = cursor.getAndAdd(1)) <= upTo) {
            addToResultListIfPrime(currentValue, result);
        }
    }
}