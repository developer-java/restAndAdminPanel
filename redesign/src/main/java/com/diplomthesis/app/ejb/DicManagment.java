package com.diplomthesis.app.ejb;

import com.diplomthesis.app.persistence.Region;
import com.diplomthesis.app.persistence.Sight;

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

    public List<Sight> getSightList(){
        return em.createQuery("SELECT d FROM Sight d").getResultList();
    }

    public List<Sight> getSightList(Long id){
        return em.createQuery("SELECT d FROM Sight d where d.region.id = :id")
                .setParameter("id",id)
                .getResultList();
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

    public void delete(Sight sight){
        em.remove(sight);
    }
    public void delete(Region region){
        em.remove(region);
    }
}
