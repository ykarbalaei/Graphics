package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.TalentMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.UserManager;

public class TalentHintView implements Screen {
    private Stage stage;
    private final TalentMenuController controller;
    private final TextButton backButton;
    private final Skin skin;
    private final Label title;
    private final Main game;
    private UserManager userManager;

    public TalentHintView(Main game, UserManager userManager, GameSettings settings, Skin skin) {
        this.game = game;
        this.controller = new TalentMenuController(settings);
        this.skin       = skin;
        this.backButton = new TextButton("← Back", skin);
        this.title      = new Label("Hints & Talents", skin);
        this.userManager = userManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.top();

        table.add(title).colspan(2).padTop(20).padBottom(20).row();

        // --- Hero Guides ---
        table.add(new Label("Hero Guides:", skin)).left().pad(5);
        List<String> heroList = new List<>(skin);
        heroList.setItems(controller.getHeroGuides());
        heroList.getStyle().font.getData().setScale(0.85f);
        table.add(new ScrollPane(heroList, skin)).width(700).height(175).pad(5).row();

        // --- Active Keys ---
        table.add(new Label("Active Keys:", skin)).left().pad(5);
        List<String> keyList = new List<>(skin);
        keyList.setItems(controller.getActiveKeys());
        keyList.getStyle().font.getData().setScale(0.85f);
        table.add(new ScrollPane(keyList, skin)).width(700).height(175).pad(5).row();

        // --- Cheat Codes ---
        table.add(new Label("Cheat Codes:", skin)).left().pad(5);
        List<String> cheatList = new List<>(skin);
        cheatList.setItems(controller.getCheats());
        cheatList.getStyle().font.getData().setScale(0.85f);
        table.add(new ScrollPane(cheatList, skin)).width(700).height(125).pad(5).row();

        // --- Abilities ---
        table.add(new Label("Abilities:", skin)).left().pad(5);
        List<String> abilityList = new List<>(skin);
        abilityList.setItems(controller.getAbilities());
        abilityList.getStyle().font.getData().setScale(0.85f);
        table.add(new ScrollPane(abilityList, skin)).width(700).height(175).pad(5).row();

        // --- Back Button ---
        table.add(backButton).colspan(2).padTop(30);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(
                    new MainMenuView(
                        new MainMenuController(game, userManager),
                        skin,
                        userManager.getCurrentUser(),
                        false
                    )
                );
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { dispose(); }
    @Override public void dispose() { stage.dispose(); }
}
