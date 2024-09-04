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
    HashMap<Integer, Spell> spells,
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
        var unitsWithoutOwners = encounter.units.values().stream().filter(u -> u.owner == null).toList();
        for (var unit : unitsWithoutOwners) {
            var units = encounter.units.values().stream().filter(u -> u.owner == unit.id || u.id == unit.id).toList();
            int totalDamage = units.stream().mapToInt(u -> u.damageDone.stream().filter(d -> d.target().friendly != u.friendly).mapToInt(DamageEvent::amount).sum()).sum();
            float dps = totalDamage / ((float) duration / 1000 );
            damageDone.put(unit.id, dps);
        }
        return damageDone;
    }
}
