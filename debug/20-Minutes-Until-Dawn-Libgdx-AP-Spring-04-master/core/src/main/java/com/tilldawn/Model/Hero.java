package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hero {
    private HeroType type;
    private Texture   texture;
    private Sprite    sprite;

    public Hero(HeroType type) {
        this.type = type;

        // مقداردهی اولیه برای جلوگیری از خطا در تست‌های کنسولی
        this.texture = null;
        this.sprite  = null;

        // فقط وقتی LibGDX در حال اجراست تکسچر و اسپریت را بساز
        if (Gdx.app != null) {
            this.texture = new Texture(type.getTexturePath());
            this.sprite  = new Sprite(texture);
            this.sprite.setSize(64, 64);
            // اگر لازم باشد می‌توانید موقعیت اولیه را هم ست کنید:
            // this.sprite.setPosition(
            //     Gdx.graphics.getWidth()  * 0.5f - 32,
            //     Gdx.graphics.getHeight() * 0.5f - 32
            // );
        }
    }

    public HeroType getType() {
        return type;
    }

    public String getName() {
        return type.getDisplayName();
    }

    public String getDescription() {
        return type.getDescription();
    }

    /**
     * بازگرداندن Sprite برای رسم در بازی.
     * در تست کنسولی ممکن است null باشد.
     */
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public String toString() {
        return getName();
    }
}
