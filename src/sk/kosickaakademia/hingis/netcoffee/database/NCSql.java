package sk.kosickaakademia.hingis.netcoffee.database;

import com.mysql.cj.protocol.Resultset;
import sk.kosickaakademia.hingis.netcoffee.entity.User;
import sk.kosickaakademia.hingis.netcoffee.utility.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class NCSql {
    private final String insertUserQuery = "insert into user (login, password) values (?, ?)";
    private final String loginUserQuery = "select * from user where login like ? and password like ?";

    public Connection connect() {
        Connection connection;
        try{
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
        if(login.equals("") ||
                password.equals("") ||
                password.length() < 6)
            return false;

        String hashedPwd = new Util().getMd5(password);

        try (Connection connection = connect()){
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
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public User loginUser (String login, String password){
        if(login.equals("") ||
                password.equals("") ||
                password.length() < 6)
            return null;

        String hashedPwd = new Util().getMd5(password);

        try (Connection connection = connect()){
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(loginUserQuery);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, hashedPwd);
                ResultSet resultset = preparedStatement.executeQuery();
                if(resultset.next()){
                    int userID = resultset.getInt("id");
                    return new User(userID, login, hashedPwd);
                } else {
                    System.out.println("Wrong login or password");
                    return null;
                }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
