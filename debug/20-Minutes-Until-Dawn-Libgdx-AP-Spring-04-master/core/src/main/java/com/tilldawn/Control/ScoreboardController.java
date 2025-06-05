package com.tilldawn.Control;

import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardController {
    private final UserManager userManager;
    public ScoreboardController(UserManager userManager) {
        this.userManager = userManager;
    }
    public List<User> getSortedByScore() {
        return userManager.getTopUsers().stream()
                .sorted(Comparator.comparingInt(User::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<User> getSortedByUsername() {
        return userManager.getTopUsers().stream()
                .sorted(Comparator.comparing(User::getUsername))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<User> getSortedByKills() {
        System.out.println("im sorted by kills");
        return userManager.getTopUsers().stream()
                .sorted(Comparator.comparingInt(User::getKills).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

//    public List<User> getSortedByKills() {
//        List<User> users = new ArrayList<>(userManager.loadAllUsers());
//        users.sort(Comparator.comparingInt(User::getKills).reversed());
//        return users;
//    }


    public List<User> getSortedBySurvivalTime() {
        return userManager.getTopUsers().stream()
                .sorted(Comparator.comparingInt(User::getTime).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public String formatSurvivalTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return min + "m " + sec + "s";
    }
}
