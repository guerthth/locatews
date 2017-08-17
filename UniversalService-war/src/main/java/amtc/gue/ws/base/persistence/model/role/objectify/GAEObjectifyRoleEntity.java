package amtc.gue.ws.base.persistence.model.role.objectify;

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
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;

@Entity
public class GAEObjectifyRoleEntity extends GAERoleEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String role;
	@Index
	private String description;
	@Index
	private List<Ref<GAEObjectifyUserEntity>> users = new ArrayList<>();

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
		Set<GAEUserEntity> userEntities = new HashSet<>();
		if (users != null) {
			for (Ref<GAEObjectifyUserEntity> userRef : users) {
				userEntities.add(userRef.get());
			}
		}
		return userEntities;
	}

	@Override
	public void setUsers(Set<GAEUserEntity> users, boolean alsoSetRoles) {
		this.users = new ArrayList<>();
		if (users != null) {
			for (GAEUserEntity userEntity : users) {
				if (alsoSetRoles) {
					addToUsersAndRoles(userEntity);
				} else {
					addToUsersOnly(userEntity);
				}
			}
		}
	}

	@Override
	public void addToUsersOnly(GAEUserEntity user) {
		if (user != null && user.getKey() != null) {
			Ref<GAEObjectifyUserEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyUserEntity.class, user.getKey()));
			users.add(referenceToAdd);
		}
	}

	@Override
	public void removeUser(GAEUserEntity user) {
		if (users != null && !users.isEmpty()) {
			for (Ref<GAEObjectifyUserEntity> existingUser : users) {
				if (existingUser.get() != null && existingUser.get().getKey().equals(user.getKey())) {
					users.remove(existingUser);
					user.removeRole(this);
				}
			}
		}
	}

	@Override
	public void addToUsersAndRoles(GAEUserEntity user) {
		if (user != null) {
			user.addToRolesOnly(this);
			addToUsersOnly(user);
		}
	}

	@Override
	public String getWebsafeKey() {
		return Key.create(GAEObjectifyRoleEntity.class, role).getString();
	}
}
