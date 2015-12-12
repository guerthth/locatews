
package amtc.gue.ws.books.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "removeBooks", namespace = "http://service.books.ws.gue.amtc/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeBooks", namespace = "http://service.books.ws.gue.amtc/")
public class RemoveBooks {

    @XmlElement(name = "booksToRemove", namespace = "")
    private amtc.gue.ws.books.service.inout.Books booksToRemove;

    /**
     * 
     * @return
     *     returns Books
     */
    public amtc.gue.ws.books.service.inout.Books getBooksToRemove() {
        return this.booksToRemove;
    }

    /**
     * 
     * @param booksToRemove
     *     the value for the booksToRemove property
     */
    public void setBooksToRemove(amtc.gue.ws.books.service.inout.Books booksToRemove) {
        this.booksToRemove = booksToRemove;
    }

}
