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
import kaos.persistence.AbstractDAO;
/**
 *
 * @author David
 */
@Stateless
public class TeamMessageList extends AbstractDAO<TeamMessage, Long> {
    @PersistenceContext
    private EntityManager em;
    
    public TeamMessageList() {
        super(TeamMessage.class);
    }
    public EntityManager getEntityManager() {
        return em;
    }
    public List<TeamMessage> getBySender(String sender){
        String jpql = "select tm from TeamMessage tm where tm.sender_login = :sender";
        return  em.createQuery(jpql, TeamMessage.class).setParameter("sender", sender).getResultList();
        
    }
    public List<TeamMessage> getByReciever(String toTeam) {
        String jpql = "select tm from TeamMessage tm where tm.toteam_name = :toTeam";
        return em.createQuery(jpql, TeamMessage.class).setParameter("toTeam", toTeam).getResultList();
        
       }
    
    }
