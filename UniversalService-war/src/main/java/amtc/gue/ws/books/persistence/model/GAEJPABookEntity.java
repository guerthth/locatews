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
	private Set<GAEJPATagEntity> tags = new HashSet<>();

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

	/**
	 * Method that sets the specific tags in the tags Set
	 * 
	 * @param tags
	 *            the tags that should be added to the Set
	 * @param alsoSetBooks
	 *            flag that specifies if the book reference in the tags should
	 *            be set or not. true = also set book reference
	 */
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

	/**
	 * Method setting only the tags in the tags Set
	 * 
	 * @param tag
	 *            the tag that should be added to the Set
	 */
	public void addToTagsOnly(GAEJPATagEntity tag) {
		if (tag != null) {
			tags.add(tag);
		}
	}

	/**
	 * Method setting the tags in the tags set including the corresponding book
	 * reference
	 * 
	 * @param tag
	 *            the tag that should be added to the Set
	 */
	public void addToTagsAndBooks(GAEJPATagEntity tag) {
		if (tag != null) {
			tags.add(tag);
			if (tag.getBooks() == null) {
				tag.setBooks(new HashSet<GAEJPABookEntity>());
			}
			tag.getBooks().add(this);
		}
	}

	@Override
	public String toString() {
		return EntityMapper.mapBookEntityToJSONString(this);
	}
}
