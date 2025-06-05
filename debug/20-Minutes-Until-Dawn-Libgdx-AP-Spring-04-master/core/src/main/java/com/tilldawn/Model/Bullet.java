package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.Main;

public class Bullet {
    private float x, y;
    private float velocityX, velocityY;
    private Sprite sprite;
    private boolean expired;
    private float lifetime = 2f;
    private Texture texture;
    private CollisionRect collisionRect;

    public Bullet(float startX, float startY, float targetX, float targetY) {
        this.x = startX;
        this.y = startY;

        float angle = MathUtils.atan2(targetY - startY, targetX - startX);
        float speed = 500f;
        this.velocityX = MathUtils.cos(angle) * speed;
        this.velocityY = MathUtils.sin(angle) * speed;

        this.texture = new Texture("bullet.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(15f, 15f);
        this.sprite.setOrigin(4f, 4f);
        this.sprite.setPosition(x, y);
        this.collisionRect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void update(float delta) {
        x += velocityX * delta;
        y += velocityY * delta;
        sprite.setPosition(x, y);

        lifetime -= delta;
        if (lifetime <= 0) {
            expired = true;
        }
    }

    public void render() {
        sprite.draw(Main.getBatch());
    }

    public boolean isExpired() {
        return expired || x < 0 || x > Gdx.graphics.getWidth() ||
            y < 0 || y > Gdx.graphics.getHeight();
    }

    public boolean isActive() {
        return !expired &&
            x >= 0 && x <= Gdx.graphics.getWidth() &&
            y >= 0 && y <= Gdx.graphics.getHeight();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void dispose() {
        texture.dispose();
    }
}
