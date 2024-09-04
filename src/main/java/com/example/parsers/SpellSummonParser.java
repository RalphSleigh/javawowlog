package com.example.parsers;

import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;

public class SpellSummonParser extends AbstractParser {
    @Override
    public void parseEvent(LogFile logFile, LogEvent event) {
        var encounter = logFile.currentEncounter;
        var caster = encounter.getUnitFromFields(event, 5);
        var ownerGuid = event.getString(1);
        caster.owner = ownerGuid;
    }
}