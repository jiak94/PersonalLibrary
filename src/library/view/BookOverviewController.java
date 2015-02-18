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
import library.model.*;
import library.Main;
import javafx.scene.control.*;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


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
		
		bookTable.setItems(main.getBookData());
		this.bookData = main.getBookData();
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
			this.noteLabel.setText(book.getNote());
			this.purchaseDateLabel.setText(DateUtil.format(book.getPurchaseDate()));
			this.categoryLabel.setText(book.getCategory());
            this.bookCover.setImage(loadImage(eliminateDashes(book.getISBN())));
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
     * eliminate dashes
     * @param isbn
     */
    private String eliminateDashes(String isbn) {
        return isbn.replaceAll("-", "");
    }

    /**
     * Get books Cover Page
     * @param isbn
     */
    private Image loadImage(String isbn) {
        String imgUrl = "http://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg";

        Image img = new Image(imgUrl);

        return img;
    }

    /**
     * Retrieve JSON
     * @param isbn
     */
    protected String retriveJSON(String isbn) throws Exception{
        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream inputStream = connection.getInputStream();

        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);

        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            sb.append(str);
        }
        reader.close();
        connection.disconnect();
        return sb.toString();
    }
	
	/**
	 * Called when delete button is clicked
	 */
	public void handleDeleteBook() {
		int selectedIndex = bookTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			bookTable.getItems().remove(selectedIndex);
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
			editButton.setDisable(true);
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
		Book tempBook = new Book();
		boolean okClicked = main.showBookEditDialog(tempBook);
		
		if (okClicked) {
			main.getBookData().add(tempBook);
		}
	}
}
