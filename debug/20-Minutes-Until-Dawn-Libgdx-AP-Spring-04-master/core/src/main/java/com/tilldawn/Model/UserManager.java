package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private User currentUser;
    private final String savePath = "user_data.json";
    private final String allUsersPath = "all_users.json";
    private final String[] avatarPaths = {
        "avatars/avatar1.png",
        "avatars/avatar2.png",
        "avatars/avatar3.png",
        "avatars/avatar4.png"
    };

    public UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void logout() {
        removeUser(currentUser);
        currentUser = null;
    }

    public void saveUser() {
        System.out.println("saveUser");
        if (currentUser != null) {
            Json json = new Json();
            FileHandle file = Gdx.files.local(savePath);
            file.writeString(json.toJson(currentUser), false);
            List<User> users = loadAllUsers();
            for (User user : users) {
                System.out.println(user.getUsername()+":score: "+ user.getScore());
            }
            if (users==null) {
                System.out.println("222users null");
            }
            users.removeIf(u -> u.getUsername().equalsIgnoreCase(currentUser.getUsername()));
            users.add(currentUser);
            saveAllUsers(users);
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        saveUser();
    }

    public User findUser(String username) {
        for (User user : loadAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void saveAllUsers(List<User> users) {
        Json json = new Json();
        FileHandle file = Gdx.files.local(allUsersPath);
        file.writeString(json.toJson(users), false);
    }

    public List<User> loadAllUsers() {
        FileHandle file = Gdx.files.local(allUsersPath);
//        if (!file.exists()) return new ArrayList<>();
        Json json = new Json();
        return json.fromJson(ArrayList.class, User.class, file.readString());
    }
    public boolean isUsernameTaken(String username) {
        return findUser(username) != null;
    }
    public String getRandomAvatarPath() {
        int idx = (int) (Math.random() * avatarPaths.length);
        return avatarPaths[idx];
    }
    public User registerUser(String username, String password, String securityQuestion, String securityAnswer, String avatarPath) {
        User newUser = new User(username, avatarPath,password,securityQuestion,securityAnswer);
        List<User> users = loadAllUsers();
        users.add(newUser);
        saveAllUsers(users);
        setCurrentUser(newUser);
        return newUser;
    }
    public void createGuestUser() {
        System.out.println("createGuestUser");
        User guest = new User("Guest" + System.currentTimeMillis(),getRandomAvatarPath(),"no password","","");
        setCurrentUser(guest);
    }
    public User getUserByUsername(String username) {
        for (User user : loadAllUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void updateUser(User updatedUser) {
        List<User> users = loadAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveAllUsers(users);
    }


//    public void removeUser(User currentUser) {
//        List<User> users = loadAllUsers();
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getUsername().equalsIgnoreCase(currentUser.getUsername())) {
//                users.remove(i);
//                break;
//            }
//        }
//    }
    public void removeUser(User currentUser) {
        List<User> users = loadAllUsers();
        users.removeIf(u -> u.getUsername().equalsIgnoreCase(currentUser.getUsername()));
        saveAllUsers(users);
    }

    public boolean hasSave() {
        FileHandle file = Gdx.files.local(savePath);
        return file.exists();
    }

    public List<User> getTopUsers() {
        List<User> all = loadAllUsers();
        all.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));

        // فقط ۱۰ تای اول
        return all.subList(0, Math.min(10, all.size()));
    }
}
