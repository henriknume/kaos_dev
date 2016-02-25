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
public class TeamList extends AbstractDAO<Team, String> {
    @PersistenceContext
    private EntityManager em;
    
    public TeamList() {
        super(Team.class);
    }
    public EntityManager getEntityManager() {
        return em;
    }
    public Team getByTeamName(String name){
        String jpql = "select t from Team t where t.name = :name";
        List<Team> temp = em.createQuery(jpql, Team.class).setParameter("name", name).getResultList();
        if(temp.size() == 0) {
        return null;
        }
        else {
            return temp.get(0);
        }
    }
    
}
