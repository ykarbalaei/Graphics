package com.tilldawn.Control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Enemy.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeController {
    private List<Tree> trees = new ArrayList<>();
    private PlayerController playerController;
    private static final int MIN_SPAWN_DISTANCE = 200;
    private static final int MAX_SPAWN_DISTANCE = 400;
    private Random random = new Random();

    public TreeController(PlayerController playerController) {
        this.playerController = playerController;
        spawnInitialTrees(5);
    }

    private void spawnInitialTrees(int count) {
        for (int i = 0; i < count; i++) {
            Vector2 spawnPos = generateSpawnPosition();
            trees.add(new Tree(spawnPos.x, spawnPos.y, playerController.getPlayer()));
        }
    }

    public Array<Tree> getTrees() {
        Array<Tree> array = new Array<>();
        for (Tree tree : trees) {
            array.add(tree);
        }
        return array;
    }

    private Vector2 generateSpawnPosition() {
        Vector2 playerPos = new Vector2(
            playerController.getPlayer().getX(),
            playerController.getPlayer().getY()
        );

        float angle = random.nextFloat() * (float)Math.PI * 2;
        float distance = MIN_SPAWN_DISTANCE +
            random.nextFloat() * (MAX_SPAWN_DISTANCE - MIN_SPAWN_DISTANCE);
        float time=TimerController.getInstance().getTimeRemaining();
        System.out.println("Tree Generated  in: "+ time);

        return new Vector2(
            playerPos.x + (float)Math.cos(angle) * distance,
            playerPos.y + (float)Math.sin(angle) * distance
        );
    }

    public void update(float deltaTime) {
        for (int i = trees.size() - 1; i >= 0; i--) {
            Tree tree = trees.get(i);
            tree.update(deltaTime);

            if (!tree.isAlive()) {
                System.out.println("sedddd");
                EnemyController.spawnSeed(tree.getX(), tree.getY(),playerController.getPlayer());
                trees.remove(i);

            }
        }
    }

    public void render() {
        for (Tree tree : trees) {
            tree.render();
        }
    }
}
