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

import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.persistence.model.GAEPersistenceEntity;
import amtc.gue.ws.books.util.BookServiceEntityMapper;

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
	@ManyToMany(mappedBy = "books", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPATagEntity> tags = new HashSet<>();
	@Unowned
	@ManyToMany(mappedBy = "books", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPAUserEntity> users = new HashSet<>();

	@Override
	public String getKey() {
		return this.bookId;
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
		return this.tags;
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
		clearSet(this.tags);
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
			this.tags.add(tag);
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
			this.tags.add(tag);
			if (tag.getBooks() == null) {
				tag.setBooks(new HashSet<GAEJPABookEntity>());
			}
			tag.getBooks().add(this);
		}
	}

	public Set<GAEJPAUserEntity> getUsers() {
		return this.users;
	}

	/**
	 * Method that sets the specific users in the users Set
	 * 
	 * @param users
	 *            the users that should be added to the Set
	 * @param alsoSetBooks
	 *            flag that specifies if the book reference in the users Set
	 *            should be set or not. true = also set book reference
	 */
	public void setUsers(Set<GAEJPAUserEntity> users, boolean alsoSetBooks) {
		clearSet(this.users);
		if (users != null) {
			for (GAEJPAUserEntity user : users) {
				if (alsoSetBooks) {
					addToUsersAndBooks(user);
				} else {
					addToUsersOnly(user);
				}
			}
		}
	}

	/**
	 * Method setting the users in the users set including the corresponding
	 * book reference
	 * 
	 * @param user
	 *            the user that should be added to the Set
	 */
	public void addToUsersAndBooks(GAEJPAUserEntity user) {
		if (user != null) {
			this.users.add(user);
			if (user.getBooks() == null) {
				user.setBooks(new HashSet<GAEJPABookEntity>(), true);
			}
			user.getBooks().add(this);
		}
	}

	/**
	 * Method setting only the users in the users Set
	 * 
	 * @param user
	 *            the user that should be added to the Set
	 */
	public void addToUsersOnly(GAEJPAUserEntity user) {
		if (user != null) {
			this.users.add(user);
		}
	}

	/**
	 * Method removing a user from the bookentity
	 * 
	 * @param user
	 *            the userEntity that should be removed
	 */
	public void removeUser(GAEJPAUserEntity user) {
		for (GAEJPAUserEntity existingUser : this.users) {
			if (existingUser.getKey().equals(user.getKey())) {
				this.users.remove(existingUser);
			}
		}
		user.removeBook(this);
	}

	@Override
	public String toString() {
		return BookServiceEntityMapper.mapBookEntityToJSONString(this);
	}
}
