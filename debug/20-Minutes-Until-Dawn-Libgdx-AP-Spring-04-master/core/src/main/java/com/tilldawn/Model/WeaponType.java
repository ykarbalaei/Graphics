package com.tilldawn.Model;

public enum WeaponType {
    SMG_DUAL(24, 2f, 1, 8,5f,1.0f),
    REVOLVER(6, 1f, 1, 20,20f,0.8f),
    SHOTGUN(2, 1f, 4, 10,10f,1.1f);

    private int maxAmmo;
    private float reloadTime;
    private int fireRate;
    private  int damage;
    private final float spreadAngle; // زاویه پخش بین گلوله‌ها (درجه)
    private final float speedModifier;

    WeaponType(int maxAmmo, float reloadTime, int projectiles, int damage, float spreadAngle , float speedModifier) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.fireRate = projectiles;
        this.damage = damage;
        this.spreadAngle = spreadAngle;
        this.speedModifier = speedModifier;
    }

    public int getMaxAmmo() { return maxAmmo; }
    public float getReloadTime() { return reloadTime; }
    public int getfireRate() { return fireRate; }
    public int getDamage() { return damage; }
    public void setFireRate(int fireRate) {this.fireRate = fireRate;}
    public void setDamage(int damage) {this.damage = damage;}

    public float getSpreadAngle() {
        return spreadAngle;
    }
    public float getSpeedModifier() {
        return speedModifier;
    }
}
