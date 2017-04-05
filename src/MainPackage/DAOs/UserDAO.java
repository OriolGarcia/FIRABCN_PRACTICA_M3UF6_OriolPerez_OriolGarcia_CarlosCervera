package MainPackage.DAOs;

import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by ogs10_000 on 29/03/2017.
 */
public interface UserDAO {

    public boolean AddUser(Connection conn, String Username, String Password, Boolean permissions, Boolean Active);
    public boolean UpdateUser(Connection conn, String Username,String Password,Boolean permissions, Boolean Active);
    public boolean DeleteUser(Connection conn, String Username);
    public boolean LoginUser(Connection conn, String User, String password);
    public void findbyUsername(Connection conn, String UsernameSearch, TableView tableView);
    public boolean[] SelectPermisionsActiveFromUser(Connection conn, String User);
}
