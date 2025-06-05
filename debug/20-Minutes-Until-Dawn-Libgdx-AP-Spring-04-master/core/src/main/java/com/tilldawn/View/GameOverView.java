package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.*;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.UserManager;

public class GameOverView {
    private static Stage stage;
    private final Label title;
    private static Label scoreLabel, timeLabel, killsLabel, nameLabel;
    private static TextButton retryButton, mainMenuButton;
    private static Skin skin;
    private static UserManager userManager;
    private  final Main game;

    private final GameSettings settings;




    public GameOverView(Stage stage,GameSettings settings,Main game,Skin skin, UserManager userManager) {
        this.game = game;
        this.settings = settings;
        this.skin = skin;
        this.userManager = userManager;
        this.title = new Label("!GameOver!", skin);
        this.scoreLabel = new Label("Score: 0", skin);
        this.timeLabel = new Label("Time: 0", skin);
        this.killsLabel = new Label("Kills: 0", skin);
//        this.retryButton = new TextButton("Retry", skin);
//        this.mainMenuButton = new TextButton("Main Menu", skin);
    }

    public  void show(Player player, int score, float time, int kills) {

        if(time==DurationMenuController.getTime()*60){
            title.setText("!You Win!");
        }
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        nameLabel = new Label("Name: " + player.getUsername(), skin);
        scoreLabel = new Label("Score: " + score, skin);
        timeLabel = new Label("Time: " + (int) time + "s", skin);
        killsLabel = new Label("Kills: " + kills, skin);

        retryButton = new TextButton("Try Again", skin);
        mainMenuButton = new TextButton("Main Menu", skin);

        retryButton.addListener(event -> {
            if (retryButton.isPressed()) {
                restartGame(); // باید این متد رو بنویسی
            }
            return true;
        });

        mainMenuButton.addListener(event -> {
            if (mainMenuButton.isPressed()) {
               goToMainMenu(); // باید این متد رو بنویسی
            }
            return true;
        });
        table.add(title).pad(10).row();
        table.add(nameLabel).pad(10).row();
        table.add(scoreLabel).pad(10).row();
        table.add(timeLabel).pad(10).row();
        table.add(killsLabel).pad(10).row();
        table.add(retryButton).pad(20).row();
        table.add(mainMenuButton).pad(10);

    }

    public static void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public static void dispose() {
        stage.dispose();
    }

    private void restartGame() {
        int period=0;
        period=DurationMenuController.getTime();
        GameAssetManager.getInstance().loadTextures();
        TimerController.init(period*60);
        System.out.println("Timer started"+period);
        GameController gameController = new GameController(settings,userManager,game);
        game.setScreen(new GameView(
            gameController,
            skin,
            settings
        ));
        // کلاس اصلی بازی رو ریست کن، مثل:
         //MainGame.setScreen(new GameScreen());
    }

    private void goToMainMenu() {
        MainMenuController mainMenuController = new MainMenuController(game, userManager);
        Skin skin = GameAssetManager.getInstance().getSkin();
        game.setScreen(new MainMenuView(
            mainMenuController,
            skin,
            userManager.getCurrentUser(),
            false // hasSave
        ));

    }
}
