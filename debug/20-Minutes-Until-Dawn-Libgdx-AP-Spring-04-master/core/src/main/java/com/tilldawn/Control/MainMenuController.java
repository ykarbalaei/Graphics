package com.tilldawn.Control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.*;

public class MainMenuController {
    private MainMenuView view;
    private final Main game;
    private final UserManager userManager;
    private final GameSettings settings;
    private Stage stage;

    public MainMenuController(Main game, UserManager um) {
        this.game = game;
        this.userManager = um;
        this.settings = new GameSettings();

    }

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        view.getSettingsButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                SettingsManager settings = SettingsManager.getInstance();;
                game.setScreen(new SettingsScreen(game, userManager, settings));
            }
        });
        view.getProfileButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new ProfileScreen(game, userManager));
            }
        });
        view.getPregameButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new PreGameMenuView(game,
                    settings,
                    GameAssetManager.getInstance().getSkin(),userManager));
            }
        });
        view.getScoreboardButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new ScoreboardView(
                    GameAssetManager.getInstance().getSkin(),
                    userManager,game
                ));
            }
        });
        view.getTalentButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new TalentHintView(
                    game,
                    userManager,
                    settings,
                    GameAssetManager.getInstance().getSkin()
                ));
            }
        });
        view.getContinueButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                System.out.println("Continue");
                System.out.println("Save: " + SaveManager.hasSave());

                try {
//                if (!view.getContinueButton().isDisabled()) {
                    System.out.println("in this if");
                    if (SaveManager.hasSave()) {
                        SaveData data = SaveManager.loadGame();
                        if (data == null) {
                            System.out.println("No save file found.");
                            return;
                        }
                        // یا هرجایی که نگه‌داریش می‌کنی
                        GameSettings settings = new GameSettings();
                        GameController controller = new GameController(settings, userManager, game);
                        GameView gameView = new GameView(controller, GameAssetManager.getInstance().getSkin(), settings);
                        gameView.show();
                        // مقداردهی به بازیکن از روی save
                        Player p = controller.getPlayerController().getPlayer();

                        p.setXp(data.xp);
                        p.setLevel(data.level);
                        p.setHP(data.hp);
                        p.setKills(data.kills);
                        p.setLives(data.lives);
                        p.getPosition().set(data.playerX, data.playerY);
                        p.setAbilities(data.abilities);

                        System.out.println("Game is creating...");
                        // زمان و سایر حالت‌ها
                        TimerController.init(data.remainingTime);
                        controller.getWeaponController().setInfiniteAmmo(data.infiniteAmmo);

                        game.setScreen(new GameView(controller, GameAssetManager.getInstance().getSkin(), settings));
                    } else {
                        System.out.println("save failed");
                        Stage tempStage = new Stage(new ScreenViewport());
                        Dialog dialog = new Dialog("Error", GameAssetManager.getInstance().getSkin());
                        dialog.text("No saved game found.");
                        dialog.button("OK");
                        dialog.show(tempStage);
                    }
                }catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
//            }
        });

        view.getLogoutButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                try {
                    userManager.logout();
                    game.setScreen(new LoginScreen(game, userManager));
                }catch(Exception e1) {
                    System.out.println("logout failed"+e1.getMessage());
                }
//            game.setScreen(new LoginScreen(game, userManager));
            }
        });
        view.getExitButton().addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
}
