package com.example.api;

import java.util.HashMap;

import com.example.logitems.Encounter;

public class UnitResponse {
    public String id;
    public String name;
    public boolean friendly;
    public String owner;
    public HashMap<Integer, Float> damageBySpell = new HashMap<Integer, Float>();

    public UnitResponse(Encounter encounter, String unitId) {
        var unit = encounter.units.get(unitId);
        this.id = unit.id;
        this.name = unit.name;
        this.friendly = unit.friendly;
        this.owner = unit.owner;

        unit.damageDone.forEach(d -> {
            damageBySpell.merge(d.spell().id(), (float)d.amount(), (oldValue, newValue) -> oldValue + newValue);
        });
    }
}