package library.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import library.DateUtil.DateUtil;
import library.Util.CheckInternetConnection;
import library.model.*;
import library.Main;
import javafx.scene.control.*;



public class BookOverviewController {
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> titleColumn;
    @FXML
    private ImageView bookCover = new ImageView();
	
	@FXML
	private Label titleLabel;
	@FXML
	private Label ISBNLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private Label purchaseDateLabel;
	@FXML
	private Label locationLabel;
	@FXML
	private Label publisherLabel;
	@FXML
	private Label noteLabel;
	@FXML
	private Button editButton;
	@FXML
	private Label categoryLabel;
	@FXML
	private TextField searchField;
	@FXML
	private Label authors;
	
	private ObservableList<Book> bookData;
	
	//reference to main application
	private Main main;
	
	//default constructor
	public BookOverviewController() {
	}
	
	/**
	 * Initializes the controller class.
	 * Automatic called method
	 */
	@FXML
	private void initialize() {
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
		
		//Clear book details when it initializes
		showBookDetails(null);
		
		//Listen for selection changes and show the details
		bookTable.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> showBookDetails(newValue));
		
	}
	
	public void addFilter() {
		FilteredList<Book> filteredData = new FilteredList<>(bookData, p-> true);
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all books;
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
		
		SortedList<Book> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(bookTable.comparatorProperty());
		
		bookTable.setItems(sortedData);
	}
	
	/**
	 * give a reference back to itself
	 * @param mainApp
	 */
	public void setMain(Main mainApp) {
		this.main = mainApp;
		
		this.bookData = main.getBookData();
		bookTable.setItems(main.getBookData());
	}
	
	/**
	 * show book detail
	 * @param book
	 */
	public void showBookDetails(Book book) {
		if (book != null) {
			this.titleLabel.setText(book.getTitle());
			this.ISBNLabel.setText(book.getISBN());
			this.publisherLabel.setText(book.getPublisher());
			this.priceLabel.setText("$ "+book.getPrice());
			this.locationLabel.setText(book.getLocation());
			try{
				this.authors.setText(book.getAuthors());
			} catch(Exception e) {
				this.authors.setText("No data");
			}
			this.noteLabel.setText(book.getNote());
			this.purchaseDateLabel.setText(DateUtil.format(book.getPurchaseDate()));
			this.categoryLabel.setText(book.getCategory());
            try {
            	if (CheckInternetConnection.checkConnection()) {
            		this.bookCover.setImage(loadImage(book.getMediumCoverLink()));
            	}
            	else {
            		this.bookCover.setImage(new Image("file:Resources/images/no_book_cover.jpg"));
            	}
			} catch (Exception e) {
				this.bookCover.setImage(new Image("file:Resources/images/no_book_cover.jpg"));
			}
            this.bookCover.setSmooth(true);
            this.bookCover.setCache(true);
		}
		else {
			//set everything to null
			this.titleLabel.setText("");
			this.ISBNLabel.setText("");
			this.priceLabel.setText("");
			this.publisherLabel.setText("");
			this.locationLabel.setText("");
			this.noteLabel.setText("");
			this.purchaseDateLabel.setText("");
			this.categoryLabel.setText("");
		}
	}

    /**
     * Get books Cover Page
     * @param isbn
     */
    private Image loadImage(String url) throws Exception {
        Image img = new Image(url);
        return img;
    }
	
	/**
	 * Called when delete button is clicked
	 */
	public void handleDeleteBook() {
		int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			bookTable.getItems().remove(selectedIndex);
			//bookTable.get(selectedIndex)
		}
		else {
			Dialogs.create()
					.title("No Selection")
					.masthead("No Book has been selected")
					.message("Please Select a book on the left")
					.showWarning();
		}
	}
	
	/**
	 * Called when Edit button is clicked
	 */
	public void handleEditBook() {
		Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
		if (selectedBook != null) {
			boolean okClicked = main.showBookEditDialog(selectedBook);
			
			if (okClicked) {
				showBookDetails(selectedBook);
			}
		}
		else {
			Dialogs.create()
					.title("No selection")
					.masthead("No book selected")
					.message("Please selected a book")
					.showWarning();
		}
	}
	
	/**
	 * Called when new button is clicked
	 */
	public void handleNewBook() {
		Book tempBook = new Book(null, null);
		boolean okClicked = main.showNewBookDialog(tempBook);
		
		if (okClicked) {
			main.getBookData().add(tempBook);
		}
	}
}
