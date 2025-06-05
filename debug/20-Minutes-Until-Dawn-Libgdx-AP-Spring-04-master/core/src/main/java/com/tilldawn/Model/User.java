package com.tilldawn.Model;

import java.time.Duration;

public class User {
    private String username;
    private String avatarPath;
    private int score;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private int kills;
    private int survivalTimeInSeconds;
    private HeroType selectedHero;
    private WeaponType selectedWeapon;
    private int selectedDuration;
    private int time;
    public User() {
    }
    public User(String username, String avatarPath, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.avatarPath = avatarPath;
        this.score = 0;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
//        this.selectedHero =HeroType.SHANA ;
        this.selectedWeapon=WeaponType.SMG_DUAL;
    }


    public String getUsername() {
        return username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getScore() {
        return score;
    }

    public void allScore(int score) {
        this.score+=score;
    }
    public void allTime(int time) {
        this.time+=time;
    }
    public void allKills(int kills) {
        this.kills+=kills;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getKills() {
        return kills;
    }

    public int getSurvivalTimeInSeconds() {
        return survivalTimeInSeconds;
    }

    public int getTime() {
        return time;
    }

    public HeroType getSelectedHero() {return selectedHero;}
    public void setSelectedHero(HeroType selectedHero) {this.selectedHero = selectedHero;}
    public WeaponType getSelectedWeapon() {return selectedWeapon;}
    public void setSelectedWeapon(WeaponType selectedWeapon) {this.selectedWeapon = selectedWeapon;}
    public int getSelectedDuration() {return selectedDuration;}
    public void setSelectedDuration(int selectedDuration) {this.selectedDuration = selectedDuration;}
}
