package ru.penkin.java.basic.homework16;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBaseUserService implements UserService {      // vetka_dz18_jdbc
    class User {
        private String login;
        private String password;
        private String username;
        private String role;
        private Boolean kick;


        public User(String login, String password, String username, Boolean kick, String role) {
            this.login = login;
            this.password = password;
            this.username = username;
            this.kick = kick;
            this.role = role;
        }
    }

    private List<DataBaseUserService.User> users;
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/DbUsersRole";
    private static final String DATABASE_LOGIN = "postgres";
    private static final String DATABASE_PASSWORD = "1";
    private static Connection connect;
    private static Statement statement;
    private static PreparedStatement pStatement;

    private void connect() throws SQLException {
        connect = DriverManager.getConnection(DATABASE_URL, DATABASE_LOGIN, DATABASE_PASSWORD);
        statement = connect.createStatement();
    }

    public DataBaseUserService() {
        try {
            connect();
            List<DataBaseUserService.User> users = new ArrayList<>();
            ResultSet rs = statement.executeQuery("Select login, password, username, kick, role from userdata");
            while (rs.next()) {
                users.add(new User(rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getBoolean("kick"),
                        rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        List<DataBaseUserService.User> users = new ArrayList<>();
        try {
            pStatement = connect.prepareStatement("Select username from userdata where login = ? and password = ?");
            pStatement.setString(1, login);
            pStatement.setString(2, password);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewUser(String login, String password, String username) {
        try {
            pStatement = connect.prepareStatement(
                    "INSERT INTO userdata(login, password, username, kick, role) VALUES(?, ?, ?, ?, ?) ");
            connect.setAutoCommit(true);
            pStatement.setString(1, login);
            pStatement.setString(2, password);
            pStatement.setString(3, username);
            pStatement.setBoolean(4, false);
            pStatement.setString(5, "user");
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLoginAlreadyExist(String login) {
        try {
            pStatement = connect.prepareStatement("Select 1 from userdata where login = ?");
            pStatement.setString(1, login);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUsernameAlreadyExist(String username) {
        try {
            pStatement = connect.prepareStatement("Select 1 from userdata where username = ?");
            pStatement.setString(1, username);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkAdmin(String username) {
        try {
            pStatement = connect.prepareStatement("Select role from userdata where username = ?");
            pStatement.setString(1, username);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    return (Objects.equals(rs.getString(1), "admin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void userKick(String username) {
        try {
            pStatement = connect.prepareStatement("update userdata set kick = true where username = ?");
            connect.setAutoCommit(true);
            pStatement.setString(1, username);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkKick(String username) {
        try {
            pStatement = connect.prepareStatement("Select kick from userdata where username = ?");
            pStatement.setString(1, username);
            try (ResultSet rs = pStatement.executeQuery()) {
                while (rs.next()) {
                    return (Objects.equals(rs.getString(1), "true"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}