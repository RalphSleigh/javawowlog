package com.example.parsers;

import com.example.logitems.EncounterType;
import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;

public class EncounterStartParser extends AbstractParser {
    public void parseEvent(LogFile logFile, LogEvent event) {
        var startTime = event.getTimestamp();
        logFile.startEncounter(event.getString(2), EncounterType.BOSS, startTime);
    }
}