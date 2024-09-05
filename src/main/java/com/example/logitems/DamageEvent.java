package com.example.logitems;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonBackReference;

public record DamageEvent(@JsonBackReference Unit source, @JsonBackReference Unit target, Spell spell, int amount, Duration timestamp){
}
