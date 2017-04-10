package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.EstandsDAO;
import MainPackage.DAOs.FiresDAO;
import MainPackage.DAOs.UserDAO;
import MainPackage.Utils;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by oriol on 10/04/2017.
 */
public class EstandsDAOImplement implements EstandsDAO {
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd = null;

    public boolean AddEstand(Connection conn, String Nom, Float Superficie, Float Quota, Date DataInici, Date DataFi, int Fira, int Empresa){
        try {
            String cadenaSQL = "INSERT INTO Estands(Nom,SuperficieEstand,QuotaEstand,DataInici,DataFi,Fira,Empresa)"
                    +" VALUES(?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, Nom);
            pstmt.setFloat(2, Superficie);
            pstmt.setFloat(3, Quota);
            pstmt.setDate(4, new java.sql.Date(DataInici.getTime()));
            pstmt.setDate(5, new java.sql.Date(DataFi.getTime()));
            pstmt.setInt(6, Fira);
            pstmt.setInt(7, Empresa);
            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            System.out.println("Estand: S'ha afegit " + n + " items");
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }
    }



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
