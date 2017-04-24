package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
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
 * Created by ogs10_000 on 30/03/2017.
 */
public class FiresDAOImplement implements FiresDAO {

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd= null;


    public boolean AddFira(Connection conn, String Titol,String Ubicació,float Superficie,float PreuEntrada, Date DataInici, Date DataFi){

        try {
            String cadenaSQL = "INSERT INTO Fires(Titol,Ubicacio,`Superficie Fira`,`Preu Entrada`,DataInici,DataFi)"
            +" VALUES(?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Titol);
            pstmt.setString(2, Ubicació);
            pstmt.setFloat(3, Superficie);
            pstmt.setFloat(4, PreuEntrada);
            pstmt.setDate(5, new java.sql.Date(DataInici.getTime()));
            pstmt.setDate(6, new java.sql.Date(DataFi.getTime()));
            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            System.out.println("Fires: S'ha afegit " + n + " items");
            if (n>0)return true;
            else return false;
        }catch (SQLException ex){
            System.out.println(ex);
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }

    }
    public boolean UpdateFira(Connection conn,int id, String Titol,String Ubicació, float Superficie,float PreuEntrada, Date DataInici,Date DataFi){
        try {
            String cadenaSQL = "UPDATE Fires SET Titol=?,Ubicacio=?,`Superficie Fira`=?,`Preu Entrada`=?,DataInici=?,DataFi=? WHERE FiraID=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Titol);
            pstmt.setString(2, Ubicació);
            pstmt.setFloat(3, Superficie);
            pstmt.setFloat(4, PreuEntrada);
            pstmt.setDate(5, new java.sql.Date(DataInici.getTime()));
            pstmt.setDate(6, new java.sql.Date(DataFi.getTime()));
            pstmt.setInt(7,id);
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

    public boolean DeleteFira(Connection conn, int id){
        try {
            String cadenaSQL = "DELETE from Fires Where FiraID = ?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,id);


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
            System.out.println(ex.getErrorCode());
            return false;
        }finally {
            try {
                pstmt.clearParameters();
            }catch (SQLException ex){}
        }

    }

    public void findbyParams(Connection conn,String TitolSearch,TableView tableView){

        try {
            String cadenaSQL= "SELECT *  FROM Fires WHERE (LENGTH(?) <1 or Titol like ?)";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, TitolSearch);
            pstmt.setString(2,"%"+TitolSearch+"%");
            System.out.println("1");
            try (ResultSet resultat = pstmt.executeQuery()) {
                System.out.println("retorna resultSet");
                Utils.omplirTableView(tableView,resultat);

            }
        }catch (SQLException ex){
            System.out.println("Error :" + ex.getErrorCode());

        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("3");

            }
        }

    }
    public void omplirCamps(Connection conn, int id, TextField txtFielTitolFira, TextField txtFieldUbicació, TextField txtFieldSuperificie,TextField txtPreuEntrada, DatePicker DataInici, DatePicker DataFi){

        try {
            String cadenaSQL= "SELECT Titol,Ubicacio,`Superficie Fira`,`Preu Entrada`,DataInici,DataFi FROM Fires WHERE FiraID = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setInt(1,id);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {

                    txtFielTitolFira.setText(resultat.getString(1));
                    txtFieldUbicació.setText(resultat.getString(2));
                    txtFieldSuperificie.setText(resultat.getString(3));
                    txtPreuEntrada.setText(resultat.getString(4));
                    DataInici.setValue(resultat.getDate(5).toLocalDate());
                    DataFi.setValue(resultat.getDate(6).toLocalDate());
                }
                System.out.println("1");
            }
        }catch (SQLException ex){
            System.out.println("Error :" + ex.getErrorCode());

        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("3");

            }
        }

    }

    public void omplirComboBox(Connection conn, ComboBox comboBox){

        try {
            String cadenaSQL= "SELECT FiraID, Titol from Fires";
            pstmt = conn.prepareStatement(cadenaSQL);
              try (ResultSet resultat = pstmt.executeQuery()) {
                  ObservableList<Item> model= FXCollections.observableArrayList();


                  while (resultat.next()) {

                    model.add( new Item(resultat.getInt(1), resultat.getString(2)) );
                }

               comboBox.getItems().addAll(model);
                 }
        }catch (SQLException ex){
            System.out.println("Error :" + ex.getMessage());

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
