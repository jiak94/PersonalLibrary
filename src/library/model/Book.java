package library.model;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.*;
import library.DateUtil.DateUtil;
import library.DateUtil.LocalDateAdapter;

/**
 * Define a Class Model call Book
 * Contains all the detail of the book.
 * @author Jiakuan
 *
 */
public class Book {
	
	//Some Instance Variable
	private final StringProperty title;
	private final StringProperty publisher;
	private final StringProperty ISBN;
	private final DoubleProperty price;
	private final StringProperty authors;
	private final StringProperty mediumCoverLink;
	private final StringProperty smallCoverLink;
	private final StringProperty location;
	private final StringProperty note;
	private final StringProperty category;
	private final ObjectProperty<LocalDate> purchaseDate;
	
	//Default constructor
	public Book() {
		this(null, null);
	}
	
	/**
	 * get today's date in format of MM/dd/yyyy
	 * @return today's date as String
	 */
	private String getTodayDate() {
		Date date = new Date();
		
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}
	
	/**
	 * Constructor with some data
	 * @param title
	 * @param ISBN
	 */
	public Book(String title, String ISBN) {
		this.title = new SimpleStringProperty(title);
		this.ISBN = new SimpleStringProperty(ISBN);
		
		this.publisher = new SimpleStringProperty("");
		this.price = new SimpleDoubleProperty(0.0);
		this.location = new SimpleStringProperty("");
		this.note = new SimpleStringProperty("");
		this.authors = new SimpleStringProperty("");
		this.mediumCoverLink = new SimpleStringProperty("");
		this.smallCoverLink = new SimpleStringProperty("");
		this.category = new SimpleStringProperty("-Select-");
		this.purchaseDate = new SimpleObjectProperty<LocalDate>(DateUtil.convert(getTodayDate()));
	}
	
	public String getAuthors() {
		return authors.get();
	}
	
	public void setAuthors(String authors) {
		this.authors.set(authors);
	}
	
	public StringProperty getAuthorsProperty() {
		return authors;
	}
	
	public String getMediumCoverLink() {
		return mediumCoverLink.get();
	}
	
	public void setMediumCoverLink(String link) {
		this.mediumCoverLink.set(link);
	}
	
	public StringProperty getMediumCoverLinkProperty() {
		return mediumCoverLink;
	}
	
	public String getSmallCoverLink() {
		return smallCoverLink.get();
	}
	
	public void setSmallCoverLink(String link) {
		this.smallCoverLink.set(link);
	}
	
	public StringProperty getSmallCoverLinkProperty() {
		return smallCoverLink;
	}
	
	public String getTitle() {
		return title.get();
	}
	
	public void setTitle(String title) {
		this.title.set(title);
	}
	
	public StringProperty getTitleProperty() {
		return title;
	}
	
	public String getISBN() {
		return ISBN.get();
	}
	
	public void setISBN(String ISBN) {
		this.ISBN.set(ISBN);
	}
	
	public StringProperty getISBNProperty() {
		return ISBN;
	}
	
	public String getPublisher() {
		return publisher.get();
	}
	
	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}
	
	public StringProperty getPublisherProperty() {
		return publisher;
	}
	
	public double getPrice() {
		return price.get();
	}
	
	public void setPrice(double price) {
		this.price.set(price);
	}
	
	public DoubleProperty getPriceProperty() {
		return price;
	}
	
	public String getLocation() {
		return location.get();
	}
	
	public void setLocation(String location) {
		this.location.set(location);
	}
	
	public StringProperty getLocationProperty() {
		return location;
	}
	
	public String getCategory() {
		return category.get();
	}
	
	public void setCategory(String category) {
		this.category.set(category);
	}
	
	public StringProperty getCategoryProperty() {
		return category;
	}
	
	public String getNote() {
		return note.get();
	}
	
	public void setNote(String note) {
		this.note.set(note);
	}
	
	public StringProperty getNoteProperty() {
		return note;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getPurchaseDate() {
		return purchaseDate.get();
	}
	
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate.set(purchaseDate);
	}
	
	public ObjectProperty<LocalDate> getPurchaseDateProperty() {
		return purchaseDate;
	}
}