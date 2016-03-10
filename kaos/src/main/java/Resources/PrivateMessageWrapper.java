
package Resources;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import kaos.core.KaosUser;
import kaos.core.PrivateMessage;

/**
 *
 * @author uhrj
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "PrivateMessage", propOrder = {
    "id",
    "text",
    "timestamp",
    "sender",
    "receiver"
})
public class PrivateMessageWrapper {

    private PrivateMessage privateMessage;

    protected PrivateMessageWrapper() { // Must have!!
    }
   
    public  PrivateMessageWrapper(PrivateMessage teamMessage) { 
        this.privateMessage = teamMessage; 
    }
    
    @XmlElement
    public long getId() {
        return privateMessage.getId();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public String getText() {
        return privateMessage.getText();
    }

    @XmlElement
    public Date getTimestamp() {
        return privateMessage.getTimestamp();
    }
    
    @XmlElement
    public KaosUser getSender() {
        return privateMessage.getSender();
    }
    
    @XmlElement
    public KaosUser getReceiver() {
        return privateMessage.getReceiver();
    }
}
