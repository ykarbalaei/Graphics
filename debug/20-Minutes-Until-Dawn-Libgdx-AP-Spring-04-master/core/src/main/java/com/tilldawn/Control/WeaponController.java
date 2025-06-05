package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Enemy.Enemy;

import java.util.ArrayList;
import java.util.Iterator;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Player player;
    private OrthographicCamera camera;
    private final GameSettings settings;
    private EnemyController enemyController;
    private int currentAmmo;
    private final int maxAmmo;
    private boolean isReloading = false;
    private float reloadTimer = 0f;
    private final float reloadDuration = 2.0f;
    private boolean infiniteAmmo = false;
//    private boolean showMuzzle = false;
//    private float muzzleTimer = 0f;
//    private final float muzzleDuration = 0.1f; // فقط برای ۰.۱ ثانیه نمایش داده میشه


    public WeaponController(Weapon weapon,
                            Player player,
                            OrthographicCamera camera,
                            GameSettings settings,
                            EnemyController enemyController) {
        this.settings = settings;
        this.weapon = weapon;
        this.player = player;
        this.camera = camera;
        this.enemyController = enemyController;
        this.maxAmmo = weapon.getMaxAmmo();
        this.currentAmmo = maxAmmo;
    }

    public void update(float delta) {
        updateWeaponPosition();
        updateBullets(delta);
        updateReload(delta);
        checkBulletCollisions();
    }

    public void render() {
        weapon.getSprite().draw(Main.getBatch());
        renderBullets();
    }

    private void updateWeaponPosition() {
        Sprite weaponSprite = weapon.getSprite();
        weaponSprite.setPosition(
            player.getX() + player.getPlayerSprite().getWidth() * 0.5f,
            player.getY() + player.getPlayerSprite().getHeight() * 0.3f
        );
    }

    public void handleWeaponRotation(int screenX, int screenY) {

        if (camera == null) {
            Gdx.app.error("DEBUG", "⚠️ Camera is null! Fallback activated");
            weapon.getSprite().setRotation(45);
            return;
        }

        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        float angle = MathUtils.atan2(
            worldCoords.y - player.getY(),
            worldCoords.x - player.getX()
        ) * MathUtils.radDeg;
        weapon.getSprite().setRotation(angle - 90);
    }

    public void handleWeaponShoot(int screenX, int screenY) {

        if (!infiniteAmmo) {
            if (weapon.getCurrentAmmo() <= 0) {
                Gdx.app.log("Weapon", "No ammo! Reload required.");
                startReload();
                return;
            }
            weapon.setCurrentAmmo(weapon.getCurrentAmmo() - 1);
        }

        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));

        float dx = worldCoords.x - player.getX();
        float dy = worldCoords.y - player.getY();
        float baseAngleRad = MathUtils.atan2(dy, dx);

        float weaponAngleRad = weapon.getSprite().getRotation() * MathUtils.degRad;
        float tipX = weapon.getSprite().getX() +
            weapon.getSprite().getWidth()/2 +
            MathUtils.cos(weaponAngleRad) * weapon.getSprite().getHeight();
        float tipY = weapon.getSprite().getY() +
            weapon.getSprite().getHeight()/2 +
            MathUtils.sin(weaponAngleRad) * weapon.getSprite().getHeight();

        int projectileCount = weapon.getProjectileCount();
        float spread = weapon.getType().getSpreadAngle();

        for (int i = 0; i < projectileCount; i++) {
            float angleOffset = (i - projectileCount / 2f) * spread;
            float finalAngleRad = baseAngleRad + angleOffset * MathUtils.degRad;

            float targetX = tipX + MathUtils.cos(finalAngleRad) * 100;
            float targetY = tipY + MathUtils.sin(finalAngleRad) * 100;

            bullets.add(new Bullet(
                tipX,
                tipY,
                worldCoords.x,
                worldCoords.y
            ));}
        weapon.setCurrentAmmo(weapon.getCurrentAmmo() - 1);
        GameAssetManager.getInstance().shootSound.play(1.0f);
//        showMuzzle = true;
//        muzzleTimer = 0f;
        // حجم صدا (1.0 = کامل)

    }

    public void setInfiniteAmmo(boolean value) {
        this.infiniteAmmo = value;
    }

    private void updateBullets(float delta) {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update(delta);
            if (bullet.isExpired()) {
                iterator.remove();
                bullet.dispose();
            }
        }
    }

    private void renderBullets() {
        for (Bullet bullet : bullets) {
            bullet.render();
        }
    }

    private void startReload() {
        if (!isReloading) {
            isReloading = true;
            reloadTimer = 0f;
            Gdx.app.log("WeaponController", "🔄 Reloading started...");
        }
    }
    private void updateReload(float delta) {
        if (isReloading) {
            reloadTimer += delta;
            if (reloadTimer >= reloadDuration) {
                currentAmmo = maxAmmo;
                weapon.setCurrentAmmo(maxAmmo);
                isReloading = false;
                Gdx.app.log("WeaponController", "✅ Reload complete. Ammo refilled.");
            }
        }
    }
    private void checkBulletCollisions() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();

            if (!bullet.isActive()) continue;

            for (Enemy enemy : enemyController.getAllEnemies()) {
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    enemy.takeDamage(weapon.getDamage());
                    if (!enemy.isAlive()) {
                        player.increasekills(1);
                    }
                    //enemy.decreaseHp(1);
                    iterator.remove();
                    bullet.dispose();
                    Gdx.app.log("COLLISION", "Bullet hit enemy!");
                    break;
                }
            }
        }
    }
}
