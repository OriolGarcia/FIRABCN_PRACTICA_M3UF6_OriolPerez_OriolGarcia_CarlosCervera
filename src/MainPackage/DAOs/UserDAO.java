package MainPackage.DAOs;

/**
 * Created by ogs10_000 on 29/03/2017.
 */
public interface UserDAO {

        public boolean AddUser(String Username,String Password,Boolean permissions, Boolean Active);
    public boolean UpdateUser(String Username,String Password,Boolean permissions, Boolean Active);
    public boolean DeleteUser(String Username);
    public boolean LoginUser(String User, String password);
}
