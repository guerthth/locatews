package amtc.gue.ws.books.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.persistence.model.TagEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.service.inout.output.Status;

/**
 * Class mapping objects to JPA entities
 * 
 * @author Thomas
 *
 */
public class EntityMapper {

	/**
	 * Method for the mapping of Books
	 * 
	 * @param book
	 *            the book element that should be transformed
	 * @param type
	 *            the database action
	 * @return mapped BookEntity
	 */
	public static BookEntity mapBookToEntity(Book book, PersistenceTypeEnum type) {

		BookEntity bookEntity = new BookEntity();
		if (book.getId() != null && type != PersistenceTypeEnum.ADD)
			bookEntity.setId(book.getId());
		bookEntity.setAuthor(book.getAuthor());
		bookEntity.setDescription(book.getDescription());
		bookEntity.setISBN(book.getISBN());
		bookEntity.setPrice(book.getPrice());
		bookEntity.setTags(mapTagsToTagEntityList(book.getTags()));
		bookEntity.setTitle(book.getTitle());

		return bookEntity;
	}

	/**
	 * Method to map a Books object to a list of BookEntity objects this method
	 * 
	 * @param books
	 *            object containing list of Book object
	 * @param type
	 *            the database action
	 * @return list of BookEntity objects that should be persisted
	 */
	public static List<BookEntity> transformBooksToBookEntities(Books books,
			PersistenceTypeEnum type) {

		List<BookEntity> bookEntityList = new ArrayList<BookEntity>();
		for (Book book : books.getBooks()) {
			bookEntityList.add(mapBookToEntity(book, type));
		}

		return bookEntityList;
	}

	/**
	 * Method that maps a BookEntity object to a Book object
	 * 
	 * @param bookEntity
	 *            the BookEntity that should be mapped
	 * @return the Book object
	 */
	public static Book mapBookEntityToBook(BookEntity bookEntity) {
		Book book = new Book();
		book.setId(bookEntity.getId());
		book.setAuthor(bookEntity.getAuthor());
		book.setDescription(bookEntity.getDescription());
		book.setISBN(bookEntity.getISBN());
		book.setPrice(bookEntity.getPrice());
		book.setTags(mapTagEntityListToTags(bookEntity.getTags()));
		book.setTitle(bookEntity.getTitle());
		return book;
	}

	/**
	 * Method that maps a list of BookEntities to a Books object
	 * 
	 * @param books
	 *            the list of BookEntities
	 * @return a Books object
	 */
	public static Books transformBookEntitiesToBooks(
			List<BookEntity> bookEntityList) {
		Books books = new Books();
		List<Book> bookList = new ArrayList<Book>();
		for (BookEntity bookEntity : bookEntityList) {
			bookList.add(mapBookEntityToBook(bookEntity));
		}
		books.setBooks(bookList);
		return books;
	}

	/**
	 * Method mapping a business delegator output to a service response
	 * 
	 * @param bdOutput
	 *            business output of a business delegator
	 * @return mapped service response object
	 */
	public static BookServiceResponse mapBdOutputToBookServiceResponse(
			IDelegatorOutput bdOutput) {

		// create the status object
		Status status = new Status();
		status.setStatusMessage(bdOutput.getStatusMessage());
		status.setStatusCode(bdOutput.getStatusCode());

		// create Service Response
		BookServiceResponse bookServiceResponse = new BookServiceResponse();
		bookServiceResponse.setStatus(status);

		if (bdOutput.getOutputObject() instanceof Books) {
			bookServiceResponse.setBook((Books) bdOutput.getOutputObject());
		} else {
			bookServiceResponse.setBook(null);
		}

		return bookServiceResponse;
	}

	/**
	 * Method taking a Tags object and returning the tags as one String
	 * seperated by |
	 * 
	 * @param tags
	 *            the Tags object that should be transformed to a String
	 * @return the transformed tagstring
	 */
	public static String mapTagsToString(Tags tags) {
		StringBuilder sb = new StringBuilder();
		if (tags != null && tags.getTags() != null) {
			for (String tag : tags.getTags()) {
				sb.append(removeSpecialCharacters(tag)).append(",");
			}
			String tagString = sb.toString();
			return tagString.substring(0, tagString.length() - 1);
		} else
			return null;
	}

	/**
	 * This method maps a Tags input object to a collection of TagEntities
	 * 
	 * @param tags
	 *            the Tags input object that should be mapped
	 * @return a collection of TagEntities
	 */
	public static List<TagEntity> mapTagsToTagEntityList(Tags tags) {
		List<TagEntity> tagEntityList = null;
		TagEntity tagEntity = new TagEntity();
		if (tags != null) {
			tagEntityList = new ArrayList<TagEntity>();
			for (String tag : tags.getTags()) {
				tagEntity.setTagName(tag);
				tagEntityList.add(tagEntity);
			}
		}
		return tagEntityList;
	}

	/**
	 * This methid maps a collection of TagEntities to a Tags object
	 * 
	 * @param tagEntityCollection
	 *            a collection of TagEntities that should be mapped
	 * @return a Tags object
	 */
	public static Tags mapTagEntityListToTags(
			Collection<TagEntity> tagEntityCollection) {
		Tags tags = new Tags();
		List<String> tagList = new ArrayList<String>();
		if (tagEntityCollection != null) {
			for (TagEntity tagEntity : tagEntityCollection) {
				String tag = (tagEntity != null) ? tagEntity.getTagName()
						: null;
				tagList.add(tag);
			}
		}
		tags.setTags(tagList);
		return tags;
	}

	/**
	 * Method removing special characters from a string
	 * 
	 * @param tag
	 *            the tag which should be cleaned of special characters
	 * @return the string without special characters
	 */
	public static String removeSpecialCharacters(String tag) {
		return tag.replace(",", "");
	}

	/**
	 * Method taking a String of tags and transforms it into a Tags object
	 * 
	 * @param tags
	 *            the String containing all the tags
	 * @return the Tags object
	 */
	public static Tags mapTagStringToTags(String tagString) {
		Tags tags = new Tags();
		List<String> tagList;
		if (tagString != null) {
			tagList = Arrays.asList(tagString.split(","));
		} else {
			tagList = new ArrayList<String>();
		}
		tags.setTags(tagList);
		return tags;
	}

	/**
	 * Method mapping a BookEntity to a JSON String
	 * 
	 * @param bookEntity
	 *            the BookEntity object that should be mapped
	 * @return the JSON String
	 */
	public static String mapBookEntityToJSONString(BookEntity bookEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (bookEntity != null) {
			sb.append("id: ");
			String id = bookEntity.getId() != null ? bookEntity.getId() + ", "
					: ", ";
			sb.append(id);
			sb.append("title: ");
			String title = bookEntity.getTitle() != null ? bookEntity
					.getTitle() + ", " : ",";
			sb.append(title);
			sb.append("author: ");
			String author = bookEntity.getAuthor() != null ? bookEntity
					.getAuthor() + "," : ",";
			sb.append(author);
			sb.append("description: ");
			String description = bookEntity.getDescription() != null ? bookEntity
					.getDescription() + ", "
					: ", ";
			sb.append(description);
			sb.append("ISBN: ");
			String ISBN = bookEntity.getISBN() != null ? bookEntity.getISBN()
					+ ", " : ", ";
			sb.append(ISBN);
			sb.append("price: ");
			String price = bookEntity.getPrice() != null ? bookEntity
					.getPrice() + ", " : ", ";
			sb.append(price);
			sb.append("tags: ");
			sb.append("[");
			if (bookEntity.getTags() != null) {
				for (int i = 0; i < bookEntity.getTags().size(); i++) {
					TagEntity tagEntity = bookEntity.getTags().get(i);
					String tag = tagEntity != null ? tagEntity.getTagName()
							: "";
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
	public static String mapBookEntityListToConsolidatedJSONString(
			List<BookEntity> bookEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (bookEntities != null && bookEntities.size() > 0) {
			int listSize = bookEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapBookEntityToJSONString(bookEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(",");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
