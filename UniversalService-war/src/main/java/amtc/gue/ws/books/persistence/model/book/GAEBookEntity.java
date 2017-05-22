package amtc.gue.ws.books.persistence.model.book;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

/**
 * Book Persistence entity for GAE datastore
 * 
 * @author Thomas
 *
 */
public abstract class GAEBookEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the title of the BookEntity
	 * 
	 * @return title of the BookEntity
	 */
	public abstract String getTitle();

	/**
	 * Setter for the title of the BookEntity
	 * 
	 * @param title
	 *            of the BookEntity
	 */
	public abstract void setTitle(String title);

	/**
	 * Getter for the author of the BookEntity
	 * 
	 * @return author of the BookEntity
	 */
	public abstract String getAuthor();

	/**
	 * Setter for the author of the BookEntity
	 * 
	 * @param author
	 *            of the BookEntity
	 */
	public abstract void setAuthor(String author);

	/**
	 * Getter for the price of the BookEntity
	 * 
	 * @return price of the BookEntity
	 */
	public abstract String getPrice();

	/**
	 * Setter for the price of the BookEntity
	 * 
	 * @param price
	 *            of the BookEntity
	 */
	public abstract void setPrice(String price);

	/**
	 * Getter for the ISBN of the BookEntity
	 * 
	 * @return ISBN of the BookEntity
	 */
	public abstract String getISBN();

	/**
	 * Setter for the ISBN of the BookEntity
	 * 
	 * @param ISBN
	 *            of the BookEntity
	 */
	public abstract void setISBN(String ISBN);

	/**
	 * Getter for the description of the BookEntity
	 * 
	 * @return description of the BookEntity
	 */
	public abstract String getDescription();

	/**
	 * Setter for the description of the BookEntity
	 * 
	 * @param description
	 *            of the BookEntity
	 */
	public abstract void setDescription(String description);

	/**
	 * Getter for the TagEntities of the BookEntity
	 * 
	 * @return TagEntities of the BookEntity
	 */
	public abstract Set<GAETagEntity> getTags();

	/**
	 * Setter for the TagEntities of the BookEntity
	 * 
	 * @param tags
	 *            TagEntities of the BookEntity
	 * @param alsoSetBooks
	 *            flag depicting if the respective BookEntities should be set in
	 *            the TagEntities
	 */
	public abstract void setTags(Set<GAETagEntity> tags, boolean alsoSetBooks);

	/**
	 * Method only adding a TagEntity to the BookEntity
	 * 
	 * @param tag
	 *            the TagEntity that should be added to the BookEntity
	 */
	public abstract void addToTagsOnly(GAETagEntity tag);

	/**
	 * Method adding a TagEntity to theBookEntity. The BookEntity is also added
	 * to the TagEntity
	 * 
	 * @param tag
	 *            the TagEntity that should be added to the BookEntity
	 */
	public abstract void addToTagsAndBooks(GAETagEntity tag);

	/**
	 * Method removing a TagEntity from the BookEntity
	 * 
	 * @param tag
	 *            the TagEntity that should be removed from the BookEntity
	 */
	public abstract void removeTag(GAETagEntity tag);

	/**
	 * Getter for the UserEntities of the BookEntity
	 * 
	 * @return UserEntities of the BookEntity
	 */
	public abstract Set<GAEUserEntity> getUsers();

	/**
	 * Setter for the UserEntities of the BookEntity
	 * 
	 * @param users
	 *            UserEntities of the BookEntity
	 * @param alsoSetBooks
	 *            flag depicting if the respective BookEntities should be set in
	 *            the UserEntities
	 */
	public abstract void setUsers(Set<GAEUserEntity> users, boolean alsoSetBooks);

	/**
	 * Method only adding a UserEntity to the BookEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the BookEntity
	 */
	public abstract void addToUsersOnly(GAEUserEntity user);

	/**
	 * Method adding a UserEntitiy to the BookEntity. The BookEntity is also
	 * added to the UserEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the BookEntity
	 */
	public abstract void addToUsersAndBooks(GAEUserEntity user);

	/**
	 * Method removing a UserEntity from the BookEntity
	 * 
	 * @param user
	 *            the UserEntity that should be removed from the BookEntity
	 */
	public abstract void removeUser(GAEUserEntity user);

	@Override
	public String toString() {
		return BookServiceEntityMapper.mapBookEntityToJSONString(this);
	}
}
