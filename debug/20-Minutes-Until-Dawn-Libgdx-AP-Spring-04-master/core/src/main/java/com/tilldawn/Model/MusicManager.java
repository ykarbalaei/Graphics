package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicManager {
    private static MusicManager instance;
    private Music currentMusic;
    private float volume = 1.0f;
    private String currentTrackName;


    private MusicManager() {}

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public void playMusic(String fileName) {
        if (currentTrackName != null && currentTrackName.equals(fileName) && currentMusic != null && currentMusic.isPlaying()) {
            return;
        }

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentTrackName = fileName;
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        currentMusic.setLooping(true);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (currentMusic != null) {
            currentMusic.setVolume(volume);
        }
    }

    public String getCurrentTrackName() {
        return currentTrackName;
    }

    public void dispose() {
        if (currentMusic != null) {
            currentMusic.dispose();
        }
    }
}
