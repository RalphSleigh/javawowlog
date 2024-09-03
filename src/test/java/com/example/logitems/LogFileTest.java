package com.example.logitems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class LogFileTest {

    @Test
    public void testStartEncounter() {
        LogFile logFile = new LogFile();
        logFile.startEncounter("Test", EncounterType.BOSS, LocalDateTime.now());
        assertEquals(2, logFile.encounters.size());
        assertEquals("Test", logFile.encounters.get(1).encounterName);
        assertEquals(EncounterType.BOSS, logFile.encounters.get(1).encounterType);
        assertTrue(logFile.encounters.get(1).active);
    }

    @Test
    public void testEndEncounter() {
        LogFile logFile = new LogFile();
        logFile.startEncounter("Test", EncounterType.BOSS, LocalDateTime.now());
        logFile.endEncounter(LocalDateTime.now());
        assertEquals(3, logFile.encounters.size());
        assertEquals("Trash", logFile.encounters.get(2).encounterName);
        assertEquals(EncounterType.TRASH, logFile.encounters.get(2).encounterType);
        assertTrue(logFile.encounters.get(2).active);
    }

}

