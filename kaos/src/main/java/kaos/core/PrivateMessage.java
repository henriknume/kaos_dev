/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @Temporal(TemporalType.DATE)
    private Date timestamp;
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
        this.timestamp = new Date();
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
