package org.java.logger.config;

import org.java.logger.enums.LogLevel;

import java.util.EnumSet;

public class LoggerConfig {

    private LogLevel minLogLevel;
    private EnumSet<LogLevel> acceptedLogLevels;

    public LoggerConfig(LogLevel minLogLevel) {
        this.minLogLevel = minLogLevel;
        this.acceptedLogLevels = EnumSet.range(minLogLevel, LogLevel.ERROR);
    }

    public LogLevel getMinLogLevel() {
        return minLogLevel;
    }

    public void setMinLogLevel(LogLevel minLogLevel) {
        this.minLogLevel = minLogLevel;
        this.acceptedLogLevels = EnumSet.range(minLogLevel, LogLevel.ERROR);
    }

    public boolean shouldLog(LogLevel level) {
        return acceptedLogLevels.contains(level);
    }
}
