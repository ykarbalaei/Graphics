
package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.tilldawn.Control.HeroController;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final Skin skin;
    private Animation<TextureRegion> eyebatFlyAnim;
    private Animation<TextureRegion> tentacleWalkAnim;
    private Animation<TextureRegion> characterIdleAnim;
    private Animation<TextureRegion> characterRunAnim;
    private Texture playerIdleTexture;
    private Texture playerRunTexture;
    private Animation<TextureRegion> playerIdleAnim;
    private Texture smgTexture;
    private Texture bulletTexture;
    private Animation<TextureRegion> treeIdleAnim;
    private Animation<TextureRegion> treeWalkAnim;
    private Texture[] treePowerupTexture = new Texture[2];
    public Sound shootSound;
    private Texture hitEffectTexture;
    private boolean showHitEffect = false;
    private float hitEffectTimer = 0f;
    private final float HIT_EFFECT_DURATION = 0.2f;

    private GameAssetManager() {
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        loadTextures();

    }

    public static synchronized GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public void loadTextures() {
        loadEyebatAnimations();
        loadTentacleAnimations();

        Array<TextureRegion> playerFrames = new Array<>();
        for (int i = 0; i < 6; i++) {
            playerFrames.add(new TextureRegion(new Texture("1/Idle_" + i + ".png")));
        }
        playerIdleAnim = new Animation<>(0.1f, playerFrames);
        playerIdleTexture = new Texture("1/Idle_0.png");
        playerRunTexture = new Texture("1/characters/Run_0.png");
        smgTexture = new Texture("smg/SMGStill.png");
        bulletTexture = new Texture("bullet.png");
        hitEffectTexture = new Texture(Gdx.files.internal("effects/hit_effect.png"));

        loadTreeAssets();
        loadCharacterAnimations();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));

    }

    public void showHitEffect() {
        showHitEffect = true;
        hitEffectTimer = HIT_EFFECT_DURATION;
    }

    private void loadEyebatAnimations() {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("eyebat/T_EyeBat_" + i + ".png")));
        }
        eyebatFlyAnim = new Animation<>(0.1f, frames);
    }

    private void loadTentacleAnimations() {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("tentacle/walk_" + i + ".png")));
        }
        tentacleWalkAnim = new Animation<>(0.1f, frames);
    }



    public void loadCharacterAnimations() {

        Array<TextureRegion> idleFrames = new Array<>();
        User user= UserManager.getInstance().getCurrentUser();
        String heroName;

        try {

            heroName = HeroController.getHeroSelected().getDisplayName().toLowerCase();


            // بارگذاری فریم‌های idle
            for (int i = 0; i < 6; i++) {
                String idlePath = String.format("heros/%s/idle/idle_%d.png",
                    heroName.toLowerCase(), i);
                try {
                    Texture texture = new Texture(Gdx.files.internal(idlePath));
                    idleFrames.add(new TextureRegion(texture));
                    Gdx.app.log("LOAD", "Success: " + idlePath);
                } catch (Exception e) {
                    Gdx.app.error("LOAD", "Failed: " + idlePath, e);
                    // استفاده از تصویر پیشفرض
                    Texture defaultTex = new Texture(Gdx.files.internal("heros/shana/idle/idle_0.png"));
                    idleFrames.add(new TextureRegion(defaultTex));
                }
            }
            characterIdleAnim = new Animation<>(0.1f, idleFrames);
            runAnimations(heroName);
        } catch (Exception e) {
            Gdx.app.error("Animation", "Critical error", e);
            System.out.println(e.getMessage());;
            loadDefaultAnimations();
        }
    }
    private void runAnimations(String heroName) {
        Array<TextureRegion> runFrames = new Array<>();

        try {
            for (int i = 0; i < 4; i++) {
                String runPath = String.format("heros/%s/run/Run_%d.png", heroName, i);
                runFrames.add(new TextureRegion(new Texture(runPath)));
                Gdx.app.debug("Animation", "Loaded: " + runPath);
            }
            characterRunAnim = new Animation<>(0.08f, runFrames);

        } catch (GdxRuntimeException e) {
            Gdx.app.error("Animation", "Failed to load run animations", e);
            // بارگذاری انیمیشن دویدن پیشفرض
            loadDefaultRunAnimation();
        }
    }
    private void loadDefaultAnimations() {
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 0; i < 6; i++) {
            idleFrames.add(new TextureRegion(new Texture("1/Idle_" + i + ".png")));
        }
        characterIdleAnim = new Animation<>(0.1f, idleFrames);

    }

    private void loadDefaultRunAnimation() {
        Array<TextureRegion> runFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            runFrames.add(new TextureRegion(new Texture("1/characters/Run_" + i + ".png")));
        }
        characterRunAnim = new Animation<>(0.08f, runFrames);
    }

    public Texture getPlayerIdleTexture() {
        return playerIdleTexture;
    }

    public Texture getPlayerRunTexture() {
        return playerRunTexture;
    }

    public Animation<TextureRegion> getTentacleWalkAnim() {
        return new Animation<>(0.1f,
            new TextureRegion(new Texture("tentacle/walk_0.png")),
            new TextureRegion(new Texture("tentacle/walk_1.png")),
            new TextureRegion(new Texture("tentacle/walk_2.png")),
            new TextureRegion(new Texture("tentacle/walk_3.png"))
        );
    }

    private void loadTreeAssets() {
        Array<TextureRegion> treeIdleFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            treeIdleFrames.add(new TextureRegion(new Texture("TreeMonster/T_TreeMonster_" + i + ".png")));
        }
        treeIdleAnim = new Animation<>(0.15f, treeIdleFrames);

        Texture walkSheet = new Texture("TreeMonster/T_TreeMonsterWalking.png");
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/4, walkSheet.getHeight());
        Array<TextureRegion> walkFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            walkFrames.add(tmp[0][i]);
        }
        treeWalkAnim = new Animation<>(0.1f, walkFrames);

        for (int i = 0; i < 2; i++) {
            treePowerupTexture[i] = new Texture("TreeMonster/T_PowerupTreeArrows_" + i + ".png");
        }
    }

    public void dispose() {
        skin.dispose();
        smgTexture.dispose();
        bulletTexture.dispose();
        playerIdleTexture.dispose();
        playerRunTexture.dispose();

        // اضافه کردن dispose برای بافت‌های جدید (تغییر اصلی 4)
        for (TextureRegion region : eyebatFlyAnim.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : tentacleWalkAnim.getKeyFrames()) {
            region.getTexture().dispose();
        }

        for (Texture tex : treePowerupTexture) {
            if (tex != null) tex.dispose();
        }
    }

    public Texture get(String path, Class<Texture> type) {
        if (type == Texture.class) {
            return new Texture(Gdx.files.internal(path));
        }
        return null;
    }

    public Animation<TextureRegion> getEyebatFlyAnim() {
        return eyebatFlyAnim; // تغییر از ساخت جدید به بازگرداندن انیمیشن از پیش بارگذاری شده
    }

    public Texture getTexture() {
        return smgTexture;
    }

    public Skin getSkin() { return skin; }
    public Animation<TextureRegion> getTreeIdleAnim() { return treeIdleAnim; }
    public Animation<TextureRegion> getCharacterIdleAnimation() { return characterIdleAnim; }
    public Animation<TextureRegion> getCharacterRunAnimation() { return characterRunAnim; }

    private Animation<TextureRegion> weaponReloadAnim;

    public void loadWeaponReloadAnimation() {
        Array<TextureRegion> reloadFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            reloadFrames.add(new TextureRegion(new Texture("smg/Reload_" + i + ".png")));
        }
        weaponReloadAnim = new Animation<>(0.1f, reloadFrames);
    }

}
