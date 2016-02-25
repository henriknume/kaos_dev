package kaos.test;

import java.util.List;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import kaos.core.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing the persistence layer
 *
 * NOTE NOTE NOTE: JavaDB (Derby) must be running (not using an embedded
 * database) GlassFish not needed using Arquillian
 *
 * @author hajo
 */
@RunWith(Arquillian.class)
public class TestChatPersistence {

    @Inject
    Chat chat;

    @Inject
    UserTransaction utx;  // This is not an EJB so have to handle transactions

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "chat.war")
                // Add all classes
                .addPackage("kaos.core")
                // This will add test-persitence.xml as persistence.xml (renamed)
                // in folder META-INF, see Files > jpa_managing > target > arquillian
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                // Must have for CDI to work
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Before  // Run before each test
    public void before() throws Exception {
        clearAll();
    }
    
    @Test
    public void testCreateUser(){
        KaosUser u = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        chat.getUserList().create(u);
        List<KaosUser> ul = chat.getUserList().findAll();
        assertTrue(ul.size() > 0);
        assertTrue(ul.get(0).equals(u));
    }
    
    @Test
    public void testUserGetByLogin() throws Exception {
        KaosUser u = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        chat.getUserList().create(u);
        KaosUser temp = chat.getUserList().getByLogin("uhrj");
        assertTrue(temp != null);
        assertTrue(temp.getLogin().equals(u.getLogin()));
    }
    

    @Test
    public void testDeleteUser() throws Exception {
        KaosUser u = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        chat.getUserList().create(u);
        List<KaosUser> ul = chat.getUserList().findAll();
        assertTrue(ul.size() > 0);
        assertTrue(ul.get(0).equals(u));
        chat.getUserList().delete("uhrj");
        ul = chat.getUserList().findAll();
        assertTrue(ul.isEmpty());
    }
    
    @Test
    public void testUpdate() throws Exception {
        KaosUser u = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        chat.getUserList().create(u);
        KaosUser temp = chat.getUserList().getByLogin("uhrj");
        assertTrue(temp.getPassword().equals(u.getPassword()));
        u.setPassword("456");
        chat.getUserList().update(u);
        assertTrue(chat.getUserList().getByLogin("uhrj").getPassword().equals(u.getPassword()));
    }
    */
    @Test
    public void testFindRange() throws Exception {
        KaosUser d = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        KaosUser a = new KaosUser("Arvid", "444", "avd@student.chalmers.se");
        KaosUser m = new KaosUser("enki" , "323", "enk@student.chalmers.se");
         chat.getUserList().create(d);
         chat.getUserList().create(a);
         chat.getUserList().create(m);
        List<KaosUser> ks = chat.getUserList().findRange(1, 2);
        assertTrue(ks.get(0).getLogin().equals(a.getLogin()));
        assertTrue(ks.get(1).getLogin().equals(m.getLogin()));
    }
    
    @Test
    public void testCount() throws Exception {
         KaosUser d = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
         KaosUser a = new KaosUser("Arvid", "444", "avd@student.chalmers.se");
         KaosUser m = new KaosUser("enki" , "323", "enk@student.chalmers.se");
         chat.getUserList().create(d);
         chat.getUserList().create(a);
         chat.getUserList().create(m);
         assertTrue(chat.getUserList().count() == 3);
    }
    
    // Need a standalone em to remove testdata between tests
    // No em accessible from interfaces
    @PersistenceContext(unitName = "kaos_test_pu")
    @Produces
    @Default
    EntityManager em;

    // Order matters
    private void clearAll() throws Exception {  
        utx.begin();  
        em.joinTransaction();
        em.createQuery("delete from PrivateMessage").executeUpdate();
        em.createQuery("delete from TeamMessage").executeUpdate();
        em.createQuery("delete from KaosUser").executeUpdate();
        em.createQuery("delete from Team").executeUpdate();
        utx.commit();
    }

}
