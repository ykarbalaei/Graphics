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
import com.tilldawn.Control.WeaponMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.Weapon;
import com.tilldawn.Model.WeaponType;

public class WeaponMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final SelectBox<WeaponType> weaponBox;
    private final TextButton nextButton;
    //private final WeaponMenuController controller;

    public WeaponMenuView(WeaponMenuController controller, Skin skin) {
        //this.controller = controller;
        this.title      = new Label("Select Your Weapon", skin);
        this.weaponBox  = new SelectBox<>(skin);
        this.nextButton = new TextButton("Next", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // پر کردن لیست سلاح‌ها
        Array<WeaponType> types = new Array<>(WeaponType.values());
        weaponBox.setItems(types);

        // چینش با Table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(title).pad(10).row();
        table.add(weaponBox).width(200).pad(10).row();
        table.add(nextButton).pad(10);
        stage.addActor(table);

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                WeaponType chosenType = WeaponMenuController.getWeaponSelected();
                Weapon      weapon    = new Weapon(chosenType);
                //controller.selectWeapon(weapon);

                //GameSettings settings = controller.getSettings();
                DurationMenuController durationCtrl =
                    new DurationMenuController();

                Main.getMain().setScreen(
                    new DurationMenuView(
                        durationCtrl,                                // فقط controller
                        GameAssetManager.getInstance().getSkin()
                    )
                );
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height)    {}
    @Override public void pause()                         {}
    @Override public void resume()                        {}
    @Override public void hide()      { dispose();      }
    @Override public void dispose()   { stage.dispose();}
}
