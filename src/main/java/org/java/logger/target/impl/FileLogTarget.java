package org.java.logger.target.impl;

import org.java.logger.enums.LogLevel;
import org.java.logger.target.LogTarget;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogTarget implements LogTarget {

    private final String logFileName;

    public FileLogTarget(final String logFileName) {
        this.logFileName = logFileName;
    }

    @Override
    public void log(LogLevel level, String message) {
        String logLine = "[" + level.toString() + "] " + message + System.lineSeparator();
        try (FileWriter fileWriter = new FileWriter(logFileName, true)){
            fileWriter.write(logLine);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
