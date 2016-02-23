/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
}
