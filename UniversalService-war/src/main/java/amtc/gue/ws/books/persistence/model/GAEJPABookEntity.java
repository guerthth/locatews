package amtc.gue.ws.books.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.datanucleus.api.jpa.annotations.Extension;

import amtc.gue.ws.books.persistence.model.gae.GAEPersistenceEntity;
import amtc.gue.ws.books.utils.EntityMapper;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Books stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "book")
public class GAEJPABookEntity extends GAEPersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(name = "bookId")
	private String bookId;
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

	@Unowned
	@ManyToMany(mappedBy = "books", cascade = CascadeType.MERGE/*
																 * ,targetEntity=
																 * GAEJPATagEntity
																 * .class
																 */, fetch = FetchType.EAGER)
	private Set<GAEJPATagEntity> tags = new HashSet<GAEJPATagEntity>();

	@Override
	public String getKey() {
		return bookId;
	}

	@Override
	public void setKey(String bookId) {
		this.bookId = bookId;
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
		this.ISBN = iSBN;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<GAEJPATagEntity> getTags() {
		return tags;
	}

	public void setTags(Set<GAEJPATagEntity> tags, boolean alsoSetBooks) {
		this.tags.clear();
		if (tags != null) {
			for (GAEJPATagEntity tag : tags) {
				if (alsoSetBooks) {
					addToTagsAndBooks(tag);
				} else {
					addToTagsOnly(tag);
				}
			}
		}
	}

	public void addToTagsOnly(GAEJPATagEntity tag) {
		if (tag != null) {
			tags.add(tag);
		}
	}

	public void addToTagsAndBooks(GAEJPATagEntity tag) {
		if (tag != null) {
			tags.add(tag);
			tag.getBooks().add(this);
		}
	}

	public String toString() {
		return EntityMapper.mapBookEntityToJSONString(this);
	}
}
