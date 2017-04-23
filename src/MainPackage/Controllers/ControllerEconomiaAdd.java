package MainPackage.Controllers;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.TipusEmpresesDAO;
import MainPackage.DAOsImplements.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private ComboBox<Item> ComBFires;

    @FXML
    private Label lbErrorAdd;

    public void init(Connection conn, BDAccessor bdAccessor, int FiraID) {
        connection = conn;
        this.bdAccessor = bdAccessor;
        FiresDAOImplement firesDAOImplement= new FiresDAOImplement();
        firesDAOImplement.omplirComboBox(connection,ComBFires);
        ComBFires.getSelectionModel().select(new Item(FiraID,""));

    }

    public void AddEconomiaEvent(ActionEvent event) {

        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String curr = dateFormat.format(date);
            System.out.println(curr);
            Date currDate = dateFormat.parse(curr);
            int numVisitants = Integer.parseInt(txtNumVisitants.getText());
            EconomiaDAOImplement ecoDAOImpl = new EconomiaDAOImplement();
            ecoDAOImpl.AddEconomia(connection, ComBFires.getValue().getId(), currDate, numVisitants,10F);
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