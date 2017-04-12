package MainPackage.DAOs;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.util.Date;

/**
 * Created by Carlos on 3/04/17.
 */
public interface EmpresesDAO {
    public boolean AddEmpresa(Connection conn, String Nom, String CIF, String PersonaContacte, String Telefon, int Tipus, int Fira);
    public boolean UpdateEmpresa(Connection conn, int id, String Nom, String CIF, String PersonaContacte, String Telefon, int Tipus, int Fira);
    public boolean DeleteEmpresa(Connection conn, int id);
    public void findbyParams(Connection conn, String NomSearch, int Fira, TableView tableView);
    public void omplirCamps(Connection conn, int id, TextField txtFielNomEmpresa, TextField txtFieldCIF, TextField txtPersonaContacte,TextField txtTelefon);
    public void omplirComboBox(Connection conn, ComboBox comboBox);
}
