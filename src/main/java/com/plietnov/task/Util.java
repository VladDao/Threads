package com.plietnov.task;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {

    private static final Logger LOGGER = Logger.getLogger(Util.class);
    private static final String REGEX = "\\d+";

    private Util() {
    }

    public static String readFromConsole() {
        BufferedReader bf = new BufferedReader(
                new InputStreamReader(System.in));
        String tmpReader = StringUtils.EMPTY;
        try {
            tmpReader = bf.readLine().trim();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return tmpReader;
    }

    public static boolean isDigit(String input) {
        return input.matches(REGEX);
    }
}
