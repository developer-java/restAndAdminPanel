package kz.sud.cabinet.officeRest.ejb;


import kz.sud.cabinet.officeRest.persistence.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DicManagment {
    @PersistenceContext(unitName = "unitBase")
    private EntityManager em;

    public List<Region> getRegionList(){
        List<Region> regions = new ArrayList<>();
        for(Region region : (List<Region>)em.createQuery("SELECT d FROM Region d").getResultList()){
            region.setCountSight(getCoundSightByRegionId(region.getId()));
            regions.add(region);
        }
        return regions;
    }

    public List<Sight> getSightList(){
        return em.createQuery("SELECT d FROM Sight d").getResultList();
    }

    public List<Sight> getSightList(Long id){
        return em.createQuery("SELECT d FROM Sight d where d.paternId.id = :id")
                .setParameter("id",id)
                .getResultList();
    }
    public List<SightC> getSightListC(Long id){
        return em.createQuery("SELECT d FROM SightC d where d.paternId.id = :id")
                .setParameter("id",id)
                .getResultList();
    }
    public List<SightC> getSightListC(){
        return em.createQuery("SELECT d FROM SightC d")
                .getResultList();
    }
    public List<Sight> getSightList(Sight.Category category, Long id){
        return em.createQuery("SELECT d FROM Sight d where d.paternId.id = :id and d.category = :category")
                .setParameter("id",id)
                .setParameter("category",category)
                .getResultList();
    }
    public Long getCoundSightByRegionId(Long id){
        return (Long) em.createQuery("SELECT COUNT(n) FROM Sight n WHERE n.paternId.id = :id")
                .setParameter("id",id)
                .getSingleResult();
    }
    public void add(Region dic){
        em.persist(dic);
    }
    public Region merge(Region dic){
        return em.merge(dic);
    }

    public void add(Sight dic){
        em.persist(dic);
    }
    public Sight merge(Sight dic){
        return em.merge(dic);
    }

    public void add(Coment coment){
        em.persist(coment);
    }
    public List<Coment> getComents(Long sightId){
        return em.createQuery("SELECT c FROM Coment c WHERE c.sightId = :id")
                .setParameter("id",sightId)
                .getResultList();
    }
    public Sight getSightById(Long id){
        return em.find(Sight.class, id);
    }
    public List<Coment> getComentList(Long id){
        List<Coment> coments = em.createQuery("SELECT d FROM Coment d where d.sightId = :id")
                .setParameter("id",id)
                .getResultList();
        return coments;
    }

    public List<Sight> getSightList(Sight.Category category) {
        return em.createQuery("SELECT s FROM Sight s WHERE s.category = :category")
                .setParameter("category",category)
                .getResultList();
    }

    public List<City> getCityList(Long id) {
        List<City> coments = em.createQuery("SELECT d FROM City d where d.region.id = :id")
                .setParameter("id",id)
                .getResultList();
        return coments;
    }

    public List<SightC> getSightCList(SightC.Category category, Long id) {
        return em.createQuery("SELECT s FROM SightC s WHERE s.category = :ctg AND s.paternId.id = :id")
                .setParameter("ctg",category)
                .setParameter("id",id)
                .getResultList();
    }
}
