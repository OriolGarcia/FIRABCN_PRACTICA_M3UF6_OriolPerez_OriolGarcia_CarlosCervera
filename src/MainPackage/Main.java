package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScenePrincipal.fxml"));
        Parent root = (Parent)loader.load();
        primaryStage.setTitle("LOG IN - CREATLAFIRA");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        ControllerLogin controller = (ControllerLogin)loader.getController();
        controller.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
