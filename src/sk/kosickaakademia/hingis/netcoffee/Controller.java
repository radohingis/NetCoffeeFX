package sk.kosickaakademia.hingis.netcoffee;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private void openMainForm() {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Welcome to the NetCoffee");
            stage.setScene(new Scene(root, 1024, 768));
            stage.show();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
