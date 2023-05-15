package org.java.logger.target.impl;

import org.java.logger.enums.LogLevel;
import org.java.logger.target.LogTarget;

public class ConsoleLogTarget implements LogTarget {

    @Override
    public void log(LogLevel level, String message) {
        System.out.println("[" + level.toString() + "] " + message);
    }
}
