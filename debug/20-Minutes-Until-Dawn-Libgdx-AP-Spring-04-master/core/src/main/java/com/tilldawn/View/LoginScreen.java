package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.UserManager;


public class LoginScreen extends ScreenAdapter {
    private Main game;
    private LoginController controller;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;

    private TextField usernameField;
    private TextField passwordField;
    private Label messageLabel;

    public LoginScreen(Main game, UserManager userManager) {
        this.game = game;
        this.controller = new LoginController(game, userManager, this);
        this.skin = GameAssetManager.getInstance().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        messageLabel = new Label("", skin);

        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin);
        TextButton forgotButton = new TextButton("Forgot Password", skin);

        loginButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogin(usernameField.getText(), passwordField.getText());
            }
        });

        registerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.goToRegister();
            }
        });

        forgotButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                controller.goToForgotPassword();
            }
        });

        table.add(new Label("Username:", skin));
        table.add(usernameField).row();
        table.add(new Label("Password:", skin));
        table.add(passwordField).row();
        table.add(loginButton).colspan(2).row();
        table.add(registerButton).colspan(2).row();
        table.add(forgotButton).colspan(2).row();
        table.add(messageLabel).colspan(2).row();
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
        backgroundTexture.dispose();
    }

    public void showMessage(String msg) {
        messageLabel.setText(msg);
    }
}
