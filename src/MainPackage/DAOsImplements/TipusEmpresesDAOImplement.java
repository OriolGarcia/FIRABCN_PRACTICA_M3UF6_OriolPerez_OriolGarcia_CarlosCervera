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
 * Created by Carlos on 30/03/17.
 */
public class TipusEmpresesDAOImplement implements TipusEmpresesDAO {


        private static Connection conn;
        private static PreparedStatement pstmt;
        private static BDAccessor bd= null;
        public boolean AddTipusEmpreses(Connection conn, String Username, String Password, Boolean permissions, Boolean Active) {


            try {
                String cadenaSQL = "INSERT INTO TipusEmpreses";
                pstmt = conn.prepareStatement(cadenaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, Username);
                pstmt.setString(2, Password);
                pstmt.setBoolean(3, permissions);
                pstmt.setBoolean(4, Active);

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
            } catch (SQLException ex) {
                System.out.println(ex);
                return false;
            } finally {
                try {
                    pstmt.clearParameters();
                } catch (SQLException ex) {
                }
            }
        }






        public boolean UpdateTipusEmpreses(Connection conn, String Username, String Password, Boolean Permissions, Boolean Active){
            return true;
        }
        public boolean DeleteTipusEmpreses(Connection conn, String Username){
            return true;
        }


}