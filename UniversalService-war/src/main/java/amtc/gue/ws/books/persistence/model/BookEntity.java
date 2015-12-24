package amtc.gue.ws.books.persistence.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.google.appengine.datanucleus.annotations.Unowned;


/**
 * Model for Books stored to the datastore
 * @author Thomas
 *
 */
@Entity
public class BookEntity extends PersistenceEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId")
	private Long bookId;
	@Column(name = "title")
	private String title;
	@Column(name = "author")
	private String author; 
	@Column(name = "price")
	private String price;
	@Column(name = "ISBN")
	private String ISBN; 
	@Column(name = "description")
	private String description;
	@ManyToMany
	@Unowned
	private List<TagEntity> tags;

	// Getters and Setters
	public Long getId() {
		return bookId;
	}
	
	public void setId(Long id) {
		this.bookId = id;
	}
	
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<TagEntity> getTags() {
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}
	
	
}
