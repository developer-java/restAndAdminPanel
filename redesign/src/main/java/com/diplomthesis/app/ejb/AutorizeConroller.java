package com.diplomthesis.app.ejb;

import com.diplomthesis.app.persistence.SystemUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AutorizeConroller {

    @PersistenceContext(unitName = "unitBase")
    private EntityManager em;

    public SystemUser getUser(String login, String pass){
        SystemUser user = null;

        try {
            user = (SystemUser) em.createQuery("SELECT u FROM SystemUser u where u.login = :login and u.password = :pass")
                    .setParameter("login",login)
                    .setParameter("pass",pass)
                    .getSingleResult();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }
    public void add(SystemUser user){
        em.persist(user);
    }
}
