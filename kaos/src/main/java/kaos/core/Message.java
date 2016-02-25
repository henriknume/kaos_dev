/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Davidf
 */
@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date time;
    @ManyToOne
    private KaosUser user;
    
    public Message() {
    }
    
    public Message(Long id, String text, Date time){
        this.id = id;
        this.text = text;
        this.time = time;
    }
    
    @Override
    public String toString() {
        return "kaos.core.Message[ id=" + id + ", text=" + text+",time=" + time +"]";
    }
    
}
