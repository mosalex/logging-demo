package org.java.logger;

import org.java.logger.config.LoggerConfig;
import org.java.logger.enums.LogLevel;
import org.java.logger.target.impl.ConsoleLogTarget;
import org.java.logger.target.impl.EmailLogTarget;
import org.java.logger.target.impl.FileLogTarget;

public class Main {

    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        logger.log(LogLevel.DEBUG, "Debug message");
        logger.log(LogLevel.INFO, "Info message");
        logger.log(LogLevel.WARNING, "Warning message");
        logger.log(LogLevel.ERROR, "Error message");

        // Add additional log targets
        logger.addLogTarget(new EmailLogTarget("test1@test.ro", "test2@test.ro"));
        logger.addLogTarget(new FileLogTarget("log.log"));

        // Change log configuration
        LoggerConfig config = new LoggerConfig(LogLevel.WARNING);
        logger.setConfig(config);

        logger.log(LogLevel.DEBUG, "Debug message");
        logger.log(LogLevel.WARNING, "Warning message");
        logger.log(LogLevel.ERROR, "Error message");

        logger.addTargetMinLevel(new ConsoleLogTarget(), LogLevel.DEBUG); // Set minimum log level for console target
        logger.addTargetMinLevel(new FileLogTarget("log.log"), LogLevel.WARNING); // Set minimum log level for file target

        // testing the config.properties configuration
        Logger logger2 = Logger.getInstance();

        logger2.log(LogLevel.DEBUG, "Debug message 2");
        logger2.log(LogLevel.INFO, "Info message 2");
        logger2.log(LogLevel.WARNING, "Warning message 2");
        logger2.log(LogLevel.ERROR, "Error message 2");
    }
}
