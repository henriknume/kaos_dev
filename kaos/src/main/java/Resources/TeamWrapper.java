
package Resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import kaos.core.Team;

/**
 *
 * @author Davidf
 * 
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Team", propOrder = {
    "name",
    "password"
   
})
public class TeamWrapper {
    private Team team;
    
    
    protected TeamWrapper() { // Must have!!
    }
    
     public  TeamWrapper(Team team) { 
        this.team = team; 
    }
    
    @XmlElement
    public String getLogin() {
        return team.getName();
    }

    @XmlElement 
    public String getPassword() {
        return team.getPassword();
    }
    
}
