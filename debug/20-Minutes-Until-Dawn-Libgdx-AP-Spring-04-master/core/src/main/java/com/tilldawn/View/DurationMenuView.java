package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.DurationMenuController;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameSettings;

public class DurationMenuView implements Screen {
    private Stage stage;
    private final Label       title;
    private final SelectBox<Integer> durationBox;
    private final TextButton  startButton;
    private final DurationMenuController controller;
//    private final GameSettings settings;

    public DurationMenuView(DurationMenuController controller, Skin skin) {
        this.controller   = controller;
        this.title        = new Label("Select Duration", skin);
        this.durationBox  = new SelectBox<>(skin);
        this.startButton  = new TextButton("Start Game", skin);

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // پر کردن لیست مدت‌ها
        Array<Integer> options = new Array<>(new Integer[] {2, 5, 10, 20});
        durationBox.setItems(options);

        // چینش با Table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(title).pad(10).row();
        table.add(durationBox).width(100).pad(10).row();
        table.add(startButton).pad(10);
        stage.addActor(table);

        // Listener برای دکمه شروع بازی
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int chosen = durationBox.getSelected();
                controller.selectDuration(chosen);

                // تنظیمات نهایی رو می‌گیریم
//                GameSettings settings = controller.getSettings();
                System.out.println("game startingggggggggg");
                // راه‌اندازی شروع بازی
//                Main.getMain().setScreen(
//                    new GameView(
//                        new GameController(settings),
//                        GameAssetManager.getInstance().getSkin(),settings
//                    )
//                );
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height)  {}
    @Override public void pause()                       {}
    @Override public void resume()                      {}
    @Override public void hide()   { dispose();         }
    @Override public void dispose(){ stage.dispose();   }
}
