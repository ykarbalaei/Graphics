package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Enemy.Enemy;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Seed;

import java.util.ArrayList;
import java.util.List;

public class EnemyController {
    private PlayerController playerController;
    Array<Enemy> allEnemies = new Array<>();
    private TreeController treeController;
    private TentacleController tentacleController;
    private EyebatController eyebatController;
    private static List<Seed> seeds = new ArrayList<>();
    private SeedController seedCtr;
//    private ElderController elderController;

    public EnemyController(PlayerController playerController) {
        this.playerController = playerController;
        treeController = new TreeController(playerController);
        tentacleController = new TentacleController(playerController);
        eyebatController = new EyebatController(playerController.getPlayer());
        seedCtr = new SeedController(playerController);
//        elderController = new ElderController(playerController);
    }

    public static List<Seed> getSeeds() {
        return seeds;
    }

    public void killAllEnemies() {
        for (Enemy enemy : allEnemies) {
            enemy.setAlive(false); // یا enemy.kill()، هرچی که برای حذف دشمن استفاده می‌کنی
        }
    }

    public Array<Enemy> getAllEnemies() {
        Array<Enemy> allEnemies = new Array<>();

        if (treeController.getTrees() != null) {
            allEnemies.addAll(treeController.getTrees());
        }

        if (tentacleController.getTentacles() != null) {
            allEnemies.addAll(tentacleController.getTentacles());
        }

        if (eyebatController.getBats() != null) {
            allEnemies.addAll(eyebatController.getBats());
        }

        return allEnemies;
    }

    public static void spawnSeed(float x, float y, Player player) {
        if(!isOutOfBounds(x,y)) {
            System.out.println("spawnSeed");
            seeds.add(new Seed(x, y, player));
        }
    }

    private static boolean isOutOfBounds(float x, float y) {
        return x < -100 || x > Gdx.graphics.getWidth() + 100 ||
            y < -100 || y > Gdx.graphics.getHeight() + 100;
    }
    public void update(float deltaTime) {
        Vector2 playerPos = new Vector2(
            playerController.getPlayer().getPosX(),
            playerController.getPlayer().getPosY()
        );
/// hhhhhhhhhhhhhhhh
        System.out.println("update");
        seedCtr.update(deltaTime);
        treeController.update(deltaTime);
        tentacleController.update(deltaTime);
        eyebatController.update(deltaTime);


//        elderController.update(deltaTime, playerPos);
    }

    public void render() {
        seedCtr.render();
        treeController.render();
        tentacleController.render();
        eyebatController.render();
//        elderController.render();
    }
}
