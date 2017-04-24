package MainPackage.DAOs;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.util.Date;

/**
 * Created by Carlos on 3/04/17.
 */
public interface EconomiaDAO {
    public boolean AddEconomia(Connection conn,int firaID, Date data, int numVisitants, Float recaptació);
    public boolean UpdateEconomia(Connection conn, int firaID, Date data, int numVisitants, Float recaptacio, Date dataID);
    public boolean DeleteEconomia(Connection conn, int firaID, Date dataID);
    public void findbyParams(Connection conn, String TitolSearch,int id, TableView tableView);
    public void omplirCamps(Connection conn, int id, Date dateID, TextField txtFieldNumVisitants, DatePicker dtDataActual);
    public float getQuotaEstands(Connection conn, int firaID);
    public float getPreuEntrada(Connection conn, int firaID);
}
