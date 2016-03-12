/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author johannes_laptop
 */
@Stateless
public class UserList extends AbstractDAO<KaosUser, String>{
    @PersistenceContext
    private EntityManager em;

    public UserList() {
        super(KaosUser.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public KaosUser getByLogin(String login){
        String jpql = "select u from KaosUser u where u.login = :login";
        List<KaosUser> l = em.createQuery(jpql, KaosUser.class).setParameter("login", login).getResultList();  
        if(l.isEmpty())
            return null;
        else 
            //since login is unique there will be maximum 1 in the list
            return l.get(0);
    }
}
