package MainPackage.JPA;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ogs10_000 on 19/04/2017.
 */
public class ServeiEconomiaFira{
        protected EntityManager em;
        public ServeiEconomiaFira(EntityManager em) {
            this.em = em;
        }

        public EconomiaFira crearEconomiaFira(int FiraID,GregorianCalendar date, int numVisitants,float recaptació) {
            EconomiaFira ef = new EconomiaFira( FiraID,date,  numVisitants, recaptació);
            em.persist(ef);
            return ef;
        }

        public void esborrarAparcament(int id) {
            Aparcament emp = trobarAparcament(id);
            if (emp != null) {
                em.remove(emp);
            }
        }


        public Aparcament trobarAparcament(int id) {
            return em.find(Aparcament.class, id);
        }

        public List<Aparcament> trobarTotsAparcaments() {
            TypedQuery<Aparcament> query = em.createQuery(
                    "SELECT e FROM Aparcament e", Aparcament.class);
            return query.getResultList();
        }

}
