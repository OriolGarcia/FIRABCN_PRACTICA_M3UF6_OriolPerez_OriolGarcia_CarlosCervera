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
    private GregorianCalendar Date;
    private int numVisitants;
    private float Recaptació;
    @ManyToOne
    @JoinColumn(name = "FiraID")
    private Fires fires;

}
