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
    public List<TeamMessage> getBySender(KaosUser sender){
        String jpql = "select tm from TeamMessage tm where tm.sender = :sender";
        return  em.createQuery(jpql, TeamMessage.class).setParameter("sender", sender).getResultList();
        
    }
    public List<TeamMessage> getByReceiver(Team receiver) {
        String jpql = "select tm from TeamMessage tm where tm.receiver = :receiver";
        return em.createQuery(jpql, TeamMessage.class).setParameter("receiver", receiver).getResultList();
        
       }
    
    }
