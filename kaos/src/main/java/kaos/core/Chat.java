/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author johannes_laptop
 */
@Named
@ApplicationScoped
public class Chat {
    @EJB
    private UserList userList;
    @EJB
    private TeamList teamList;
    @EJB
    private PrivateMessageList privateMessageList;
    @EJB
    private TeamMessageList teamMessageList;
    
    public Chat() {
        Logger.getAnonymousLogger().log(Level.INFO, "Shop alive");
    }
    
    public UserList getUserList(){
        return userList;
    }
    
    public TeamList getTeamList(){
        return teamList;
    }
    
    public PrivateMessageList getPrivateMessageList(){
        return privateMessageList;
    }
    
    public TeamMessageList getTeamMessageList(){
        return teamMessageList;
    }
}
