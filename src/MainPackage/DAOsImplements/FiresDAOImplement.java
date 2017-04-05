package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.FiresDAO;
import MainPackage.DAOs.UserDAO;
import MainPackage.Utils;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by ogs10_000 on 30/03/2017.
 */
public class FiresDAOImplement implements FiresDAO {

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd= null;
    public boolean AddFira(Connection conn, String Titol,String Ubicació,float Superficie Date DataInici, Date DataFi){



        try {
            String cadenaSQL = "INSERT INTO Usuaris(Username,Password,PermisosAdmin,Actiu) VALUES(?,MD5(?),?,?);";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Username);
            pstmt.setString(2, Password);
            pstmt.setBoolean(3, permissions);
            pstmt.setBoolean(4,Active);

            int n = pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                while (rs.next()) {
                    System.out.println("Codi generat per getGeneratedKeys():"
                            + rs.getInt(1));
                }
            }
            conn.commit();
            System.out.println("S'ha afegit " + n + " items");
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
    public boolean UpdateUser(Connection conn, String Username,String Password,Boolean permissions, Boolean Active){
        try {
            String cadenaSQL = "UPDATE Usuaris SET Username=?,Password= MD5(?),PermisosAdmin=?,Actiu=? WHERE Username=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Username);
            pstmt.setString(2, Password);
            pstmt.setBoolean(3, permissions);
            pstmt.setBoolean(4,Active);
            pstmt.setString(5,Username);

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

    public boolean[] SelectPermisionsActiveFromUser(Connection conn, String User){


        try {
            String cadenaSQL= "SELECT PermisosAdmin,Actiu FROM Usuaris WHERE Username = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1,User);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {

                    return new boolean[]{!resultat.getBoolean(1),!resultat.getBoolean(2)     };



                }
                System.out.println("1");
                return new boolean[]{false,false};
            }
        }catch (SQLException ex){
            System.out.println(ex.getErrorCode());
            return new boolean[]{false,false};
        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("3");

            }
        }

    }




    public boolean UpdateUserPermissions(Connection conn, String Username,Boolean permissions, Boolean Active){
        try {
            String cadenaSQL = "UPDATE Usuaris SET Username=?,PermisosAdmin=?,Actiu=? WHERE Username=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Username);
            pstmt.setBoolean(2, permissions);
            pstmt.setBoolean(3,Active);
            pstmt.setString(4,Username);

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
    public boolean DeleteUser(Connection conn, String Username){


        try {
            String cadenaSQL = "DELETE from Usuaris Where Username=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Username);


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
    public boolean LoginUser(Connection conn, String User, String password){

        try {
            String cadenaSQL= "SELECT count(Username), MD5(?),Password FROM Usuaris WHERE Username = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, password);
            pstmt.setString(2,User);

            try (ResultSet resultat = pstmt.executeQuery()) {

                while (resultat.next()) {

                    int n=resultat.getInt(1);
                    if (n!=1) return false;
                    else if (!resultat.getString(2).equals(resultat.getString(3)))
                        return false;
                    else{ return true;}



                }
                System.out.println("1");
                return false;
            }
        }catch (SQLException ex){
            System.out.println(ex.getErrorCode());
            return false;
        }
        finally {
                try{
               pstmt.clearParameters();
            }catch (SQLException ex){
                    System.out.println("3");

            }
        }
 }
    public void findbyUsername(Connection conn,String UsernameSearch,TableView tableView){

        try {
            String cadenaSQL= "SELECT *  FROM Usuaris WHERE (LENGTH(?) <1 or Username like ?)";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, UsernameSearch);
            pstmt.setString(2,"%"+UsernameSearch+"%");
            System.out.println("1");
            try (ResultSet resultat = pstmt.executeQuery()) {
                System.out.println("retorna resultSet");
                Utils.omplirTableView(tableView,resultat);

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
