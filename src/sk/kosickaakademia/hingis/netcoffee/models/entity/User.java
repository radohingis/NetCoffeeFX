package sk.kosickaakademia.hingis.netcoffee.models.entity;

public class User {
    private String name;
    private String password;
    private int userID;

    public User(int userID, String name, String password) {
        this.name = name;
        this.password = password;
        this.userID = userID;
    }
    public User(int userID, String name) {
        this.name = name;
        this.userID = userID;
    }

    public User () {}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }
}
