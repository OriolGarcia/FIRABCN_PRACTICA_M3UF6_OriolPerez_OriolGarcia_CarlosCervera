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

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by oriol on 24/04/2017.
 */
public class ControllerEconomiaUpd {
    Connection connection;
    BDAccessor bdAccessor;
    int id;
    Date dateID;

    @FXML
    private TextField txtNumVisitants;

    @FXML
    private DatePicker dtDataActual;

    @FXML
    private ComboBox<Item> ComBFires;

    @FXML
    private Label lbErrorAdd;

    EconomiaDAOImplement ecoDAOImp;

    /**
     * Funció per actualitzar un dia a ecnonomia
     * @param conn connexió a la BD
     * @param bdAccessor per accedir a la BD
     * @param id identificador de Fira
     * @param dateID dia a actualitzar
     */
    public void init(Connection conn, BDAccessor bdAccessor,int id, Date dateID) {
        connection = conn;
        this.bdAccessor = bdAccessor;
        this.id = id;
        this.dateID = dateID;
        ecoDAOImp = new EconomiaDAOImplement();

        FiresDAOImplement firesDAOImplement = new FiresDAOImplement();
        firesDAOImplement.omplirComboBox(connection, ComBFires);
        ComBFires.getSelectionModel().select(new Item(id, ""));


        /*txtNomEmpresa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtNomEmpresa.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });*/
        ecoDAOImp.omplirCamps(conn, id, dateID, txtNumVisitants, dtDataActual);

    }

    /**
     * Per actualitzar el dia a economia
     * @param event
     */
    public void UpdEconomiaEvent(ActionEvent event) {
        float quotaTotal;
        float preuEntrada;
        float recaptacio;
        float EntPerVis;

        try {
            LocalDate localDate1 = dtDataActual.getValue();
            Instant instant = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            Date currDate = Date.from(instant);
            int numVisitants = Integer.parseInt(txtNumVisitants.getText());
            EconomiaDAOImplement ecoDAOImpl = new EconomiaDAOImplement();

            quotaTotal = ecoDAOImpl.getQuotaEstands(connection,ComBFires.getValue().getId());
            preuEntrada = ecoDAOImpl.getPreuEntrada(connection,ComBFires.getValue().getId());

            EntPerVis = preuEntrada * numVisitants;
            recaptacio = quotaTotal + EntPerVis;

            System.out.println("Recaptacio: " + recaptacio);

            ecoDAOImpl.UpdateEconomia(connection, ComBFires.getValue().getId(), currDate, numVisitants, recaptacio, dateID);
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
