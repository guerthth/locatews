package amtc.gue.ws.base.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import amtc.gue.ws.base.util.UserServiceEntityMapper;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Users stored in the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "user")
public class GAEJPAUserEntity extends GAEPersistenceEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "userId")
	private String userId;
	@Column(name = "password")
	private String password;
	@Unowned
	@ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPARoleEntity> roles = new HashSet<>();
	@Unowned
	@ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<GAEJPABookEntity> books = new HashSet<>();

	@Override
	public String getKey() {
		return this.userId;
	}

	@Override
	public void setKey(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<GAEJPARoleEntity> getRoles() {
		return this.roles;
	}

	/**
	 * Method that sets the specific roles in the roles Set
	 * 
	 * @param roles
	 *            the roles that should be added to the Set
	 * @param alsoSetUsers
	 *            flag that specifies if the User reference in the roles should
	 *            be set or not. true = also set user reference
	 */
	public void setRoles(Set<GAEJPARoleEntity> roles, boolean alsoSetUsers) {
		this.roles.clear();
		if (roles != null) {
			for (GAEJPARoleEntity role : roles) {
				if (alsoSetUsers) {
					addToRolesAndUsers(role);
				} else {
					addToRolesOnly(role);
				}
			}
		}
	}

	/**
	 * Method setting only the roles in the roles Set
	 * 
	 * @param role
	 *            the role that should be added to the Set
	 */
	public void addToRolesOnly(GAEJPARoleEntity role) {
		if (role != null) {
			this.roles.add(role);
		}
	}

	/**
	 * Method setting only the roles in the roles Set
	 * 
	 * @param role
	 *            the role that should be added to the Set
	 */
	public void addToRolesAndUsers(GAEJPARoleEntity role) {
		if (role != null) {
			this.roles.add(role);
			if (role.getUsers() == null) {
				role.setUsers(new HashSet<GAEJPAUserEntity>());
			}
			role.getUsers().add(this);
		}
	}

	public Set<GAEJPABookEntity> getBooks() {
		return this.books;
	}

	/**
	 * Method setting books for the users book set
	 * 
	 * @param books
	 *            that should be added for the user
	 * @param alsoSetUsers
	 *            flag that specifies if the User reference in the roles should
	 *            be set or not. true = also set user reference
	 */
	public void setBooks(Set<GAEJPABookEntity> books, boolean alsoSetUsers) {
		this.books.clear();
		if (books != null) {
			for (GAEJPABookEntity book : books) {
				if (alsoSetUsers) {
					addToBooksAndUsers(book);
				} else {
					addToBooksOnly(book);
				}
			}
		}
	}

	/**
	 * Method adding books to the books Set
	 * 
	 * @param book
	 *            the book that should be added to the books Set
	 */
	public void addToBooksAndUsers(GAEJPABookEntity book) {
		if (book != null) {
			this.books.add(book);
			if (book.getUsers() == null) {
				book.setUsers(new HashSet<GAEJPAUserEntity>(), true);
			}
			book.getUsers().add(this);
		}
	}

	/**
	 * Method setting only the books in the books Set
	 * 
	 * @param book
	 *            the book that should be added to the Set
	 */
	private void addToBooksOnly(GAEJPABookEntity book) {
		if (book != null) {
			this.books.add(book);
		}
	}

	/**
	 * Method removing a book from the userEntity
	 * 
	 * @param book
	 *            the bookEntity that should be removed
	 */
	public void removeBook(GAEJPABookEntity book) {
		for (GAEJPABookEntity existingBook : this.books) {
			if (existingBook.getKey().equals(book.getKey())) {
				this.books.remove(existingBook);
			}
		}
	}

	@Override
	public String toString() {
		return UserServiceEntityMapper.mapUserEntityToJSONString(this);
	}
}
