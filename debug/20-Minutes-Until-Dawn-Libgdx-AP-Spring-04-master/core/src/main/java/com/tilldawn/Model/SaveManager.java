// com.tilldawn.Model.SaveManager.java
package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class SaveManager {
    private static final String SAVE_PATH = "save_data.json";

    public static void saveGame(SaveData data) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SAVE_PATH);
        file.writeString(json.toJson(data), false);
    }

    public static SaveData loadGame() {
        FileHandle file = Gdx.files.local(SAVE_PATH);
        if (!file.exists()) return null;

        Json json = new Json();
        return json.fromJson(SaveData.class, file.readString());
    }

    public static boolean hasSave() {
        return Gdx.files.local(SAVE_PATH).exists();
    }

    public static void deleteSave() {
        Gdx.files.local(SAVE_PATH).delete();
    }
}
