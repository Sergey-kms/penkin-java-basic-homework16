package ru.penkin.java.basic.homework16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryUserService implements UserService {  // vetka1_dz16
    class User {
        private String login;
        private String password;
        private String username;
        private String role;
        private Boolean kick;


        public User(String login, String password, String username, String role) {
            this.login = login;
            this.password = password;
            this.username = username;
            this.role = role;
            this.kick = false;
        }
    }

    private List<User> users;

    public InMemoryUserService() {
        this.users = new ArrayList<>(Arrays.asList(
                new User("petya", "password1", "petr", "user"),
                new User("vitya", "password2", "viktor", "user"),
                new User("vanya", "password3", "ivan", "user"),
                new User("leha", "password4", "alexey", "user"),
                new User("serega", "password5", "sergey", "user"),
                new User("administator", "password6", "administator", "admin")
        ));
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.username;
            }
        }
        return null;
    }

    @Override
    public void createNewUser(String login, String password, String username, String role) {
        users.add(new User(login, password, username, role));
    }

    @Override
    public boolean isLoginAlreadyExist(String login) {
        for (User u : users) {
            if (u.login.equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUsernameAlreadyExist(String username) {
        for (User u : users) {
            if (u.username.equals(username)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean checkAdmin(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                if (user.role.equals("admin")) {
                    return true;
                }
            }
        }
        return false;
    }
    public void userKick(String username) {
        for (User u : users) {
            if (u.username.equals(username)) {
                u.kick = true;
            }
        }
    }
    public boolean checkKick(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user.kick;
            }
        }
        return false;
    }

}