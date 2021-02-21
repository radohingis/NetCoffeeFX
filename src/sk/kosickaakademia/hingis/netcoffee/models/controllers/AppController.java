package sk.kosickaakademia.hingis.netcoffee.models.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.kosickaakademia.hingis.netcoffee.models.database.NCSql;
import sk.kosickaakademia.hingis.netcoffee.models.entity.User;

public class AppController {
    public Label username;
    public TextArea actual_message;
    public Button send_msg_button;
    public TextField receiverNick;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void greeting(){
        username.setText(user.getName());
    }


    public void sendMessage(ActionEvent actionEvent) {
        NCSql dat = new NCSql();
        int from = dat.getUserId(user.getName());
        String to = receiverNick.getText().trim();
        String msg = actual_message.getText().trim();
        dat.sendMessage(from, to, msg);
        actual_message.setText("");
    }

//    public void showMessages(){
//        NCSql dat = new NCSql();
//        String login = user.getName();
//        String friendsLogin = receiverNick.getText();
//    }
}
