
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
import javax.ejb.EJB;
import javax.inject.Inject;
import kaos.core.Chat;
import kaos.core.KaosUser;
import kaos.core.PasswordProtection;
import kaos.core.TeamMessage;
import kaos.core.UserList;
/**
 *
 * @author Davidf
 */
@Path("messages")
public class MessageResource {
    
    private final static Logger log = Logger.getAnonymousLogger();
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private Chat chat;
    

    @POST
    @Path("/team/{team}")
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response createTeamMessage(@PathParam(value = "team") String team, JsonObject json)throws NoSuchAlgorithmException  {    // JSON parameter
           System.out.println("AHBISDASD" + team + json.getString("message")+json.getString("sender"));
           TeamMessage tm = new TeamMessage(json.getString("message"), chat.getUserList().getByLogin(json.getString("sender")), chat.getTeamList().getByTeamName(team));
           chat.getTeamMessageList().create(tm);
           return Response.ok().build();
        }
        
    @GET
    @Path("/{team}")
    @Produces(value = {MediaType.APPLICATION_JSON})
        public Response findAllTeamMessages(@PathParam(value = "team") String team) {
            List<TeamMessage> l = chat.getTeamMessageList().getByReceiver(chat.getTeamList().getByTeamName(team));
            
            List<TeamMessageWrapper> teamMessageWrapped = new ArrayList<>();
                for(TeamMessage tm : l) {
                    teamMessageWrapped.add(new TeamMessageWrapper(tm));
        }
            GenericEntity<Collection< TeamMessageWrapper>> ge = new GenericEntity<Collection<TeamMessageWrapper>>(teamMessageWrapped) {};
            return Response.ok(ge).build(); 
     }
/*
    @POST
    @Path("/user")
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response createMessage(JsonObject json)throws NoSuchAlgorithmException  {    // JSON parameter
            String salt = PasswordProtection.getSalt();
            KaosUser user = new KaosUser(json.getString("login"),
                    PasswordProtection.hashPassword(json.getString("password"), salt) + salt
                    ,json.getString("email"));
            chat.getUserList().create(user);
            
            return Response.ok().build();
        }
        

      
     @GET
     @Path("/user")
     @Consumes(value = {MediaType.APPLICATION_JSON})
     @Produces({MediaType.APPLICATION_JSON})
        public Response findAllMessagesUser(JsonObject json)throws NoSuchAlgorithmException  {
            
            String login1 = json.getString("user1");
            String login2 = json.getString("user2");
            log.log(Level.INFO, login1 + "OCH!!!" + login2);
            return Response.ok().build();
     }
    */ 
}
