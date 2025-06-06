// com.tilldawn.Model.SaveData.java
package com.tilldawn.Model;

import com.tilldawn.Model.AbilityType;
import java.util.List;

public class SaveData {
    public String username;
    public int xp, level, kills, lives;
    public float hp;
    public float playerX, playerY;
    public List<AbilityType> abilities;
    public float remainingTime;
    public boolean infiniteAmmo;
}
