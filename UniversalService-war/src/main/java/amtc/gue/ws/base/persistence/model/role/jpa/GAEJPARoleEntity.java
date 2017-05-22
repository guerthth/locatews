package amtc.gue.ws.base.persistence.model.role.jpa;

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
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for roles stored in the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "role")
public class GAEJPARoleEntity extends GAERoleEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "role")
	private String role;
	@Column(name = "description")
	private String description;
	@Unowned
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPAUserEntity> users = new HashSet<>();

	@Override
	public String getKey() {
		return role;
	}

	@Override
	public void setKey(String role) {
		this.role = role;
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
	public Set<GAEUserEntity> getUsers() {
		Set<GAEUserEntity> foundUsers = new HashSet<>();
		for (GAEJPAUserEntity userEntity : users) {
			foundUsers.add(userEntity);
		}
		return foundUsers;
	}

	@Override
	public void setUsers(Set<GAEUserEntity> users, boolean alsoSetUsers) {
		clearSet(this.users);
		if (users != null) {
			for (GAEUserEntity user : users) {
				if (alsoSetUsers) {
					addToUsersAndRoles(user);
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
	public void removeUser(GAEUserEntity user) {
		if (users != null && !users.isEmpty()) {
			for (GAEJPAUserEntity existingUser : users) {
				if (existingUser.getKey().equals(user.getKey())) {
					users.remove(existingUser);
					user.removeRole(this);
				}
			}
		}
	}

	@Override
	public void addToUsersAndRoles(GAEUserEntity user) {
		// not implemented
		addToUsersOnly(user);
	}
}
