package org.java.logger.target;

import org.java.logger.enums.LogLevel;

public interface LogTarget {
    void log(LogLevel level, String message);
}
