package com.example.logitems;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Encounter {
    public String encounterName;
    public EncounterType encounterType;
    public boolean active;
    public LocalDateTime startTime = LocalDateTime.MIN;
    public LocalDateTime endTime = LocalDateTime.MAX;
    public HashMap<String, Unit> units = new HashMap<String, Unit>();
    public HashMap<String, Spell> spells = new HashMap<String, Spell>();
    public Encounter(String encounterName, EncounterType encounterType, boolean active, LocalDateTime startTime)  {
        this.encounterName = encounterName;
        this.encounterType = encounterType;
        this.active = active;
        this.startTime = startTime;
    }

    public Unit getUnitFromFields(LogEvent event, int offset) {
        String id = event.getString(offset);
        String name = event.getString(offset + 1);
        return units.computeIfAbsent(id, key -> new Unit(id, name));
    }

    public Spell getSpellFromFields(LogEvent event, int offset) {
        int id = event.getInt(offset);
        String name = event.getString(offset + 1);
        int school = event.getHex(offset + 2);
        return spells.computeIfAbsent(name, key -> new Spell(id, name, school));
    }
}