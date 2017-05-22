package amtc.gue.ws.books.persistence.model.tag.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Tags stored to the GAE datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "tag")
public class GAEJPATagEntity extends GAETagEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tagname")
	private String tagname;
	@Unowned
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPABookEntity> books = new HashSet<>();

	@Override
	public String getKey() {
		return tagname;
	}

	@Override
	public void setKey(String tagname) {
		this.tagname = tagname;
	}

	@Override
	public Set<GAEBookEntity> getBooks() {
		Set<GAEBookEntity> foundBooks = new HashSet<>();
		for (GAEBookEntity bookEntity : books) {
			foundBooks.add(bookEntity);
		}
		return foundBooks;
	}

	@Override
	public void setBooks(Set<GAEBookEntity> books, boolean alsoSetTags) {
		clearSet(this.books);
		if (books != null) {
			for (GAEBookEntity book : books) {
				if (alsoSetTags) {
					addToBooksAndTags(book);
				} else {
					addToBooksOnly(book);
				}
			}
		}
	}

	@Override
	public void addToBooksOnly(GAEBookEntity book) {
		if (book != null && book instanceof GAEJPABookEntity) {
			this.books.add((GAEJPABookEntity) book);
		}
	}

	@Override
	public void addToBooksAndTags(GAEBookEntity book) {
		if (book != null && book instanceof GAEJPABookEntity) {
			this.books.add((GAEJPABookEntity) book);
			if (book.getTags() == null) {
				book.setTags(new HashSet<GAETagEntity>(), false);
			}
			book.addToTagsOnly(this);
		}
	}

	@Override
	public void removeBook(GAEBookEntity book) {
		if (books != null && !books.isEmpty()) {
			for (GAEJPABookEntity existingBook : books) {
				if (existingBook.getKey().equals(book.getKey())) {
					books.remove(existingBook);
				}
			}
			book.removeTag(this);
		}
	}
}
