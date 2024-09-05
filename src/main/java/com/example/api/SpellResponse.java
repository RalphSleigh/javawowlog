package com.example.api;

import java.time.Duration;
import java.util.List;

import com.example.logitems.Unit;

public class SpellResponse {
    public Duration timestamp;
    public int amount;

    SpellResponse(Duration timestamp, int amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public static List<SpellResponse> fromUnit(Unit unit, int spellId) {
        return unit.damageDone.stream()
        .filter(d -> d.spell().id() == spellId)
        .map(d -> new SpellResponse(d.timestamp(), d.amount())).toList();
    }
}
