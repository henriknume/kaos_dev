package kaos.test;

import java.util.Date;
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
    
    /////////////// Tests for User ////////////////////////////////////
 
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
    public void testUserUpdate() throws Exception {
        KaosUser u = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        chat.getUserList().create(u);
        KaosUser temp = chat.getUserList().getByLogin("uhrj");
        assertTrue(temp.getPassword().equals(u.getPassword()));
        u.setPassword("456");
        chat.getUserList().update(u);
        assertTrue(chat.getUserList().getByLogin("uhrj").getPassword().equals(u.getPassword()));
    }
    
    @Test
    public void testUserFindRange() throws Exception {
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
    public void testUserCount() throws Exception {
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
    
    ///////////////////////// Tests for Team class ////////////////////
    
    @Test
    public void testCreateTeam(){
        Team te = new Team("TeamUSA" , "1227");
        chat.getTeamList().create(te);
        List<Team> ul = chat.getTeamList().findAll();
        assertTrue(ul.size() > 0);
        assertTrue(ul.get(0).equals(te));
    }
    @Test
     public void testTeamGetByName() throws Exception {
        Team te = new Team("TeamSweden", "1337");
        chat.getTeamList().create(te);
        Team temp = chat.getTeamList().getByTeamName("TeamSweden");
        assertTrue(temp != null);
        assertTrue(temp.getName().equals(te.getName()));
    }
    @Test
    public void testDeleteTeam() throws Exception {
        Team te = new Team("TeamKaos","1447");
        chat.getTeamList().create(te);
        List<Team> ul = chat.getTeamList().findAll();
        assertTrue(ul.size() > 0);
        assertTrue(ul.get(0).equals(te));
        chat.getTeamList().delete("TeamKaos");
        ul = chat.getTeamList().findAll();
        assertTrue(ul.isEmpty());
    }
    @Test
    public void testTeamUpdate() throws Exception {
        Team te = new Team("TeamSurf","1557");
        chat.getTeamList().create(te);
        Team temp = chat.getTeamList().getByTeamName("TeamSurf");
        assertTrue(temp.getPassword().equals(te.getPassword()));
        te.setPassword("456");
        chat.getTeamList().update(te);
        assertTrue(chat.getTeamList().getByTeamName("TeamSurf").getPassword().equals(te.getPassword()));
    }
    @Test
    public void testTeamFindRange() throws Exception {
        Team d = new Team("TeamSweden","1227");
        Team a = new Team("TeamUSA","1337");
        Team m = new Team("TeamKaos","1447");
        chat.getTeamList().create(d);
        chat.getTeamList().create(a);
        chat.getTeamList().create(m);
        List<Team> ks = chat.getTeamList().findRange(1, 2);
        assertTrue(ks.get(0).getName().equals(a.getName()));
        assertTrue(ks.get(1).getName().equals(m.getName()));
    }
    @Test
    public void testTeamCount() throws Exception {
        Team d = new Team("TeamSweden","1227");
        Team a = new Team("TeamUSA","1337");
        Team m = new Team("TeamKaos","1447");
        chat.getTeamList().create(d);
        chat.getTeamList().create(a);
        chat.getTeamList().create(m);
        assertTrue(chat.getTeamList().count() == 3);
    }
    
////////////////// Tests for Private and Team Messages ///////////////////
    @Test
    public void testPMCreate() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        PrivateMessage pm = new PrivateMessage("hej hopp", new Date(), s, r);
        chat.getPrivateMessageList().create(pm);
        List<PrivateMessage> pml = chat.getPrivateMessageList().findAll();
        assertTrue(pml.size() > 0);
        assertTrue(pml.get(0).equals(pm));
    }
    
    @Test
    public void testPMDelete() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        PrivateMessage pm = new PrivateMessage("hej hopp", new Date(), s, r);
        chat.getPrivateMessageList().create(pm);
        List<PrivateMessage> pml = chat.getPrivateMessageList().findAll();
        assertTrue(pml.size() > 0);
        assertTrue(pml.get(0).equals(pm));
        chat.getPrivateMessageList().delete(pm.getId());
        pml = chat.getPrivateMessageList().findAll();
        assertTrue(pml.isEmpty());
    }
    
    @Test
    public void testPMUpdate() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        PrivateMessage pm = new PrivateMessage("hej hopp", new Date(), s, r);
        chat.getPrivateMessageList().create(pm);
        List<PrivateMessage> pml = chat.getPrivateMessageList().findAll();
        assertTrue(pml.size() > 0);
        assertTrue(pml.get(0).equals(pm));
        pm.setText("uppdaterad text");
        chat.getPrivateMessageList().update(pm);
        pml = chat.getPrivateMessageList().findAll();
        assertTrue(pml.get(0).getText().equals("uppdaterad text"));
    }
    
    @Test
    public void testPMFindRange() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        PrivateMessage pm1 = new PrivateMessage("meddelande 1", new Date(), s, r);
        PrivateMessage pm2 = new PrivateMessage("meddelande 2", new Date(), s, r);
        PrivateMessage pm3 = new PrivateMessage("meddelande 3", new Date(), s, r);
        chat.getPrivateMessageList().create(pm1);
        chat.getPrivateMessageList().create(pm2);
        chat.getPrivateMessageList().create(pm3);
        List<PrivateMessage> pml = chat.getPrivateMessageList().findRange(1,2);
        assertTrue(pml.size() == 2);
        assertTrue(pml.get(0).equals(pm2));
        assertTrue(pml.get(1).equals(pm3));
    }
    /*
    findrange
    count
    getbysender
    getbyreceiver
    
    */
}
