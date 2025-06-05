package com.tilldawn.Model;

public enum AbilityType {
    EXTRA_DAMAGE("Extra Damage", "افزایش قدرت تیر"),
    EXTRA_SPEED("Speed Boost", "افزایش سرعت حرکت"),
    HEAL("Heal", "بازگردانی مقداری HP"),
    IMMUNITY("Immunity", "مصونیت موقت"),
    MORE_BULLETS("More Bullets", "افزایش تعداد گلوله‌ها");

    private final String name;
    private final String description;

    AbilityType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
