package Resources;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import kaos.core.KaosUser;
import kaos.core.Team;
import kaos.core.TeamMessage;

/**
 *
 * @author uhrj
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TeamMessage", propOrder = {
    "id",
    "text",
    "timestamp",
    "sender",
    "receiver"
})

public class TeamMessageWrapper {

    private TeamMessage teamMessage;

    protected TeamMessageWrapper() { // Must have!!
    }
   
    public  TeamMessageWrapper(TeamMessage teamMessage) { 
        this.teamMessage = teamMessage; 
    }
    
    @XmlElement
    public long getId() {
        return teamMessage.getId();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public String getText() {
        return teamMessage.getText();
    }

    @XmlElement
    public Date getTimestamp() {
        return teamMessage.getTimestamp();
    }
    
    @XmlElement
    public KaosUser getSender() {
        return teamMessage.getSender();
    }
    
    @XmlElement
    public Team getReceiver() {
        return teamMessage.getReceiver();
    }
}
