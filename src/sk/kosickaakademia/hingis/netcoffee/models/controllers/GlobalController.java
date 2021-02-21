package sk.kosickaakademia.hingis.netcoffee.models.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.kosickaakademia.hingis.netcoffee.Main;

import java.io.IOException;

public abstract class GlobalController {
    public void backToHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../views/main.fxml"));
        Scene rootScene = new Scene(root);
        Stage main = Main.main;
        main.setScene(rootScene);
    }
}
