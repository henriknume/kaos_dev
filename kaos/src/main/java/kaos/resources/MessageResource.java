package kaos.resources;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import kaos.core.Chat;
import kaos.core.PrivateMessage;
import kaos.core.TeamMessage;
/**
 *
 * @author Davidf & uhrj
 */
@Path("messages")
public class MessageResource {
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private Chat chat;
    
    @POST
    @Path("/team/{team}")
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response createTeamMessage(@PathParam(value = "team") String team, JsonObject json)throws NoSuchAlgorithmException  {    // JSON parameter
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

    @POST
    @Path("/user")
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response createPrivateMessage(JsonObject json)throws NoSuchAlgorithmException  {    // JSON parameter
           PrivateMessage pm = new PrivateMessage(json.getString("message"), chat.getUserList().getByLogin(json.getString("sender")), chat.getUserList().getByLogin(json.getString("receiver")));
           chat.getPrivateMessageList().create(pm);
           return Response.ok().build();
        }
        
    @GET
    @Path("/user/{user1}/{user2}")
    @Produces(value = {MediaType.APPLICATION_JSON})
        public Response findPrivateConversation(@PathParam(value = "user1") String user1, @PathParam(value = "user2") String user2)throws NoSuchAlgorithmException  {
            List<PrivateMessage> l = chat.getPrivateMessageList().getUserConversation(chat.getUserList().getByLogin(user1), chat.getUserList().getByLogin(user2));
            List<PrivateMessageWrapper> privateMessageWrapped = new ArrayList<>();
                for(PrivateMessage pm : l) {
                    privateMessageWrapped.add(new PrivateMessageWrapper(pm));
        }
            GenericEntity<Collection<PrivateMessageWrapper>> ge = new GenericEntity<Collection<PrivateMessageWrapper>>(privateMessageWrapped) {};
            return Response.ok(ge).build(); 
     } 
}
