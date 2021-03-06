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
import javafx.scene.control.*;

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

    /**
     * Afegir Estand
     * @param conn connexió a la BD
     * @param Nom Nom del estand
     * @param Superficie superficie
     * @param Quota quota estand
     * @param DataInici data inici estand
     * @param DataFi data fi estand
     * @param Fira Fira a la qual pertany
     * @param Empresa Empresa a la que pertany
     * @return retorna true si s'ha afegit correctament
     */
    public boolean AddEstand(Connection conn, String Nom, Float Superficie, Float Quota, Date DataInici, Date DataFi, int Fira, int Empresa){
        try {
            System.out.println("e4");
            String cadenaSQL = "INSERT INTO Estands(Nom,SuperficieEstand,QuotaEstand,DataInici,DataFi,Fira,Empresa)"
                    +" VALUES(?,?,?,?,?,?,?);";
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
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            conn.commit();
            System.out.println("Estand: S'ha afegit " + n + " items");
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Actualitzar estand
     * @param conn connexió a la BD
     * @param id identificador de estand
     * @param Nom Nom de la empresa
     * @param Superficie superficie
     * @param Quota quota estand
     * @param DataInici data inici estand
     * @param DataFi data fi estand
     * @param Fira Fira a la cual pertany
     * @param Empresa empresa del estand
     * @return retorna true si s'ha modificat correctament
     */
    public boolean UpdateEstand(Connection conn, int id, String Nom, Float Superficie, Float Quota, Date DataInici, Date DataFi, int Fira, int Empresa) {
        try {
            String cadenaSQL = "UPDATE Estands SET Nom=?,SuperficieEstand=?,QuotaEstand=?,DataInici=?,DataFi=?,Fira=?,Empresa=? WHERE EstandID=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, Nom);
            pstmt.setFloat(2, Superficie);
            pstmt.setFloat(3, Quota);
            pstmt.setDate(4, new java.sql.Date(DataInici.getTime()));
            pstmt.setDate(5, new java.sql.Date(DataFi.getTime()));
            pstmt.setInt(6, Fira);
            pstmt.setInt(7, Empresa);
            pstmt.setInt(8, id);
            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){

            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }
    }

    /**
     * Eliminar estand
     * @param conn connexió a la BD
     * @param id identificador estand
     * @return retorna true si s'ha eliminat correctament
     */
    public boolean DeleteEstand(Connection conn, int id){
        try {
            String cadenaSQL = "DELETE from Estands Where EstandID = ?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id);


            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            if(Utils.dialegConfirmacioEliminacio()) {
                conn.commit();

            }else{
                conn.rollback();}
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en eliminar");
            alert.setContentText("Per eliminar aquest registre primer has d'eliminar el seu contingut!");
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }

    }

    /**
     * Per cercar estand
     * @param conn connexió a la BD
     * @param NomSearch nom a cercar
     * @param Fira fira
     * @param tableView taula on cercar
     */

    public void findbyParams(Connection conn, String NomSearch, int Fira, TableView tableView){
        try {
            String cadenaSQL= "SELECT EstandID,Estands.Nom,SuperficieEstand,QuotaEstand,Estands.DataInici,Estands.DataFi,Fires.Titol, Empreses.EmpresaID as `Empresa`,Estands.Fira "
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

    /**
     * Per omplir els camps al actualitzar
     * @param conn connexió a la BD
     * @param id identificador de la fira
     * @param txtFieldNomEstand camp text nom estand
     * @param txtFieldSuperficie camp text superficie
     * @param txtFieldQuota camp text quota
     * @param txtFieldDtInici camp text data inici
     * @param txtFieldDtFi camp data fi
     */
    public void omplirCamps(Connection conn, int id, TextField txtFieldNomEstand, TextField txtFieldSuperficie, TextField txtFieldQuota, DatePicker txtFieldDtInici, DatePicker txtFieldDtFi) {
        try {
            String cadenaSQL= "SELECT Nom,SuperficieEstand,QuotaEstand,DataInici,DataFi,Fira,Empresa FROM Estands WHERE EstandID = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setInt(1,id);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {

                    txtFieldNomEstand.setText(resultat.getString(1));
                    txtFieldSuperficie.setText(resultat.getString(2));
                    txtFieldQuota.setText(resultat.getString(3));
                    //txtTelefon.setText(resultat.getString(4));
                    txtFieldDtInici.setValue(resultat.getDate(4).toLocalDate());
                    txtFieldDtFi.setValue(resultat.getDate(5).toLocalDate());
                }
                System.out.println("1");
            }
        }catch (SQLException ex){
            System.out.println(ex.getErrorCode());

        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("3");

            }
        }
    }

}
