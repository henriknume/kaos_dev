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
 * @author johannes_laptop
 */
@Stateless
public class PrivateMessageList extends AbstractDAO<PrivateMessage, Long>{
    @PersistenceContext
    private EntityManager em;

    public PrivateMessageList() {
        super(PrivateMessage.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<PrivateMessage> getBySender(KaosUser sender){
        String jpql = "select m from PrivateMessage m where m.sender = :sender";
        return em.createQuery(jpql, PrivateMessage.class).setParameter("sender", sender).getResultList();  
    }
    
    public List<PrivateMessage> getByReceiver(KaosUser receiver){
        String jpql = "select m from PrivateMessage m where m.receiver = :receiver";
        return em.createQuery(jpql, PrivateMessage.class).setParameter("receiver", receiver).getResultList();  
    }
    
    public List<PrivateMessage> getUserConversation(KaosUser user1, KaosUser user2){
        String jpql = "select m from PrivateMessage m where m.receiver = :user1 and m.sender = :user2 or m.receiver = :user2 and m.sender = :user1 order by m.timestamp";
        return em.createQuery(jpql, PrivateMessage.class).setParameter("user1", user1).setParameter("user2", user2).getResultList();
    }
}