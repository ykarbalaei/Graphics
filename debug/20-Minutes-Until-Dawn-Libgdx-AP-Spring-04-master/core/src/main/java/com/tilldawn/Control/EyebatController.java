package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.tilldawn.Model.Enemy.Eyebat;
import com.tilldawn.Model.Enemy.EyebatBullet;
import com.tilldawn.Model.Player;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EyebatController {
    private List<Eyebat> bats = new ArrayList<>();
    private Player player;
    private float spawnTimer = 0;
    private int waveCount = 0;
    private float gameTime = 0;

    private static final float BASE_SPAWN_RATE = 10f;
    private static final float MIN_SPAWN_INTERVAL = 0.5f;
    private static final int MIN_BATS_PER_WAVE = 2;
    private static final int MAX_BATS_PER_WAVE = 5;
    private static final float SPAWN_DISTANCE = 300f;

    public EyebatController(Player player) {
        this.player = player;
    }

    public void update(float delta) {
        gameTime += delta;
        spawnTimer += delta;

        float spawnInterval = calculateSpawnInterval();

        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;
            waveCount++;
            spawnWave();
        }

        for (int i = bats.size() - 1; i >= 0; i--) {
            Eyebat bat = bats.get(i);
            bat.update(delta);

            if (bat.shouldRemove()) {
                EnemyController.spawnSeed(bat.getX(), bat.getY(),player);
                bats.remove(i);
            }
        }
        checkBulletCollisions(player);

    }

    private float calculateSpawnInterval() {
        float interval = (BASE_SPAWN_RATE * waveCount - gameTime + 30) / 30f;
        return Math.max(interval, MIN_SPAWN_INTERVAL);
    }

    private void spawnWave() {
        int batCount = MathUtils.random(MIN_BATS_PER_WAVE, MAX_BATS_PER_WAVE);

        for (int i = 0; i < batCount; i++) {
            Vector2 spawnPos = calculateSpawnPosition();
            bats.add(new Eyebat(spawnPos.x, spawnPos.y, player));
        }
    }

    private Vector2 calculateSpawnPosition() {
        float angle = MathUtils.random(0f, 2 * MathUtils.PI);
        return new Vector2(
            player.getX() + MathUtils.cos(angle) * SPAWN_DISTANCE,
            player.getY() + MathUtils.sin(angle) * SPAWN_DISTANCE
        );
    }

    public void render() {
//        if (!player.isAlive()) {
//            return;
//        }
        for (Eyebat bat : bats) {
            bat.render();
        }
    }

    public Array<Eyebat> getBats() {
        Array<Eyebat> array = new Array<>();
        for (Eyebat bat : bats) {
            array.add(bat);
        }
        return array;
    }

//    public void checkBulletCollisions(Player player) {
//        if (player == null) return;
//
//        for (Eyebat bat : bats) {
//            for (EyebatBullet bullet : bat.getBullets()) {
//                if (bullet.isActive() &&
//                    bullet.barkhord(player)) { // تغییر به collidesWith
//
//                    bullet.setActive(false);
//
//                    player.reduceHP(1);
//
//                }else if(!bullet.isActive()) {
//                   //comment
//                }
//            }
//        }
//    }

    public void checkBulletCollisions(Player player) {
        if (player == null) return;

        for (Eyebat bat : bats) {
            Iterator<EyebatBullet> iterator = bat.getBullets().iterator();

            while (iterator.hasNext()) {
                EyebatBullet bullet = iterator.next();

                if (bullet.isActive() && bullet.barkhord(player)) {
                    bullet.setActive(false);
                    player.reduceHP(.5F);
                    Gdx.app.log("EYEBAT", "Bullet hit player!");
                    iterator.remove(); // ← گلوله رو حذف کن
                } else if (!bullet.isActive()) {
                    iterator.remove(); // ← غیر فعال = حذف
                }
            }
        }
    }

}
