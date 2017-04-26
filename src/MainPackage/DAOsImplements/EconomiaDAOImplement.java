package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.EconomiaDAO;
import MainPackage.Utils;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    public boolean AddEconomia(Connection conn, int firaID, Date data, int numVisitants, Float recaptació){
        try {
            String cadenaSQL = "INSERT INTO ECONOMIAFIRA(FiraID,DATA,NumVisitants,Recaptació)"
                    +" VALUES(?,?,?,?);";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, firaID);
            pstmt.setDate(2, new java.sql.Date(data.getTime()));
            pstmt.setInt(3, numVisitants);
            pstmt.setFloat(4, recaptació);

            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            System.out.println("Economia: S'ha afegit " + n + " items");
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

    public boolean UpdateEconomia(Connection conn, int id, Date data, int numVisitants, Float recaptacio, Date dataID) {
        try {
            String cadenaSQL = "UPDATE ECONOMIAFIRA SET DATA=?,NumVisitants=?,Recaptació=? WHERE FiraID=? AND DATA=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);

            pstmt.setDate(1, new java.sql.Date(data.getTime()));
            pstmt.setInt(2, numVisitants);
            pstmt.setFloat(3, recaptacio);
            pstmt.setInt(4,id);
            pstmt.setDate(5, new java.sql.Date(dataID.getTime()));

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

    public boolean DeleteEconomia(Connection conn, int firaID, Date dateID) {
        try {
            String cadenaSQL = "DELETE from ECONOMIAFIRA Where FiraID = ? AND DATA = ?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,firaID);
            pstmt.setDate(2,new java.sql.Date(dateID.getTime()));


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

    public void findbyParams(Connection conn, String TitolSearch, int id, TableView tableView) {
        try {
            String cadenaSQL= "SELECT ECONOMIAFIRA.FiraID,DATA,NumVisitants,Recaptació"
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

    public void omplirCamps(Connection conn, int id, Date dateID, TextField numVisitants, DatePicker dtDataActual){
        try {
            String cadenaSQL= "SELECT NumVisitants, DATA FROM ECONOMIAFIRA WHERE FiraID = ? AND DATA = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setInt(1, id);
            pstmt.setDate(2, new java.sql.Date(dateID.getTime()));

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {

                    numVisitants.setText(resultat.getString(1));
                    dtDataActual.setValue(resultat.getDate(2).toLocalDate());

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

    public float getQuotaEstands(Connection conn, int firaID){
        float quotaTotal = 0;

        try {
            String cadenaSQL= "SELECT SUM(QuotaEstand) FROM Estands WHERE Fira = ?;";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setInt(1,firaID);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {
                    quotaTotal = resultat.getFloat(1);
                    System.out.println("Quota total: " + quotaTotal);

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

        return quotaTotal;
    }

    public float getPreuEntrada(Connection conn, int firaID){
        float preuEntrada = 0;

        try {
            String cadenaSQL= "SELECT `Preu Entrada` FROM Fires WHERE FiraID = ?;";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setInt(1,firaID);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {
                    preuEntrada = resultat.getFloat(1);
                    System.out.println("Preu entrada: " + preuEntrada);

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

        return preuEntrada;
    }
}
