package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.RegisterController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

public class RegisterScreen extends ScreenAdapter {
    private Main game;
    private UserManager userManager;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private RegisterController registerController;

    private TextField usernameField;
    private TextField passwordField;
    private SelectBox<String> securityQuestionBox;
    private TextField securityAnswerField;
    private Label messageLabel;

    public RegisterScreen(Main game, UserManager userManager) {
        this.game = game;
        this.userManager = userManager;
        this.skin = GameAssetManager.getInstance().getSkin();
        this.registerController = new RegisterController(game, userManager, this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        securityQuestionBox = new SelectBox<>(skin);
        securityQuestionBox.setItems("How old are you?");

        securityAnswerField = new TextField("", skin);
        messageLabel = new Label("", skin);

        TextButton registerButton = new TextButton("Register", skin);
        TextButton guestButton = new TextButton("Skip / Guest", skin);
        TextButton backButton = new TextButton("Back", skin);

        registerButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String question = securityQuestionBox.getSelected();
                String answer = securityAnswerField.getText().trim();

                String result = registerController.handleRegister(username, password, question, answer);
                if (!result.equals("success")) {
                    messageLabel.setText(result);
                }
            }
        });

        guestButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    System.out.println("1");
                    userManager.createGuestUser();
                    System.out.println("2");
                    User currentUser = userManager.getCurrentUser();
                    System.out.println("3");
                    Skin skin = GameAssetManager.getInstance().getSkin();
                    MainMenuController controller = new MainMenuController(game, userManager);
                    // اگر متد hasSave وجود دارد می‌توان از آن استفاده کرد
                    game.setScreen(new MainMenuView(controller, skin, currentUser, false));
                }catch (Exception e) {
                    System.out.println("message : "+e.getMessage());
                }
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen(game, userManager));
            }
        });

        table.add(new Label("Username:", skin));
        table.add(usernameField).row();

        table.add(new Label("Password:", skin));
        table.add(passwordField).row();

        table.add(new Label("Security Question:", skin));
        table.add(securityQuestionBox).row();

        table.add(new Label("Answer:", skin));
        table.add(securityAnswerField).row();

        table.add(registerButton).colspan(2).row();
        table.add(guestButton).colspan(2).row();
        table.add(backButton).colspan(2).row();
        table.add(messageLabel).colspan(2).row();
    }

    @Override
    public void render(float delta) {
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
