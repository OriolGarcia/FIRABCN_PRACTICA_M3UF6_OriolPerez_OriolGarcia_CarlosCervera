package MainPackage.JPA;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ogs10_000 on 19/04/2017.
 */
@Entity
@Access(AccessType.FIELD)
public class Fires {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int FiraID;
    private String Titol;
    private String Ubicacio;
    private String Superficie_Fira;
    private GregorianCalendar DataInici;
    private String DataFi;

    @OneToMany(cascade=CascadeType.PERSIST , mappedBy = "fires")
    private List<EconomiaFira>EconomiaFira=new ArrayList<EconomiaFira>();

    public int getFiraID() {
        return FiraID;
    }

    public void setFiraID(int firaID) {
        FiraID = firaID;
    }

    public String getTitol() {
        return Titol;
    }

    public void setTitol(String titol) {
        Titol = titol;
    }

    public String getUbicacio() {
        return Ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        Ubicacio = ubicacio;
    }

    public String getSuperficie_Fira() {
        return Superficie_Fira;
    }

    public void setSuperficie_Fira(String superficie_Fira) {
        Superficie_Fira = superficie_Fira;
    }

    public GregorianCalendar getDataInici() {
        return DataInici;
    }

    public void setDataInici(GregorianCalendar dataInici) {
        DataInici = dataInici;
    }

    public String getDataFi() {
        return DataFi;
    }

    public void setDataFi(String dataFi) {
        DataFi = dataFi;
    }

    public List<MainPackage.JPA.EconomiaFira> getEconomiaFira() {
        return EconomiaFira;
    }

    public void setEconomiaFira(List<MainPackage.JPA.EconomiaFira> economiaFira) {
        EconomiaFira = economiaFira;
    }

    @Override
    public String toString() {
        return "Fires{" + "id=" + FiraID + ", titol=" + Titol + '}';
    }
}
