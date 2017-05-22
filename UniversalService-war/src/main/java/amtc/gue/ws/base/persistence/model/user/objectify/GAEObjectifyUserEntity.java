package amtc.gue.ws.base.persistence.model.user.objectify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;

@Entity
public class GAEObjectifyUserEntity extends GAEUserEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String userId;
	@Index
	private String password;
	@Index
	private String email;
	@Index
	private List<Ref<GAEObjectifyRoleEntity>> roles = new ArrayList<>();
	@Index
	private List<Ref<GAEObjectifyBookEntity>> books = new ArrayList<>();

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
		Set<GAERoleEntity> roleEntities = new HashSet<>();
		if (roles != null) {
			for (Ref<GAEObjectifyRoleEntity> roleRef : roles) {
				roleEntities.add(roleRef.get());
			}
		}
		return roleEntities;
	}

	@Override
	public void setRoles(Set<GAERoleEntity> roles, boolean alsoSetUsers) {
		this.roles = new ArrayList<>();
		if (roles != null) {
			for (GAERoleEntity roleEntity : roles) {
				if (alsoSetUsers) {
					addToRolesAndUsers(roleEntity);
				} else {
					addToRolesOnly(roleEntity);
				}
			}
		}
	}

	@Override
	public void addToRolesOnly(GAERoleEntity role) {
		if (role != null) {
			Ref<GAEObjectifyRoleEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyRoleEntity.class, role.getKey()));
			roles.add(referenceToAdd);
		}
	}

	@Override
	public void addToRolesAndUsers(GAERoleEntity role) {
		if (role != null) {
			role.addToUsersOnly(this);
			addToRolesOnly(role);
		}
	}

	@Override
	public void removeRole(GAERoleEntity role) {
		if (roles != null && !roles.isEmpty()) {
			for (Ref<GAEObjectifyRoleEntity> existingRole : roles) {
				if (existingRole.get() != null && existingRole.get().getKey().equals(role.getKey())) {
					roles.remove(existingRole);
					role.removeUser(this);
				}
			}
		}
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
	public void setBooks(Set<GAEBookEntity> books, boolean alsoSetUsers) {
		this.books = new ArrayList<>();
		if (books != null) {
			for (GAEBookEntity bookEntity : books) {
				if (alsoSetUsers) {
					addToBooksAndUsers(bookEntity);
				} else {
					addToBooksOnly(bookEntity);
				}
			}
		}
	}

	@Override
	public void addToBooksOnly(GAEBookEntity book) {
		if (book != null) {
			Ref<GAEObjectifyBookEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyBookEntity.class, Long.valueOf(book.getKey()).longValue()));
			books.add(referenceToAdd);
		}
	}

	@Override
	public void addToBooksAndUsers(GAEBookEntity book) {
		if (book != null) {
			book.addToUsersOnly(this);
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
				book.removeUser(this);
			}
		}
	}
}