package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.ScoreboardController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

import java.util.List;

public class ScoreboardView implements Screen {
    private final Stage stage;
    private final ScoreboardController controller;
    private final Skin skin;
    private Table table;
    private final TextButton backButton;
    private final UserManager userManager;
    private  final Main game;
    private String currentSort = "Score";

    public ScoreboardView(Skin skin, UserManager userManager, Main game) {
        this.skin = skin;
        this.controller = new ScoreboardController(userManager);
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.backButton = new TextButton("← Back", skin);
        this.userManager = userManager;
        this.game = game;


    }

    @Override
    public void show() {
        table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);
        rebuildTable(controller.getSortedByScore()); // پیش‌فرض
    }

    private void rebuildTable(List<User> sortedList) {
        table.padTop(20).top();

        Label sortLabel = new Label("Sorted by: " + currentSort, skin);
        sortLabel.setColor(Color.LIGHT_GRAY);
        table.add(sortLabel).colspan(4).padBottom(10).row();

        Label title = new Label("Scoreboard", skin);
        table.add(title).colspan(5).padTop(20).row();

        TextButton sortScore = new TextButton("Sort by Score", skin);
        TextButton sortName  = new TextButton("Sort by Username", skin);
        TextButton sortKills = new TextButton("Sort by Kills", skin);
        TextButton sortTime  = new TextButton("Sort by Survival", skin);

        sortScore.addListener(e -> { currentSort = "Score"; refresh(controller.getSortedByScore()); return true; });
        sortName.addListener(e -> { currentSort = "Username"; refresh(controller.getSortedByUsername()); return true; });
        sortKills.addListener(e -> { currentSort = "Kills"; refresh(controller.getSortedByKills()); return true; });
        sortTime.addListener(e -> { currentSort = "Survival"; refresh(controller.getSortedBySurvivalTime()); return true; });

        table.add(sortScore).pad(5);
        table.add(sortName).pad(5);
        table.add(sortKills).pad(5);
        table.add(sortTime).pad(5).row();

        table.add("Username").pad(5);
        table.add("Score").pad(5);
        table.add("Kills").pad(5);
        table.add("Survival").pad(5).row();

        displayUsers(sortedList);

        table.add(backButton).colspan(4).padTop(50).bottom();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuController controller = new MainMenuController(game, userManager);
                game.setScreen(new MainMenuView(controller,
                    GameAssetManager.getInstance().getSkin(),
                    userManager.getCurrentUser(),
                    false));
            }
        });
    }



    private void displayUsers(List<User> users) {
        String current = "";
        if (UserManager.getInstance().getCurrentUser() != null) {
            current = userManager.getCurrentUser().getUsername();
        }
        boolean currentShown = false;
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            Label name  = new Label(u.getUsername(), skin);
            Label score = new Label(String.valueOf(u.getScore()), skin);
            Label kills = new Label(String.valueOf(u.getKills()), skin);
            Label time  = new Label(controller.formatSurvivalTime(u.getTime()), skin);


            if (i == 0) name.setColor(Color.GOLD);
            if (i == 1) name.setColor(new Color(0.75f, 0.75f, 0.75f, 1f)); // Silver
            if (i == 2) name.setColor(new Color(0.8f, 0.5f, 0.2f, 1f));

            if (u.getUsername().equals(current)) {
                name.setColor(Color.CYAN);
                score.setColor(Color.CYAN);
                kills.setColor(Color.CYAN);
                time.setColor(Color.CYAN);
                currentShown = true;
            }

            table.add(name).pad(5);
            table.add(score).pad(5);
            table.add(kills).pad(5);
            table.add(time).pad(5).row();
        }
        if (!currentShown && userManager.getCurrentUser() != null) {
            User u = userManager.getCurrentUser();

            Label name  = new Label(u.getUsername(), skin);
            Label score = new Label(String.valueOf(u.getScore()), skin);
            Label kills = new Label(String.valueOf(u.getKills()), skin);
            Label time  = new Label(controller.formatSurvivalTime(u.getTime()), skin);

            name.setColor(Color.CYAN);
            score.setColor(Color.CYAN);
            kills.setColor(Color.CYAN);
            time.setColor(Color.CYAN);

            table.add(name).pad(5);
            table.add(score).pad(5);
            table.add(kills).pad(5);
            table.add(time).pad(5).row();
        }
    }

    private void refresh(List<User> sorted) {
        table.clearChildren(); // فقط محتوا، نه کل table
        rebuildTable(sorted); // جدول رو از نو می‌سازه
    }

    @Override public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();

    }


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
