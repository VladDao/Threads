package com.plietnov.task.part2;

import com.plietnov.task.part1.PrimeBase;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class PrimeSeparateList implements Callable, PrimeBase {

    private int from;
    private int upTo;

    public PrimeSeparateList(int from, int upTo) {
        this.from = from;
        this.upTo = upTo;
    }

    @Override
    public List<Integer> call() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = from; i < upTo; i++) {
            addToResultListIfPrime(i, list);
        }
        return list;
    }
}