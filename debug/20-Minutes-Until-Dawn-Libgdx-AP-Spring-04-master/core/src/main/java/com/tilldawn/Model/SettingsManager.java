package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

public class SettingsManager {
    private static SettingsManager instance;

    private final Preferences prefs;

    public int getKeyDown() {
        return prefs.getInteger("key_down", Input.Keys.S);
    }

    public void setKeyDown(int keycode) {
        prefs.putInteger("key_down", keycode);
    }

    public int getKeyLeft() {
        return prefs.getInteger("key_left", Input.Keys.A);
    }

    public void setKeyLeft(int keycode) {
        prefs.putInteger("key_left", keycode);
    }

    public int getKeyRight() {
        return prefs.getInteger("key_right", Input.Keys.D);
    }

    public void setKeyRight(int keycode) {
        prefs.putInteger("key_right", keycode);
    }

    private SettingsManager() {
        prefs = Gdx.app.getPreferences("game_settings");
    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public float getMusicVolume() {
        return prefs.getFloat("music_volume", 0.5f);
    }

    public void setMusicVolume(float volume) {
        prefs.putFloat("music_volume", volume);
    }

    public String getCurrentMusic() {
        return prefs.getString("current_music", "track1.mp3");
    }

    public void setCurrentMusic(String track) {
        prefs.putString("current_music", track);
    }

    public boolean isSfxEnabled() {
        return prefs.getBoolean("sfx_enabled", true);
    }

    public void setSfxEnabled(boolean enabled) {
        prefs.putBoolean("sfx_enabled", enabled);
    }

    public boolean isReloadAutoEnabled() {
        return prefs.getBoolean("reload_auto", false);
    }

    public void setReloadAutoEnabled(boolean enabled) {
        prefs.putBoolean("reload_auto", enabled);
    }

    public boolean isGrayscaleEnabled() {
        return prefs.getBoolean("grayscale", false);
    }

    public void setGrayscaleEnabled(boolean enabled) {
        prefs.putBoolean("grayscale", enabled);
    }

    public int getKeyUp() {
        return prefs.getInteger("key_up", com.badlogic.gdx.Input.Keys.W);
    }

    public void setKeyUp(int keycode) {
        prefs.putInteger("key_up", keycode);
    }

    public void save() {
        prefs.flush();
    }


}
