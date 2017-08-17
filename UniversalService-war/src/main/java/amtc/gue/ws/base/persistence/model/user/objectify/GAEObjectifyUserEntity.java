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
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;

/**
 * Model for Users stored in the Objectify datastore
 * 
 * @author Thomas
 *
 */
@Entity
public class GAEObjectifyUserEntity extends GAEUserEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Index
	private String email;
	@Index
	private String userName;
	@Index
	private String password;
	@Index
	private List<Ref<GAEObjectifyRoleEntity>> roles = new ArrayList<>();
	@Index
	private List<Ref<GAEObjectifyBookEntity>> books = new ArrayList<>();
	@Index
	private List<Ref<GAEObjectifyBillinggroupEntity>> billinggroups = new ArrayList<>();

	@Override
	public String getKey() {
		return email;
	}

	@Override
	public void setKey(String email) {
		this.email = email;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public Set<GAEBillinggroupEntity> getBillinggroups() {
		Set<GAEBillinggroupEntity> billinggroupEntities = new HashSet<>();
		if (billinggroups != null) {
			for (Ref<GAEObjectifyBillinggroupEntity> billinggroupRef : billinggroups) {
				billinggroupEntities.add(billinggroupRef.get());
			}
		}
		return billinggroupEntities;
	}

	@Override
	public void setBillinggroups(Set<GAEBillinggroupEntity> billinggroups, boolean alsoSetUsers) {
		this.billinggroups = new ArrayList<>();
		if (billinggroups != null) {
			for (GAEBillinggroupEntity billinggroup : billinggroups) {
				if (alsoSetUsers) {
					addToBillinggroupsAndUsers(billinggroup);
				} else {
					addToBillinggroupsOnly(billinggroup);
				}
			}
		}
	}

	@Override
	public void addToBillinggroupsOnly(GAEBillinggroupEntity billinggroup) {
		if (billinggroup != null) {
			// TODO Check websafe
			// Ref<GAEObjectifyBillinggroupEntity> referenceToAdd = Ref.create(
			// Key.create(GAEObjectifyBillinggroupEntity.class,
			// Long.valueOf(billinggroup.getKey()).longValue()));
			// TODO Check if this websafe version is working
			Ref<GAEObjectifyBillinggroupEntity> referenceToAdd = Ref.create(
					Key.create(GAEObjectifyBillinggroupEntity.class, Key.create(billinggroup.getWebsafeKey()).getId()));
			billinggroups.add(referenceToAdd);
		}
	}

	@Override
	public void addToBillinggroupsAndUsers(GAEBillinggroupEntity billinggroup) {
		if (billinggroup != null) {
			billinggroup.addToUsersOnly(this);
			addToBillinggroupsOnly(billinggroup);
		}
	}

	@Override
	public void removeBillinggroup(GAEBillinggroupEntity billinggroup) {
		if (billinggroups != null && !billinggroups.isEmpty()) {
			Ref<GAEObjectifyBillinggroupEntity> billinggroupRefToRemove = null;
			for (Ref<GAEObjectifyBillinggroupEntity> existingBillinggroupRef : billinggroups) {
				GAEObjectifyBillinggroupEntity existingBillinggroup = existingBillinggroupRef.get();
				if (existingBillinggroup != null && existingBillinggroup.getKey().equals(billinggroup.getKey())) {
					billinggroupRefToRemove = existingBillinggroupRef;
					break;
				}
			}
			if (billinggroupRefToRemove != null) {
				billinggroups.remove(billinggroupRefToRemove);
				billinggroup.removeUser(this);
			}
		}
	}

	@Override
	public String getWebsafeKey() {
		return Key.create(GAEObjectifyUserEntity.class, email).getString();
	}
}