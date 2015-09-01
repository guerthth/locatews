package amtc.gue.ws.books.utils;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.service.inout.output.Status;

/**
 * Class mapping objects to JPA entities
 * @author Thomas
 *
 */
public class EntityMapper {
	
	/**
	 * Method for the mapping of Books
	 * @param book element
	 * @return mapped BookEntity
	 */
	public static BookEntity mapBookToEntity(Book book){
		
		BookEntity bookEntity = new BookEntity();
		bookEntity.setAuthor(book.getAuthor());
		bookEntity.setDescription(book.getDescription());
		bookEntity.setISBN(book.getISBN());
		bookEntity.setPrice(book.getPrice());
		bookEntity.setTags(book.getTags());
		bookEntity.setTitle(book.getTitle());
		
		return bookEntity;
	}
	
	/**
	 * Method to map a Books object to a list of BookEntity objects
	 * 
	 * @param books object containing list of Book object
	 * @param bookEntityList the list of BookEntities
	 * @return list of BookEntity objects
	 */
	public static List<BookEntity> transformBooksToBookEntities(Books books){
		
		List<BookEntity> bookEntityList = new ArrayList<BookEntity>();
		
		for(Book book : books.getBooks()){
			bookEntityList.add(mapBookToEntity(book));
		}
		
		return bookEntityList;
	}
	
	/**
	 * Method mapping a business delegator output to a service response
	 * 
	 * @param bdOutput business output of a business delegator
	 * @return mapped service response object
	 */
	public static BookServiceResponse mapBdOutputToServiceResponse(IDelegatorOutput bdOutput){
		
		// create the status object
		Status status = new Status();
		status.setStatusMessage(bdOutput.getStatusMessage());
		status.setStatusCode(bdOutput.getStatusCode());
		
		// create Service Response
		BookServiceResponse serviceResponse = new BookServiceResponse();
		serviceResponse.setStatus(status);
		
		if(bdOutput.getOutputObject() instanceof Books){
			serviceResponse.setBook((Books)bdOutput.getOutputObject());
		} else {
			serviceResponse.setBook(null);
		}
		
		return serviceResponse;
	}
}
