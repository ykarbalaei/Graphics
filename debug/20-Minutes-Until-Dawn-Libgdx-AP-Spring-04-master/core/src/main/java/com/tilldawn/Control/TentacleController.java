package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Enemy.TentacleEnemy;

import java.util.ArrayList;
import java.util.List;

public class TentacleController {
    private List<TentacleEnemy> tentacles = new ArrayList<>();
    private PlayerController playerController;
    private float spawnTimer = 0;
    private static final float SPAWN_INTERVAL = 3f;
    private static final float MIN_SPAWN_DISTANCE = 200f;
    private static final float MAX_SPAWN_DISTANCE = 400f;

    public TentacleController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void update(float deltaTime) {
        spawnTimer += deltaTime;
        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0;
            spawnTentacle();
        }

        for (int i = tentacles.size() - 1; i >= 0; i--) {
            TentacleEnemy tentacle = tentacles.get(i);
            tentacle.update(deltaTime);

            if (tentacle.shouldRemove()) {
                EnemyController.spawnSeed(tentacle.getX(), tentacle.getY(),tentacle.getTarget());
                tentacles.remove(i);
            }
        }
    }


    public Array<TentacleEnemy> getTentacles() {
        Array<TentacleEnemy> array = new Array<>();
        for (TentacleEnemy tentacle : tentacles) {
            array.add(tentacle);
        }
        return array;
    }

    private void spawnTentacle() {
        Player player = playerController.getPlayer();
        if (player == null) return;

        float angle = MathUtils.random(0f, 2 * MathUtils.PI);
        float distance = MathUtils.random(MIN_SPAWN_DISTANCE, MAX_SPAWN_DISTANCE);

        float spawnX = player.getX() + MathUtils.cos(angle) * distance;
        float spawnY = player.getY() + MathUtils.sin(angle) * distance;

        spawnX = MathUtils.clamp(spawnX, 0, Gdx.graphics.getWidth());
        spawnY = MathUtils.clamp(spawnY, 0, Gdx.graphics.getHeight());

        tentacles.add(new TentacleEnemy(spawnX, spawnY, player));
    }

    public void render() {
        for (TentacleEnemy tentacle : tentacles) {
            tentacle.render();
        }
    }
}
