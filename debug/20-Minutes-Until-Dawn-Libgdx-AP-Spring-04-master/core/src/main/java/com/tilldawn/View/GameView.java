package com.tilldawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tilldawn.Control.DurationMenuController;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.TimerController;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;


public class GameView implements Screen, InputProcessor {
    private static Stage stage;
    private GameController controller;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private final GameSettings settings;
    private Label liveLabel;
    private Label hpLabel;
    private Label xpLabel;
    private Label levelLabel;
    private Label killsLabel;
    private Skin skin;
    private Label timerLabel;
    private Table hudTable;
    private ProgressBar xpBar;
    public static GameView lastGameView = null;

    private static boolean selectingAbility = false;
    private static List<AbilityType> offeredAbilities = new ArrayList<>();
    private static Window abilityWindow;
    private ShaderProgram grayscaleShader;
    private boolean grayscaleEnabled;



    public GameView(GameController controller, Skin skin, GameSettings settings) {
        gameCamera = new OrthographicCamera(); // اول دوربین را بسازید
        gameViewport = new FitViewport(2000, 1000, gameCamera);//تنظیم اندازه ی صفحات
        this.controller = controller;
        this.settings = settings;
        this.skin = skin;
        controller.setView(this,settings);
    }

    public Skin getSkin() {
        return skin;
    }


    public OrthographicCamera getGameCamera() {
        return this.gameCamera;
    }
    @Override
    public void show() {
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("SHADER", "Grayscale shader compile error:\n" + grayscaleShader.getLog());
        }
        stage = new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage); // برای کلیک روی دکمه‌های UI
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);


        // ساخت HUD
        timerLabel = new Label("00:00", GameAssetManager.getInstance().getSkin());

        hudTable = new Table();
        hudTable.top().right(); // تایمر بالا سمت راست قرار بگیره
        hudTable.setFillParent(true);
        hudTable.add(timerLabel).pad(10);

        stage.addActor(hudTable);



        if (controller != null && controller.getPlayerController() != null) {
            gameCamera.position.set(
                controller.getPlayerController().getPlayer().getX(),
                controller.getPlayerController().getPlayer().getY(),
                0
            );
        } else {
            gameCamera.position.set(gameViewport.getWorldWidth()/2, gameViewport.getWorldHeight()/2, 0);
        }

        Table table = new Table();
        table.top().left(); // گوشه بالا چپ
        table.setFillParent(true);

        liveLabel=new Label("lives:", skin);
        hpLabel = new Label("HP:", skin);
        xpLabel = new Label("XP:", skin);
        levelLabel = new Label("Level: 1", skin);
        killsLabel = new Label("Kills:", skin);

        table.add(liveLabel).pad(10);
        table.row();
        table.add(hpLabel).pad(10);
        table.row();
        table.add(xpLabel).pad(10);
        table.row();
        table.add(levelLabel).pad(10);
        table.row();
        table.add(killsLabel).pad(10);

        xpBar = new ProgressBar(0, 1, 0.01f, false, skin);
        xpBar.setAnimateDuration(0.25f);
        xpBar.setWidth(150);
        xpBar.setHeight(20);

        table.add(xpLabel).pad(10);
        table.row();
        table.add(xpBar).pad(10);


        stage.addActor(table);

        gameCamera.update();
    }

    public Stage getStage() {
        return stage;
    }


    @Override
    public void render(float delta) {
        if (TimerController.getInstance().isPaused()) {
            stage.act(delta);
            stage.draw(); // فقط UI رو نمایش بده
            return; // بقیه بازی (enemy, player, weapon...) آپدیت نشن
        }
        if (controller != null ) {
            controller.updateGame(delta);

        }
        if (controller != null && controller.getPlayerController() != null) {
            gameCamera.position.set(
                controller.getPlayerController().getPlayer().getX(),
                controller.getPlayerController().getPlayer().getY(),
                0
            );
        }
        gameCamera.update();

        ScreenUtils.clear(0, 0, 0, 1);
        boolean grayscaleEnabled = SettingsManager.getInstance().isGrayscaleEnabled();
        if (grayscaleEnabled) {
            Main.getBatch().setShader(grayscaleShader);
        } else {
            Main.getBatch().setShader(null); // بازگشت به حالت عادی
        }
        Main.getBatch().setProjectionMatrix(gameCamera.combined);
        if (controller != null) {
            controller.renderGame();
        }
        int xp = controller.getPlayerController().getPlayer().getXp();
        int level = controller.getPlayerController().getPlayer().getLevel();
        int xpToNextLevel = level * 20;

        float xpProgress = (float) xp / xpToNextLevel;
        xpBar.setValue(xpProgress);

        liveLabel.setText(controller.getPlayerController().getPlayer().getLives());
        hpLabel.setText("HP: " + controller.getPlayerController().getPlayer().getHP());
        xpLabel.setText("XP: " + controller.getPlayerController().getPlayer().getXp());
        levelLabel.setText("Level: " + controller.getPlayerController().getPlayer().getLevel());
        killsLabel.setText("Kills: " + controller.getPlayerController().getPlayer().getKills());
        xpBar.setValue(xpProgress);
        float timeRemaining = TimerController.getInstance().getTimeRemaining();
        int minutes = (int)(timeRemaining / 60);
        int seconds = (int)(timeRemaining % 60);
        String timeText = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText(timeText);
        stage.getViewport().apply();
        stage.act(Math.min(delta, 1/30f));
        stage.draw();
    }

    public void showGameOverWindow(Player player, int score, float time, int kills) {
        TimerController.getInstance().pause();

        Label title;
        if ((int) time == DurationMenuController.getTime() * 60) {
            title = new Label("!You Win!", GameAssetManager.getInstance().getSkin());
        } else {
            title = new Label("Game Over", GameAssetManager.getInstance().getSkin());
        }

        Stage stage = getStage(); // همون stageی که تو GameView هست
        stage.clear(); // پاک‌کردن stage قبلی (مثلاً HUD)

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label nameLabel = new Label("Name: " + player.getUsername(), GameAssetManager.getInstance().getSkin());
        Label scoreLabel = new Label("Score: " + score, GameAssetManager.getInstance().getSkin());
        Label timeLabel = new Label("Time: " + (int) time + "s", GameAssetManager.getInstance().getSkin());
        Label killsLabel = new Label("Kills: " + kills, GameAssetManager.getInstance().getSkin());

        UserManager userManager = controller.getUserManager();
        userManager.saveUser();
        TextButton retryButton = new TextButton("Try Again", GameAssetManager.getInstance().getSkin());
        TextButton mainMenuButton = new TextButton("Main Menu", GameAssetManager.getInstance().getSkin());

        // 🎯 لیسنر برای دکمه Try Again
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                int period=0;
//                Main game = controller.getGame();
//                UserManager userManager = controller.getUserManager();
//                period=DurationMenuController.getTime();
//                GameAssetManager.getInstance().loadTextures();
//                TimerController.init(period*60);
//                System.out.println("Timer started"+period);
//                GameController gameController = new GameController(settings,userManager,game);
//                game.setScreen(new GameView(
//                    gameController,
//                    skin,
//                    settings
//                ));
                // راه‌اندازی مجدد بازی:
                Main game = controller.getGame();
                UserManager userManager = controller.getUserManager();
                GameSettings settings = controller.getSettings(); // اگر ذخیره شده
                game.setScreen(new GameView(new GameController(settings, userManager, game), GameAssetManager.getInstance().getSkin(), settings));
            }
        });

        // 🎯 لیسنر برای دکمه منوی اصلی
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main game = controller.getGame();
                UserManager userManager = controller.getUserManager();
                MainMenuController mainMenuController = new MainMenuController(game, userManager);
                game.setScreen(new MainMenuView(
                    mainMenuController,
                    GameAssetManager.getInstance().getSkin(),
                    userManager.getCurrentUser(),
                    false
                ));
            }
        });

        // 👇 اضافه کردن آیتم‌ها به جدول
        table.add(title).pad(10).row();
        table.add(nameLabel).pad(10).row();
        table.add(scoreLabel).pad(10).row();
        table.add(timeLabel).pad(10).row();
        table.add(killsLabel).pad(10).row();
        table.add(retryButton).pad(20).row();
        table.add(mainMenuButton).pad(10);
    }

    public static GameView getLastGameView() {
        return lastGameView;
    }

    public static void setLastGameView(GameView lastGameView) {
        GameView.lastGameView = lastGameView;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.R) {
            if (!controller.getWeaponController().isReloading()) {
                controller.getWeaponController().manualReload();
            }
            return true;

        }
        if (keycode == Input.Keys.H) {
            controller.getPlayerController().getPlayer().increaseHP(10);
            Gdx.app.log("CHEAT", "Full HP activated!");
            return true;
        }
        if (keycode == Input.Keys.L) {
            controller.getPlayerController().getPlayer().increaseLives(1);
            Gdx.app.log("CHEAT", "Extra life granted!");
            return true;
        }
        if (keycode == Input.Keys.K) {
            controller.getEnemyController().killAllEnemies();
            Gdx.app.log("CHEAT", "All enemies killed!");
            return true;
        }
        if (keycode == Input.Keys.X) {
            controller.getPlayerController().getPlayer().gainXP(10);
            Gdx.app.log("CHEAT", "XP increased!");
            return true;
        }
        if (keycode == Input.Keys.G) {
            controller.getWeaponController().setInfiniteAmmo(true);
            Gdx.app.log("CHEAT", "Infinite ammo enabled!");
            return true;
        }
        if (keycode == Input.Keys.B) {
            Main game = controller.getGame();
            TimerController.getInstance().pause(); // توقف بازی
            lastGameView=this;
            SettingsManager settings = SettingsManager.getInstance();

            game.setScreen(new SettingsScreen(game, controller.getUserManager(), settings));
            return true;
        }
        if (selectingAbility) {
            if (keycode == Input.Keys.NUM_1 || keycode == Input.Keys.NUM_2 || keycode == Input.Keys.NUM_3) {
                System.out.println("chose key successfully!!");
                int selected = keycode - Input.Keys.NUM_1; // 0,1,2
                AbilityType chosen = offeredAbilities.get(selected);
                System.out.println("Selected ability: " + chosen.getName());

                controller.getPlayerController().getPlayer().addAbility(chosen);
                selectingAbility = false;
                TimerController.getInstance().resume();

                if (abilityWindow != null) abilityWindow.remove();

                return true;
            }
        }
        if (keycode == Input.Keys.P) {
            if (!TimerController.getInstance().isPaused()) {
                TimerController.getInstance().pause();
                showPauseMenu();
            }
            return true;
        }
        return false;
    }

    public static void showAbilitySelectionMenu() {
        System.out.println("Show ability selection menu");
        selectingAbility = true;
        TimerController.getInstance().pause();

        abilityWindow = new Window("Choose an Ability", GameAssetManager.getInstance().getSkin());
        abilityWindow.setMovable(false);

        List<AbilityType> all = new ArrayList<>(Arrays.asList(AbilityType.values()));
        Collections.shuffle(all);
        offeredAbilities = all.subList(0, 3); // سه تای اول رندوم

        Table buttonTable = new Table();
        buttonTable.pad(20).defaults().pad(15);

        for (int i = 0; i < 3; i++) {
            AbilityType ability = offeredAbilities.get(i);
            String text = (i + 1) + " - " + ability.getName() + "\n" + ability.getDescription();
            Label label = new Label(text, GameAssetManager.getInstance().getSkin());
            buttonTable.add(label).width(200);
        }
        System.out.println("Show ability selection menu222");
        abilityWindow.add(buttonTable);
        abilityWindow.pack();
        abilityWindow.setPosition(
            (Gdx.graphics.getWidth() - abilityWindow.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - abilityWindow.getHeight()) / 2f
        );

        stage.addActor(abilityWindow);
        System.out.println("back to Game!!");
//        TimerController.getInstance().resume();
//        abilityWindow.remove();
    }


    private void showPauseMenu() {
        PauseMenu menu = new PauseMenu(this,stage);
        stage.addActor(menu);
    }

    public GameController getController() {
        return controller;
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height); // تطبیق ویوپورت با اندازه جدید پنجره
        stage.getViewport().update(width, height, true); // برای UI
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
