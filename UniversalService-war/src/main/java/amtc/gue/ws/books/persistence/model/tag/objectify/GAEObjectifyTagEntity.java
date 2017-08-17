package amtc.gue.ws.books.persistence.model.tag.objectify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;

/**
 * Model for Objectify Tags stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
public class GAEObjectifyTagEntity extends GAETagEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String tagname;
	@Index
	private List<Ref<GAEObjectifyBookEntity>> books = new ArrayList<>();

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
		Set<GAEBookEntity> bookEntities = new HashSet<>();
		if (books != null) {
			for (Ref<GAEObjectifyBookEntity> bookRef : books) {
				bookEntities.add(bookRef.get());
			}
		}
		return bookEntities;
	}

	@Override
	public void setBooks(Set<GAEBookEntity> books, boolean alsoSetTags) {
		this.books = new ArrayList<>();
		if (books != null) {
			for (GAEBookEntity bookEntity : books) {
				if (alsoSetTags) {
					addToBooksAndTags(bookEntity);
				} else {
					addToBooksOnly(bookEntity);
				}
			}
		}
	}

	@Override
	public void addToBooksOnly(GAEBookEntity book) {
		if (book != null && book.getKey() != null) {
			Ref<GAEObjectifyBookEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyBookEntity.class, Long.valueOf(book.getKey()).longValue()));
			books.add(referenceToAdd);
		}
	}

	@Override
	public void addToBooksAndTags(GAEBookEntity book) {
		if (book != null) {
			book.addToTagsOnly(this);
			addToBooksOnly(book);
		}
	}

	@Override
	public void removeBook(GAEBookEntity book) {
		if (books != null && !books.isEmpty()) {
			Ref<GAEObjectifyBookEntity> bookRefToRemove = null;
			for (Ref<GAEObjectifyBookEntity> existingBookRef : books) {
				GAEObjectifyBookEntity existingBook = existingBookRef.get();
				if (existingBook != null && existingBook.getKey().equals(book.getKey())) {
					bookRefToRemove = existingBookRef;
					break;
				}
			}
			if (bookRefToRemove != null) {
				books.remove(bookRefToRemove);
				book.removeTag(this);
			}
		}
	}

	@Override
	public String getWebsafeKey() {
		return Key.create(GAEObjectifyTagEntity.class, tagname).getString();
	}
}
