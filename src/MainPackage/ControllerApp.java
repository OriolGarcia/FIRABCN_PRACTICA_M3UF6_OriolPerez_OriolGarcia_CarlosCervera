package MainPackage;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
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
        System.out.println("Pantalla App");
    }
    private void initiailizeTableViewUsers(){

        UserDAOImplement UserDAOImpl = new UserDAOImplement();

       UserDAOImpl.findbyUsername(connection,txtUserSearch.getText(),TbVUsers);

    }
}
