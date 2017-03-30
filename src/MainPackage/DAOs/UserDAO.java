package MainPackage.DAOs;

import java.sql.Connection;

/**
 * Created by ogs10_000 on 29/03/2017.
 */
public interface UserDAO {

        public boolean AddUser(Connection conn, String Username, String Password, Boolean permissions, Boolean Active);
    public boolean UpdateUser(Connection conn, String Username,String Password,Boolean permissions, Boolean Active);
    public boolean DeleteUser(Connection conn, String Username);
    public boolean LoginUser(Connection conn, String User, String password);
}
