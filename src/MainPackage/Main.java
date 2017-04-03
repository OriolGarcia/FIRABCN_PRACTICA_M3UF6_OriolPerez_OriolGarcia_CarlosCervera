package MainPackage;

import MainPackage.Controllers.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scenes/LoginScene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("LOG IN - CREATLAFIRA");
        primaryStage.setScene(new Scene(root, 560, 200));
        primaryStage.show();
        ControllerLogin controller = loader.getController();
        controller.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
