package sk.kosickaakademia.hingis.netcoffee.database;

import com.mysql.cj.protocol.Resultset;
import sk.kosickaakademia.hingis.netcoffee.entity.User;
import sk.kosickaakademia.hingis.netcoffee.utility.Util;

import javax.xml.transform.Result;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class NCSql {
    private final String insertUserQuery = "insert into user (login, password) values (?, ?)";
    private final String loginUserQuery = "select * from user where login like ? and password like ?";
    private final String updatePasswordQuery = "update user set password = ? where login = ? and password = ?";
    private final String findUserQuery = "select id from user where login like ?";

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


//    public boolean sendMessage(int from, String to, String msg) {
//        if(from == null || to.equals("") || msg.equals("")) return false;
//
//        return false;
//    }
    //todo public List<Message> getMyMessages(String login, String password), public void deleteAllMyMessages(String login, String password)
}
