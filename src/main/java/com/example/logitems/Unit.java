package com.example.logitems;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    public String id;
    public String name;
    public List<DamageEvent> damageDone = new ArrayList<DamageEvent>();
    public List<DamageEvent> damageTaken = new ArrayList<DamageEvent>();

    Unit(String id, String name) {
        this.id = id;
        this.name = name;
    }
}