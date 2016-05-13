package amtc.gue.ws.books.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.datanucleus.api.jpa.annotations.Extension;

import amtc.gue.ws.books.persistence.model.gae.GAEPersistenceEntity;
import amtc.gue.ws.books.utils.EntityMapper;

import com.google.appengine.datanucleus.annotations.Unowned;

/**
 * Model for Tags stored to the GAE datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name="tag")
public class GAEJPATagEntity extends GAEPersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(name = "tagId")
	private String tagId;
	@Column(name = "tagname")
	private String tagname;

	@Unowned
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<GAEJPABookEntity> books = new HashSet<GAEJPABookEntity>();

	@Override
	public String getKey() {
		return tagId;
	}

	@Override
	public void setKey(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagname;
	}

	public void setTagName(String tagName) {
		this.tagname = tagName;
	}

	public Set<GAEJPABookEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<GAEJPABookEntity> books) {
		this.books.clear();
		if (books != null) {
			for (GAEJPABookEntity book : books) {
				addToBooks(book);
			}
		}
	}

	public void addToBooks(GAEJPABookEntity book) {
		if(book != null){
			books.add(book);
			book.getTags().add(this);
		}
	}

	public String toString() {
		return EntityMapper.mapTagEntityToJSONString(this);
	}

}
