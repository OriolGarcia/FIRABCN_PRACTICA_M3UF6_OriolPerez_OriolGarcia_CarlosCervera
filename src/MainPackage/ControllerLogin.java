package MainPackage;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ControllerLogin {
    BDAccessor accessor= new BDAccessor();
    Connection connexio;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
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

    public void Login(ActionEvent event) throws IOException{
        UserDAOImplement UserDAOImpl = new UserDAOImplement();
        String usuari= txtUser.getText(),pass=txtPassword.getText();
        if( UserDAOImpl.LoginUser(connexio,usuari,pass)){

            System.out.println("Usuari logejat");
            Logged(event);
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loggin Failed");
            alert.setHeaderText("Usuari mal autenicat.");
            alert.setContentText("Pot ser que l'usuari o el password hagin estat mal escrits.");
            alert.showAndWait();
        }

    }
    private void Logged(ActionEvent event) throws IOException{
        Parent AppParent = FXMLLoader.load(getClass().getResource("AppScene.fxml"));
        Scene AppScene = new Scene(AppParent);
        Stage AppStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AppStage.setScene(AppScene);
        AppStage.show();
    }

}
