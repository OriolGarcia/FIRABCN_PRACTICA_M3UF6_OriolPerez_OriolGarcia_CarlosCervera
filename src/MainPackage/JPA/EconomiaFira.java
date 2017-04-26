package MainPackage.JPA;

//import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import MainPackage.Accessor.BDAccessor;
import MainPackage.DAOsImplements.EconomiaDAOImplement;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
/**
 * Created by ogs10_000 on 19/04/2017.
 */
@Entity
@Access(AccessType.FIELD)
public class EconomiaFira {
    @Id
    private int FiraID;
    @Id
    private GregorianCalendar date;
    private int numVisitants;

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public int getNumVisitants() {
        return numVisitants;
    }

    public void setNumVisitants(int numVisitants) {
        this.numVisitants = numVisitants;
    }

    public float getRecaptació() {
        return recaptació;
    }

    public void setRecaptació(float recaptació) {
        this.recaptació = recaptació;
    }

    public Fires getFires() {
        return fires;
    }

    public void setFires(Fires fires) {
        this.fires = fires;
    }

    private float recaptació;
    @ManyToOne
    @JoinColumn(name = "FiraID")
    private Fires fires;
    public EconomiaFira(){}
    public    EconomiaFira(int FiraID,GregorianCalendar date, int numVisitants,float recaptació){
            this.FiraID=FiraID;
            this.date=date;
            this.numVisitants=numVisitants;
            this.recaptació=recaptació;



            }


    @Override
    public String toString() {
        BDAccessor accessor = new BDAccessor();
        Connection connexio;
        try {
            connexio = accessor.obtenirConnexio();
            EconomiaDAOImplement ecoDAOImpl = new EconomiaDAOImplement();
            float quotaTotal = ecoDAOImpl.getQuotaEstands(connexio,FiraID);
            float   preuEntrada = ecoDAOImpl.getPreuEntrada(connexio,FiraID);
            float EntPerVis = preuEntrada * numVisitants;
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
            String dateFormatted = fmt.format(date);
            return "Economia per el dia "+dateFormatted+":  \n"+"Numero de visitants:"+numVisitants
                    +"\n Recaptació Total:"+recaptació+ " €"
                    +"\n Recaptació per entrades:"+EntPerVis
                    +"\n Recaptació per quotes estands:"+quotaTotal;
        }catch(IOException ex) {

        }
        catch(ClassNotFoundException ex){

        }
        catch(SQLException e){

        }
        return "";
    }
}
