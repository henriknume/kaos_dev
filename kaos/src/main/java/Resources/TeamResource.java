
package Resources;
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
import kaos.core.Chat;
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
    private final Chat chat = SingletonChat.INSTANCE.getChat();

@GET
@Path("/log/")
@Produces({MediaType.APPLICATION_JSON})
    public Response findTeam(@PathParam("name") String name,
            @Context Request request) {
        Team team = chat.getTeamList().find(name);
        if (team != null) {
            return Response.ok(new TeamWrapper((team))).build(); // 200 ok!
        } else {
            return Response.noContent().build();  // 204
        }
    }
            
@POST
@Consumes(value = MediaType.APPLICATION_JSON)
    public Response createTeam(JsonObject json) {     // JSON parameter
        Team team = new Team(json.getString("name"), json.getString("password"));
        chat.getTeamList().create(team);
        return Response.ok().build();
    }
    
@GET
@Path("/count")
@Produces({MediaType.APPLICATION_JSON})
    public Response countTeams() {
        
        log.log(Level.INFO, "Count.........logging.........");
        int c = chat.getTeamList().count();
        if(c > 0 ){
        // Can't return primitive types, create object
            JsonObject value = Json.createObjectBuilder().add("value", c ).build();
            return Response.ok(value).build();  // 200 
        }
        else
            {
            return Response.noContent().build();  // 204
        }
    }
    
    /////////////   find members method here ///////////
    
    
}
