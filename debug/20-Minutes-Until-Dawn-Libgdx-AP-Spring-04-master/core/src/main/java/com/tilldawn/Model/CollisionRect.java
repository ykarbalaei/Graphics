package com.tilldawn.Model;

public class CollisionRect {
    static float x, y;
    static float width, height;
    public CollisionRect(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y){
        this.x = x;
        this.y = y;
    }

    public static boolean collidesWith(CollisionRect rect){
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }

    public float distanceTo(CollisionRect other) {
        float centerX1 = this.x + this.width / 2;
        float centerY1 = this.y + this.height / 2;
        float centerX2 = other.x + other.width / 2;
        float centerY2 = other.y + other.height / 2;

        float dx = centerX1 - centerX2;
        float dy = centerY1 - centerY2;

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

}
