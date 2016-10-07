package amtc.gue.ws.books.service.inout;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the Book complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "book", propOrder = {"title", "author", "price", "ISBN",
		"tags" })
public class Book extends Item {

	// title of the book
	@XmlElement(name = "title", required = true, nillable = false)
	private String title;

	// book author
	@XmlElement(name = "author", required = true, nillable = false)
	private String author;

	// price of the book
	@XmlElement(name = "price", required = true, nillable = false)
	private String price;

	// books ISBN number
	@XmlElement(name = "ISBN", required = false, nillable = true)
	private String ISBN;

	// tags categorizing the book
	@XmlElement(name = "tags", required = true, nillable = false)
	private List<String> tags;
	
	// Getter and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
	public List<String> getTags(){
		return this.tags;
	}
	
	public void setTags(List<String> tags){
		this.tags = tags;
	}
}
