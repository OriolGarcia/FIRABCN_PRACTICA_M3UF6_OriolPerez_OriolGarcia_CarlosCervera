package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.FiresDAOImplement;
import MainPackage.DAOsImplements.UserDAOImplement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by oriol on 30/03/2017.
 */
public class ControllerFiraAdd {
    Connection connection;
    BDAccessor bdAccessor;
    @FXML
    private TextField txtTitol;
    @FXML
    private TextField txtUbicacio;
    @FXML
    private TextField txtSuperficie;
    @FXML
    private DatePicker dtDataInici;
    @FXML
    private DatePicker dtDataFi;
    @FXML
    private Label lbErrorAddFira;

    public void init(Connection conn, BDAccessor bdAccessor) {
        connection=conn;
        this.bdAccessor= bdAccessor;

        txtSuperficie.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtSuperficie.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    public void AddFiraEvent(ActionEvent event) {

        try {
            FiresDAOImplement firesDAOImpl = new FiresDAOImplement();
            Float superficie = Float.parseFloat(txtSuperficie.getText());
            LocalDate localDate1 = dtDataInici.getValue();
            Instant instant = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            Date dateInici = Date.from(instant);
            LocalDate localDate2 = dtDataInici.getValue();
            Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
            Date dateFi = Date.from(instant2);
            firesDAOImpl.AddFira(connection, txtTitol.getText(), txtUbicacio.getText(), superficie, dateInici, dateFi);
            final Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.getOnCloseRequest().handle(null);
            stage.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            lbErrorAddFira.setVisible(true);
        }
    }

}