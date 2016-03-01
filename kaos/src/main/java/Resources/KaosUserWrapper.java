
package Resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import kaos.core.KaosUser;

/**
 *
 * @author Davidf
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "KaosUser", propOrder = {
    "login",
    "password",
    "email"
})
public class KaosUserWrapper {

    private KaosUser user;

    protected KaosUserWrapper() { // Must have!!
    }
   
    public  KaosUserWrapper(KaosUser user) { 
        this.user = user; 
    }
    
    @XmlElement
    public String getLogin() {
        return user.getLogin();
    }

    @XmlElement //If serving XML we should use @XmlAttribute 
    public String getPassword() {
        return user.getPassword();
    }

    @XmlElement
    public String getEmail() {
        return user.getEmail();
    }
    
    
}
