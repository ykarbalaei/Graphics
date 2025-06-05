package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Control.*;
import com.tilldawn.Main;
import com.tilldawn.View.GameOverView;
import com.tilldawn.View.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Player {
    private Texture playerTexture;
    private Sprite playerSprite;
    private CollisionRect rect;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    private float HP;
    private float time = 0;
    private float speed ;
    private boolean isAlive;
    private int lives;
    private final String username;
    private int kills=0;
    private int score=0;
    //اینجا رو باید روش وقت بزارم برای مشخص کردن استفاده از اسلحه یا نوع آن
    private WeaponType weapon;
    private HeroType hero;
    private int level;
    private int xp=0;
    private User currentUser;
    private List<AbilityType> abilities = new ArrayList<>();





    public Player(UserManager userManager) {
        this.username = userManager.getCurrentUser().getUsername();
        this.isAlive = true;
        this.lives = 5;
        this.hero= HeroController.getHeroSelected();
        //this.weapon= WeaponController.getWeapon
        this.playerTexture = GameAssetManager.getInstance().getPlayerIdleTexture();
        this.playerSprite = new Sprite(playerTexture);
        this.HP=hero.getHp();
        this.speed = hero.getSpeed();
        position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        playerSprite.setPosition(position.x, position.y);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        rect = new CollisionRect(position.x, position.y, playerSprite.getWidth(), playerSprite.getHeight());
        this.level = 1;
        this.weapon= WeaponMenuController.getWeaponSelected();
        this.currentUser = userManager.getCurrentUser();
    }

    public void update(float deltaTime) {

        handleInput();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        playerSprite.setPosition(position.x, position.y);
        rect.move(position.x, position.y);

        Gdx.app.log("PLAYER_UPDATE",
            String.format("Pos: (%.1f,%.1f) | Vel: (%.1f,%.1f)",
                position.x, position.y, velocity.x, velocity.y));
    }


    public void handleInput() {
        velocity.set(0, 0);
        int upKey =SettingsManager.getInstance().getKeyUp();
        int downKey =SettingsManager.getInstance().getKeyDown();
        int leftKey =SettingsManager.getInstance().getKeyLeft();
        int rightKey =SettingsManager.getInstance().getKeyRight();

        if (Gdx.input.isKeyPressed(upKey)) velocity.y = 1;
        if (Gdx.input.isKeyPressed(downKey)) velocity.y = -1;
        if (Gdx.input.isKeyPressed(leftKey)) velocity.x = -1;
        if (Gdx.input.isKeyPressed(rightKey)) velocity.x = 1;

        if (!velocity.isZero()) {
            velocity.nor().scl(speed);
            isPlayerIdle = false;
            isPlayerRunning = true;
        } else {
            isPlayerIdle = true;
            isPlayerRunning = false;
        }
    }

    public void setPosX(float x) {
        position.x = x;
    }
    public void setPosY(float y) {
        position.y = y;
    }
    public Texture getPlayerTexture() {
        return playerTexture;
    }
    public Sprite getPlayerSprite() {
        return playerSprite;
    }
    public float getPosX() {return position.x;}
    public float getPosY() {return position.y;}
    public float getHP() {return HP;}
    public CollisionRect getBounds() {return rect;}
    public boolean isPlayerIdle() {return isPlayerIdle;}
    public boolean isPlayerRunning() {return isPlayerRunning;}
    public float getTime() {return time;}
    public void setTime(float time) {this.time = time;}
    public float getX() {return position.x;}
    public float getY() {return position.y;}
    public void render() {playerSprite.draw(Main.getBatch());}
    public boolean isAlive() {return isAlive;}

    public int getXp() {
        return xp;
    }

    public HeroType getHero() {return hero;}
    public void setHero(HeroType hero) {this.hero = hero;}
    public WeaponType getWeapon() {return weapon;}
    public void setWeapon(WeaponType weapon) {this.weapon = weapon;}
    public int getScore() {return score;}
    public void setScore(int score) {this.score = score;}
    public int getKills() {return kills;}
    public void setKills(int kills) {this.kills = kills;}
    public String getUsername() {return username;}
    public int getLives() {return lives;}
    public void setLives(int lives) {this.lives = lives;}
    public void setAlive(boolean alive) {isAlive = alive;}
    public float getSpeed() {return speed*weapon.getSpeedModifier();}
    public void setSpeed(float speed) {this.speed = speed;}
    public int getLevel() {return level;}
    public List<AbilityType> getAbilities() {return abilities;}

    public void addAbility(AbilityType ability) {
        abilities.add(ability);
        applyAbilityEffect(ability); // این متد رو بعداً می‌سازیم
    }


    public void setScorePlayer(){
        int elapsed =(int) TimerController.getInstance().getElapsedTime();
        this.score= kills * elapsed;
        currentUser.allScore(score);
        currentUser.allKills(kills);
        currentUser.allTime(elapsed);
    }
    public void reduceHP(float amount) {
        HP -= amount;
        if (HP <= 0) {
            float d = -HP;
            if (lives == 0) {
                isAlive=false;
            } else {
                lives--;
                HP = hero.getHp() - d;
            }
        }
        Gdx.app.log("PLAYER", "HP: " + HP + " | Lives: " + lives);
    }
    public void increaseHP(float amount) {
        HP += amount;
        if (HP >= hero.getHp()) {
            float d = HP-hero.getHp();
            if (lives < 5) {
                lives++;
            }
        }
    }

    public void increaseLives(int amount) {
        lives += amount;
        System.out.println("lives: " + lives);
    }


    public void increasekills(int amount) {
        kills += amount;
        System.out.println("kills: " + kills);
    }
    public void gainXP(int amount) {
        System.out.println("Gain XP: " + amount+"level: "+level);
        xp += amount;
        if (xp==level*20) { // یا فرمول دلخواه
            xp -=level*20;
            increaselevel(1);
        }
    }
    public void increaselevel(int amount) {
        System.out.println("Increase level: " + amount);
        level += amount;
        GameView.showAbilitySelectionMenu();
//        List<AbilityType> allAbilities = Arrays.asList(AbilityType.values());
//        Collections.shuffle(allAbilities);
//        List<AbilityType> offered = allAbilities.subList(0, 3);

    }

    private void applyAbilityEffect(AbilityType ability) {
        try{
        switch (ability) {
            case EXTRA_DAMAGE:
                weapon.setDamage(weapon.getDamage() + 10); // فرض بر این که setDamage داریم
                break;
            case EXTRA_SPEED:
                this.speed += 100;
                break;
            case HEAL:
                this.HP = Math.min(hero.getHp(), HP + 30);
                break;
            case IMMUNITY:
                // Flag for temporary immunity (نیاز به پیاده‌سازی)
                break;
            case MORE_BULLETS:
                weapon.setFireRate(weapon.getfireRate() + 2); // فقط اگه setter داریم
                break;
        }}
        catch(NullPointerException e){
            System.out.println("NullPointerException: " + e.getMessage());
        }
    }


}
