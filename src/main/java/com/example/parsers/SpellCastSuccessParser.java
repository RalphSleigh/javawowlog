package com.example.parsers;

import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;

public class SpellCastSuccessParser extends AbstractParser {
    @Override
    public void parseEvent(LogFile logFile, LogEvent event) {
        if(!event.getString(13).equals("0000000000000000")) {
            var encounter = logFile.currentEncounter;
            var caster = encounter.getUnitFromFields(event, 1);
            var ownerGuid = event.getString(13);
            caster.owner = ownerGuid;
        }
    }
}