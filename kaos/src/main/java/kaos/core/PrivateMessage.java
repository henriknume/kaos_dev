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
public class PrivateMessage extends Message {

    private KaosUser to;
    
    private static final long serialVersionUID = 1L;

    public PrivateMessage(Long id, String text, Date time, KaosUser to) {
        super(id, text, time);
        this.to = to;
    }
}
