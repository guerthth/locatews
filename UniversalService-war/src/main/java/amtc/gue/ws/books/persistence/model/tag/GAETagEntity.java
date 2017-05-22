package amtc.gue.ws.books.persistence.model.tag;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

/**
 * Tag Persistence entity for GAE datastore
 * 
 * @author Thomas
 *
 */
public abstract class GAETagEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the BookEntities of the TagEntity
	 * 
	 * @return BookEntities of the TagEntity
	 */
	public abstract Set<GAEBookEntity> getBooks();

	/**
	 * Setter for the BookEntities of the TagEntity
	 * 
	 * @param books
	 *            BookEntities of the TagEntity
	 * @param alsoSetTags
	 *            flag depicting if the respective TagEntities should be set in
	 *            the BookEntities
	 */
	public abstract void setBooks(Set<GAEBookEntity> books, boolean alsoSetTags);

	/**
	 * Method only adding a BookEntity to the TagEntity
	 * 
	 * @param book
	 *            the BookEntity that should be added to the TagEntity
	 */
	public abstract void addToBooksOnly(GAEBookEntity book);

	/**
	 * Method adding a BookEntity to the TagEntity. The TagEntity is also added
	 * to the BookEntity
	 * 
	 * @param book
	 *            the BookEntity that should be added to the TagEntity
	 */
	public abstract void addToBooksAndTags(GAEBookEntity book);

	/**
	 * Method removing a BookEntity from the TagEntity
	 * 
	 * @param book
	 *            the BookEntity that should be removed from the TagEntity
	 */
	public abstract void removeBook(GAEBookEntity book);

	@Override
	public String toString() {
		return BookServiceEntityMapper.mapTagEntityToJSONString(this);
	}
}
