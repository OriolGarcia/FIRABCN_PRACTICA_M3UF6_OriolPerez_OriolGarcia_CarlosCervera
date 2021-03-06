package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.sql.Connection;

/**
 * Created by oriol on 22/04/2017.
 */
public class ControllerEconomiaAdd {
    Connection connection;
    BDAccessor bdAccessor;

    @FXML
    private TextField txtNumVisitants;

    @FXML
    private DatePicker dtDataActual;

    @FXML
    private ComboBox<Item> ComBFires;

    @FXML
    private Label lbErrorAdd;

    /**
     * Funció que inicia afegir un dia a economia
     * @param conn connexió a la BD
     * @param bdAccessor paràmetre per accedir a la BD
     * @param FiraID identificador de Fira
     */
    public void init(Connection conn, BDAccessor bdAccessor, int FiraID) {
        connection = conn;
        this.bdAccessor = bdAccessor;
        FiresDAOImplement firesDAOImplement= new FiresDAOImplement();
        firesDAOImplement.omplirComboBox(connection,ComBFires);
        ComBFires.getSelectionModel().select(new Item(FiraID,""));

    }

    /**
     * Per afegir el dia a economia
     * @param event
     */
    public void AddEconomiaEvent(ActionEvent event) {
        float quotaTotal;
        float preuEntrada;
        float recaptacio;
        float EntPerVis;

        try {
            //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //Date date = new Date();
            //Date date =
            //String curr = dateFormat.format(date);

            LocalDate localDate1 = dtDataActual.getValue();
            Instant instant = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            Date currDate = Date.from(instant);
            //System.out.println(curr);
            //Date currDate = dateFormat.parse(curr);
            int numVisitants = Integer.parseInt(txtNumVisitants.getText());
            EconomiaDAOImplement ecoDAOImpl = new EconomiaDAOImplement();

            quotaTotal = ecoDAOImpl.getQuotaEstands(connection,ComBFires.getValue().getId());
            preuEntrada = ecoDAOImpl.getPreuEntrada(connection,ComBFires.getValue().getId());

            EntPerVis = preuEntrada * numVisitants;
            recaptacio = quotaTotal + EntPerVis;

            System.out.println("Recaptacio: " + recaptacio);

            ecoDAOImpl.AddEconomia(connection, ComBFires.getValue().getId(), currDate, numVisitants, recaptacio);
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