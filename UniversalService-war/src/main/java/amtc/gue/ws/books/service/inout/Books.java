package amtc.gue.ws.books.service.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Books complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Books {

	@XmlElement(name = "books")
	List<Book> books;

	/**
	 * Setter for the Books list
	 * 
	 * @return Books list
	 */
	public List<Book> getBooks() {

		if (books == null) {
			books = new ArrayList<>();
		}

		return this.books;

	}

	/**
	 * Setter for list Books
	 * 
	 * @param books
	 *            list
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
