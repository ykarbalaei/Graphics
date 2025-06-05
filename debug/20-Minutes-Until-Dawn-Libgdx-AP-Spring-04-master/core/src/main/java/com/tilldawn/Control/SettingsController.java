package com.tilldawn.Control;

import com.tilldawn.Model.SettingsManager;

public class SettingsController {
    private SettingsManager settingsManager;

    public SettingsController(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public float getMusicVolume() {
        return settingsManager.getMusicVolume();
    }

    public void setMusicVolume(float volume) {
        settingsManager.setMusicVolume(volume);
    }

    public String getCurrentMusic() {
        return settingsManager.getCurrentMusic();
    }

    public boolean isSfxEnabled() {
        return settingsManager.isSfxEnabled();
    }

    public void setSfxEnabled(boolean enabled) {
        settingsManager.setSfxEnabled(enabled);
    }

    public boolean isReloadAutoEnabled() {
        return settingsManager.isReloadAutoEnabled();
    }

    public void setReloadAutoEnabled(boolean enabled) {
        settingsManager.setReloadAutoEnabled(enabled);
    }

    public void setCurrentMusic(String music) {
        settingsManager.setCurrentMusic(music);
    }

    public boolean isGrayscaleEnabled() {
        return settingsManager.isGrayscaleEnabled();
    }

    public void setGrayscaleEnabled(boolean enabled) {
        settingsManager.setGrayscaleEnabled(enabled);
    }

    public int getKeyUp() {
        return settingsManager.getKeyUp();
    }
    public void setKeyUp(int keycode) { settingsManager.setKeyUp(keycode); }

    public int getKeyDown() { return settingsManager.getKeyDown(); }
    public void setKeyDown(int keycode) { settingsManager.setKeyDown(keycode); }

    public int getKeyLeft() { return settingsManager.getKeyLeft(); }
    public void setKeyLeft(int keycode) { settingsManager.setKeyLeft(keycode); }

    public int getKeyRight() { return settingsManager.getKeyRight(); }
    public void setKeyRight(int keycode) { settingsManager.setKeyRight(keycode); }

    public void saveSettings() {
        settingsManager.save();
    }
}
