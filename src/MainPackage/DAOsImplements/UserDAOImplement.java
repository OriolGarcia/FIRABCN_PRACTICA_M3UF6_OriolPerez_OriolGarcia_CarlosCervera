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

    /**
     * Afegir usuari
     * @param conn
     * @param Username username usuari
     * @param Password password usuari
     * @param permissions permisos usuari
     * @param Active
     * @return retorna true si s'ha inserit correctament
     */
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

    /**
     * Actualitzar User
     * @param conn connexió a la BD
     * @param Username username usuari
     * @param Password password usuari
     * @param permissions permisos usuari
     * @param Active
     * @return retorna true si s'ha modificat correctament
     */
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

    /**
     * Per seleccionar el permisos del usuari a crear
     * @param conn connexió a la BD
     * @param User usuari
     * @return retorna true si s'ha afegit correctament
     */
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

    /**
     * Actualizar usuari
     * @param conn connexió a la BD
     * @param Username username usuari
     * @param permissions permisos usuari
     * @param Active
     * @return retorna true si s'ha modificat correctament
     */
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

    /**
     * Eliminar usuari
     * @param conn connexió a la BD
     * @param Username username usuari
     * @return retorna true si s'ha eliminat correctament
     */
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
            if(Utils.dialegConfirmacioEliminacio()) {
                conn.commit();

            }else{
                conn.rollback();}
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

    /**
     * Per fer el login del usuari
     * @param conn connexió a la BD
     * @param User user del usuari
     * @param password password del usuari
     * @return retorna true si s'ha fet el login correctament
     */
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

    /**
     * Per cercar usuari
     * @param conn connexió a la BD
     * @param UsernameSearch cercar usuari
     * @param tableView taula on cercar
     */
    public void findbyUsername(Connection conn,String UsernameSearch,TableView tableView){
        try {
            String cadenaSQL= "SELECT *  FROM Usuaris WHERE (LENGTH(?) <1 or Username like ?)";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, UsernameSearch);
            pstmt.setString(2,"%"+UsernameSearch+"%");
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

    /**
     * Per obtenir permisos
     * @param conn connexió a la BD
     * @param UsernameSearch cercar usuari
     * @return retorna true si s'ha obtingut la informacio correctament
     */
    public Boolean getPermissions(Connection conn,String UsernameSearch){
        try {
            String cadenaSQL= "SELECT PermisosAdmin  FROM Usuaris WHERE  Username = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, UsernameSearch);
            try (ResultSet resultat = pstmt.executeQuery()) {
                if (resultat.next()) {
                    return resultat.getBoolean(1);
                }else return false;
            }

        }catch (SQLException ex){
            System.out.println(ex.getErrorCode());
                return  false;
        }
        finally {
            try{
                pstmt.clearParameters();
            }catch (SQLException ex){
                System.out.println("3");

            }
        }

    }

    /**
     * Per obtenir si l'usuari esta actiu
     * @param conn connexió a la BD
     * @param Username nom usuari
     * @return retorna true si s'ha obtingut la informacio correctament
     */
    public Boolean getActiu(Connection conn,String Username){
        try {
            String cadenaSQL= "SELECT Actiu  FROM Usuaris WHERE  Username = ?";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, Username);
            try (ResultSet resultat = pstmt.executeQuery()) {
                if (resultat.next()) {
                    System.out.println(resultat.getBoolean("Actiu"));
                    return resultat.getBoolean(1);
                }
                else return false;
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return  false;
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
