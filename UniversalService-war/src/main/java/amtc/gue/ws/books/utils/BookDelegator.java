package amtc.gue.ws.books.utils;

import java.util.List;

import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;

public class BookDelegator {
	
	public static String getBookText(Books items){
		
		String bookString = "";
		
		if (items != null){
			
			List<Book> books = items.getBooks();
			
			for (Book book : books){
				bookString += book.getAuthor() + ", ";
				bookString += book.getISBN() + ", ";
				bookString += book.getPrice() + ", ";
				bookString += book.getTags() + ", ";
				bookString += book.getTitle() + " | ";
				
			}
		}
		return bookString;
	}
	
}
