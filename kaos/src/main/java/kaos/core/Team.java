/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Davidf
 */
@Entity
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    @OneToMany
    private List<KaosUser> members = new ArrayList<>();
    
    public Team() {
    }
    
    public Team(String name, String password){
        this.name = name;
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "kaos.core.Team[ id=" + name + ", pass="+ password +" ]";
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Team){
            Team temp = (Team)o;
            if(this.name.equals(temp.name))
                return true;
        }
            return false;
    }
    
    public void addUser(KaosUser user){
        members.add(user);
    }
    
    public void removeUser(KaosUser user){
        if(members.contains(user))
            members.remove(user);
    }
}
