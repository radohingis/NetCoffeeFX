package sk.kosickaakademia.hingis.netcoffee;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.hingis.netcoffee.database.NCSql;
import sk.kosickaakademia.hingis.netcoffee.entity.User;

import java.io.IOException;

public class AppController extends SignUpFormController {
    public PasswordField passwordVal;
    public TextField usernameVal;
    public Label username;

    public void loginUser(ActionEvent actionEvent) throws IOException {
        NCSql dat = new NCSql();
        String nick = usernameVal.getText().trim();
        String pass = passwordVal.getText().trim();
        User user = dat.loginUser(nick, pass);
        if(user != null){
            openApp(user.getName());
        }
    }

    public void openApp(String login) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("./App.fxml"));
        Scene app = new Scene(root);
        Stage main = Main.main;
        main.setScene(app);
        System.out.println("Success");
    }

}
