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

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for roles stored in the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "role")
public class GAEJPARoleEntity extends GAEPersistenceEntity {
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
		return this.role;
	}

	@Override
	public void setKey(String role) {
		this.role = role;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<GAEJPAUserEntity> getUsers() {
		return this.users;
	}

	public void setUsers(Set<GAEJPAUserEntity> users) {
		this.users.clear();
		if (users != null) {
			for (GAEJPAUserEntity user : users) {
				addToUsers(user);
			}
		}
	}

	/**
	 * Method adding users to the users Set
	 * 
	 * @param user
	 *            the user that should be added to the users Set
	 */
	private void addToUsers(GAEJPAUserEntity user) {
		if (user != null) {
			this.users.add(user);
			user.getRoles().add(this);
		}
	}

	@Override
	public String toString(){
		return UserServiceEntityMapper.mapRoleEntityToJSONString(this);
	}
}
