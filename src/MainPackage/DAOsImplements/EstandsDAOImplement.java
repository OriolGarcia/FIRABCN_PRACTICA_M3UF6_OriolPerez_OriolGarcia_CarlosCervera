package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.EstandsDAO;
import MainPackage.Utils;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oriol on 10/04/2017.
 */
public class EstandsDAOImplement implements EstandsDAO {
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd = null;



    public void findbyParams(Connection conn, String NomSearch, int Fira, TableView tableView){
        try {
            String cadenaSQL= "SELECT EstandID,Estands.Nom,SuperficieEstand,QuotaEstand,Estands.DataInici,Estands.DataFi,Fires.Titol, Empreses.EmpresaID as `Empresa` "
                    +" FROM Estands"
                    +" INNER JOIN Fires ON Fires.FiraID = Estands.Fira"
                    +" INNER JOIN Empreses ON Empreses.EmpresaID = Estands.Empresa"
                    +" WHERE ((LENGTH(?) <1 or Estands.Nom like ?) and Empresa = ?)";

            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, NomSearch);
            pstmt.setString(2,"%"+NomSearch+"%");
            pstmt.setInt(3,Fira);
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
