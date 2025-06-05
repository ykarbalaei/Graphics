package com.tilldawn.Control;

import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.HeroType;

public class DurationMenuController {
    private static int time;


    public DurationMenuController() {}

    public void selectDuration(int minutes) {
        time = minutes;
        System.out.println("Duration selected: " + minutes + " minutes");
    }

    public static int getTime() {
        return time;
    }

}
