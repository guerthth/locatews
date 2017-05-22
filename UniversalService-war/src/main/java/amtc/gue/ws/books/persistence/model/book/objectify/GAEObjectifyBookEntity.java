package amtc.gue.ws.books.persistence.model.book.objectify;

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
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;

/**
 * Model for Objectify Books stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
public class GAEObjectifyBookEntity extends GAEBookEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long bookId;
	@Index
	private String title;
	@Index
	private String author;
	@Index
	private String price;
	@Index
	private String ISBN;
	@Index
	private String description;
	@Index
	private List<Ref<GAEObjectifyTagEntity>> tags = new ArrayList<>();
	@Index
	private List<Ref<GAEObjectifyUserEntity>> users = new ArrayList<>();

	@Override
	public String getKey() {
		if (bookId != null) {
			return String.valueOf(bookId);
		} else
			return null;
	}

	@Override
	public void setKey(String bookId) {
		if (bookId != null) {
			this.bookId = Long.valueOf(bookId);
		} else {
			this.bookId = null;
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getPrice() {
		return price;
	}

	@Override
	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String getISBN() {
		return ISBN;
	}

	@Override
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
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
	public Set<GAETagEntity> getTags() {
		Set<GAETagEntity> tagEntities = new HashSet<>();
		if (tags != null) {
			for (Ref<GAEObjectifyTagEntity> tagRef : tags) {
				GAEObjectifyTagEntity tagEntity = tagRef.get();
				tagEntities.add(tagEntity);
			}
		}
		return tagEntities;
	}

	@Override
	public void setTags(Set<GAETagEntity> tags, boolean alsoSetBooks) {
		this.tags = new ArrayList<>();
		if (tags != null) {
			for (GAETagEntity tagEntity : tags) {
				if (alsoSetBooks) {
					addToTagsAndBooks(tagEntity);
				} else {
					addToTagsOnly(tagEntity);
				}
			}
		}
	}

	@Override
	public void addToTagsOnly(GAETagEntity tag) {
		if (tag != null) {
			Ref<GAEObjectifyTagEntity> referenceToAdd = Ref
					.create(Key.create(GAEObjectifyTagEntity.class, tag.getKey()));
			tags.add(referenceToAdd);
		}
	}

	@Override
	public void addToTagsAndBooks(GAETagEntity tag) {
		if (tag != null) {
			tag.addToBooksOnly(this);
			addToTagsOnly(tag);
		}
	}

	@Override
	public void removeTag(GAETagEntity tag) {
		Ref<GAEObjectifyTagEntity> tagRefToRemove = null;
		for (Ref<GAEObjectifyTagEntity> existingTagRef : tags) {
			GAEObjectifyTagEntity existingTag = existingTagRef.get();
			if (existingTag != null && existingTag.getKey().equals(tag.getKey())) {
				tagRefToRemove = existingTagRef;
				break;
			}
		}
		if (tagRefToRemove != null) {
			tags.remove(tagRefToRemove);
			tag.removeBook(this);
		}
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
	public void setUsers(Set<GAEUserEntity> users, boolean alsoSetBooks) {
		this.users = new ArrayList<>();
		if (users != null) {
			for (GAEUserEntity userEntity : users) {
				if (alsoSetBooks) {
					addToUsersAndBooks(userEntity);
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
	public void addToUsersAndBooks(GAEUserEntity user) {
		if (user != null) {
			user.addToBooksOnly(this);
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
				user.removeBook(this);
			}
		}
	}
}
