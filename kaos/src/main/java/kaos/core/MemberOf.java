/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author johannes_laptop
 */
public class MemberOf {
    
    private Team team;
    
    @Getter
    private List<KaosUser> members = new ArrayList<>();
    
    public MemberOf(Team team, KaosUser user){
        this.team = team;
        add(user);
    }
    
    public void add(KaosUser user){
        members.add(user);
    }
    
    public void remove(KaosUser user){
        if(members.contains(user))
            members.remove(user);
    }
}
