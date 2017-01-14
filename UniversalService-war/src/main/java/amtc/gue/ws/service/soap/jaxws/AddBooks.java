
package amtc.gue.ws.service.soap.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addBooks", namespace = "http://service.books.ws.gue.amtc/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addBooks", namespace = "http://service.books.ws.gue.amtc/")
public class AddBooks {

    @XmlElement(name = "books", namespace = "")
    private amtc.gue.ws.books.inout.Books books;

    /**
     * 
     * @return
     *     returns Books
     */
    public amtc.gue.ws.books.inout.Books getBooks() {
        return this.books;
    }

    /**
     * 
     * @param books
     *     the value for the books property
     */
    public void setBooks(amtc.gue.ws.books.inout.Books books) {
        this.books = books;
    }

}
