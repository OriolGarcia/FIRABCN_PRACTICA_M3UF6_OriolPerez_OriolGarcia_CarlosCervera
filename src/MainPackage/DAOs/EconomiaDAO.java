package MainPackage.DAOs;

import javafx.scene.control.TableView;

import java.sql.Connection;
import java.util.Date;

/**
 * Created by Carlos on 3/04/17.
 */
public interface EconomiaDAO {
    public boolean AddEconomia(int firaID, Date data, int numVisitants, Float recaptació);
    public boolean UpdateEconomia();
    public boolean DeleteEconomia();
    public void findbyParams(Connection conn, String TitolSearch,int id, TableView tableView);
}
