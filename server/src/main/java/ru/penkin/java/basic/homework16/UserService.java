package ru.penkin.java.basic.homework16;

public interface UserService {          //vetka_dz18_jdbc
    String getUsernameByLoginAndPassword(String login, String password);
    void createNewUser(String login, String password, String username);
    boolean isLoginAlreadyExist(String login);
    boolean isUsernameAlreadyExist(String username);
    boolean checkAdmin(String username);
    void userKick(String username);
    boolean checkKick(String username);
}