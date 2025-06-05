package com.tilldawn.Control;

import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Ability;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.HeroType;

public class TalentMenuController {
    private final GameSettings settings;

    public TalentMenuController(GameSettings settings) {
        this.settings = settings;
    }

    /** برمی‌گرداند لیستی از نام و توضیح حداقل ۳ هیرو برای بخش راهنما */
    public Array<String> getHeroGuides() {
        Array<String> guides = new Array<>();
        for (HeroType ht : HeroType.values()) {
            guides.add(ht.getDisplayName() + ": " + ht.getDescription());
            if (guides.size >= 3) break;
        }
        return guides;
    }

    /** کلیدهایی که کاربر در تنظیمات بازی از آنها استفاده می‌کند */
    public Array<String> getActiveKeys() {
        Array<String> keys = new Array<>();
        for (String key : settings.getKeyBindings()) {
            keys.add(key);
        }
        return keys;
    }


    /** لیست کدهای تقلب و توضیح کارکردشان */
    public Array<String> getCheats() {
        Array<String> cheats = new Array<>();
        for (String code : settings.getCheatCodes()) {
            cheats.add(code + " → " + lookupCheatEffect(code));
        }
        return cheats;
    }

    private String lookupCheatEffect(String code) {
        switch (code) {
            case "IDDQD": return "God Mode";
            case "IDKFA": return "All Weapons";
            default:      return "Unknown Effect";
        }
    }

    /** توانایی‌های به‌دست‌آمده و توضیح کارکردشان */
    public Array<String> getAbilities() {
        Array<String> abs = new Array<>();
        for (Ability a : settings.getAcquiredAbilities()) {
            abs.add(a.getName() + ": " + a.getEffect());
        }
        return abs;
    }
}
