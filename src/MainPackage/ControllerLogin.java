package MainPackage;

import MainPackage.Accessor.BDAccessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ControllerLogin {
    BDAccessor accessor= new BDAccessor();
    Connection connexio;

    public void init() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Missatge d'error");
        try {
            connexio = accessor.obtenirConnexio();
        }catch(IOException ex) {
            alert.setHeaderText("Error de de lectura");
            alert.setContentText("ERROR: No s'ha pogut llegir el fitxer de parametres");
            alert.showAndWait();
        }
        catch(ClassNotFoundException ex){
            alert.setHeaderText("Error de programari");
            alert.setContentText("ERROR: No s'ha pogut carregar el driver JDBC");
            alert.showAndWait();
        }
        catch(SQLException e){
            alert.setHeaderText("Error de connexió");
            alert.setContentText("ERROR: No s'ha pogut connectar amb el SGBD");
            alert.showAndWait();
        }
    }

    public void Login(ActionEvent event) {
    }

    public void handleButtonAction(ActionEvent event) throws IOException{

        Parent AppLoad = FXMLLoader.load(getClass().getResource("AppScene.fxml"));
        Scene AppScene = new Scene(AppLoad);
        Stage AppStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        AppStage.setScene(AppScene);
        AppStage.show();
    }

}
