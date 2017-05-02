package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.EmpresesDAO;
import MainPackage.DAOs.EstandsDAO;
import MainPackage.DAOs.TipusEmpresesDAO;
import MainPackage.DAOsImplements.*;
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
//import java.sql.Date;

/**
 * Created by oriol on 10/04/2017.
 */
public class ControllerEstandAdd {
    Connection connection;
    BDAccessor bdAccessor;

    @FXML
    TextField txtNomEstand;
    @FXML
    TextField txtSuperficie;
    @FXML
    TextField txtQuota;
    @FXML
    DatePicker dtDataInici;
    @FXML
    DatePicker dtDataFi;
    @FXML
    ComboBox<Item> ComBFires;
    @FXML
    ComboBox<Item> ComBTipus;
    @FXML
    Label lbErrorAdd;

    /**
     * Funció que inicia afegir estand
     * @param conn connexió a la BD
     * @param bdAccessor accès a la BD
     * @param EmpresaID identificador d'empresa
     * @param FiraID identificador de fira
     */
    public void init(Connection conn, BDAccessor bdAccessor, int EmpresaID,int FiraID) {
        connection = conn;
        this.bdAccessor = bdAccessor;

        FiresDAOImplement firesDAOImplement = new FiresDAOImplement();
        firesDAOImplement.omplirComboBox(connection,ComBFires);
        ComBFires.getSelectionModel().select(new Item(FiraID,""));
        EmpresesDAO empDAO = new EmpresesDAOImplement();
        empDAO.omplirComboBox(connection,ComBTipus);
        ComBTipus.getSelectionModel().select(new Item(EmpresaID,""));
    }

    /**
     * Per afegir un estand
     * @param event
     */
    public void AddEstandEvent(ActionEvent event) {
        try {
            EstandsDAOImplement estandsDAOImpl = new EstandsDAOImplement();
            Float superficie = Float.parseFloat(txtSuperficie.getText());
            Float quota = Float.parseFloat(txtQuota.getText());
            LocalDate localDate1 = dtDataInici.getValue();
            Instant instant = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            Date dateInici = Date.from(instant);
            LocalDate localDate2 = dtDataFi.getValue();
            Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
            Date dateFi = Date.from(instant2);

            estandsDAOImpl.AddEstand(connection, txtNomEstand.getText(),superficie,quota,dateInici,dateFi,ComBFires.getValue().getId(),ComBTipus.getValue().getId());
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
//