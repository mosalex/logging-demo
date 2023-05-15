package org.java.logger.target.impl;

import org.java.logger.enums.LogLevel;
import org.java.logger.target.LogTarget;

import java.util.Arrays;

public class EmailLogTarget implements LogTarget {

    private final String[] emailList;

    public EmailLogTarget(String... emailList) {
        this.emailList = emailList;
    }

    @Override
    public void log(LogLevel level, String message) {
        System.out.print("Sending emails to: ");
        Arrays.stream(emailList).forEach(System.out::print);
        System.out.print(" with: ");
        System.out.println("[" + level.toString() + "] " + message);
    }
}
