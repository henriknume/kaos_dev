
package Resources;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import kaos.core.Chat;
import kaos.core.KaosUser;
import kaos.core.PasswordProtection;
import kaos.core.Team;



/**
 *
 * @author Davidf
 * 
 */
@Path("teams")
public class TeamResource {
    
    private final static Logger log = Logger.getAnonymousLogger();
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private Chat chat;

    @POST
    @Path("/join")
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response joinTeam(JsonObject json) throws NoSuchAlgorithmException  {
            log.log(Level.INFO, "========================== 1");
            Team t = chat.getTeamList().getByTeamName(json.getString("team_name"));
            log.log(Level.INFO, "========================== 2");
            String passDB = t.getPassword();   // hämtar databas pass
            String passClient = json.getString("password");   // hämtar skickat pass från json 
            boolean passStatus = PasswordProtection.checkPassword(passClient, passDB); // kollar om password är samma
            log.log(Level.INFO, "========================== 3: " + passStatus);
                if (t != null && passStatus){                                          // som i databasen
                // Skapar en ny KaosUser som skickats tillbaka med "uncrypt password"
                t.addUser(chat.getUserList().getByLogin(json.getString("login")));
                chat.getTeamList().update(t);
                t.setPassword(json.getString("password"));
                return Response.ok(new TeamWrapper(t)).build(); // 200 ok
            } else {
                return Response.noContent().build();  // 204
            }
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response createTeam(JsonObject json)throws NoSuchAlgorithmException  {     // JSON parameter
            String salt = PasswordProtection.getSalt();
            Team team = new Team(json.getString("team_name"), PasswordProtection.hashPassword(json.getString("password"), salt) + salt);
            chat.getTeamList().create(team);
            return Response.ok().build();
        }

    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON})
        public Response countTeams() {

            log.log(Level.INFO, "Count.........logging.........");
            int c = chat.getTeamList().count();
            // Can't return primitive types, create object
            JsonObject value = Json.createObjectBuilder().add("value", c ).build();
            return Response.ok(value).build();  // 200 
        }
    
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
        public Response findAll() {
            List<Team> teamList = chat.getTeamList().findAll();
            List<TeamWrapper> teamWrapped = new ArrayList<TeamWrapper>();
                for(Team t : teamList) {
                    teamWrapped.add(new TeamWrapper(t));
        }
            GenericEntity<Collection< TeamWrapper>> ge = new GenericEntity<Collection<TeamWrapper>>(teamWrapped) {};
            return Response.ok(ge).build(); 
     }
        
    @GET
    @Path("/members/{teamname}")
    @Produces({MediaType.APPLICATION_JSON})
        public Response findMembers(@PathParam("teamname") String team) {
            List<KaosUser> l = chat.getTeamList().getMembers(team);
            if (l != null) {
                List<KaosUserWrapper> userWrapped = new ArrayList<KaosUserWrapper>();
                for(KaosUser u : l) {
                    userWrapped.add(new KaosUserWrapper(u));
                }
                GenericEntity<Collection< KaosUserWrapper>> ge = new GenericEntity<Collection<KaosUserWrapper>>(userWrapped) {};
                return Response.ok(ge).build(); // 200 ok
            } else {
                return Response.noContent().build();  // 204
            }
    }
}
