package com.plietnov.task.part3;

import com.plietnov.task.Util;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class FileReader {

    private static final Logger LOGGER = Logger.getLogger(FileFinderThread.class);
    private static final String LS = System.lineSeparator();
    private static final String CURRENT_MAX_LENGTH = "Current max length= ";
    private static final String MAX_LENGTH = "Max_length= ";
    private static final String FIRST_SEQUENCES_START = "first sequences, start= ";
    private static final String SECOND_SEQUENCES_START = "second sequences, start= ";
    private static final String PROCESSING_START = "Processing start";
    private static final String END = " end= ";
    private static final String EXIT = "e";
    private static final String ENTER_FILE_NAME = "Please enter file name";
    private static final String FILE_NOT_FOUND = "File not found, try again";
    public static final PriorityBlockingQueue<Integer> SOUT = new PriorityBlockingQueue<>();
    private FileFinderThread threadFinder;
    private FileToByte fileToByte = new FileToByte();
    private List<FileFinderThread> threadList = new ArrayList<>();
    private HashMap<Sequence, Sequence> result = new HashMap<>();

    public void start() {
        initThread(fileToByte);
        String input = StringUtils.EMPTY;
        while (ObjectUtils.notEqual(EXIT, input)) {
            LOGGER.info(ENTER_FILE_NAME);
            input = Util.readFromConsole();
            fileFinder(input);
        }
    }

    private void fileFinder(String input) {
        if (!fileToByte.setFile(input)) {
            LOGGER.error(FILE_NOT_FOUND);
        } else {
            synchronized (FileFinderThread.LOCK) {
                FileFinderThread.LOCK.notify();
            }
            printResult();
        }
    }

    private void initThread(FileToByte file) {
        threadFinder = new FileFinderThread(file, result);
        threadFinder.setDaemon(true);
        threadList.add(threadFinder);
        threadFinder.start();
    }

    private void printResult() {
        LOGGER.info(PROCESSING_START);
        while (threadFinder.isNotWait() || !SOUT.isEmpty()) {
            if (!SOUT.isEmpty()) {
                LOGGER.info(CURRENT_MAX_LENGTH + SOUT.poll());
            }
            Thread.currentThread().interrupt();
        }
        Optional<Map.Entry<Sequence, Sequence>> ss = result.entrySet().stream().max(Comparator.comparing(s -> s.getKey().getLength()));
        ss.ifPresent(sequencesSequencesEntry -> LOGGER.info(MAX_LENGTH + sequencesSequencesEntry.getKey().getLength() + LS +
                FIRST_SEQUENCES_START + sequencesSequencesEntry.getKey().getFirst() + END + sequencesSequencesEntry.getKey().getLast() + LS +
                SECOND_SEQUENCES_START + sequencesSequencesEntry.getValue().getFirst() + END + sequencesSequencesEntry.getValue().getLast()));
        result.clear();
    }
}
