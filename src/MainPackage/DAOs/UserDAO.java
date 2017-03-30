package MainPackage.DAOs;

/**
 * Created by ogs10_000 on 29/03/2017.
 */
public interface UserDAO {

    public boolean AddUser();
    public boolean UpdateUser();
    public boolean DeleteUser();
    public boolean LoginUser(String User, String password);
}
