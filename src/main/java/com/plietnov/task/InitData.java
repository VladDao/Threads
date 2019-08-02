package com.plietnov.task;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;

public class InitData {

    private static final Logger LOGGER = Logger.getLogger(InitData.class);
    private static final String ENTER_NUMBER_OF_THREAD = "Please enter number of thread";
    private static final String INCORRECT_INPUT = "Input mast be number, try again";
    private static final String ENTER_UPPER_BOUND = "Please enter upper bound";
    private static final String EXIT = "e";

    private int thread = 0;
    private int maxValue = 0;

    InitData() {
        init();
    }

    public int getThreadAmount() {
        return thread;
    }

    public int getMaxValue() {
        return maxValue;
    }

    private void init() {
        thread = input(ENTER_NUMBER_OF_THREAD);
        maxValue = input(ENTER_UPPER_BOUND);
    }

    private int input(String message) {
        String input = StringUtils.EMPTY;
        while (ObjectUtils.notEqual(EXIT, input)) {
            LOGGER.info(message);
            input = Util.readFromConsole();
            if (Util.isDigit(input)) {
                return Integer.parseInt(input);
            } else {
                LOGGER.error(INCORRECT_INPUT);
            }
        }
        throw new InvalidParameterException();
    }
}
