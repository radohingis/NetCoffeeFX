package sk.kosickaakademia.hingis.netcoffee.database;


import sk.kosickaakademia.hingis.netcoffee.entity.Message;
import sk.kosickaakademia.hingis.netcoffee.entity.User;
import sk.kosickaakademia.hingis.netcoffee.utility.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NCSql {
    private final String insertUserQuery = "insert into user (login, password) values (?, ?)";
    private final String loginUserQuery = "select * from user where login like ? and password like ?";
    private final String updatePasswordQuery = "update user set password = ? where login = ? and password = ?";
    private final String findUserQuery = "select id from user where login like ?";
    private final String insertNewMessageQuery = "insert into message (fromUser, toUser, text) values (?, ?, ?)";
    private final String getMyMessagesQuery = "select * from message where toUser=?";
    private final String getUserNameQuery = "select login from user where id=?";
    private final String deleteMyMessagesQuery = "delete from message where toUser=?";

    public Connection connect() {
        Connection connection;
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = properties.getProperty("dburl");
            String name = properties.getProperty("dbuser");
            String pwd = properties.getProperty("dbpwd");

            connection = DriverManager.getConnection(url, name, pwd);

            return connection;

        } catch (SQLException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertNewUser(String login, String password) {
        if (login.equals("") ||
                password.equals("") ||
                password.length() < 6)
            return false;

        String hashedPwd = new Util().getMd5(password);

        try (Connection connection = connect()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, hashedPwd);
                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("User with this login already exists");
                    connection.close();
                    return false;
                } else {
                    System.out.println("You successfully logged in " + login);
                    connection.close();
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public User loginUser(String login, String password) {
        if (login.equals("") ||
                password.equals("") ||
                password.length() < 6)
            return null;

        String hashedPwd = new Util().getMd5(password);

        try (Connection connection = connect()) {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(loginUserQuery);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, hashedPwd);
                ResultSet resultset = preparedStatement.executeQuery();
                if (resultset.next()) {
                    int userID = resultset.getInt("id");
                    return new User(userID, login, hashedPwd);
                } else {
                    System.out.println("Wrong login or password");
                    return null;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(String login, String oldPassword, String newPassword, String newPasswordTest) {
        if(oldPassword.equals(newPassword) || oldPassword.equals(newPasswordTest)){
            System.out.println("New password must be different than old one");
        }
        if (!oldPassword.equals("")) {
            User user = loginUser(login, oldPassword);
            if(!user.getPassword().equals("")){
                try (Connection connection = connect()) {
                    if (connection != null) {
                        PreparedStatement preparedStatement = connection.prepareStatement(updatePasswordQuery);
                        if (newPassword.equals(newPasswordTest)) {
                            preparedStatement.setString(1, new Util().getMd5(newPassword));
                            preparedStatement.setString(2, login);
                            preparedStatement.setString(3, user.getPassword());
                            preparedStatement.executeUpdate();
                            System.out.println("Password changed succesfully");
                            connection.close();
                            return true;
                        } else {
                            System.out.println("New password misspelled in one case");
                            connection.close();
                            return false;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }
    public int getUserId(String login) {
        if(login.equals("")) return -1;

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findUserQuery);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                return resultSet.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    public boolean sendMessage(int from, String to, String msg) {
        if(to.equals("") || msg.equals("")) return false;
        int receiverId = getUserId(to);

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertNewMessageQuery);
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, receiverId);
            preparedStatement.setString(3, msg);
            int result = preparedStatement.executeUpdate();
            if(result == 0){
                System.out.println("Failed to send message");
                return false;
            }
            if(result > 0){
                System.out.println("Goddamn worked");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Message> getMyMessages(String login){

        List<Message> myMessages = new ArrayList<>();

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getMyMessagesQuery);
            preparedStatement.setInt(1, getUserId(login));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String from = getUserName(resultSet.getInt("fromUser"));
                String to = getUserName(resultSet.getInt("toUser"));
                Date date = resultSet.getDate("dt");
                String msg = resultSet.getString("text");
                myMessages.add(new Message(id, from, to, date, msg));
            }
            return myMessages;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private String getUserName(int id) {
        if(id < 1) return "";

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getUserNameQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("login");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private boolean deleteAllMyMessages(String login) {
        if(login.equals("")) return false;

        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteMyMessagesQuery);
            preparedStatement.setInt(1, getUserId(login));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    //todo public void deleteAllMyMessages(String login, String password)
}
