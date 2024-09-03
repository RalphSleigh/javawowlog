package com.example.logitems;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogFile {
    public ArrayList<Encounter> encounters = new ArrayList<Encounter>();
    public Encounter currentEncounter;

    public LogFile() {
        currentEncounter = new Encounter("Trash", EncounterType.TRASH, true, LocalDateTime.MIN);
        encounters.add(currentEncounter);
    }

    public void startEncounter(String encounterName, EncounterType encounterType, LocalDateTime startTime) {
        currentEncounter.active = false;
        currentEncounter = new Encounter(encounterName, encounterType, true, startTime);
        encounters.add(currentEncounter);
    }

    public void endEncounter(LocalDateTime endTime) {
        currentEncounter.active = false;
        currentEncounter.endTime = endTime;
        currentEncounter = new Encounter("Trash", EncounterType.TRASH, true, endTime);
        encounters.add(currentEncounter);
    }
}
