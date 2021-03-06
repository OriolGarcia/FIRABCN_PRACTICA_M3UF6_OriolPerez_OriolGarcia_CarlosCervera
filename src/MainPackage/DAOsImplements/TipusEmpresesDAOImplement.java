package MainPackage.DAOsImplements;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOs.*;
import MainPackage.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
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
    private static BDAccessor bd = null;

    public boolean AddTipusEmpreses(Connection conn, String Username, String Password, Boolean permissions, Boolean Active) {

        return false;
    }

    public boolean UpdateTipusEmpreses(Connection conn, String Username, String Password, Boolean Permissions, Boolean Active){
        return true;
    }


    public boolean DeleteTipusEmpreses(Connection conn, String Username){
        return true;
    }

    /**
     * Per omplir el comboBox de tipus d'emprees
     * @param conn connexió a la BD
     * @param comboBox combobox
     */
    public void omplirComboBox(Connection conn, ComboBox comboBox) {

        try {
            String cadenaSQL = "SELECT TipusID, TitolTipus from TipusEmpresa";
            pstmt = conn.prepareStatement(cadenaSQL);
            try (ResultSet resultat = pstmt.executeQuery()) {
                ObservableList<Item> model = FXCollections.observableArrayList();

                while (resultat.next()) {

                    model.add(new Item(resultat.getInt(1), resultat.getString(2)));
                }

                comboBox.getItems().addAll(model);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());

        } finally {
            try {
                pstmt.clearParameters();
            } catch (SQLException ex) {
                System.out.println("3");

            }
        }
    }
}
