
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
import kaos.core.UserList;
/**
 *
 * @author Davidf
 */
@Path("users")
public class UserResource {
    
    private final static Logger log = Logger.getAnonymousLogger();
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private Chat chat;
    
    @GET
    @Path("/{login}")
    @Produces({MediaType.APPLICATION_JSON})
        public Response findUser(@PathParam("login") String login,
                @Context Request request) {
            KaosUser p = chat.getUserList().getByLogin(login);
            if (p != null) {
                return Response.ok(new KaosUserWrapper((p))).build(); // 200 ok!
            } else {
                return Response.noContent().build();  // 204
            }
        }
    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response createUser(JsonObject json)throws NoSuchAlgorithmException  {    // JSON parameter
            log.log(Level.INFO, json.getString("login") + json.getString("password") + json.getString("email"));
            String salt = PasswordProtection.getSalt();
            KaosUser user = new KaosUser(json.getString("login"),
                    PasswordProtection.hashPassword(json.getString("password"), salt) + salt
                    ,json.getString("email"));
            chat.getUserList().create(user);
            
            return Response.ok().build();
        }

    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON})
        public Response countUsers() {

            log.log(Level.INFO, "Count.........logging.........");
            int c = chat.getUserList().count();
            // Can't return primitive types, create object
            JsonObject value = Json.createObjectBuilder().add("value", c ).build();
            return Response.ok(value).build();  // 200 
        }
     //////////////// might be used if needed ///////////////////
    @DELETE
    @Path("{id: \\d+}")
        public Response deleteUser(@PathParam(value = "id") String id) {

        try{
            chat.getUserList().delete(id);
            return Response.ok().build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
      }
    @PUT
    @Path(value = "{id: \\d+}")
    @Consumes(value = MediaType.APPLICATION_JSON)
        public Response updateUser(@PathParam(value = "id") String id, JsonObject json) throws NoSuchAlgorithmException {
            String salt = PasswordProtection.getSalt();
            chat.getUserList().update(new KaosUser(id, PasswordProtection.hashPassword(json.getString("password"), salt) + salt 
                    ,json.getString("email")));
            return Response.ok().build(); // 200
        }

     @GET
     @Produces(value = {MediaType.APPLICATION_JSON})
        public Response findAll() {
            List<KaosUser> kaosUserList = chat.getUserList().findAll();
            List<KaosUserWrapper> userWrapped = new ArrayList<KaosUserWrapper>();
                for(KaosUser p : kaosUserList) {
                    userWrapped.add(new KaosUserWrapper(p));
        }
            GenericEntity<Collection< KaosUserWrapper>> ge = new GenericEntity<Collection<KaosUserWrapper>>(userWrapped) {};
            return Response.ok(ge).build(); 
     }
}
