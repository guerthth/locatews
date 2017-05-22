package amtc.gue.ws.base.persistence.model.user.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.jpa.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Users stored in the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "user")
public class GAEJPAUserEntity extends GAEUserEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "userId")
	private String userId;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email;

	@Unowned
	@ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPARoleEntity> roles = new HashSet<>();
	@Unowned
	@ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<GAEJPABookEntity> books = new HashSet<>();

	@Override
	public String getKey() {
		return userId;
	}

	@Override
	public void setKey(String userId) {
		this.userId = userId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Set<GAERoleEntity> getRoles() {
		Set<GAERoleEntity> foundRoles = new HashSet<>();
		for (GAEJPARoleEntity roleEntity : roles) {
			foundRoles.add(roleEntity);
		}
		return foundRoles;
	}

	@Override
	public void setRoles(Set<GAERoleEntity> roles, boolean alsoSetUsers) {
		clearSet(this.roles);
		if (roles != null) {
			for (GAERoleEntity role : roles) {
				if (alsoSetUsers) {
					addToRolesAndUsers(role);
				} else {
					addToRolesOnly(role);
				}
			}
		}
	}

	@Override
	public void addToRolesOnly(GAERoleEntity role) {
		if (role != null && role instanceof GAEJPARoleEntity) {
			roles.add((GAEJPARoleEntity) role);
		}
	}

	@Override
	public void addToRolesAndUsers(GAERoleEntity role) {
		if (role != null && role instanceof GAEJPARoleEntity) {
			roles.add((GAEJPARoleEntity) role);
			if (role.getUsers() == null) {
				role.setUsers(new HashSet<GAEUserEntity>(), false);
			}
			role.addToUsersOnly(this);
		}
	}

	@Override
	public void removeRole(GAERoleEntity role) {
		if (roles != null && !roles.isEmpty()) {
			for (GAEJPARoleEntity existingRole : roles) {
				if (existingRole.getKey().equals(role.getKey())) {
					roles.remove(existingRole);
					role.removeUser(this);
				}
			}
		}
	}

	@Override
	public Set<GAEBookEntity> getBooks() {
		Set<GAEBookEntity> foundBooks = new HashSet<>();
		for (GAEJPABookEntity bookEntity : books) {
			foundBooks.add(bookEntity);
		}
		return foundBooks;
	}

	@Override
	public void setBooks(Set<GAEBookEntity> books, boolean alsoSetUsers) {
		clearSet(this.books);
		if (books != null) {
			for (GAEBookEntity book : books) {
				if (alsoSetUsers) {
					addToBooksAndUsers(book);
				} else {
					addToBooksOnly(book);
				}
			}
		}
	}

	@Override
	public void addToBooksOnly(GAEBookEntity book) {
		if (book != null && books instanceof GAEJPABookEntity) {
			books.add((GAEJPABookEntity) book);
		}
	}

	@Override
	public void addToBooksAndUsers(GAEBookEntity book) {
		if (book != null && book instanceof GAEJPABookEntity) {
			books.add((GAEJPABookEntity) book);
			if (book.getUsers() == null) {
				book.setUsers(new HashSet<GAEUserEntity>(), false);
			}
			book.addToUsersOnly(this);
		}
	}

	@Override
	public void removeBook(GAEBookEntity book) {
		if (books != null && !books.isEmpty()) {
			for (GAEJPABookEntity existingBook : books) {
				if (existingBook.getKey().equals(book.getKey())) {
					books.remove(existingBook);
					book.removeUser(this);
				}
			}
		}
	}
}
