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
 * Created by oriol on 10/04/2017.
 */
public class ControllerEmpresaUpd {
    Connection connection;
    BDAccessor bdAccessor;
    int id;

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
    private Label lbErrorUpdEmpresa;

    EmpresesDAOImplement empDAOImpl;


    public void init(Connection conn, BDAccessor bdAccessor,int id) {
        connection = conn;
        this.bdAccessor = bdAccessor;
        this.id = id;
        empDAOImpl = new EmpresesDAOImplement();

        FiresDAOImplement firesDAOImplement = new FiresDAOImplement();
        firesDAOImplement.omplirComboBox(connection,ComBFires);
        ComBFires.getSelectionModel().select(new Item(id,""));
        TipusEmpresesDAO tipusEmpresesDAO = new TipusEmpresesDAOImplement();
        tipusEmpresesDAO.omplirComboBox(connection,ComBTipus);
        ComBTipus.getSelectionModel().select(0);

        /*txtNomEmpresa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtNomEmpresa.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });*/
        empDAOImpl.omplirCamps(conn,id,txtNomEmpresa,txtCIF,txtPersonaContacte,txtTelefon);
    }

    public void UpdEmpresaEvent(ActionEvent event){

        try {
            String nomEmpresa = txtNomEmpresa.getText();
            String CIF = txtCIF.getText();
            String persContacte = txtPersonaContacte.getText();
            //Date dateInici = Date.from(instant);
            String  telefon = txtTelefon.getText();
            int tipus = ComBTipus.getValue().getId();
            int fira = ComBFires.getValue().getId();
            /*Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
            Date dateFi = Date.from(instant2);*/
            empDAOImpl.UpdateEmpresa(connection, id, nomEmpresa, CIF, persContacte, telefon, tipus, fira);
            final Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.getOnCloseRequest().handle(null);
            stage.close();
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            lbErrorUpdEmpresa.setVisible(true);
        }
    }
}
