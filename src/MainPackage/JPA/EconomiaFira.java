package MainPackage.JPA;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
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
    private float recaptació;
    @ManyToOne
    @JoinColumn(name = "FiraID")
    private Fires fires;
        EconomiaFira(int FiraID,GregorianCalendar date, int numVisitants,float recaptació){
            this.FiraID=FiraID;
            this.date=date;
            this.numVisitants=numVisitants;
            this.recaptació=recaptació;



            }
}
