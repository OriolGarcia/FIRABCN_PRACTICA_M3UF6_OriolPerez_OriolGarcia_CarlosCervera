package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

/**
 * Created by oriol on 30/03/2017.
 */
public class ControllerUserAdd {
    Connection connection;
    BDAccessor bdAccessor;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPasswordRepeat;
    @FXML
    private CheckBox chckBPermisos;
    @FXML
    private CheckBox checkBActiu;

    /**
     * Funció per iniciar afegir usuari
     * @param conn connexió a la BD
     * @param bdAccessor accès a la BD
     */
    public void init(Connection conn, BDAccessor bdAccessor) {
        connection=conn;
        this.bdAccessor= bdAccessor;
    }

    /**
     * Per afegir un nou usuari
     * @param event
     */
    public void AddUserEvent(ActionEvent event) {
        if(!txtUser.getText().isEmpty()&&!txtPassword.getText().isEmpty()&&!txtPasswordRepeat.getText().isEmpty())
        if(txtPassword.getText().equals(txtPasswordRepeat.getText())){
            UserDAOImplement UserDAOImpl = new UserDAOImplement();

            UserDAOImpl.AddUser(connection,txtUser.getText(),txtPassword.getText(),chckBPermisos.isSelected(),checkBActiu.isSelected());
            final Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.getOnCloseRequest().handle(null);
            stage.close();

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missatge informatiu");
            alert.setHeaderText("Passwords");
            alert.setContentText("Els camps dels dos passwords ha de ser iguals.");
            alert.showAndWait();
        }

    }
}