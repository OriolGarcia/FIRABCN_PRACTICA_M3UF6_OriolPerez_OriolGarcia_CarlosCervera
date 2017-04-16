package MainPackage.DAOs;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ogs10_000 on 04/04/2017.
 */
public interface FiresDAO {
    public boolean AddFira(Connection conn, String Titol,String Ubicació, float Superficie,float PreuEnetrada,Date Datainici,Date DataFi);
    public boolean UpdateFira(Connection conn,int id, String Titol,String Ubicació, float Superficie,float PreuEntrada,Date Datainici,Date DataFi);
    public boolean DeleteFira(Connection conn, int id);
    public void findbyParams(Connection conn, String TitolSearch, TableView tableView);
    public void omplirCamps(Connection conn, int id, TextField txtFielTitolFira, TextField Ubicació, TextField Superificie,TextField txtPreuEntrada, DatePicker DataInici,DatePicker DataFi);
    public void omplirComboBox(Connection conn, ComboBox comboBox);//
}