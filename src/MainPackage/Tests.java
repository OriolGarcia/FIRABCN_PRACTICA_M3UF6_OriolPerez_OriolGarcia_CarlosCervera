package MainPackage;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.UserDAOImplement;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ogs10_000 on 26/04/2017.
 */
public class Tests {

///


    @Test
    public void testConnection() throws Exception {

        BDAccessor accessor = new BDAccessor();

        Connection connexio = accessor.obtenirConnexio();
        assertFalse(connexio.isClosed());
        accessor.tancarConnexio(connexio);
        assertTrue(connexio.isClosed());
    }
    @Test
    public void testLogin()throws Exception {

        BDAccessor accessor = new BDAccessor();
        Connection connexio = accessor.obtenirConnexio();
        UserDAOImplement UserDAOImpl = new UserDAOImplement();
       String usuari = "admin";String pass="admin";
      assertTrue(UserDAOImpl.LoginUser(connexio, usuari, pass));
         usuari = "FAKEUSER"; pass="FAKEPASS";
        assertFalse(UserDAOImpl.LoginUser(connexio, usuari, pass));

    }
    }
