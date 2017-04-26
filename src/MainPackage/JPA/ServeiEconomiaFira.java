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

        public void esborrarEconomiaFira(int EconomiaFiraId) {
            EconomiaFira economiaFira = trobarEconomiaFira(EconomiaFiraId);
            if (economiaFira != null) {
                em.remove(economiaFira);
            }
        }


        public EconomiaFira trobarEconomiaFira(int EconomiaFiraId) {
            return em.find(EconomiaFira.class, EconomiaFiraId);
        }
    public String EconomiaFiraToString(int FiraID) {
        List<EconomiaFira> economiesFira = trobarTotsEconomiaFira(FiraID);
        if (economiesFira == null) {
            System.out.println("No EconomiaFiras found . ");
            return"";
        } else {

            float Total = 0;
            for (EconomiaFira economiaFira : economiesFira) {
                Total += economiaFira.getRecaptació();
            }

            return "Total recaptat en tota la fira:"+Total;
        }
    }
    public List<EconomiaFira> trobarTotsEconomiaFira(int FiraID) {
        TypedQuery<EconomiaFira> query = em.createQuery(
                "SELECT e FROM EconomiaFira e where e.FiraID="+FiraID, EconomiaFira.class);
        return query.getResultList();
    }


}
