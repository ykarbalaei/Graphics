package com.tilldawn.Control;

import com.tilldawn.Model.HeroType;
import com.tilldawn.Model.WeaponType;

public class WeaponMenuController {
    private static WeaponType weaponSelected= WeaponType.SHOTGUN;
    public WeaponMenuController() {}

    public static void setWeaponSelected(WeaponType weaponSelected) {
        WeaponMenuController.weaponSelected=weaponSelected;
    }

    public static WeaponType getWeaponSelected() {
        return weaponSelected;
    }
}
