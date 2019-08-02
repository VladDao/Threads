package com.plietnov.task.part1;

import java.util.Collection;

public interface PrimeBase {

    default boolean isPrime(int number) {
        int max = (int) Math.sqrt(number);
        for (int i = 2; i <= max; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    default void addToResultListIfPrime(int inputValue, Collection<Integer> resultCollection) {
        if (isPrime(inputValue)) {
            resultCollection.add(inputValue);
        }
    }
}
