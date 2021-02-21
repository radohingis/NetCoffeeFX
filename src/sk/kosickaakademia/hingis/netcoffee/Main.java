package sk.kosickaakademia.hingis.netcoffee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.kosickaakademia.hingis.netcoffee.models.database.NCSql;

public class Main extends Application {

    public static Stage main;

    @Override
    public void start(Stage primaryStage) throws Exception{
        main = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
        Scene mainScene = new Scene(root);
        main.setScene(mainScene);
        main.show();

    }


    public static void main(String[] args) {
        launch(args);

    }
}
