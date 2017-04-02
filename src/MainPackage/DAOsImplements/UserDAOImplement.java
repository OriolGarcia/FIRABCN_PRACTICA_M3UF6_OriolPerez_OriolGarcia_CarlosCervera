package MainPackage.DAOsImplements;
import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.*;
import MainPackage.Utils;
import javafx.scene.control.TableView;
import sun.security.util.Password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ogs10_000 on 30/03/2017.
 */
public class UserDAOImplement implements UserDAO {

    private static Connection conn;
    private static PreparedStatement pstmt;
    private static BDAccessor bd= null;
    public boolean AddUser(Connection conn, String Username,String Password,Boolean permissions, Boolean Active){



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
