package com.tilldawn.Control;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.RegisterScreen;

public class RegisterController {
    private Main game;
    private UserManager userManager;
    private RegisterScreen screen;

    public RegisterController(Main game, UserManager userManager, RegisterScreen screen) {
        this.game = game;
        this.userManager = userManager;
        this.screen = screen;
    }

    public String handleRegister(String username, String password, String securityQuestion, String securityAnswer) {
        if (userManager.isUsernameTaken(username)) {
            return "Username is already taken.";
        }

        if (!isPasswordStrong(password)) {
            return "Weak password";
        }

        String avatarPath = userManager.getRandomAvatarPath();
        User newUser = userManager.registerUser(username, password, securityQuestion, securityAnswer, avatarPath);

        MainMenuController controller = new MainMenuController(game, userManager);
        Skin skin = GameAssetManager.getInstance().getSkin();
        game.setScreen(new MainMenuView(controller, skin, newUser, false));
        return "success";
    }

    static boolean isPasswordStrong(String password) {
        return password.length() >= 8 &&
            password.matches(".*[!@%$#&*()_].*") &&
            password.matches(".*\\d.*") &&
            password.matches(".*[A-Z].*");
    }
}
