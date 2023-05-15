package org.java.logger.target.impl;

import org.java.logger.enums.LogLevel;
import org.java.logger.target.LogTarget;

import java.util.Arrays;

public class ApiLogTarget implements LogTarget {

    private final String[] apisList;

    public ApiLogTarget(String... apisList) {
        this.apisList = apisList;
    }

    @Override
    public void log(LogLevel level, String message) {
        System.out.println("Sending calls to: ");
        Arrays.stream(apisList).forEach(System.out::println);
        System.out.print("with: ");
        System.out.println("[" + level.toString() + "] " + message);
    }
}
