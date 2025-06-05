package com.tilldawn.Model.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class TentacleEnemy extends Enemy {
    private Sprite sprite;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime = 0;
    private Player target;

    public TentacleEnemy(float x, float y, Player player) {

        super(x, y, 25, 55f, 40, 60); // HP:25, Speed:30, Width:40, Height:60

        this.target = player;

        this.walkAnimation = GameAssetManager.getInstance().getTentacleWalkAnim();

        TextureRegion firstFrame = walkAnimation.getKeyFrame(0);
        this.sprite = new Sprite(firstFrame);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(x, y);
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (speed > 0 && target != null) {
            float dx = target.getX() - x;
            float dy = target.getY() - y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);

            if (distance > 5f) {
                x += (dx/distance) * speed * deltaTime;
                y += (dy/distance) * speed * deltaTime;
            }
        // حرکت به سمت بازیکن
//        if (target != null && target.isAlive()) {
//            Vector2 direction = new Vector2(
//                target.getX() - this.x,
//                target.getY() - this.y
//            ).nor();
            // بررسی برخورد ساده بر اساس فاصله
            else if(isAlive()){
                    target.reduceHP(2); // کم کردن HP بازیکن
                    target.increasekills(1);
                    alive=false;      // یا نه، بستگی به طراحی داره


            }

        }

        // بروزرسانی اسپرایت
        sprite.setPosition(x, y);
        sprite.setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    private void updateAlive() {
        hp--;
        if(hp <= 0) {
            alive=false;
        }
    }
    @Override
    public void render() {
        if (isAlive()) {
            sprite.draw(Main.getBatch());
        }
    }

    public boolean shouldRemove() {
        return !isAlive() || isOutOfBounds();
    }

    private boolean isOutOfBounds() {
        return x < -100 || x > Gdx.graphics.getWidth() + 100 ||
            y < -100 || y > Gdx.graphics.getHeight() + 100;
    }
}
