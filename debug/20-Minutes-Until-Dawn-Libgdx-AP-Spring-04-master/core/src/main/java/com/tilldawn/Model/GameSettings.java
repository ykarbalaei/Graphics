package com.tilldawn.Model;

import com.tilldawn.Control.PreGameMenuController;

import java.util.ArrayList;
import java.util.List;

public class GameSettings {
    private Hero selectedHero;
    private Weapon selectedWeapon;
    private int durationMinutes;
    private boolean blackAndWhiteMode;
    private final List<Ability> acquiredAbilities = new ArrayList<>();
    private final List<String> cheatCodes = new ArrayList<>();
    private final List<String> keyBindings = new ArrayList<>();


    public GameSettings() {
        keyBindings.add("W - Move Up");
        keyBindings.add("A - Move Left");
        keyBindings.add("S - Move Down");
        keyBindings.add("D - Move Right");

        cheatCodes.add("IDDQD");
        cheatCodes.add("IDKFA");

        acquiredAbilities.add(new Ability("Fireball", "Shoots a fiery projectile"));
        acquiredAbilities.add(new Ability("Dash", "Quickly moves forward"));
    }


    // --- Hero ---
    public Hero getSelectedHero() {
        return selectedHero;
    }
    public void setSelectedHero(Hero selectedHero) {
        this.selectedHero = selectedHero;
    }

    // --- Weapon ---
    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }
    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    // --- Duration ---
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    // --- Graphics Mode ---
    public boolean isBlackAndWhiteMode() {
        return blackAndWhiteMode;
    }
    public void setBlackAndWhiteMode(boolean blackAndWhiteMode) {
        this.blackAndWhiteMode = blackAndWhiteMode;
    }

    // --- Abilities & Cheats (برای منوی Pause) ---
    public List<Ability> getAcquiredAbilities() {
        return acquiredAbilities;
    }
    public void addAbility(Ability a) {
        acquiredAbilities.add(a);
    }
    public List<String> getCheatCodes() {
        return cheatCodes;
    }
    public void addCheatCode(String code) {
        cheatCodes.add(code);
    }

    /** برمی‌گرداند لیست کلیدهای فعال در تنظیمات بازی */
    public List<String> getKeyBindings() {
        return keyBindings;
    }

    /** افزودن یک کلید جدید به تنظیمات */
    public void addKeyBinding(String key) {
        keyBindings.add(key);
    }

}
