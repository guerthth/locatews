package amtc.gue.ws.books.util.mapper.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

/**
 * Class responsible for of JPA Entities relevant for the BookService
 * 
 * @author Thomas
 *
 */
public class BookServiceJPAEntityMapper extends BookServiceEntityMapper {
	@Override
	public GAEBookEntity mapBookToEntity(Book book, DelegatorTypeEnum type) {
		GAEBookEntity bookEntity = new GAEJPABookEntity();
		if (book.getId() != null && type != DelegatorTypeEnum.ADD)
			bookEntity.setKey(book.getId());
		bookEntity.setAuthor(book.getAuthor());
		bookEntity.setDescription(book.getDescription());
		bookEntity.setISBN(book.getISBN());
		bookEntity.setPrice(book.getPrice());
		if (type != DelegatorTypeEnum.ADD) {
			bookEntity.setTags(mapTagsToTagEntityList(book.getTags()), true);
		} else {
			bookEntity.setTags(mapTagsToTagEntityList(book.getTags()), false);
		}

		bookEntity.setTitle(book.getTitle());

		return bookEntity;
	}

	@Override
	public Set<GAETagEntity> mapTagsToTagEntityList(List<String> tags) {
		Set<GAETagEntity> tagEntityList = new HashSet<>();
		if (tags != null) {
			for (String tag : tags) {
				GAETagEntity tagEntity = new GAEJPATagEntity();
				tagEntity.setKey(mapTagsToSimplyfiedString(tag));
				tagEntityList.add(tagEntity);
			}
		}
		return tagEntityList;
	}

	@Override
	public GAEBookEntity copyBookEntity(GAEBookEntity bookEntity) {
		GAEJPABookEntity bookEntityCopy = new GAEJPABookEntity();
		bookEntityCopy.setKey(bookEntity.getKey());
		bookEntityCopy.setAuthor(bookEntity.getAuthor());
		bookEntityCopy.setDescription(bookEntity.getDescription());
		bookEntityCopy.setISBN(bookEntity.getISBN());
		bookEntityCopy.setPrice(bookEntity.getPrice());
		bookEntityCopy.setTags(null, false);
		bookEntityCopy.setTitle(bookEntity.getTitle());
		bookEntityCopy.setUsers(null, false);
		return bookEntityCopy;
	}
}
