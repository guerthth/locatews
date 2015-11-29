package amtc.gue.ws.books.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
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
	 *            element
	 * @return mapped BookEntity
	 */
	public static BookEntity mapBookToEntity(Book book) {

		BookEntity bookEntity = new BookEntity();
		bookEntity.setAuthor(book.getAuthor());
		bookEntity.setDescription(book.getDescription());
		bookEntity.setISBN(book.getISBN());
		bookEntity.setPrice(book.getPrice());
		bookEntity.setTags(mapTagsToString(book.getTags()));
		bookEntity.setTitle(book.getTitle());

		return bookEntity;
	}

	/**
	 * Method to map a Books object to a list of BookEntity objects
	 * 
	 * @param books
	 *            object containing list of Book object
	 * @param bookEntityList
	 *            the list of BookEntities
	 * @return list of BookEntity objects
	 */
	public static List<BookEntity> transformBooksToBookEntities(Books books) {

		List<BookEntity> bookEntityList = new ArrayList<BookEntity>();

		for (Book book : books.getBooks()) {
			bookEntityList.add(mapBookToEntity(book));
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
		book.setAuthor(bookEntity.getAuthor());
		book.setDescription(bookEntity.getDescription());
		book.setISBN(bookEntity.getISBN());
		book.setPrice(bookEntity.getPrice());
		book.setTags(mapTagStringToTags(bookEntity.getTags()));
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
	public static BookServiceResponse mapBdOutputToServiceResponse(
			IDelegatorOutput bdOutput) {

		// create the status object
		Status status = new Status();
		status.setStatusMessage(bdOutput.getStatusMessage());
		status.setStatusCode(bdOutput.getStatusCode());

		// create Service Response
		BookServiceResponse serviceResponse = new BookServiceResponse();
		serviceResponse.setStatus(status);

		if (bdOutput.getOutputObject() instanceof Books) {
			serviceResponse.setBook((Books) bdOutput.getOutputObject());
		} else {
			serviceResponse.setBook(null);
		}

		return serviceResponse;
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
			String price = bookEntity.getPrice() != null ? bookEntity.getPrice() + ", " : ", ";
			sb.append(price);
			// TODO: Changes needed once the datamodel is changed
			sb.append("tags: ");
			String tags = bookEntity.getTags() != null ? bookEntity.getTags() + "" : "";
			sb.append(tags);
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * Method mapping a List of JSONStrings to one consolidated String
	 * 
	 * @param JSONStrings the list of JSONStrings that should be mapped
	 * @return the consolidated String
	 */
	public static String mapJSONStringsToConsolidatedString(List<String> JSONStrings){
		StringBuilder sb = new StringBuilder();
		if(JSONStrings != null && JSONStrings.size() > 0){
			int listSize = JSONStrings.size();
			for(int i = 0;i<listSize;i++){
				sb.append(JSONStrings.get(i));
				if(i != listSize - 1){
					sb.append(System.getProperty("line.separator"));
				}
			}
		}
		return sb.toString();
	}
}
