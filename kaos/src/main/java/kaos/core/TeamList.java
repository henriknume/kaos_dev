/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private final static Logger log = Logger.getAnonymousLogger();
    
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
        if(temp.isEmpty())
            return null;
        else
            return temp.get(0);
    }
    public List<KaosUser> getMembers(String teamName){
        String jpql = "select t from Team t where t.name = :name";
        List<Team> temp = em.createQuery(jpql, Team.class).setParameter("name", teamName).getResultList();
        if(temp.isEmpty())
            return null;
        else {
            return temp.get(0).getMembers();
        }
    }
    public ArrayList<String> getTeamsByUser(String user){
        String jpql = "select t from Team t";
        List<Team> l = em.createQuery(jpql, Team.class).getResultList();
        ArrayList<String> sl = new ArrayList<>();
        for(Team t : l){
            for(KaosUser u : t.getMembers()){
                if(u.getLogin().equals(user))
                    sl.add(t.getName());
            }
        }
        return sl;
    }
}
