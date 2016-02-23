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
public class ToTeam {
    
    private Map<Team, List<TeamMessage>> messages = new HashMap<>();
    
    public TeamMessage getMessage(Team team, TeamMessage teamMessage) {
         TeamMessage temp;
        List<TeamMessage > teamMessages = messages.get(team);
        
       for(TeamMessage  ms : teamMessages){
        
            if(ms.equals(teamMessage))
                return ms;
        }
        return temp = new TeamMessage(1337L, "no message found", 1337L); // FIX WITH regular get date
    }
    public void postMessage(Team team, TeamMessage teamMessage) {
        ArrayList<TeamMessage> temp = new ArrayList();
        temp.add(teamMessage);
        messages.put(team, temp);
    }
}
