/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author johannes_laptop
 */
@Entity
public class KaosUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;
    
    @Getter
    @Setter
    private String email;

    public KaosUser() {
    }
    
    public KaosUser(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }
}