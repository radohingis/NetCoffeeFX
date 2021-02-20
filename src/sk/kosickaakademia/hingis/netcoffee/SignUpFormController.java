package sk.kosickaakademia.hingis.netcoffee;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.kosickaakademia.hingis.netcoffee.database.NCSql;

import java.io.IOException;

public class SignUpFormController {

    public TextField usernameVal;
    public PasswordField passwordVal;
    public CheckBox communityAgreement;

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("./main.fxml"));
        Scene rootScene = new Scene(root);
        Stage main = Main.main;
        main.setScene(rootScene);
    }

    public void submitUser(ActionEvent actionEvent) throws IOException {
        NCSql dat = new NCSql();
        if(communityAgreement.isSelected()){
            dat.insertNewUser(usernameVal.getText(), passwordVal.getText());
            backToHome(actionEvent);
        }
    }
}
