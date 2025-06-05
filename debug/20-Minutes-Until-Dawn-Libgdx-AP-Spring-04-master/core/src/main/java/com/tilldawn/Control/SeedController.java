package com.tilldawn.Control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Enemy.Tree;
import com.tilldawn.Model.Seed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeedController {


    private PlayerController playerController;

    public SeedController(PlayerController playerController) {
        this.playerController = playerController;
    }

//    public Array<Seed> getSeeds() {
//        Array<Seed> array = new Array<>();
//        for (Seed seed : seeds) {
//            array.add(seed);
//        }
//        return array;
//    }

    public void update(float deltaTime) {
        System.out.println("update seeds");
        for (int i = EnemyController.getSeeds().size() - 1; i >= 0; i--) {
            Seed tree = EnemyController.getSeeds().get(i);
            System.out.println("in for");
            tree.updateSeed(deltaTime);
            System.out.println("after");
            if (tree.isCollected()) {
                EnemyController.getSeeds().remove(i);
            }
        }
    }

    public void render() {
        for (Seed tree :EnemyController.getSeeds()) {
            tree.render();
        }
    }
}


