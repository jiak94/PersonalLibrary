package library.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * wrap a list of books, use for saving the list of books to XML
 * @author Jiakuan
 *
 */
@XmlRootElement(name = "books")
public class BookListWrapper {
	private List<Book> books;
	
	@XmlElement(name = "book")
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
