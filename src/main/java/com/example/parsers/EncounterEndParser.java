package com.example.parsers;

import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;

public class EncounterEndParser extends AbstractParser {
    public void parseEvent(LogFile logFile, LogEvent event) {
        logFile.endEncounter(event.getTimestamp());
    }
}
