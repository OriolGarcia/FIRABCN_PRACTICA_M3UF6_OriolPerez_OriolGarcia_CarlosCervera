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
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd = null;

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
        try {
            String cadenaSQL= "SELECT FiraID,DATA,NumVisitants,Recaptació"
                    +" FROM ECONOMIAFIRA"
                    +" INNER JOIN Fires ON Fires.FiraID = ECONOMIAFIRA.FiraID"
                    +" WHERE ((LENGTH(?) <1 or DATA like ?) and ECONOMIAFIRA.FiraID = ?)";

            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, TitolSearch);
            pstmt.setString(2,"%"+TitolSearch+"%");
            pstmt.setInt(3,id);
            System.out.println("1");

            try (ResultSet resultat = pstmt.executeQuery()) {
                System.out.println("retorna resultSet");
                Utils.omplirTableView(tableView,resultat);

            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());

        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){

            }
        }

    }
}
