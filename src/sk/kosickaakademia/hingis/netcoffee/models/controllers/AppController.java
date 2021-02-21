package sk.kosickaakademia.hingis.netcoffee.models.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import sk.kosickaakademia.hingis.netcoffee.models.database.NCSql;
import sk.kosickaakademia.hingis.netcoffee.models.entity.Message;
import sk.kosickaakademia.hingis.netcoffee.models.entity.User;

import java.util.Date;
import java.util.List;

public class AppController {
    public Label username;
    public TextArea actual_message;
    public Button send_msg_button;
    public TextField receiverNick;
    public ListView users;
    public ListView my_messages;
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
        showMessages();
    }

    public void showMessages(){
        NCSql dat = new NCSql();
        String login = user.getName();
        String friendsLogin = receiverNick.getText();
        List<Message> objectMessages = dat.getMyMessages(login, friendsLogin);
        if(objectMessages.isEmpty()){
            String noMsgs = "";
            my_messages.getItems().add(0, noMsgs);
        }
        my_messages.getItems().clear();
        for(Message message : objectMessages){
            Date dt = message.getDate();
            String from = message.getFrom();
            String msg = message.getText();
            my_messages.getItems().add(0, dt + " > " + from + " : \"" + msg + "\"");
        }
    }

    public void showUsers(User user){
        NCSql dat = new NCSql();
        List<String> nicks = dat.showUsers(user.getName());
        for(String nick : nicks) {
            users.getItems().add(0, nick);
        }
    }

    public void setReceiver(MouseEvent mouseEvent) {
        String receiver = users.getSelectionModel().getSelectedItem().toString();
        receiverNick.setText(receiver);
    }
}
