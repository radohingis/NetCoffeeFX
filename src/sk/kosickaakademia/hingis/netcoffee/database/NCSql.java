package sk.kosickaakademia.hingis.netcoffee.database;

import sk.kosickaakademia.hingis.netcoffee.utility.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class NCSql {
    private final String insertUserQuery = "insert into user (login, password) values (?, ?)";

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

        try {
            Connection connection = connect();

            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, hashedPwd);
                if (preparedStatement.executeUpdate() == 0) {
                    System.out.println("User with this login already exists");
                    return false;
                } else {
                    System.out.println("You successfully logged in " + login);
                    return true;
                }
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }
}
