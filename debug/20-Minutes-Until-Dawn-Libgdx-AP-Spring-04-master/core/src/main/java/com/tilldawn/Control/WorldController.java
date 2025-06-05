package com.tilldawn.Control;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private OrthographicCamera camera; // اضافه شده

    public WorldController(PlayerController playerController, OrthographicCamera camera) {
        this.playerController = playerController;
        this.camera = camera;
        this.backgroundTexture = new Texture("background.png");

        // تنظیم حالت تکرار برای بکگراند (اگر نیاز است)
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public void update(float delta) {
        // حرکت پس‌زمینه همراه با دوربین (اختیاری)
        // اگر می‌خواهید پس‌زمینه ثابت بماند، این بخش را حذف کنید
    }

    public void render() {
        Main.getBatch().draw(backgroundTexture, 0, 0,
            camera.viewportWidth, camera.viewportHeight);
    }

    public void dispose() {
        backgroundTexture.dispose();
    }
}
