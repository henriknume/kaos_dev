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
@Entity
public class TeamMessage extends Message {
    private Team toTeam;
    
    private static final long serialVersionUID = 1L;

    public TeamMessage(Long id, String text, Date time, Team toTeam) {
        super(id, text, time);
        this.toTeam = toTeam;
    }
    
}
