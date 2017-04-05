package MainPackage.DAOs;

import javafx.scene.control.TableView;

import java.sql.Connection;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ogs10_000 on 04/04/2017.
 */
public interface FiresDAO {


    public boolean AddFira(Connection conn, String Titol,String Ubicació, float Superficie,Date Datainici,Date DataFi);
    public boolean UpdateFira(Connection conn,int id, String Titol,String Ubicació, float Superficie,Date Datainici,Date DataFi);
    public boolean DeleteFira(Connection conn, int id);
    public void findbyParams(Connection conn, String TitolSearch, TableView tableView);

    //
   }
