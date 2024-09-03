package com.example.parsers;

import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;

public abstract class AbstractParser {
    public void parseEvent(LogFile logFile, LogEvent event) {}
}
