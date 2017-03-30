package MainPackage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("LOG IN - CREATLAFIRA");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        ControllerLogin controller = loader.getController();
        controller.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
