package org.java.logger;

import org.java.logger.config.LoggerConfig;
import org.java.logger.enums.LogLevel;
import org.java.logger.target.LogTarget;
import org.java.logger.target.impl.ApiLogTarget;
import org.java.logger.target.impl.ConsoleLogTarget;
import org.java.logger.target.impl.EmailLogTarget;
import org.java.logger.target.impl.FileLogTarget;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Logger {


    private static final String CONFIG_FILE_PATH = "config.properties";

    private static final String TARGETS_LIST_KEY = "targets";
    private static final String EMAILS_LIST_KEY = "target.email.list";
    private static final String APIS_LIST_KEY = "target.apis.list";
    private static final String LOG_FILE_NAME = "target.file.name";

    private static final Logger instance = new Logger();
    private final List<LogTarget> logTargets = new ArrayList<>();
    private LoggerConfig config = new LoggerConfig(LogLevel.DEBUG);
    private final Map<LogTarget, LogLevel> targetMinLevels = new HashMap<>();

    private Logger() {
        logTargets.add(new ConsoleLogTarget());

        targetMinLevels.put(new ConsoleLogTarget(), LogLevel.DEBUG);

        loadConfig();
    }

    private void loadConfig() {
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            final var configProperties = new Properties();
            configProperties.load(fileInputStream);

            final var targetsList = configProperties.getProperty(TARGETS_LIST_KEY);
            if (targetsList != null && targetsList.trim().length() > 0) {
                final var targets = targetsList.split(",");
                logTargets.clear();
                for (String target : targets) {
                    switch (target) {
                        case "ApiLogTarget" -> {
                            final var apisList = configProperties.getProperty(APIS_LIST_KEY);
                            logTargets.add(new ApiLogTarget(apisList.split(",")));
                        }
                        case "ConsoleLogTarget" -> logTargets.add(new ConsoleLogTarget());
                        case "EmailLogTarget" -> {
                            final var emailsList = configProperties.getProperty(EMAILS_LIST_KEY);
                            logTargets.add(new EmailLogTarget(emailsList.split(",")));
                        }
                        case "FileLogTarget" -> {
                            final var fileName = configProperties.getProperty(LOG_FILE_NAME);
                            logTargets.add(new FileLogTarget(fileName));
                        }
                        default -> {
                        }
                    }
                }
            }

            if (logTargets.isEmpty()) {
                logTargets.add(new ConsoleLogTarget());
            }

            for (LogTarget target : logTargets) {
                String targetName = target.getClass().getSimpleName();
                String minLogLevelStr = configProperties.getProperty(targetName);
                if (minLogLevelStr != null) {
                    LogLevel minLogLevel = LogLevel.valueOf(minLogLevelStr);
                    targetMinLevels.put(target, minLogLevel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        return instance;
    }

    public void addLogTarget(LogTarget target) {
        logTargets.add(target);
    }

    public void removeLogTarget(LogTarget target) {
        logTargets.remove(target);
    }

    public void setConfig(LoggerConfig config) {
        this.config = config;
    }

    public void addTargetMinLevel(LogTarget target, LogLevel minLogLevel) {
        targetMinLevels.put(target, minLogLevel);
    }

    public void removeTargetMinLevel(LogTarget target) {
        targetMinLevels.remove(target);
    }

    public void log(LogLevel level, String message) {
        if (!config.shouldLog(level)) {
            // System.err.println("Log level " + level + " is not accepted");
            return;
        }

        for (LogTarget target : logTargets) {
            LogLevel targetMinLevel = targetMinLevels.get(target);
            if (targetMinLevel != null && level.ordinal() < targetMinLevel.ordinal()) {
                continue;
            }
            target.log(level, message);
        }
    }
}
