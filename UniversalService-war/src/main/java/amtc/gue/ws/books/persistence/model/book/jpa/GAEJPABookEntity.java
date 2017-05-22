package amtc.gue.ws.books.persistence.model.book.jpa;

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

import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Books stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "book")
public class GAEJPABookEntity extends GAEBookEntity {

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
		return bookId;
	}

	@Override
	public void setKey(String bookId) {
		this.bookId = bookId;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getPrice() {
		return price;
	}

	@Override
	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String getISBN() {
		return ISBN;
	}

	@Override
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Set<GAETagEntity> getTags() {
		Set<GAETagEntity> foundTags = new HashSet<>();
		for (GAETagEntity tagEntity : tags) {
			foundTags.add(tagEntity);
		}
		return foundTags;
	}

	@Override
	public void setTags(Set<GAETagEntity> tags, boolean alsoSetBooks) {
		clearSet(this.tags);
		if (tags != null) {
			for (GAETagEntity tag : tags) {
				if (alsoSetBooks) {
					addToTagsAndBooks(tag);
				} else {
					addToTagsOnly(tag);
				}
			}
		}
	}

	@Override
	public void addToTagsOnly(GAETagEntity tag) {
		if (tag != null && tag instanceof GAEJPATagEntity) {
			this.tags.add((GAEJPATagEntity) tag);
		}
	}

	@Override
	public void addToTagsAndBooks(GAETagEntity tag) {
		if (tag != null && tag instanceof GAEJPATagEntity) {
			this.tags.add((GAEJPATagEntity) tag);
			if (tag.getBooks() == null) {
				tag.setBooks(new HashSet<GAEBookEntity>(), false);
			}
			tag.addToBooksOnly(this);
		}
	}

	@Override
	public void removeTag(GAETagEntity tag) {
		if (tags != null && !tags.isEmpty()) {
			for (GAEJPATagEntity existingTag : tags) {
				if (existingTag.getKey().equals(tag.getKey())) {
					tags.remove(existingTag);
					tag.removeBook(this);
				}
			}
		}
	}

	@Override
	public Set<GAEUserEntity> getUsers() {
		Set<GAEUserEntity> foundUsers = new HashSet<>();
		for (GAEJPAUserEntity userEntity : users) {
			foundUsers.add(userEntity);
		}
		return foundUsers;
	}

	@Override
	public void setUsers(Set<GAEUserEntity> users, boolean alsoSetBooks) {
		clearSet(this.users);
		if (users != null) {
			for (GAEUserEntity user : users) {
				if (alsoSetBooks) {
					addToUsersAndBooks(user);
				} else {
					addToUsersOnly(user);
				}
			}
		}
	}

	@Override
	public void addToUsersOnly(GAEUserEntity user) {
		if (user != null && user instanceof GAEJPAUserEntity) {
			users.add((GAEJPAUserEntity) user);
		}
	}

	@Override
	public void addToUsersAndBooks(GAEUserEntity user) {
		if (user != null && user instanceof GAEJPAUserEntity) {
			users.add((GAEJPAUserEntity) user);
			if (user.getBooks() == null) {
				user.setBooks(new HashSet<GAEBookEntity>(), false);
			}
			user.addToBooksOnly(this);
		}
	}

	@Override
	public void removeUser(GAEUserEntity user) {
		if (users != null && !users.isEmpty()) {
			for (GAEJPAUserEntity existingUser : users) {
				if (existingUser.getKey().equals(user.getKey())) {
					users.remove(existingUser);
					user.removeBook(this);
				}
			}
		}
	}
}
