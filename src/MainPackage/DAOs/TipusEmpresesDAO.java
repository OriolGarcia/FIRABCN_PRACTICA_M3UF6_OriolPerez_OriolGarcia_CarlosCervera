package MainPackage.DAOs;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Carlos on 30/03/17.
 */


public interface TipusEmpresesDAO {

        public boolean AddTipusEmpreses(Connection conn, String Username, String Password, Boolean Permissions, Boolean Active);
        public boolean UpdateTipusEmpreses(Connection conn, String Username, String Password, Boolean Permissions, Boolean Active);
        public boolean DeleteTipusEmpreses(Connection conn, String Username);
        public  void omplirComboBox(Connection conn, ComboBox comboBox);
}
