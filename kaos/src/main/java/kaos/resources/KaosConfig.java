/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.resources;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Davidf
 */

@javax.ws.rs.ApplicationPath("webresources")
public class KaosConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(kaos.resources.MessageResource.class);
        resources.add(kaos.resources.TeamResource.class);
        resources.add(kaos.resources.UserResource.class);
    }
}
