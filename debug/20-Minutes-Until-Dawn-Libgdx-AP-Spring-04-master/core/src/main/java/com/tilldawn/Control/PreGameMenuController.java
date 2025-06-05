package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;

import java.awt.event.InputEvent;

public class PreGameMenuController {
    private PreGameMenuView view;
    private Pregame pregame;
    private final GameSettings settings;
    private static Hero selectedHero;
    private String selectedHeroName;
    private UserManager userManager;
    private final Main game;

    public PreGameMenuController(GameSettings settings,UserManager userManager,Main game) {
        this.game = game;
        this.settings = settings;
        this.userManager = userManager;

        settings.addKeyBinding("W");
        settings.addKeyBinding("A");
        settings.addKeyBinding("S");
        settings.addKeyBinding("D");

        settings.addCheatCode("FKFK");
        settings.addCheatCode("7f8f");

        settings.addAbility(new Ability("Fireball", "Shoots a fiery projectile"));
        settings.addAbility(new Ability("Dash", "Quickly moves forward"));
    }

    public void selectHero(Hero hero) {
        System.out.println(">> reached selectHero");
        Gdx.app.log("DEBUG", "In selectHero(), hero = " + hero.getType());
        settings.setSelectedHero(hero);
    }

    public GameSettings getSettings() {
        return settings;
    }


    public void setView(PreGameMenuView view) {
        this.view = view;
        this.pregame = new Pregame();
    }

    public void handlePreGameMenuButtons() {
        view.getStartGameButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (settings.getSelectedHero() == null) {
                    Gdx.app.log("PreGame", "Please select a hero first!");
                    return;
                }

                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(
                    new GameView(
                        new GameController(settings,userManager,game),
                        GameAssetManager.getInstance().getSkin(),settings
                    )
                );
            }
        });
    }

    public void setSelectedHero(Hero selectedHero) {
        this.selectedHero = selectedHero;
    }



    public void selectHero(HeroType heroType) {
        this.selectedHero = new Hero(heroType);
        Gdx.app.log("DEBUG", "Hero selected: " + selectedHero.getName());
    }

    public static Hero getSelectedHero(){
        return selectedHero;
    }


}
