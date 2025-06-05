package com.tilldawn.Model.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Control.TimerController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class Tree extends Enemy {
    private Animation<TextureRegion> currentAnim;
    private float stateTime;
    private Player target;

    public Tree(float x, float y, Player target) {
        super(x, y, 20, 65f, 50, 100);
        this.currentAnim = GameAssetManager.getInstance().getTreeIdleAnim();
        this.stateTime = 0;
        this.target = target;
    }

    @Override
    public void update(float deltaTime) {
        if (speed > 0 && target != null) {
            float dx = target.getX() - x;
            float dy = target.getY() - y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);

            if (distance > 5f) {
                x += (dx/distance) * speed * deltaTime;
                y += (dy/distance) * speed * deltaTime;
            }

            else if (alive) {
                alive = false; // درخت نابود می‌شود
                target.increasekills(1);
                System.out.println("Tree Died! time: " + TimerController.getInstance().getTimeRemaining()+"*"+stateTime);
                target.reduceHP(1);
                Gdx.app.log("TREE", "Destroyed on collision with player");
            }
        }
    }

    @Override
    public void render() {
        if (alive) { // فقط اگر زنده باشد رندر شود
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = currentAnim.getKeyFrame(stateTime, true);
            Main.getBatch().draw(currentFrame, x, y, width, height);
        }
    }

    public void dispose() {
    }
}
