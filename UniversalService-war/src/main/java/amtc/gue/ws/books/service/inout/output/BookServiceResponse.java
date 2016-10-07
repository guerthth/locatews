package amtc.gue.ws.books.service.inout.output;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.books.service.inout.Book;

/**
 * JAXB object for the BookServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BookServiceResponse extends ServiceResponse {

	@XmlElement(name="books")
	private List<Book> books;
	
	// Getters and Setters
	public List<Book> getBooks(){
		return this.books;
	}
	
	public void setBooks(List<Book> books){
		this.books = books;
	}
}
