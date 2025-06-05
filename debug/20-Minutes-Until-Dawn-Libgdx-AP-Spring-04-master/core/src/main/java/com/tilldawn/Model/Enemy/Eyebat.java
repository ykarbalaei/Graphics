package com.tilldawn.Model.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Main;
import com.tilldawn.Model.CollisionRect;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class Eyebat extends Enemy {
    private Animation<TextureRegion> flyAnimation;
    private float stateTime;
    private Vector2 targetPosition;
    private float attackCooldown;
    private Player player;
    private Sprite sprite;
    private Vector2 finalDirection;
    private static final float ATTACK_DELAY = 1.5f;
    private Array<EyebatBullet> bullets;
    private Texture bulletTexture;
    private float shootTimer = 0;
    //برای تنظیم زمان شلیک استفاده میشه
    private static final float SHOOT_INTERVAL = 10f;

    public Eyebat(float x, float y, Player player) {
        super(x, y, 30, 80f, 30, 30);
        this.player = player;
        this.flyAnimation = GameAssetManager.getInstance().getEyebatFlyAnim();
        this.sprite = new Sprite(flyAnimation.getKeyFrame(0));
        this.targetPosition = new Vector2(x, y);
        this.finalDirection = new Vector2();
        this.sprite.setSize(width, height);
        this.bullets = new Array<>();
        this.bulletTexture = new Texture(Gdx.files.internal("eyebat/eyebatbullet.png"));
        Gdx.app.log("BOUNDS", "New Eyebat bounds: " + System.identityHashCode(this.getBounds()));
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        attackCooldown -= delta;
        shootTimer += delta;

//        if(hp == 0) {
//            player.increasekills(1);
//        }
        if (player == null) return;

        // محاسبه هدف جدید هر 1.5 ثانیه
        if (attackCooldown <= 0) {
            calculateNewTarget();
            attackCooldown = ATTACK_DELAY;
        }

        // شلیک هر 3 ثانیه
        if (shootTimer >= SHOOT_INTERVAL) {
            shoot();
            shootTimer = 0;
        }

        // محاسبه جهت نهایی
        Vector2 toPlayer = new Vector2(
            player.getX() - x,
            player.getY() - y
        ).nor();

        Vector2 toTarget = new Vector2(
            targetPosition.x - x,
            targetPosition.y - y
        ).nor();

        finalDirection.set(toPlayer).scl(0.7f).add(toTarget.scl(0.3f)).nor();

        // اعمال حرکت
        x += finalDirection.x * speed * delta;
        y += finalDirection.y * speed * delta;

        // به‌روزرسانی اسپرایت
        sprite.setRegion(flyAnimation.getKeyFrame(stateTime, true));
        sprite.setPosition(x - width/2, y - height/2);
        sprite.setFlip(finalDirection.x < 0, false);

        // آپدیت پرتابه‌ها
        for (EyebatBullet bullet : bullets) {
            bullet.update(delta);
        }

//        if (player != null &&
//            getCollision().collidesWith(player.getBounds())) {
//
//            float distance = player.getBounds().distanceTo(this.getCollision());
//            if (distance < 2f) {
//                this.hp = 0;
//                player.increasekills(1);
//                player.reduceHP(2);
//                Gdx.app.log("COLLISION", "Eyebat collided with player");
//            }
//        }

        if (player != null &&
            eybatBarkhord()) {
//            this.hp = 0; // هیولای فعلی می‌میرد
            alive = false;
            player.increasekills(1);
            player.reduceHP(2);
            Gdx.app.log("COLLISION", "Eyebat collided with player");
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x - width/2, y - height/2, width, height);
    }

    public CollisionRect getCollision() {
        return new CollisionRect(x - width/2, y - height/2, width, height);
    }

    private void calculateNewTarget() {
        float angle = MathUtils.random(0, 2 * MathUtils.PI);
        float distance = MathUtils.random(30f, 50f);
        targetPosition.set(
            player.getX() + MathUtils.cos(angle) * distance,
            player.getY() + MathUtils.sin(angle) * distance
        );
    }


    private void shoot() {

        if (player == null) return;

        Vector2 direction = new Vector2(
            player.getX() - x,
            player.getY() - y
        ).nor();
        bullets.add(new EyebatBullet(
            x, y,
            direction.x, direction.y,
            bulletTexture
        ));
    }

    public boolean eybatBarkhord(){
        float dx = player.getX() -x;
        float dy = player.getY() - y;
        float distance = (float)Math.sqrt(dx*dx + dy*dy);

        if (distance > 2f) {
            return false;
        }else {
            return true;
        }
    }
    @Override
    public void render() {
        if (isAlive()) {
            sprite.draw(Main.getBatch());
            // رندر پرتابه‌ها
            for (EyebatBullet bullet : bullets) {
                System.out.println("called bullet: " );
                bullet.render();}
        }
    }

    public Array<EyebatBullet> getBullets() {
        return bullets;
    }

    public boolean shouldRemove() {
        return !isAlive() || isOutOfBounds();
    }

    private boolean isOutOfBounds() {
        return x < -100 || x > Gdx.graphics.getWidth() + 100 ||
            y < -100 || y > Gdx.graphics.getHeight() + 100;
    }
}
