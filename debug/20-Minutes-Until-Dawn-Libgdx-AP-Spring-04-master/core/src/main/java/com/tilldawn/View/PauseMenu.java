// com.tilldawn.View.PauseMenu.java
package com.tilldawn.View;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.TimerController;
import com.tilldawn.Main;
import com.tilldawn.Model.AbilityType;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.SettingsManager;
import com.tilldawn.Model.UserManager;

public class PauseMenu extends Window {
    Stage stage;
    public PauseMenu(GameView gameView,Stage stage) {
        super("Game Paused", GameAssetManager.getInstance().getSkin());
        setMovable(false);
        this.stage=stage;

        TextButton resumeButton = new TextButton("Resume", getSkin());
        TextButton settingsButton = new TextButton("Settings", getSkin());
        TextButton mainMenuButton = new TextButton("Main Menu", getSkin());

        add(resumeButton).pad(10).row();
        add(settingsButton).pad(10).row();
        add(mainMenuButton).pad(10).row();

        pack();
        setPosition(
            (Gdx.graphics.getWidth() - getWidth()) / 2f,
            (Gdx.graphics.getHeight() - getHeight()) / 2f
        );

        resumeButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                TimerController.getInstance().resume();
                remove(); // حذف منو از stage
            }
        });

        final CheckBox grayscaleCheckBox = new CheckBox("Grayscale Mode", GameAssetManager.getInstance().getSkin());
        grayscaleCheckBox.setChecked(SettingsManager.getInstance().isGrayscaleEnabled());
        grayscaleCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                boolean enabled = grayscaleCheckBox.isChecked();
                SettingsManager.getInstance().setGrayscaleEnabled(enabled);
                SettingsManager.getInstance().save();
            }
        });
        add(grayscaleCheckBox).pad(10).row();

        TextButton cheatButton = new TextButton("Cheat Codes", GameAssetManager.getInstance().getSkin());
        cheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Cheat Codes", GameAssetManager.getInstance().getSkin());
                dialog.text("H: +10 HP\nL: +1 Life\nK: Kill all enemies\nX: +10 XP\nG: Infinite Ammo");
                dialog.button("OK");
                dialog.show(stage);
            }
        });
        add(cheatButton).pad(10).row();

        TextButton abilitiesButton = new TextButton("Abilities", GameAssetManager.getInstance().getSkin());
        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("Your Abilities", GameAssetManager.getInstance().getSkin());
                StringBuilder sb = new StringBuilder();
                for (AbilityType ability : gameView.getController().getPlayerController().getPlayer().getAbilities()) {
                    sb.append("- ").append(ability.getName()).append("\n");
                }
                dialog.text(sb.toString().isEmpty() ? "No abilities yet!" : sb.toString());
                dialog.button("Close");
                dialog.show(stage);
            }
        });
        add(abilitiesButton).pad(10).row();

        TextButton saveButton = new TextButton("Save Game", GameAssetManager.getInstance().getSkin());
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // بعداً اینجا پیاده‌سازی ذخیره بازی میاد
                Dialog dialog = new Dialog("Save", GameAssetManager.getInstance().getSkin());
                dialog.text("Save functionality coming soon...");
                dialog.button("OK");
                dialog.show(stage);
            }
        });
        add(saveButton).pad(10).row();

//        settingsButton.addListener(new ClickListener() {
//            @Override public void clicked(InputEvent event, float x, float y) {
//                GameView.setLastGameView(gameView);
//                Main game = gameView.getController().getGame();
//                game.setScreen(new SettingsScreen(game, gameView.getController().getUserManager(), SettingsManager.getInstance()));
//            }
//        });

        mainMenuButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                Main game = gameView.getController().getGame();
                UserManager userManager = gameView.getController().getUserManager();
                MainMenuController mainMenuController = new MainMenuController(game, userManager);
                game.setScreen(new MainMenuView(mainMenuController, getSkin(), userManager.getCurrentUser(), false));
            }
        });
    }
}
