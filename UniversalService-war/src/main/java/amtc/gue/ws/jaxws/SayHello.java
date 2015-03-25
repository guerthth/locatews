
package amtc.gue.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "sayHello", namespace = "http://ws.gue.amtc/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayHello", namespace = "http://ws.gue.amtc/")
public class SayHello {

    @XmlElement(name = "person", namespace = "")
    private amtc.gue.ws.Person person;

    /**
     * 
     * @return
     *     returns Person
     */
    public amtc.gue.ws.Person getPerson() {
        return this.person;
    }

    /**
     * 
     * @param person
     *     the value for the person property
     */
    public void setPerson(amtc.gue.ws.Person person) {
        this.person = person;
    }

}
