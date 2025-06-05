package com.tilldawn.Control;

import com.tilldawn.Model.HeroType;

public class HeroController {
    private static HeroType heroSelected=HeroType.SHANA;
    public HeroController() {}

    public static void setHeroSelected(HeroType heroSelected) {
        HeroController.heroSelected = heroSelected;
    }

    public static HeroType getHeroSelected() {
        return heroSelected;
    }
}
