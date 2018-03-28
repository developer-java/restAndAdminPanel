package kz.sud.cabinet.officeRest.ejb;
import kz.sud.cabinet.officeRest.persistence.Region;
import kz.sud.cabinet.officeRest.persistence.Sight;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FileManagement {

    @PersistenceContext(unitName = "unitBase")
    private EntityManager em;
    public String getImagePathBySightId(Long id){
        Sight sight = em.createQuery("SELECT s FROM Sight s WHERE s.id = :id", Sight.class)
                .setParameter("id",id)
                .getSingleResult();
        return sight.getImageUrl()==null ? null : sight.getImageUrl();
    }
    public String getImagePathByRegionId(Long id){
        Region region = em.createQuery("SELECT r FROM Region r WHERE r.id = :id", Region.class)
                .setParameter("id",id)
                .getSingleResult();
        return region.getImageUrl()==null ? null : region.getImageUrl();
    }
}
