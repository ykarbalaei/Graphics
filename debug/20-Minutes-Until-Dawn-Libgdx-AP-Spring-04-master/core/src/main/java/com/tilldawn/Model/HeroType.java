package com.tilldawn.Model;

public enum HeroType {
    SHANA("Shana", "Balanced speed and health", "heroes/witch.png", 4, 4),
    DIAMOND("Diamond", "High speed, low health", "heroes/witch.png", 7, 1),
    SCARLET("Scarlet", "Low speed, high health", "heroes/witch.png", 3, 5),
    LILITH("Lilith", "Moderate speed and health", "heroes/witch.png", 5, 3),
    DASHER("Dasher", "Very high speed, very low health", "heroes/witch.png", 2, 1);

    private final String displayName;
    private final String description;
    private final String texturePath;
    private final int speed;
    private final int hp;

    HeroType(String displayName, String description, String texturePath, int speed, int hp) {
        this.displayName = displayName;
        this.description = description;
        this.texturePath = texturePath;
        this.speed = speed;
        this.hp = hp;

    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
