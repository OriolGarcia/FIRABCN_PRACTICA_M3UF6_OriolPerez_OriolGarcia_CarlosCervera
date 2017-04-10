package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;

/**
 * Created by ogs10_000 on 03/04/2017.
 */
public class ControllerPermisos {
    Connection connection;
    BDAccessor bdAccessor;
    String Username;

    @FXML
    private CheckBox chckBPermisos;
    @FXML
    private CheckBox checkBActiu;
    @FXML
    private Label lbusuari;
    UserDAOImplement UserDAOImpl;

    public void init(Connection conn, BDAccessor bdAccessor, String Username) {
        connection = conn;
        this.bdAccessor = bdAccessor;
        this.Username = Username;
        lbusuari.setText(lbusuari.getText() + "  " + Username);
        UserDAOImpl = new UserDAOImplement();
        boolean[] resultats = UserDAOImpl.SelectPermisionsActiveFromUser(connection,Username);
        chckBPermisos.setSelected(!resultats[0]);
        checkBActiu.setSelected(!resultats[1]);
    }

    public void ModPermisosEvent(ActionEvent event) {

        UserDAOImplement UserDAOImpl = new UserDAOImplement();

        UserDAOImpl.UpdateUserPermissions(connection,Username,chckBPermisos.isSelected(),checkBActiu.isSelected());
        final Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.getOnCloseRequest().handle(null);
        stage.close();
    }

}
