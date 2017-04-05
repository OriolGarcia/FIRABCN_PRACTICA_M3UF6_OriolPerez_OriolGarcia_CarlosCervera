package MainPackage.DAOs;

import java.sql.Connection;

/**
 * Created by Carlos on 3/04/17.
 */
public interface EmpresesDAO {

    public boolean AddEmpreses(Connection conn, String Empreses);
    public boolean UpdateEmpreses(Connection con, String Empreses);
    public boolean DeleteEmpreses(Connection conn, String Empreses);

}
