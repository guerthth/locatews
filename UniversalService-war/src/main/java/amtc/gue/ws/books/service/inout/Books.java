package amtc.gue.ws.books.service.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the Books complex type
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name= "books", propOrder = "books")
public class Books {
	
	// list of Books
	@XmlElement(name = "book")
	List<Book> books;

	/**
	 * Setter for the Books list
	 * @return Books list
	 */
	public List<Book> getBooks() {
		
		if(books == null){
			books = new ArrayList<Book>();
		} 
		
		return this.books;
		
	}

	/**
	 * Setter for list Books
	 * @param Books list
	 */
	public void setBooks(List<Book> Books) {
		this.books = Books;
	}
	
	
	
	
}
