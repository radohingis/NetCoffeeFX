package sk.kosickaakademia.hingis.netcoffee.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class NCSql {

    public Connection getConnection() {
        try{
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            String url = properties.getProperty("dburl");
            String name = properties.getProperty("dbuser");
            String pwd = properties.getProperty("dbpwd");

            return DriverManager.getConnection(url, name, pwd);

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
