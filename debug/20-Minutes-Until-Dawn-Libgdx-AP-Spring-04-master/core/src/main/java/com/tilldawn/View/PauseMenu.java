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
import com.tilldawn.Model.*;

public class PauseMenu extends Window {
    Stage stage;
    public PauseMenu(GameView gameView,Stage stage) {
        super("Game Paused", GameAssetManager.getInstance().getSkin());

        setMovable(false);
        this.stage=stage;

        TextButton resumeButton = new TextButton("Resume", getSkin());
//        TextButton settingsButton = new TextButton("Settings", getSkin());
        TextButton mainMenuButton = new TextButton("Main Menu", getSkin());

        add(resumeButton).pad(10).row();
//        add(settingsButton).pad(10).row();
        add(mainMenuButton).pad(10).row();

        pack();
        setSize(600, 900);
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
                Dialog dialog = new Dialog("Cheat Codes", GameAssetManager.getInstance().getSkin()){
                    @Override
                    protected void result(Object object) {
                        // مثلاً بعد از زدن OK
                    }
                };
//                dialog.text("H: +10 HP\nL: +1 Life\nK: Kill all enemies\nX: +10 XP\nG: Infinite Ammo");
//                dialog.button("OK");
//                dialog.setSize(500,500);
//                dialog.setPosition(
//                    (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
//                    (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
//                );
                Label cheatLabel = new Label("H: +10 HP\nL: +1 Life\nK: Kill all enemies\nX: +10 XP\nG: Infinite Ammo",
                    GameAssetManager.getInstance().getSkin());
                cheatLabel.setWrap(true);
                cheatLabel.setWidth(400); // عرض دلخواه

                dialog.getContentTable().add(cheatLabel).width(400).height(600).pad(20).row();
                dialog.button("OK");

                dialog.pack(); // حالا اندازه درست تنظیم میشه
                dialog.setPosition(
                    (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
                    (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
                );
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
                String content = sb.toString().isEmpty() ? "No abilities yet!" : sb.toString();
                Label label = new Label(sb.toString(), GameAssetManager.getInstance().getSkin());
                label.setWrap(true);
                dialog.getContentTable().add(label).width(400).height(600).pad(20).row();
                dialog.button("Close");
                dialog.setSize(500,500);
                dialog.setPosition(
                    (Gdx.graphics.getWidth() - dialog.getWidth()) / 2f,
                    (Gdx.graphics.getHeight() - dialog.getHeight()) / 2f
                );
                dialog.show(stage);
            }
        });
        add(abilitiesButton).pad(10).row();

        TextButton saveButton = new TextButton("Save Game", GameAssetManager.getInstance().getSkin());
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player p = gameView.getController().getPlayerController().getPlayer();

                SaveData data = new SaveData();
                data.username = p.getUsername();
                data.xp = p.getXp();
                data.level = p.getLevel();
                data.hp = p.getHP();
                data.kills = p.getKills();
                data.lives = p.getLives();
                data.playerX = p.getX();
                data.playerY = p.getY();
                data.abilities = p.getAbilities();
                data.remainingTime = TimerController.getInstance().getTimeRemaining();
                data.infiniteAmmo = gameView.getController().getWeaponController().isInfiniteAmmo();

                SaveManager.saveGame(data);

                Dialog dialog = new Dialog("Save", GameAssetManager.getInstance().getSkin());
                dialog.text("Game Saved!");
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
