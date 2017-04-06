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
    public boolean AddFira(Connection conn, String Titol,String Ubicació,float Superficie, Date DataInici, Date DataFi){



        try {
            String cadenaSQL = "INSERT INTO Fires(Titol,Ubicacio,`Superficie Fira`,DataInici,DataFi)"
            +" VALUES(?,?,?,?,?);";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Titol);
            pstmt.setString(2, Ubicació);
            pstmt.setFloat(3, Superficie);
            pstmt.setDate(4, new java.sql.Date(DataInici.getTime()));
            pstmt.setDate(5, new java.sql.Date(DataFi.getTime()));
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
    public boolean UpdateFira(Connection conn,int id, String Titol,String Ubicació, float Superficie,Date DataInici,Date DataFi){
        try {
            String cadenaSQL = "UPDATE Fires SET Titol=?,Ubicacio=?,`Superficie Fira`=?,DataInici=?,DataFi=? WHERE FiraID=?;";
            pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,Titol);
            pstmt.setString(2, Ubicació);
            pstmt.setFloat(3, Superficie);
            pstmt.setDate(4, new java.sql.Date(DataInici.getTime()));
            pstmt.setDate(5, new java.sql.Date(DataFi.getTime()));
            pstmt.setInt(6,id);
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
            String cadenaSQL= "SELECT *  FROM Fira WHERE (LENGTH(?) <1 or Titol like ?)";
            pstmt = conn.prepareStatement(cadenaSQL);
            pstmt.setString(1, TitolSearch);
            pstmt.setString(2,"%"+TitolSearch+"%");
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
