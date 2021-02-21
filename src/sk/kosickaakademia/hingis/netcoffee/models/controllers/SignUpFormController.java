package sk.kosickaakademia.hingis.netcoffee.models.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.kosickaakademia.hingis.netcoffee.models.database.NCSql;

import java.io.IOException;

public class SignUpFormController extends GlobalController {

    public TextField usernameVal;
    public PasswordField passwordVal;
    public CheckBox communityAgreement;

    public void submitUser(ActionEvent actionEvent) throws IOException {
        NCSql dat = new NCSql();
        if(communityAgreement.isSelected()){
            dat.insertNewUser(usernameVal.getText().trim(), passwordVal.getText().trim());
            backToHome(actionEvent);
        }
    }
}
