package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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
        table.top().padTop(30);

        // --- عنوان بالا ---
        title.setFontScale(1.5f);
        title.setAlignment(Align.center);
        table.add(title).colspan(2).center().padBottom(25).row();

        // متد کمکی برای ساخت سکشن
        addSection(table, "Hero Guides:", controller.getHeroGuides(), 180);
        addSection(table, "Active Keys:", controller.getActiveKeys(), 180);
        addSection(table, "Cheat Codes:", controller.getCheats(), 140);
        addSection(table, "Abilities:", controller.getAbilities(), 180);

        // --- دکمه Back ---
        backButton.getLabel().setFontScale(1.1f);
        table.add(backButton).colspan(2).padTop(30).width(200).height(50);

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

    private void addSection(Table table, String titleText, com.badlogic.gdx.utils.Array<String> items, int height) {
        Label sectionLabel = new Label(titleText, skin);
        sectionLabel.setFontScale(1.1f);
        sectionLabel.setColor(Color.SKY);
        table.add(sectionLabel).left().padBottom(5).padTop(10);

        List<String> list = new List<>(skin);
        list.setItems(items);
        list.getStyle().font.getData().setScale(0.85f);
        ScrollPane scroll = new ScrollPane(list, skin);
        scroll.setScrollingDisabled(true, false);
        table.add(scroll).width(700).height(height).pad(5).row();
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
