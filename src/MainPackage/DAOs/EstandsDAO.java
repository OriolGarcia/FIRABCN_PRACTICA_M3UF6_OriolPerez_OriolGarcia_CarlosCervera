package MainPackage.DAOs;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;

/**
 * Created by oriol on 10/04/2017.
 */
public interface EstandsDAO {
    public interface EmpresesDAO {
        public boolean AddEstand(Connection conn, String Nom, Float Superficie, Float Quota, Date DataInici, Date DataFi, int Fira, int Empresa);
        public boolean UpdateEstand(Connection conn, String Nom, Float Superficie, Float Quota, Date DataInici, Date DataFi, int Fira, int Empresa);
        public boolean DeleteEstand(Connection conn, int id);
        public void findbyParams(Connection conn, String NomSearch, int Empresa, TableView tableView);
        public void omplirCamps(Connection conn, int id, TextField txtFieldNomEstand, TextField txtFieldSuperficie, TextField txtFieldQuota, TextField txtFieldDtInici, TextField txtFieldDtFi);
        //
    }
}
