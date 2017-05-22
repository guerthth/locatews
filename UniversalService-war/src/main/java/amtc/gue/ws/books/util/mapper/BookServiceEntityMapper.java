package amtc.gue.ws.books.util.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.response.TagServiceResponse;

/**
 * Class responsible for mapping of BookService related objects. Use Case
 * examples: - building up BookServiceResponse objects - mapping objects from
 * one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public abstract class BookServiceEntityMapper {
	/**
	 * Method for the mapping of Books
	 * 
	 * @param book
	 *            the book element that should be transformed
	 * @param type
	 *            the database action
	 * @return mapped GAEBookEntity
	 */
	public abstract GAEBookEntity mapBookToEntity(Book book, DelegatorTypeEnum type);

	/**
	 * Method to map a Books object to a list of BookEntity objects this method
	 * 
	 * @param books
	 *            object containing list of Book object
	 * @param type
	 *            the database action
	 * @return list of BookEntity objects that should be persisted
	 */
	public List<GAEBookEntity> transformBooksToBookEntities(Books books, DelegatorTypeEnum type) {

		List<GAEBookEntity> bookEntityList = new ArrayList<>();
		if (books != null) {
			for (Book book : books.getBooks()) {
				bookEntityList.add(mapBookToEntity(book, type));
			}
		}
		return bookEntityList;
	}

	/**
	 * This method maps a collection of TagEntities to a Tags object
	 * 
	 * @param tagEntityCollection
	 *            a collection of TagEntities that should be mapped
	 * @return a Tags object
	 */
	public static List<String> mapTagEntityListToTags(Collection<GAETagEntity> tagEntityCollection) {
		List<String> tagList = new ArrayList<String>();
		if (tagEntityCollection != null) {
			for (GAETagEntity tagEntity : tagEntityCollection) {
				String tag = (tagEntity != null) ? tagEntity.getKey() : null;
				tagList.add(tag);
			}
		}
		return tagList;
	}

	/**
	 * Method that maps a BookEntity object to a Book object
	 * 
	 * @param bookEntity
	 *            the BookEntity that should be mapped
	 * @return the Book object
	 */
	public static Book mapBookEntityToBook(GAEBookEntity bookEntity) {
		Book book = new Book();
		if (bookEntity != null) {
			book.setId(bookEntity.getKey());
			book.setAuthor(bookEntity.getAuthor());
			book.setDescription(bookEntity.getDescription());
			book.setISBN(bookEntity.getISBN());
			book.setPrice(bookEntity.getPrice());
			book.setTags(mapTagEntityListToTags(bookEntity.getTags()));
			book.setTitle(bookEntity.getTitle());
		}
		return book;
	}

	/**
	 * Method that maps a list of BookEntities to a Books object
	 * 
	 * @param bookEntityList
	 *            the list of BookEntities
	 * @return a Books object
	 */
	public static Books transformBookEntitiesToBooks(List<GAEBookEntity> bookEntityList) {
		Books books = new Books();
		List<Book> bookList = new ArrayList<>();
		if (bookEntityList != null) {
			for (GAEBookEntity bookEntity : bookEntityList) {
				bookList.add(mapBookEntityToBook(bookEntity));
			}
		}
		books.setBooks(bookList);
		return books;
	}

	/**
	 * Method mapping a BookEntity to a JSON String
	 * 
	 * @param bookEntity
	 *            the BookEntity object that should be mapped
	 * @return the JSON String
	 */
	public static String mapBookEntityToJSONString(GAEBookEntity bookEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (bookEntity != null) {
			sb.append("id: ");
			String id = bookEntity.getKey() != null ? bookEntity.getKey() + ", " : "null, ";
			sb.append(id);
			sb.append("title: ");
			String title = bookEntity.getTitle() != null ? bookEntity.getTitle() + ", " : "null, ";
			sb.append(title);
			sb.append("author: ");
			String author = bookEntity.getAuthor() != null ? bookEntity.getAuthor() + ", " : "null, ";
			sb.append(author);
			sb.append("description: ");
			String description = bookEntity.getDescription() != null ? bookEntity.getDescription() + ", " : "null, ";
			sb.append(description);
			sb.append("ISBN: ");
			String ISBN = bookEntity.getISBN() != null ? bookEntity.getISBN() + ", " : "null, ";
			sb.append(ISBN);
			sb.append("price: ");
			String price = bookEntity.getPrice() != null ? bookEntity.getPrice() + ", " : "null, ";
			sb.append(price);
			sb.append("tags: ");
			sb.append("[");
			if (bookEntity.getTags() != null) {
				for (int i = 0; i < bookEntity.getTags().size(); i++) {
					GAETagEntity tagEntity = (new ArrayList<>(bookEntity.getTags())).get(i);
					String tag = tagEntity != null ? tagEntity.getKey() : "null";
					sb.append(tag);
					if (i != bookEntity.getTags().size() - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a List of BookEntities to one consolidated JSON String
	 * 
	 * @param bookEntities
	 *            the list of BookEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapBookEntityListToConsolidatedJSONString(List<GAEBookEntity> bookEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (bookEntities != null && !bookEntities.isEmpty()) {
			int listSize = bookEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapBookEntityToJSONString(bookEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a TagEntity to a JSON String
	 * 
	 * @param tagEntity
	 *            the TagEntity object that should be mapped
	 * @return the JSON String
	 */
	public static String mapTagEntityToJSONString(GAETagEntity tagEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (tagEntity != null) {
			sb.append("tagName: ");
			String tagName = tagEntity.getKey() != null ? tagEntity.getKey() + ", " : "null, ";
			sb.append(tagName);
			sb.append("books: ");
			sb.append("[");
			if (tagEntity.getBooks() != null) {
				for (int i = 0; i < tagEntity.getBooks().size(); i++) {
					GAEBookEntity bookEntity = new ArrayList<>(tagEntity.getBooks()).get(i);
					String book = bookEntity != null ? mapBookEntityToJSONString(bookEntity) : "null";
					sb.append(book);
					if (i != tagEntity.getBooks().size() - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a List of TagEntities to one consolidated JSON String
	 * 
	 * @param tagEntities
	 *            the list of TagEntities that should be mapped to a JSON String
	 * @return the consolidated JSON String
	 */
	public static String mapTagEntityListToConsolidatedJSONString(List<GAETagEntity> tagEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (tagEntities != null && tagEntities.size() > 0) {
			int listSize = tagEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapTagEntityToJSONString(tagEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a business delegator output to a service response
	 * 
	 * @param bdOutput
	 *            business output of a business delegator
	 * @return mapped service response object
	 */
	public static BookServiceResponse mapBdOutputToBookServiceResponse(IDelegatorOutput bdOutput) {
		// create Service Response
		BookServiceResponse bookServiceResponse = null;

		if (bdOutput != null) {
			bookServiceResponse = new BookServiceResponse();
			bookServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
			if ((bdOutput.getOutputObject() instanceof Books)) {
				List<Book> bookList = ((Books) bdOutput.getOutputObject()).getBooks();
				bookServiceResponse.setBooks(bookList);
			} else {
				bookServiceResponse.setBooks(null);
			}
		}

		return bookServiceResponse;
	}

	/**
	 * This method maps a list of String tags to a collection of TagEntities
	 * 
	 * @param tags
	 *            the Tags input object that should be mapped
	 * @return a collection of TagEntities
	 */
	public abstract Set<GAETagEntity> mapTagsToTagEntityList(List<String> tags);

	/**
	 * Method mapping a String to a simplified version (remove spaces and
	 * transform to lower case)
	 * 
	 * @param currentString
	 *            the string that should be transformed
	 * @return string transformed to lowercase and removed spaces
	 */
	public static String mapTagsToSimplyfiedString(String currentString) {
		return currentString.replaceAll("\\s", "").toLowerCase();
	}

	/**
	 * Method mapping a business delegator output to a service response
	 * 
	 * @param bdOutput
	 *            business output of a business delegator
	 * @return mapped service response object
	 */
	public static TagServiceResponse mapBdOutputToTagServiceResponse(IDelegatorOutput bdOutput) {
		TagServiceResponse tagServiceResponse = null;

		if (bdOutput != null) {
			tagServiceResponse = new TagServiceResponse();
			tagServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));

			Object outputObject = bdOutput.getOutputObject();

			if (outputObject instanceof Tags) {
				List<String> tagList = ((Tags) outputObject).getTags();
				tagServiceResponse.setTags(tagList);
			} else {
				tagServiceResponse.setTags(null);
			}
		}

		return tagServiceResponse;
	}
	
	/**
	 * Method copying a GAEBookEntity. Relationships to tags and users are not
	 * included.
	 * 
	 * @param bookEntity
	 *            the GAEBookEntity that should be copied
	 * @return the copy if the GAEBookEntity
	 */
	public abstract GAEBookEntity copyBookEntity(GAEBookEntity bookEntity);
}
