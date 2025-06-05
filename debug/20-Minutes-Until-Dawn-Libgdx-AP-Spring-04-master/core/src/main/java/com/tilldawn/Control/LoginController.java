package com.tilldawn.Control;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;
import com.tilldawn.View.ForgotPasswordScreen;
import com.tilldawn.View.LoginScreen;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.RegisterScreen;

public class LoginController {
    private Main game;
    private UserManager userManager;
    private LoginScreen screen;

    public LoginController(Main game, UserManager userManager, LoginScreen screen) {
        this.game = game;
        this.userManager = userManager;
        this.screen = screen;
    }

    public void handleLogin(String username, String password) {
        User user = userManager.findUser(username);

        if (user == null) {
            screen.showMessage("User not found.");
        } else if (!user.getPassword().equals(password)) {
            screen.showMessage("Incorrect password.");
        } else {
            userManager.setCurrentUser(user);
            game.setScreen(new MainMenuView(
                new MainMenuController(game, userManager),
                GameAssetManager.getInstance().getSkin(),
                user,
//                SaveManager.hasSave(user)
                false
            ));
        }
    }

    public void goToRegister() {
        game.setScreen(new RegisterScreen(game, userManager));
    }

    public void goToForgotPassword() {
        game.setScreen(new ForgotPasswordScreen(game, userManager));
    }
}
