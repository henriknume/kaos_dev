/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Davidf
 */
public class ToUser {
         // map with a User as key and a List with messages //
    private Map<User, List<PrivateMessage>> messages = new HashMap<>();
    
    public PrivateMessage readMessage(User user,PrivateMessage privateMessage) {
        PrivateMessage temp;
        List<PrivateMessage> userMessages = messages.get(user);
        
        
        for(PrivateMessage ms : userMessages){
        
            if(ms.equals(privateMessage))
                return ms;
        }
        return temp = new PrivateMessage(1337L, "no message found", 1337L); // FIX WITH regular get date
    }
    public void addMessage(User user,PrivateMessage privateMessage) {
        
        ArrayList<PrivateMessage> temp = new ArrayList();
        temp.add(privateMessage);
        messages.put(user, temp);
    }
}
