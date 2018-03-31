package com.diplomthesis.app.ejb;

import com.diplomthesis.app.persistence.City;
import com.diplomthesis.app.persistence.Region;
import com.diplomthesis.app.persistence.Sight;
import com.diplomthesis.app.persistence.SightC;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DicManagment {
    @PersistenceContext(unitName = "unitBase")
    private EntityManager em;

    public List<Region> getRegionList(){
        return em.createQuery("SELECT d FROM Region d").getResultList();
    }

    public Region getRegion(Long id){
        return em.createQuery("SELECT d FROM Region d WHERE d.id = :id",Region.class).setParameter("id",id).getSingleResult();
    }

    public List<Sight> getSightList(){
        return em.createQuery("SELECT d FROM Sight d").getResultList();
    }

    public List<Sight> getSightList(Long id){
        return em.createQuery("SELECT d FROM Sight d where d.region.id = :id")
                .setParameter("id",id)
                .getResultList();
    }

    public List<SightC> getSightListC(Long id){
        return em.createQuery("SELECT d FROM SightC d where d.city.id = :id")
                .setParameter("id",id)
                .getResultList();
    }

    public List<City> getCityList(){
        return em.createQuery("SELECT c FROM City c").getResultList();
    }
    public List<City> getCityList(Long regionId){
        return em.createQuery("SELECT c FROM City c WHERE c.region.id = :id")
                .setParameter("id",regionId)
                .getResultList();
    }
    public void add(Region dic){
        em.persist(dic);
    }
    public void add(SightC dic){
        em.persist(dic);
    }
    public void add(City dic){
        em.persist(dic);
    }
    public Region merge(Region dic){
        return em.merge(dic);
    }

    public void add(Sight dic){
        if(em.contains(dic)){
            merge(dic);
        }else {
            em.persist(dic);
        }
    }
    public Sight merge(Sight dic){
        return em.merge(dic);
    }


    public void delete(SightC city){
        em.remove(city);
    }
    public void delete(City city){
        em.remove(city);
    }
    public void delete(Sight sight){
        em.remove(sight);
    }
    public void delete(Region region){
        em.remove(region);
    }
}
