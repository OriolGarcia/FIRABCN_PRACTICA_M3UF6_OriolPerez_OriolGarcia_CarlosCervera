package MainPackage;

import MainPackage.Accessor.BDAccessor;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

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
}
