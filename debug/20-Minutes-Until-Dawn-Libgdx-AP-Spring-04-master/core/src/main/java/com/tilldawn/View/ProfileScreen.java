package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.ProfileController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProfileScreen extends ScreenAdapter {
    private final Main game;
    private final Stage stage;
    private final Skin skin;
    private final User currentUser;
    private final ProfileController controller;
    private final Map<String, Texture> avatarTextures = new HashMap<>();

    private TextField usernameField;
    private TextField passwordField;
    private Label messageLabel;
    private Image avatarImage;

    public ProfileScreen(Main game, UserManager userManager) {
        System.out.println("ProfileScreen");
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.skin = GameAssetManager.getInstance().getSkin();
        this.currentUser = userManager.getCurrentUser();
        this.controller = new ProfileController(currentUser, userManager);

        loadAvatars();
        buildUI(userManager);
        // initial avatar load
        setNewAvatar(currentUser.getAvatarPath());
        System.out.println("ProfileScreen loaded");
    }

    private void loadAvatars() {
        System.out.println("loadAvatars");
        String[] avatarFiles = {"avatar1.png", "avatar2.png", "avatar3.png"};
        for (String avatar : avatarFiles) {
            avatarTextures.put(avatar, new Texture(Gdx.files.internal("avatars/" + avatar)));
        }
    }

    private void buildUI(UserManager userManager) {
        System.out.println("buildUI");
        usernameField = new TextField(currentUser.getUsername(), skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        messageLabel = new Label("", skin);
        avatarImage = new Image();

        // Buttons
        TextButton changeUsernameBtn = new TextButton("Change Username", skin);
        changeUsernameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (controller.changeUsername(usernameField.getText())) {
                    messageLabel.setText("Username changed!");
                } else {
                    messageLabel.setText("Username is taken!");
                }
            }
        });

        TextButton changePasswordBtn = new TextButton("Change Password", skin);
        changePasswordBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (controller.changePassword(passwordField.getText(), usernameField.getText())) {
                    currentUser.setPassword(passwordField.getText());
                    messageLabel.setText("Password changed.");
                } else {
                    messageLabel.setText("Weak password.");
                }
            }
        });

        TextButton deleteAccountBtn = new TextButton("Delete Account", skin);
        deleteAccountBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.deleteAccount();
                game.setScreen(new LoginScreen(game, userManager));
            }
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuView(new MainMenuController(game, userManager), skin, currentUser, false));
            }
        });

        SelectBox<String> avatarSelectBox = new SelectBox<>(skin);
        avatarSelectBox.setItems(avatarTextures.keySet().toArray(new String[0]));
        TextButton applyAvatarBtn = new TextButton("Set Avatar", skin);
        applyAvatarBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String sel = avatarSelectBox.getSelected();
                setNewAvatar("avatars/" + sel);
                controller.changeAvatarToInternal(sel, userManager);
            }
        });

        TextButton uploadAvatarBtn = new TextButton("Upload Avatar", skin);
        uploadAvatarBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String path = Main.fileChooser.chooseFile();
                if (path != null && new File(path).exists()) {
                    setNewAvatar(path);
                }
            }
        });

        // Layout
        Table left = new Table(skin).top().pad(10);
        left.add(new Label("Username:", skin)).left(); left.row();
        left.add(usernameField).fillX().width(200); left.row();
        left.add(changeUsernameBtn).padTop(5); left.row();
        left.add(new Label("Password:", skin)).left().padTop(10); left.row();
        left.add(passwordField).fillX().width(200); left.row();
        left.add(changePasswordBtn).padTop(5); left.row();
        left.add(deleteAccountBtn).padTop(20); left.row();
        left.add(messageLabel).padTop(10); left.row();
        left.add(backButton).padTop(10);

        Table right = new Table(skin).top().pad(10);
        right.add(new Label("Select Avatar:", skin)).left(); right.row();
        right.add(avatarSelectBox).fillX().width(200); right.row();
        right.add(applyAvatarBtn).padTop(5); right.row();
        right.add(new Label("Upload Avatar:", skin)).padTop(10).left(); right.row();
        right.add(uploadAvatarBtn).padTop(5); right.row();
        right.add(avatarImage).size(100).center().padTop(10);

        Table main = new Table();
        main.setFillParent(true);
        main.pad(20);
        main.add(left).expandX().top();
        main.add(right).expandX().top();

        ScrollPane sp = new ScrollPane(main, skin);
        sp.setFadeScrollBars(false);
        sp.setScrollingDisabled(true, false);

        Table container = new Table();
        container.setFillParent(true);
        container.add(sp).expand().fill();

        stage.addActor(container);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        avatarTextures.values().forEach(Texture::dispose);
    }

    public void setNewAvatar(String path) {
        System.out.println("setNewAvatar");
        if (path == null) return;
        FileHandle handle;
        if (new File(path).exists()) {
            handle = Gdx.files.absolute(path);
        } else {
            handle = Gdx.files.internal(path);
        }
        Texture tex = new Texture(handle);
        avatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));
        currentUser.setAvatarPath(path);
        UserManager.getInstance().saveUser();
    }

}
