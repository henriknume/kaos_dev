/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kaos.persistence.AbstractDAO;

/**
 *
 * @author johannes_laptop
 */
@Stateless
public class UserList extends AbstractDAO<KaosUser, Long>{
    @PersistenceContext
    private EntityManager em;

    public UserList() {
        super(KaosUser.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
