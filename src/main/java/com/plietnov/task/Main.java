package com.plietnov.task;

import com.plietnov.task.part1.ThreadStart;
import com.plietnov.task.part2.ExecutorServiceStart;
import com.plietnov.task.part3.FileReader;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        InitData initData = new InitData();
        ThreadStart threadStart = new ThreadStart(initData.getThreadAmount(), initData.getMaxValue());
        threadStart.startSingleList();
        threadStart.startSeparateList();
        threadStart.startSeparateListAndSeparateRange();
        ExecutorServiceStart executorServiceStart =
                new ExecutorServiceStart(initData.getThreadAmount(), initData.getMaxValue());
        executorServiceStart.poolThreadSingleList();
        executorServiceStart.poolThreadSeparateList();
        FileReader fileReader = new FileReader();
        fileReader.start();
    }
}
