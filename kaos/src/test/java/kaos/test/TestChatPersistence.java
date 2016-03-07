package kaos.test;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @Test
     public void testTeamGetMembers() throws Exception {
        KaosUser u1 = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser u2 = new KaosUser("fodavid", "456", "fodvaid@student.chalmers.se");
        chat.getUserList().create(u1);
        chat.getUserList().create(u2);
        Team te = new Team("TeamSweden", "1337");
        te.addUser(chat.getUserList().getByLogin("uhrj"));
        te.addUser(chat.getUserList().getByLogin("fodavid"));
        chat.getTeamList().create(te);
        List<KaosUser> temp = chat.getTeamList().getMembers("TeamSweden");
        assertTrue(temp.size() == 2);
        assertTrue(temp.contains(u1));
        assertTrue(temp.contains(u2));
        te = chat.getTeamList().getByTeamName("TeamSweden");
        te.removeUser(u1);
        chat.getTeamList().update(te);
        temp = chat.getTeamList().getMembers("TeamSweden");
        assertTrue(temp.size() == 1);
        assertTrue(temp.contains(u1) == false);
        assertTrue(temp.contains(u2));
    }
    
////////////////// Tests for Private and Team Messages ///////////////////
    @Test
    public void testPMCreate() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        chat.getUserList().create(s);
        chat.getUserList().create(r);
        PrivateMessage pm = new PrivateMessage("hej hopp", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        chat.getPrivateMessageList().create(pm);
        List<PrivateMessage> pml = chat.getPrivateMessageList().findAll();
        assertTrue(pml.size() > 0);
        assertTrue(pml.get(0).equals(pm));
    }
    
    @Test
    public void testPMDelete() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        chat.getUserList().create(s);
        chat.getUserList().create(r);
        PrivateMessage pm = new PrivateMessage("hej hopp", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
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
        chat.getUserList().create(s);
        chat.getUserList().create(r);
        PrivateMessage pm = new PrivateMessage("hej hopp", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
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
        chat.getUserList().create(s);
        chat.getUserList().create(r);
        PrivateMessage pm1 = new PrivateMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm2 = new PrivateMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm3 = new PrivateMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        chat.getPrivateMessageList().create(pm1);
        chat.getPrivateMessageList().create(pm2);
        chat.getPrivateMessageList().create(pm3);
        List<PrivateMessage> pml = chat.getPrivateMessageList().findRange(1,2);
        assertTrue(pml.size() == 2);
        assertTrue(pml.get(0).equals(pm2));
        assertTrue(pml.get(1).equals(pm3));
    }
    
    @Test
    public void testPMCount() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser r = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        chat.getUserList().create(s);
        chat.getUserList().create(r);
        PrivateMessage pm1 = new PrivateMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm2 = new PrivateMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm3 = new PrivateMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        chat.getPrivateMessageList().create(pm1);
        chat.getPrivateMessageList().create(pm2);
        chat.getPrivateMessageList().create(pm3);
        chat.getPrivateMessageList().count();
        assertTrue(chat.getPrivateMessageList().count() == 3);
    }
    
    @Test
    public void testPMgetBySender() throws Exception{
        KaosUser u1 = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser u2 = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        chat.getUserList().create(u1);
        chat.getUserList().create(u2);
        PrivateMessage pm1 = new PrivateMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm2 = new PrivateMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm3 = new PrivateMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm4 = new PrivateMessage("meddelande 4", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getUserList().getByLogin("uhrj"));
        PrivateMessage pm5 = new PrivateMessage("meddelande 5", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm6 = new PrivateMessage("meddelande 6", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getUserList().getByLogin("uhrj"));
        PrivateMessage pm7 = new PrivateMessage("meddelande 7", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm8 = new PrivateMessage("meddelande 8", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getUserList().getByLogin("uhrj"));
        PrivateMessage pm9 = new PrivateMessage("meddelande 9", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        chat.getPrivateMessageList().create(pm1);
        chat.getPrivateMessageList().create(pm2);
        chat.getPrivateMessageList().create(pm3);
        chat.getPrivateMessageList().create(pm4);
        chat.getPrivateMessageList().create(pm5);
        chat.getPrivateMessageList().create(pm6);
        chat.getPrivateMessageList().create(pm7);
        chat.getPrivateMessageList().create(pm8);
        chat.getPrivateMessageList().create(pm9);
        List<PrivateMessage> pml1 = chat.getPrivateMessageList().getBySender(chat.getUserList().getByLogin("uhrj"));
        List<PrivateMessage> pml2 = chat.getPrivateMessageList().getBySender(chat.getUserList().getByLogin("fodavid"));
        assertTrue(pml1.size() == 6);
        assertTrue(pml2.size() == 3);
    }
    
    @Test
    public void testPMgetByReceiver() throws Exception{
        KaosUser u1 = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser u2 = new KaosUser("fodavid", "123", "fodavid@student.chalmers.se");
        chat.getUserList().create(u1);
        chat.getUserList().create(u2);
        PrivateMessage pm1 = new PrivateMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm2 = new PrivateMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm3 = new PrivateMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm4 = new PrivateMessage("meddelande 4", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getUserList().getByLogin("uhrj"));
        PrivateMessage pm5 = new PrivateMessage("meddelande 5", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm6 = new PrivateMessage("meddelande 6", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getUserList().getByLogin("uhrj"));
        PrivateMessage pm7 = new PrivateMessage("meddelande 7", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        PrivateMessage pm8 = new PrivateMessage("meddelande 8", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getUserList().getByLogin("uhrj"));
        PrivateMessage pm9 = new PrivateMessage("meddelande 9", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getUserList().getByLogin("fodavid"));
        chat.getPrivateMessageList().create(pm1);
        chat.getPrivateMessageList().create(pm2);
        chat.getPrivateMessageList().create(pm3);
        chat.getPrivateMessageList().create(pm4);
        chat.getPrivateMessageList().create(pm5);
        chat.getPrivateMessageList().create(pm6);
        chat.getPrivateMessageList().create(pm7);
        chat.getPrivateMessageList().create(pm8);
        chat.getPrivateMessageList().create(pm9);
        List<PrivateMessage> pml1 = chat.getPrivateMessageList().getByReceiver(chat.getUserList().getByLogin("uhrj"));
        List<PrivateMessage> pml2 = chat.getPrivateMessageList().getByReceiver(chat.getUserList().getByLogin("fodavid"));
        assertTrue(pml1.size() == 3);
        assertTrue(pml2.size() == 6);
    }
 
    @Test
    public void testTMCreate() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        Team t = new Team("test_team", "456");
        chat.getUserList().create(s);
        chat.getTeamList().create(t);
        TeamMessage tm = new TeamMessage("hej hopp", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        chat.getTeamMessageList().create(tm);
        List<TeamMessage> tml = chat.getTeamMessageList().findAll();
        assertTrue(tml.size() > 0);
        assertTrue(tml.get(0).equals(tm));
    }
    
    @Test
    public void testTMDelete() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        Team t = new Team("test_team", "456");
        chat.getUserList().create(s);
        chat.getTeamList().create(t);
        TeamMessage tm = new TeamMessage("hej hopp", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        chat.getTeamMessageList().create(tm);
        List<TeamMessage> tml = chat.getTeamMessageList().findAll();
        assertTrue(tml.size() > 0);
        assertTrue(tml.get(0).equals(tm));
        chat.getTeamMessageList().delete(tm.getId());
        tml = chat.getTeamMessageList().findAll();
        assertTrue(tml.isEmpty());
    }
    
    @Test
    public void testTMUpdate() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        Team t = new Team("test_team", "456");
        chat.getUserList().create(s);
        chat.getTeamList().create(t);
        TeamMessage tm = new TeamMessage("hej hopp", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        chat.getTeamMessageList().create(tm);
        List<TeamMessage> tml = chat.getTeamMessageList().findAll();
        assertTrue(tml.size() > 0);
        assertTrue(tml.get(0).equals(tm));
        tm.setText("uppdaterad text");
        chat.getTeamMessageList().update(tm);
        tml = chat.getTeamMessageList().findAll();
        assertTrue(tml.get(0).getText().equals("uppdaterad text"));
    }
    
    @Test
    public void testTMFindRange() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        Team t = new Team("test_team", "456");
        chat.getUserList().create(s);
        chat.getTeamList().create(t);
        TeamMessage tm1 = new TeamMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        TeamMessage tm2 = new TeamMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        TeamMessage tm3 = new TeamMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        chat.getTeamMessageList().create(tm1);
        chat.getTeamMessageList().create(tm2);
        chat.getTeamMessageList().create(tm3);
        List<TeamMessage> tml = chat.getTeamMessageList().findRange(1,2);
        assertTrue(tml.size() == 2);
        assertTrue(tml.get(0).equals(tm2));
        assertTrue(tml.get(1).equals(tm3));
    }
    
    @Test
    public void testTMCount() throws Exception{
        KaosUser s = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        Team t = new Team("test_team", "456");
        chat.getUserList().create(s);
        chat.getTeamList().create(t);
        TeamMessage tm1 = new TeamMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        TeamMessage tm2 = new TeamMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        TeamMessage tm3 = new TeamMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team"));
        chat.getTeamMessageList().create(tm1);
        chat.getTeamMessageList().create(tm2);
        chat.getTeamMessageList().create(tm3);
        chat.getTeamMessageList().count();
        assertTrue(chat.getTeamMessageList().count() == 3);
    }
    
    @Test
    public void testTMgetBySender() throws Exception{
        KaosUser s1 = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser s2 = new KaosUser("fodavid", "456", "fodvaid@student.chalmers.se");
        Team t1 = new Team("test_team1", "123");
        Team t2 = new Team("test_team2", "456");
        chat.getUserList().create(s1);
        chat.getUserList().create(s2);
        chat.getTeamList().create(t1);
        chat.getTeamList().create(t2);
        TeamMessage tm1 = new TeamMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm2 = new TeamMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm3 = new TeamMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm4 = new TeamMessage("meddelande 4", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm5 = new TeamMessage("meddelande 5", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm6 = new TeamMessage("meddelande 6", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm7 = new TeamMessage("meddelande 7", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm8 = new TeamMessage("meddelande 8", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm9 = new TeamMessage("meddelande 9", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        chat.getTeamMessageList().create(tm1);
        chat.getTeamMessageList().create(tm2);
        chat.getTeamMessageList().create(tm3);
        chat.getTeamMessageList().create(tm4);
        chat.getTeamMessageList().create(tm5);
        chat.getTeamMessageList().create(tm6);
        chat.getTeamMessageList().create(tm7);
        chat.getTeamMessageList().create(tm8);
        chat.getTeamMessageList().create(tm9);
        List<TeamMessage> tml1 = chat.getTeamMessageList().getBySender(chat.getUserList().getByLogin("uhrj"));
        List<TeamMessage> tml2 = chat.getTeamMessageList().getBySender(chat.getUserList().getByLogin("fodavid"));
        assertTrue(tml1.size() == 3);
        assertTrue(tml2.size() == 6);
    }
    
    @Test
    public void testTMgetByReceiver() throws Exception{
        KaosUser s1 = new KaosUser("uhrj", "123", "uhrj@student.chalmers.se");
        KaosUser s2 = new KaosUser("fodavid", "456", "fodvaid@student.chalmers.se");
        Team t1 = new Team("test_team1", "123");
        Team t2 = new Team("test_team2", "456");
        chat.getUserList().create(s1);
        chat.getUserList().create(s2);
        chat.getTeamList().create(t1);
        chat.getTeamList().create(t2);
        TeamMessage tm1 = new TeamMessage("meddelande 1", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm2 = new TeamMessage("meddelande 2", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm3 = new TeamMessage("meddelande 3", new Date(), chat.getUserList().getByLogin("uhrj"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm4 = new TeamMessage("meddelande 4", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm5 = new TeamMessage("meddelande 5", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team1"));
        TeamMessage tm6 = new TeamMessage("meddelande 6", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm7 = new TeamMessage("meddelande 7", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm8 = new TeamMessage("meddelande 8", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        TeamMessage tm9 = new TeamMessage("meddelande 9", new Date(), chat.getUserList().getByLogin("fodavid"), chat.getTeamList().getByTeamName("test_team2"));
        chat.getTeamMessageList().create(tm1);
        chat.getTeamMessageList().create(tm2);
        chat.getTeamMessageList().create(tm3);
        chat.getTeamMessageList().create(tm4);
        chat.getTeamMessageList().create(tm5);
        chat.getTeamMessageList().create(tm6);
        chat.getTeamMessageList().create(tm7);
        chat.getTeamMessageList().create(tm8);
        chat.getTeamMessageList().create(tm9);
        List<TeamMessage> tml1 = chat.getTeamMessageList().getByReceiver(chat.getTeamList().getByTeamName("test_team1"));
        List<TeamMessage> tml2 = chat.getTeamMessageList().getByReceiver(chat.getTeamList().getByTeamName("test_team2"));
        assertTrue(tml1.size() == 4);
        assertTrue(tml2.size() == 5);
    }
    
    
    
    /////////////////////////////////////////////     Test for password Protection   ///////////////////////////////////////////////////
    
    @Test
    public void testSaltHashUserPassword() throws Exception {               // user
        
        String pass1 = "123";
        String pass2 = "456";
        
        String salt1 = PasswordProtection.getSalt();
        String salt2 = PasswordProtection.getSalt();
        
        String saltAndPass1 = PasswordProtection.hashPassword(pass1, salt1);
        String saltAndPass2 = PasswordProtection.hashPassword(pass2, salt2);
       
        KaosUser s1 = new KaosUser("uhrj", saltAndPass1 , "uhrj@student.chalmers.se");
        KaosUser s2 = new KaosUser("fodavid", saltAndPass2 , "fodvaid@student.chalmers.se");
        
        chat.getUserList().create(s1);
        chat.getUserList().create(s2);
        
        KaosUser k1 = chat.getUserList().getByLogin("uhrj");
        KaosUser k2 = chat.getUserList().getByLogin("fodavid");
        
        assertTrue(k1.getPassword().equals(PasswordProtection.hashPassword(pass1, salt1)));
        assertTrue(k2.getPassword().equals(PasswordProtection.hashPassword(pass2, salt2)));
        
    }
    @Test
    public void testSaltHashTeamPassword() throws Exception {             // Team
        
        String pass1 = "123";
        String pass2 = "456";
        
        String salt1 = PasswordProtection.getSalt();
        String salt2 = PasswordProtection.getSalt();
        
        String saltAndPass1 = PasswordProtection.hashPassword(pass1, salt1);
        String saltAndPass2 = PasswordProtection.hashPassword(pass2, salt2);
        
        Team t1 = new Team("TeamSweden", saltAndPass1 );
        Team t2 = new Team("TeamUSA", saltAndPass2 );
        
        chat.getTeamList().create(t1);
        chat.getTeamList().create(t2);
        
        Team sw = chat.getTeamList().getByTeamName("TeamSweden");
        Team us = chat.getTeamList().getByTeamName("TeamUSA");
        
        assertTrue(sw.getPassword().equals(PasswordProtection.hashPassword(pass1, salt1)));
        assertTrue(us.getPassword().equals(PasswordProtection.hashPassword(pass2, salt2)));
    
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
        em.createQuery("delete from Team").executeUpdate();
        em.createQuery("delete from KaosUser").executeUpdate();
        utx.commit();
    }
}
