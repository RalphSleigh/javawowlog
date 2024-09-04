package com.example.api;

import com.example.logitems.Encounter;
import com.example.logitems.EncounterType;

public record EncounterListItem(int index, String name, EncounterType type) {
    public EncounterListItem(int index, Encounter encounter) {
        this(index, encounter.encounterName, encounter.encounterType);
    }
}
