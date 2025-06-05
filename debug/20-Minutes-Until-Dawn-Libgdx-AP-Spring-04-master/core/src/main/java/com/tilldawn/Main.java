package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.PreGameMenuController;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;
import com.tilldawn.View.LoginScreen;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.PreGameMenuView;
import com.tilldawn.View.ProfileScreen;
import com.tilldawn.util.FileChooserInterface;

public class Main extends Game {
    public static FileChooserInterface fileChooser;
    private static Main instance;
    private SpriteBatch batch;
    private GameAssetManager assets;
    private UserManager userManager;

    public Main(FileChooserInterface chooser) {
        fileChooser = chooser;
    }

    public static Game getMain() {
      return instance;
    }

    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        assets = GameAssetManager.getInstance();
        assets.loadTextures();
        userManager = new UserManager();

        if (userManager.getCurrentUser() == null) {
            setScreen(new LoginScreen(this, userManager));
        } else {
            showMainMenu();
        }
    }

    public void showMainMenu() {
        User user = userManager.getCurrentUser();
//        boolean hasSave = SaveManager.hasSave(user);
        boolean hasSave = false;
        Skin skin = GameAssetManager.getInstance().getSkin();
        MainMenuController controller = new MainMenuController(this, userManager);

        setScreen(new MainMenuView(controller, skin, user, hasSave));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }

    // دسترسی سریع به SpriteBatch
    public static SpriteBatch getBatch() {
        return ((Main) Gdx.app.getApplicationListener()).batch;
    }

    // دسترسی به AssetManager
    public static GameAssetManager getAssets() {
        return ((Main) Gdx.app.getApplicationListener()).assets;
    }

    public void onAvatarDropped(String imagePath) {
        if (imagePath == null || !(imagePath.endsWith(".png") || imagePath.endsWith(".jpg"))) {
            System.err.println("Invalid avatar path: " + imagePath);
            return;
        }

        Screen current = getScreen();
        if (current instanceof ProfileScreen) {
            ProfileScreen profileScreen = (ProfileScreen) current;
            profileScreen.setNewAvatar(imagePath);
        } else {
            System.out.println("Profile screen is not currently active.");
        }
    }
}
