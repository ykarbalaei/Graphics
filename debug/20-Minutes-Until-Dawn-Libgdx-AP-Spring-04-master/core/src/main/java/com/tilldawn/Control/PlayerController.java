package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class PlayerController {
    private Player player;
    private Vector2 velocity = new Vector2();
    private float speed = 300f; // پیکسل بر ثانیه
    private boolean facingRight = true;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update(float delta) {
        handlePlayerInput();

        player.setPosX(player.getPosX() + velocity.x * delta);
        player.setPosY(player.getPosY() + velocity.y * delta);

        player.update(delta);
        if(velocity.isZero()) {
            idleAnimation(delta);
        } else {
            runningAnimation(delta);
        }
    }

    public void handlePlayerInput() {
        velocity.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -1;
            if(facingRight) {
                player.getPlayerSprite().flip(true, false);
                facingRight = false;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = 1;
            if(!facingRight) {
                player.getPlayerSprite().flip(true, false);
                facingRight = true;
            }
        }

        if(!velocity.isZero()) {
            velocity.nor().scl(speed);
        }
    }

    public void idleAnimation(float delta) {
        Animation<TextureRegion> animation =
            GameAssetManager.getInstance().getCharacterIdleAnimation();
        updateAnimation(animation, delta); // delta اضافه شد
    }

    public void runningAnimation(float delta) {
        Animation<TextureRegion> animation =
            GameAssetManager.getInstance().getCharacterRunAnimation();
        updateAnimation(animation, delta); // delta اضافه شد
    }

    private void updateAnimation(Animation<TextureRegion> animation, float delta) {
        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));
        player.setTime(player.getTime() + delta); // استفاده از delta برای زمان انیمیشن
    }

    public Player getPlayer() {
        return player;
    }
}
