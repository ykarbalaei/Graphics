package com.tilldawn.Model.Enemy;

import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {
    protected float x, y;
    protected int hp;
    protected float speed;
    protected int width, height;
    protected boolean alive;
    protected float directionX, directionY;

    public Enemy(float x, float y, int hp, float speed, int width, int height) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.alive = true;
        this.directionX = 0;
        this.directionY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract void update(float deltaTime);
    public abstract void render();

    public void takeDamage(int damage) {
        if (!alive) return;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            alive = false;
            onDeath();
        }
    }

    protected void onDeath() {
        System.out.println("Enemy died.");
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void decreaseHp(int damage) {
        hp -= damage;
    }

}
