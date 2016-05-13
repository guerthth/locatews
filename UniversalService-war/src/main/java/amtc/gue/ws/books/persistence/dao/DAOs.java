package amtc.gue.ws.books.persistence.dao;

import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;

/**
 * This class represents a bundle for all DAOS used in an delegator all possible
 * DAO instances have to be included since business delegators can use more than
 * one DAO Implementation
 * 
 * @author Thomas
 *
 */
public class DAOs {
	
	BookDAO bookDAO;
	TagDAO tagDAO;
	
	/*
	 * Getters and Setters
	 */
	public BookDAO getBookDAO() {
		return bookDAO;
	}
	
	public void setBookDAO(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}
	
	public TagDAO getTagDAO() {
		return tagDAO;
	}
	
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}
}
