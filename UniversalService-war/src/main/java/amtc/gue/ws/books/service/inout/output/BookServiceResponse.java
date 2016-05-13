package amtc.gue.ws.books.service.inout.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import amtc.gue.ws.books.service.inout.Books;

/**
 * JAXB object for the BookServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BookServiceResponse extends ServiceResponse {

	@XmlElement(name = "books", nillable = true, required = false)
	public Books book;

	// Getters and Setters
	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

}
