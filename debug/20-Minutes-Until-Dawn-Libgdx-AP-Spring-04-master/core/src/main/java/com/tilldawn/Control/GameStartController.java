package com.tilldawn.Control;

import com.tilldawn.Model.GameSettings;

public class GameStartController {
    private final GameSettings settings;

    public GameStartController(GameSettings settings) {
        this.settings = settings;
    }

    public void startGame() {

        if (settings.getSelectedHero() == null ||
            settings.getSelectedWeapon() == null ||
            settings.getDurationMinutes() <= 0) {
            throw new IllegalStateException("GameSettings not fully configured!");
        }
        System.out.println(">>> Launching game with: " + settings);
        // مثلاً: GameLoop loop = new GameLoop(settings); loop.run();
    }
}
