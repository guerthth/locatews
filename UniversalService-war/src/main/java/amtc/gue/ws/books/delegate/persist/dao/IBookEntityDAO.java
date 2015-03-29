package amtc.gue.ws.books.delegate.persist.dao;

import java.util.List;

import amtc.gue.ws.books.persistence.model.BookEntity;

/**
 * Interface for the database actions on BookEntities	
 * @author Thomas
 *
 */
public interface IBookEntityDAO {
	
	/**
	 * Add a new BookEntity to the DB
	 * 
	 * @param book the book to be added in the DB
	 * @return the added book
	 */
	public BookEntity addBookEntity(BookEntity book); 
	
	/**
	 * Retrieving all existing bookentities from the DB
	 * 
	 * @return list of persisted bookentities
	 */
	public List<BookEntity> getAllBookEntities();

	/**
	 * Retrieve a specific book from the DB by its ISBN
	 * 
	 * @param ISBN the ISBN number of the book to retrieve
	 * @return bookentity with the respective ISBN
	 */
	public BookEntity getBookEntity(String ISBN);
	
	/**
	 * Update a specific BookEntity
	 * 
	 * @param book the bookentity that should be updated
	 */
	public BookEntity updateBookEntity(BookEntity book);
	
	/**
	 * Delete a BookEntity from the DB
	 * 
	 * @param book the bookentity that should be deleted
	 */
	public void deleteBookEntity(BookEntity book);
}
