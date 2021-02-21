package sk.kosickaakademia.hingis.netcoffee.models.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sk.kosickaakademia.hingis.netcoffee.Main;

import java.io.IOException;

public class RootController {
    public Button sign_up_button;
    public Button sign_in_button;

    public void openSignUpForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../views/signUpForm.fxml"));
        Scene signUpForm = new Scene(root);
        Stage main = Main.main;
        main.setScene(signUpForm);
    }

    public void openSignInForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../views/signInForm.fxml"));
        Scene signInForm = new Scene(root);
        Stage main = Main.main;
        main.setScene(signInForm);
    }
}

