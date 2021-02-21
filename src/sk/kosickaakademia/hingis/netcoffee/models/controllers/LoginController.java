package sk.kosickaakademia.hingis.netcoffee.models.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sk.kosickaakademia.hingis.netcoffee.Main;
import sk.kosickaakademia.hingis.netcoffee.models.database.NCSql;
import sk.kosickaakademia.hingis.netcoffee.models.entity.User;

import java.io.IOException;

public class LoginController extends SignUpFormController {
    public PasswordField passwordVal;
    public TextField usernameVal;
    public Button sign_in_btn;


    public void loginUser(ActionEvent actionEvent) throws IOException {
        NCSql dat = new NCSql();
        String nick = usernameVal.getText().trim();
        String pass = passwordVal.getText().trim();
        User user = dat.loginUser(nick, pass);
        openApp(user);
    }

    private void openApp(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../views/App.fxml"));
        Parent root = loader.load();
        Scene appView = new Scene(root);
        Stage main = Main.main;
        AppController appController  = loader.getController();
        appController.setUser(user);
        appController.greeting();
        appController.showUsers(user);
        appController.receiverNick.requestFocus();
        main.setScene(appView);
    }

}
