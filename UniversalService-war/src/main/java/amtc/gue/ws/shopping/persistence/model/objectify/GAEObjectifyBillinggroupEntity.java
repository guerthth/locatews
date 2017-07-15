package amtc.gue.ws.shopping.persistence.model.objectify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;

/**
 * Model for Objectify Shoppinggroups stored to the datastore
 * 
 * @author thomas
 *
 */
@Entity
public class GAEObjectifyBillinggroupEntity extends GAEBillinggroupEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long billiggroupId;
	private String description;
	@Index
	private List<Ref<GAEObjectifyUserEntity>> users = new ArrayList<>();
	@Index
	private List<Ref<GAEObjectifyBillEntity>> bills = new ArrayList<>();

	@Override
	public String getKey() {
		if (billiggroupId != null) {
			return String.valueOf(billiggroupId);
		} else {
			return null;
		}
	}

	@Override
	public void setKey(String billiggroupId) {
		if (billiggroupId != null) {
			this.billiggroupId = Long.valueOf(billiggroupId);
		} else {
			this.billiggroupId = null;
		}
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
	public void setUsers(Set<GAEUserEntity> users, boolean alsoSetBillinggroups) {
		this.users = new ArrayList<>();
		if (users != null) {
			for (GAEUserEntity userEntity : users) {
				if (alsoSetBillinggroups) {
					addToUsersAndBillinggroups(userEntity);
				} else {
					addToUsersOnly(userEntity);
				}
			}
		}
	}

	@Override
	public void addToUsersOnly(GAEUserEntity user) {
		if (user != null) {
			Ref<GAEObjectifyUserEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyUserEntity.class, user.getKey()));
			users.add(referenceToAdd);
		}
	}

	@Override
	public void addToUsersAndBillinggroups(GAEUserEntity user) {
		if (user != null) {
			user.addToBillinggroupsOnly(this);
			addToUsersOnly(user);
		}
	}

	@Override
	public void removeUser(GAEUserEntity user) {
		if (users != null && !users.isEmpty()) {
			Ref<GAEObjectifyUserEntity> userRefToRemove = null;
			for (Ref<GAEObjectifyUserEntity> existingUserRef : users) {
				GAEObjectifyUserEntity existingUser = existingUserRef.get();
				if (existingUser != null && existingUser.getKey().equals(user.getKey())) {
					userRefToRemove = existingUserRef;
					break;
				}
			}
			if (userRefToRemove != null) {
				users.remove(userRefToRemove);
				user.removeBillinggroup(this);
			}
		}
	}

	@Override
	public Set<GAEBillEntity> getBills() {
		Set<GAEBillEntity> billEntities = new HashSet<>();
		if (bills != null) {
			for (Ref<GAEObjectifyBillEntity> billRef : bills) {
				billEntities.add(billRef.get());
			}
		}
		return billEntities;
	}

	@Override
	public void setBills(Set<GAEBillEntity> bills, boolean alsoSetBillinggroups) {
		this.bills = new ArrayList<>();
		if (bills != null) {
			for (GAEBillEntity billEntity : bills) {
				if (alsoSetBillinggroups) {
					addToBillsAndBillinggroups(billEntity);
				} else {
					addToBillsOnly(billEntity);
				}
			}
		}
	}

	@Override
	public void addToBillsOnly(GAEBillEntity bill) {
		if (bill != null) {
			Ref<GAEObjectifyBillEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyBillEntity.class, bill.getKey()));
			bills.add(referenceToAdd);
		}
	}

	@Override
	public void addToBillsAndBillinggroups(GAEBillEntity bill) {
		if (bill != null) {
			addToBillsOnly(bill);
		}
	}

	@Override
	public void removeBill(GAEBillEntity bill) {
		if (bills != null && !bills.isEmpty()) {
			Ref<GAEObjectifyBillEntity> billRefToRemove = null;
			for (Ref<GAEObjectifyBillEntity> existingBillRef : bills) {
				GAEObjectifyBillEntity existingBill = existingBillRef.get();
				if (existingBill != null && existingBill.getKey().equals(bill.getKey())) {
					billRefToRemove = existingBillRef;
					break;
				}
			}
			if (billRefToRemove != null) {
				bills.remove(billRefToRemove);
			}
		}
	}
}
