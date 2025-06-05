package com.tilldawn.Model;

public class Ability {
    private final String name;
    private final String effect;

    public Ability(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name + ": " + effect;
    }
}
