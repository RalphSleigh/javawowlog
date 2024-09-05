package com.example.parsers;

import java.time.Duration;

import com.example.logitems.DamageEvent;
import com.example.logitems.LogEvent;
import com.example.logitems.LogFile;

public class SpellDamageParser extends AbstractParser {
    public void parseEvent(LogFile logFile, LogEvent event) {
        var source = logFile.currentEncounter.getUnitFromFields(event, 1);
        var target = logFile.currentEncounter.getUnitFromFields(event, 5);
        var spell =  logFile.currentEncounter.getSpellFromFields(event, 9);
        var amount = event.getInt(29);
        var timestamp = Duration.between(logFile.currentEncounter.startTime, event.getTimestamp());
        var damageEvent = new DamageEvent(source, target, spell, amount, timestamp);
        source.damageDone.add(damageEvent);
        target.damageTaken.add(damageEvent);
    }
}
