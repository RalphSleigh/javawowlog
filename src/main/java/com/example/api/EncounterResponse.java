package com.example.api;

import java.time.Duration;
import java.util.HashMap;

import com.example.logitems.DamageEvent;
import com.example.logitems.Encounter;
import com.example.logitems.Spell;
import com.example.logitems.Unit;

public record EncounterResponse(
    String name,
    HashMap<String, Unit> units,
    HashMap<String, Spell> spells,
    HashMap<String, Float> damageDone,
    Duration duration
) {
    public EncounterResponse(Encounter encounter) {
        this(
            encounter.encounterName,
            encounter.units,
            encounter.spells,
            calculateDamageDone(encounter),
            Duration.between(encounter.startTime, encounter.endTime)
        );
    }

    private static HashMap<String, Float> calculateDamageDone(Encounter encounter) {
        var damageDone = new HashMap<String, Float>();
        var duration = Duration.between(encounter.startTime, encounter.endTime).toMillis();
        for (var unit : encounter.units.values()) {
            int totalDamage = unit.damageDone.stream().mapToInt(DamageEvent::amount).sum();
            float dps = totalDamage / ((float) duration / 1000 );
            damageDone.put(unit.id, dps);
        }
        return damageDone;
    }
}
