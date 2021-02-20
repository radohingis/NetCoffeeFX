package sk.kosickaakademia.hingis.netcoffee;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.io.IOException;

public class MainSceneController {
    public Button sign_up_button;

    public void openSignUpForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("./signUpForm.fxml"));
        Scene signUpForm = new Scene(root);
        Stage main = Main.main;
        main.setScene(signUpForm);
    }
}
