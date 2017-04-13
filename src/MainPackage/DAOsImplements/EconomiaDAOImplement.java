package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.EconomiaDAO;
import MainPackage.Utils;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Date;

/**
 * Created by Carlos on 3/04/17.
 */
public class EconomiaDAOImplement implements EconomiaDAO {
    public boolean AddEconomia(int firaID, Date data, int numVisitants, Float recaptació){
        return true;
    }

    public boolean UpdateEconomia() {
        return false;
    }

    public boolean DeleteEconomia() {
        return false;
    }

    public void findbyParams(Connection conn, String TitolSearch, int id, TableView tableView) {

    }
}
