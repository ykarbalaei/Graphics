package com.tilldawn.Control;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.*;

public class MainMenuController {
    private MainMenuView view;
    private final Main game;
    private final UserManager userManager;
    private final GameSettings settings;

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
            public void clicked(InputEvent e, float x, float y) {
                if (!view.getContinueButton().isDisabled()) {
//                GameState state = SaveManager.load();
//                game.setScreen(new GameScreen(game, state));
                }
            }
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
