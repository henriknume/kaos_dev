package kaos.core;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Base class for any persisting collection DAO = Data access object (also
 * called a Facade)
 *
 *   *** TODO Implement all methods ***
 *
 * @param <T> Type
 * @param <K> Primary key (id)
 * @author hajo
 */
public abstract class AbstractDAO<T, K>{

    private final Class<T> clazz;

    // To be overridden by subclasses
    protected abstract EntityManager getEntityManager();

    protected AbstractDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void create(T t) {
       getEntityManager().persist(t);
    }

    public void delete(K id) {
        T t = getEntityManager().getReference(clazz, id);
        getEntityManager().remove(t);
    }

    /**
     * Using out parameter
     *
     * @param t
     */
    public void update(T t) {
        getEntityManager().merge(t);
    }

    public T find(K id) {
       return getEntityManager().find(clazz, id);
    }

    public List<T> findAll() {
       return get(true, -1, -1);
    }

    public List<T> findRange(int first, int n) {
        return get(false, first, n);
    }

    //@SuppressWarnings("")
    private List<T> get(boolean all, int first, int n) {
        EntityManager em = getEntityManager();
        List<T> found = new ArrayList<>();
        // Warning because typename not found in string (clazz.getSimpleName())
        // Criteria API better, possible misstakes in String, NOTE space before t
        TypedQuery<T> q = em.createQuery("select t from " + clazz.getSimpleName() + " t", clazz);
        if (!all) {
            q.setFirstResult(first);
            q.setMaxResults(n);
        }
        found.addAll(q.getResultList());
        return found;
    }

    public int count() {
        EntityManager em = getEntityManager();
        // Warning because typename not found in string (clazz.getSimpleName())
        // Criteria API better, possible misstakes in String, NOTE space before t      
        Long n = em.createQuery("select count(t) from " + clazz.getSimpleName() + " t", Long.class)
                .getSingleResult();
        return n.intValue();
    } 
}
