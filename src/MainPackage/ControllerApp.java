package MainPackage;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

/**
 * Created by oriol on 30/03/2017.
 */
public class ControllerApp{
    @FXML
    TextField txtUserSearch;
    @FXML
    TableView TbVUsers;

    Connection connection;
    BDAccessor bdAccessor;

    public void init(Connection conn, BDAccessor bdAccessor) {
        connection=conn;
       this.bdAccessor= bdAccessor;
        initiailizeTableViewUsers();

        txtUserSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                initiailizeTableViewUsers();
            }
        });
    }
    private void initiailizeTableViewUsers(){

        UserDAOImplement UserDAOImpl = new UserDAOImplement();

       UserDAOImpl.findbyUsername(connection,txtUserSearch.getText(),TbVUsers);

    }
    public void afegirUsuari(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAddScene.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(root, 560, 200));
            secondStage.show();
            ControllerUserAdd controller = loader.getController();
            controller.init(connection, bdAccessor);
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    initiailizeTableViewUsers();
                }
            });
        }catch (IOException ex){}
    }
    public void eliminarUsuari(ActionEvent event) {}
    public void generarContrasenya(ActionEvent event) {}
    public void canviarPermisos(ActionEvent event) {}
    public void canviarContrasenya(ActionEvent event) {}
}
