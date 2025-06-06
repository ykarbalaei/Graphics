package com.tilldawn.Control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.*;

public class TalentMenuController {
    private final GameSettings settings;
    private SettingsManager settingsManager=SettingsManager.getInstance();

    public TalentMenuController(GameSettings settings) {
        this.settings = settings;
    }


    public Array<String> getHeroGuides() {
        Array<String> guides = new Array<>();
        for (HeroType ht : HeroType.values()) {
            guides.add(ht.getDisplayName() + ": " + ht.getDescription());
            if (guides.size >= 3) break;
        }
        return guides;
    }


        public Array<String> getActiveKeys() {
        Array<String> keys = new Array<>();
        keys.add( "Up: " + Input.Keys.toString(settingsManager.getKeyUp()));
        keys.add( "Down: " + Input.Keys.toString(settingsManager.getKeyDown()));
        keys.add( "Left: " + Input.Keys.toString(settingsManager.getKeyLeft()));
        keys.add( "Right: " + Input.Keys.toString(settingsManager.getKeyRight()));
            return keys;
        }




    public Array<String> getCheats() {
        Array<String> cheats = new Array<>();
            cheats.add("H: +10 HP");
            cheats.add("L: +1 Life");
            cheats.add("K: Kill all enemies");
            cheats.add("X: +10 XP");
            cheats.add("G: Infinite Ammo");

        return cheats;
    }

//    private String lookupCheatEffect(String code) {
//        switch (code.toUpperCase()) {
//            case "H": return "+10 HP";
//            case "L": return "+1 Life";
//            case "X": return "+10 XP";
//            case "K": return "Kill All Enemies";
//            case "G": return "Infinite Ammo";
//            default:  return "Unknown Effect";
//        }
//    }

    public Array<String> getAbilities() {
        Array<String> guides = new Array<>();
        for (AbilityType ht : AbilityType.values()) {
            guides.add(ht.getName() + ": " + ht.getDescription());
        }
        return guides;
    }
}
