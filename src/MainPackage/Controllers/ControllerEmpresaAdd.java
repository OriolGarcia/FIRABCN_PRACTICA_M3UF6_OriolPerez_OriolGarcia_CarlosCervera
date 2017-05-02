package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.TipusEmpresesDAO;
import MainPackage.DAOsImplements.EmpresesDAOImplement;
import MainPackage.DAOsImplements.FiresDAOImplement;
import MainPackage.DAOsImplements.Item;
import MainPackage.DAOsImplements.TipusEmpresesDAOImplement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by oriol on 30/03/2017.
 */
public class ControllerEmpresaAdd {
    Connection connection;
    BDAccessor bdAccessor;

    @FXML
    private TextField txtNomEmpresa;
    @FXML
    private TextField txtCIF;
    @FXML
    private TextField txtPersonaContacte;
    @FXML
    private TextField txtTelefon;
    @FXML
    private ComboBox<Item> ComBFires;
    @FXML
    private ComboBox<Item> ComBTipus;
    @FXML
    private Label lbErrorAdd;

    /**
     * Funció que inicia afegir empresa
     * @param conn connexió a la BD
     * @param bdAccessor per accedir a la BD
     * @param FiraID identificador de fira
     */
    public void init(Connection conn, BDAccessor bdAccessor, int FiraID) {
        connection = conn;
        this.bdAccessor = bdAccessor;
        FiresDAOImplement firesDAOImplement= new FiresDAOImplement();
        firesDAOImplement.omplirComboBox(connection,ComBFires);
        ComBFires.getSelectionModel().select(new Item(FiraID,""));
        TipusEmpresesDAO tipusEmpresesDAO= new TipusEmpresesDAOImplement();
        tipusEmpresesDAO.omplirComboBox(connection,ComBTipus);
        ComBTipus.getSelectionModel().select(0);
    }

    /**
     * Per afegir una empresa
     * @param event
     */
    public void AddEmpresaEvent(ActionEvent event) {

        try {
            EmpresesDAOImplement empresesDAOImplement = new EmpresesDAOImplement();
            empresesDAOImplement.AddEmpresa(connection, txtNomEmpresa.getText(),txtCIF.getText(), txtPersonaContacte.getText(),txtTelefon.getText(),ComBTipus.getValue().getId(),ComBFires.getValue().getId());
            final Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.getOnCloseRequest().handle(null);
            stage.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            lbErrorAdd.setVisible(true);
        }
    }

}