package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameOverView;
import com.tilldawn.View.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private EnemyController enemyController;
    private GameOverView gameOverView;
    private final UserManager userManager;
    private boolean isGameOver = false;
    private final Main game;
    private Texture hitEffectTexture;
    private static boolean showHitEffect = false;
    private static float hitEffectTimer = 0f;
    private static final float HIT_EFFECT_DURATION = 0.2f;
//    private boolean showHitEffect = false;
//    private float hitEffectTimer = 0f;
//    private final float HIT_EFFECT_DURATION = 0.2f;


    private final GameSettings settings;

    public GameController(GameSettings settings, UserManager userManager,Main game) {
        this.game = game;
        this.settings = settings;
        this.userManager = userManager;
        hitEffectTexture = new Texture(Gdx.files.internal("effects/hit_effect.png"));

    }

    public static void triggerHitEffect() {
        showHitEffect = true;
        hitEffectTimer = HIT_EFFECT_DURATION;
    }

    public void showHitEffect() {
        showHitEffect = true;
        hitEffectTimer = HIT_EFFECT_DURATION;
    }

    public Main getGame() {
        return game;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setView(GameView view, GameSettings settings) {
        this.view = view;
        this.playerController = new PlayerController(new Player(userManager));
        this.worldController = new WorldController(playerController, view.getGameCamera());

        //اینجا نیاز به تغییر دارد.
        Weapon chosen = settings.getSelectedWeapon();
        if (chosen == null) {
            chosen = new Weapon(WeaponType.SMG_DUAL);
        }

        this.enemyController = new EnemyController(playerController);

        this.weaponController = new WeaponController(
            chosen,
            playerController.getPlayer(),
            view.getGameCamera(),
            settings,
            enemyController
        );
    }

    public void renderGame() {

        if (!Main.getBatch().isDrawing()) {
            Main.getBatch().begin();
        }

        try {
            if (isGameOver && gameOverView != null) {
                gameOverView.render();
            } else {
                worldController.render();
                playerController.getPlayer().render();
                Player player = playerController.getPlayer();
                Main.getBatch().draw(player.getPlayerTexture(), player.getX(), player.getY(),0.01f, 0.01f);

                if (showHitEffect) {
                    Main.getBatch().draw(hitEffectTexture, player.getX(), player.getY());
                }


                enemyController.render();
                weaponController.render();
            }

        } finally {
            if (Main.getBatch().isDrawing()) {
                Main.getBatch().end();
            }
        }
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void updateGame(float delta) {
        if (view != null) {
            TimerController.getInstance().update(delta);
            System.out.println("time updated!!");
//            if (isGameOver) {
//                gameOverMenuController.update(delta);
//                return; // بقیه بازی آپدیت نشه
//            }
            TimerController.getInstance().update(delta);

            if (TimerController.getInstance().isFinished() || !playerController.getPlayer().isAlive()) {
                isGameOver = true;
                TimerController.getInstance().pause();
                //gameOverView = new GameOverView(view.getStage(),settings,game,view.getSkin(), userManager);
                Player player = playerController.getPlayer();
                player.setScorePlayer();
                float elapsed = TimerController.getInstance().getElapsedTime();
                view.showGameOverWindow(player, player.getScore(), elapsed, player.getKills());
                //gameOverView.show(player, player.getScore(), elapsed, player.getKills());
//                gameOverMenuController = new GameOverMenuController(playerController.getPlayer());
                return;
            }
            playerController.update(delta);
            if (showHitEffect) {
                hitEffectTimer -= delta;
                if (hitEffectTimer <= 0) {
                    showHitEffect = false;
                }
            }

            weaponController.update(delta);
            enemyController.update(delta);
            worldController.update(delta);
        }
    }

//    public static void triggerAbilitySelection() {
//        selectingAbility = true;
//        TimerController.getInstance().pause(); // بازی رو موقتاً متوقف کن
//    }


    public EnemyController getEnemyController() {
        return enemyController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }
}
