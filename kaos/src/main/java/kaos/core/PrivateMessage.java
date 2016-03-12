/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class PrivateMessage implements Serializable{
    
private static final long serialVersionUID = 1L;
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private Timestamp timestamp;
    @Getter
    @Setter
    private KaosUser sender;
    @Getter
    @Setter
    private KaosUser receiver;

    public PrivateMessage(){
    }
    
    public PrivateMessage(String text, KaosUser sender, KaosUser receiver) {
        this.text = text;
        timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        this.sender = sender;
        this.receiver = receiver;      
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof PrivateMessage){
            PrivateMessage temp = (PrivateMessage)o;
            if(this.id.equals(temp.id))
                return true;
        }
            return false;
    }
}
