package amtc.gue.ws.base.persistence.model.user;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;

/**
 * User Persistence Entity for GAE datastore
 * 
 * @author Thomas
 *
 */
public abstract class GAEUserEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the password of the UserEntity
	 * 
	 * @return password of the UserEntity
	 */
	public abstract String getPassword();

	/**
	 * Setter for the password of the UserEntity
	 * 
	 * @param password
	 *            of the UserEntity
	 */
	public abstract void setPassword(String password);

	/**
	 * Getter for the email of the UserEntity
	 * 
	 * @return email of the UserEntity
	 */
	public abstract String getEmail();

	/**
	 * Setter for the email of the UserEntity
	 * 
	 * @param email
	 *            of the UserEntity
	 */
	public abstract void setEmail(String email);

	/**
	 * Getter for the RoleEntities of the UserEntity
	 * 
	 * @return RoleEntities of the UserEntity
	 */
	public abstract Set<GAERoleEntity> getRoles();

	/**
	 * Setter for the RoleEntities of the UserEntity
	 * 
	 * @param roles
	 *            RoleEntities of the UserEntity
	 * @param alsoSetUsers
	 *            flag depicting if the respective UserEntities should be set in
	 *            the RoleEntities
	 */
	public abstract void setRoles(Set<GAERoleEntity> roles, boolean alsoSetUsers);

	/**
	 * Method only adding a RoleEntity to the UserEntity
	 * 
	 * @param role
	 *            the RoleEntity that should be added to the UserEntity
	 */
	public abstract void addToRolesOnly(GAERoleEntity role);

	/**
	 * Method adding a RoleEntity to the UserEntity. The UserEntity is also
	 * added to the RoleEntity
	 * 
	 * @param role
	 *            the RoleEntity that should be added to the UserEntity
	 */
	public abstract void addToRolesAndUsers(GAERoleEntity role);
	
	/**
	 * Method removing a RoleEntity from the UserEntity
	 * 
	 * @param role
	 *            the RoleEntity that should be removed
	 */
	public abstract void removeRole(GAERoleEntity role);

	/**
	 * Getter for the BookEntities of the UserEntity
	 * 
	 * @return BookEntities of the UserEntity
	 */
	public abstract Set<GAEBookEntity> getBooks();

	/**
	 * Setter for the BookEntities of the UserEntity
	 * 
	 * @param books
	 *            BookEntities of the UserEntity
	 * @param alsoSetUsers
	 *            flag depicting if the respective UserEntities should be set in
	 *            the RoleEntities
	 */
	public abstract void setBooks(Set<GAEBookEntity> books, boolean alsoSetUsers);

	/**
	 * Method only adding a BookEntity to the UserEntity
	 * 
	 * @param book
	 *            the BookEntity that should be added to the UserEntity
	 */
	public abstract void addToBooksOnly(GAEBookEntity book);

	/**
	 * Method adding a BookEntity to the UserEntity. The UserEntity is also
	 * added to the BookEntity
	 * 
	 * @param book
	 *            the BookEntity that should be added to the UserEntity
	 */
	public abstract void addToBooksAndUsers(GAEBookEntity book);

	/**
	 * Method removing a BookEntity from the UserEntity
	 * 
	 * @param book
	 *            the BookEntity that should be removed
	 */
	public abstract void removeBook(GAEBookEntity book);

	@Override
	public String toString() {
		return UserServiceEntityMapper.mapUserEntityToJSONString(this);
	}
}
