package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

public class ForgotPasswordScreen extends ScreenAdapter {
    private Main game;
    private UserManager userManager;
    private Stage stage;
    private Skin skin;

    private TextField usernameField;
    private Label securityQuestionLabel;
    private TextField answerField;
    private TextField newPasswordField;
    private Label messageLabel;

    private User user;

    public ForgotPasswordScreen(Main game, UserManager userManager) {
        this.game = game;
        this.userManager = userManager;
        this.skin = GameAssetManager.getInstance().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        usernameField = new TextField("", skin);
        answerField = new TextField("", skin);
        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        securityQuestionLabel = new Label("Enter your username to see your security question.", skin);
        messageLabel = new Label("", skin);

        TextButton checkUserButton = new TextButton("Check Username", skin);
        TextButton submitButton = new TextButton("Reset Password", skin);
        TextButton backButton = new TextButton("Back", skin);

        checkUserButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText().trim();
                user = userManager.getUserByUsername(username);
                if (user == null) {
                    messageLabel.setText("User not found.");
                } else {
                    securityQuestionLabel.setText(user.getSecurityQuestion());
                    messageLabel.setText("Answer the question to reset password.");
                }
            }
        });

        submitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (user == null) {
                    messageLabel.setText("Please check username first.");
                    return;
                }

                String answer = answerField.getText().trim();
                if (!answer.equalsIgnoreCase(user.getSecurityAnswer())) {
                    messageLabel.setText("Incorrect answer.");
                    return;
                }

                String newPassword = newPasswordField.getText().trim();
                if (newPassword.length() < 8) {
                    messageLabel.setText("Password too short.");
                    return;
                }

                user.setPassword(newPassword);
                userManager.updateUser(user);
                messageLabel.setText("Password reset successful!");
                userManager.saveUser();
                game.setScreen(new LoginScreen(game, userManager));
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen(game, userManager));
            }
        });

        table.add(new Label("Username:", skin)); table.add(usernameField).row();
        table.add(checkUserButton).colspan(2).row();
        table.add(new Label("Security Question:", skin)).colspan(2).row();
        table.add(securityQuestionLabel).colspan(2).row();
        table.add(new Label("Answer:", skin)); table.add(answerField).row();
        table.add(new Label("New Password:", skin)); table.add(newPasswordField).row();
        table.add(submitButton).colspan(2).row();
        table.add(backButton).colspan(2).row();
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
    }
}
