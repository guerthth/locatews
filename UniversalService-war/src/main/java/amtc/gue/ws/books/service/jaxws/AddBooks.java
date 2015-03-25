
package amtc.gue.ws.books.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addBooks", namespace = "http://service.books.ws.gue.amtc/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addBooks", namespace = "http://service.books.ws.gue.amtc/")
public class AddBooks {

    @XmlElement(name = "inputItems", namespace = "")
    private amtc.gue.ws.books.inout.Books inputItems;

    /**
     * 
     * @return
     *     returns Books
     */
    public amtc.gue.ws.books.inout.Books getInputItems() {
        return this.inputItems;
    }

    /**
     * 
     * @param inputItems
     *     the value for the inputItems property
     */
    public void setInputItems(amtc.gue.ws.books.inout.Books inputItems) {
        this.inputItems = inputItems;
    }

}
