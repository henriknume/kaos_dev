/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Davidf
 */
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
public class TeamMessage implements Serializable {

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
    @Getter
    @Setter
    private KaosUser sender;
    @Getter
    @Setter
    private Team toTeam;
    
    
    public TeamMessage() {
    }
    
    public TeamMessage(Long id, String text, Date time,KaosUser sender, Team toTeam){
        this.id = id;
        this.text = text;
        this.time = time;
        this.sender = sender;
        this.toTeam = toTeam;
        
    }
    @Override
    public String toString() {
        return "kaos.core.Message[ id=" + id + ", text=" + text+",time=" + time +"]";
    }
    
}
