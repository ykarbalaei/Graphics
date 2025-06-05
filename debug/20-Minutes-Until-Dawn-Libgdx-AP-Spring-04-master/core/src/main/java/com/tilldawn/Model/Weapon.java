package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    private Sprite sprite;
    private int ammo;
    private float reloadCooldown = 0;
    private final WeaponType type;
    private Player player;

    public Weapon(WeaponType type) {
        this.sprite = new Sprite(GameAssetManager.getInstance().getTexture());
        this.type = type;
        this.ammo = type.getMaxAmmo();

        sprite.setSize(50, 50);
    }

    public void update() {
        if (reloadCooldown > 0) {
            reloadCooldown -= Gdx.graphics.getDeltaTime();
        }

        sprite.setX(player.getX() + 20);
        sprite.setY(player.getY() - 10);
    }

    public boolean canShoot() {
        return ammo > 0 && reloadCooldown <= 0;
    }

    public void shoot() {
        ammo--;
        if (ammo == 0) {
            reloadCooldown = type.getReloadTime();
            ammo = type.getMaxAmmo();
        }
    }


    public int getDamage() {
        return type.getDamage();
    }


    public Sprite getSprite() {
        return sprite;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public WeaponType getType() {
        return type;
    }
    public int getMaxAmmo(){
        return type.getMaxAmmo();
    }

    public int getProjectileCount() {
        return type.getfireRate();
    }

    public int getCurrentAmmo() {
        return ammo;
    }

    public void setCurrentAmmo(int newAmmo) {
        this.ammo = Math.min(newAmmo, type.getMaxAmmo());
    }



}
