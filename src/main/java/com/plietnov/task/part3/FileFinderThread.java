package com.plietnov.task.part3;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FileFinderThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(FileFinderThread.class);
    public static final Object LOCK = new Object();
    private static final AtomicInteger CURSOR_MAIN = new AtomicInteger(0);
    private Map<Sequence, Sequence> result;
    private byte[] bytes = new byte[0];
    private FileToByte fileToByte;
    private boolean wait = true;

    public FileFinderThread(FileToByte fileToByte, Map<Sequence, Sequence> result) {
        this.fileToByte = fileToByte;
        this.result = result;
    }

    @Override
    public void run() {
        while (true) {
            waitNewDate();
            startSearching();
        }
    }

    private void startSearching() {
        int current;
        while ((current = CURSOR_MAIN.getAndIncrement()) < bytes.length) {
            findSequences(current);
        }
    }

    private void findSequences(int firstElement) {
        int firstInRangeTwo = findNext(firstElement);
        if (firstInRangeTwo == -1) {
            return;
        }
        int lastTwo = firstInRangeTwo;
        for (int lastOne = firstElement; lastOne < bytes.length; lastOne++, lastTwo++) {
            if (!(lastTwo < bytes.length && bytes[lastOne] == bytes[lastTwo])) {
                Sequence sequenceOne = new Sequence(firstElement, lastOne);
                Sequence sequenceTwo = new Sequence(firstInRangeTwo, lastTwo);
                putResult(sequenceOne, sequenceTwo);
                return;
            }
        }
    }

    private void putResult(Sequence sequenceOne, Sequence sequenceTwo) {
        FileReader.SOUT.add(sequenceOne.getLength());
        result.put(sequenceOne, sequenceTwo);
    }

    private int findNext(int index) {
        byte tmp = bytes[index];
        index++;
        for (int i = index; i < bytes.length; i++) {
            if (tmp == bytes[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean isNotWait() {
        return wait;
    }

    private void waitNewDate() {
        wait = false;
        while (!wait) {
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.error(e);
                }
                wait = wakeUp();
            }
        }
    }

    private boolean wakeUp() {
        boolean flag;
        if (fileToByte.isChanged()) {
            CURSOR_MAIN.set(0);
        }
        flag = CURSOR_MAIN.get() < fileToByte.getByte().length;
        if (flag) {
            this.bytes = fileToByte.getByte();
        }
        return flag;
    }
}