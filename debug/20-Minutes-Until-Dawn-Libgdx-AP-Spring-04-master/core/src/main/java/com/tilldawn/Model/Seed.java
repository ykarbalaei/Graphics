package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Control.EnemyController;
import com.tilldawn.Main;

public class Seed {
    private Sprite sprite;
    private float x, y;
    private boolean collected = false;
    private Player player;
    private float stateTime;

    public Seed(float x, float y,Player player) {
        System.out.println("seed");
        this.player = player;
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(new Texture("seed/seed.png")); // عکس دونه
        this.sprite.setPosition(x, y);
        this.sprite.setSize(20, 20);
        System.out.println("seed made");// بسته به گرافیکت تغییر بده

    }

    public boolean seedBarkhord(){
        System.out.println("seedBarkhord");
        float dx = player.getX() -x;
        float dy = player.getY() - y;
        float distance = (float)Math.sqrt(dx*dx + dy*dy);

        if (distance > 5f) {
            return false;
        }else {
            return true;
        }
    }

    public void updateSeed(float delta) {
        System.out.println("hhhjjjjkkkk");
        if (player != null &&
            seedBarkhord()) {
            System.out.println("barkhord: "+ seedBarkhord());
            collected = true;
            player.gainXP(5);
            Gdx.app.log("COLLISION", "Eyebat collided with player");
        }
    }
    public void render() {
        if (!collected) {
            sprite.draw(Main.getBatch());
            stateTime += Gdx.graphics.getDeltaTime();
        }
    }

    public boolean isCollected() {
        return collected;
    }

//    public void collect() {
//        collected = true;
//    }

    public float getX() { return x; }
    public float getY() { return y; }
}
