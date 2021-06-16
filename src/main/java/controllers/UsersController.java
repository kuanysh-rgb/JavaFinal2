package controllers;

import models.User;

public class UsersController {
    private static UsersController instance;
    private static User currentUser;

    public static UsersController getInstance() {
        if (instance != null) {
            return instance;
        }
        return new UsersController();
    }

    public void deleteCurrentUser() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        UsersController.currentUser = currentUser;
    }
}