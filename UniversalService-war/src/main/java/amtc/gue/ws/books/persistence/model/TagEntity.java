package amtc.gue.ws.books.persistence.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Tags stored to the datastore
 * @author Thomas
 *
 */
@Entity
public class TagEntity extends PersistenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tagId")
	private Long tagId;
	@Column(name = "tagname")
	private String tagname;
	@ManyToMany(mappedBy = "tags")
	@Unowned
	private Collection<BookEntity> books;

	@Override
	public Long getId() {
		return this.tagId;
	}

	@Override
	public void setId(Long id) {
		this.tagId = id;
	}
	
	public String getTagName() {
		return tagname;
	}

	public void setTagName(String tagName) {
		this.tagname = tagName;
	}
	
	public Collection<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(Collection<BookEntity> books) {
		this.books = books;
	}

}
