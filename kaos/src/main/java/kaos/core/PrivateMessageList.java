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

    public EntityManager getEntityManager() {
        return em;
    }
    
    //SELECT * FROM PrivateMessage, Message WHERE PrivateMessage.id = Message.id
    
    
    public List<PrivateMessage> getBySender(String sender){
        String jpql = "select m from PrivateMessage m where m.sender_login = :sender";
        return em.createQuery(jpql, PrivateMessage.class).setParameter("sender", sender).getResultList();  
    }
    
    public List<PrivateMessage> getByReceiver(String receiver){
        String jpql = "select m from PrivateMessage m where m.receiver_login = :receiver";
        return em.createQuery(jpql, PrivateMessage.class).setParameter("receiver", receiver).getResultList();  
    }
    
}