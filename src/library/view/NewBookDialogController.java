package library.view;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
<<<<<<< HEAD
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
=======
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
>>>>>>> 0740888cead9bed77a61d66c25354533f42e2284
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.DateUtil.DateUtil;
import library.Util.GoogleBookInfo;
import library.model.Book;

public class NewBookDialogController {
	@FXML
	private TextField ISBNField;
	@FXML
	private TextField priceField;
	@FXML
    private ChoiceBox<String> category;
    @FXML
<<<<<<< HEAD
    private TextField locationField;
    @FXML
    private TextField purchaseDateField;
    @FXML
    private TextField publisher;
    @FXML
    private TextField author;
    @FXML
    private TextField title;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Button dataButton;
    
	private int manuallyData = 0;
=======
    private TextArea noteArea;
    @FXML
    private TextField locationField;
    @FXML
    private TextField purchaseDateField;
	
>>>>>>> 0740888cead9bed77a61d66c25354533f42e2284
	private Stage dialogStage;
	private Book book;
	private boolean okClicked = false;
	private GoogleBookInfo gBook;
	
	/**
	 * automatically initializes function
	 */
	@FXML
	public void initialize() {
		ObservableList<String> categories = FXCollections.observableArrayList("-Select-", "Text Book", "Fiction", "Arts & Photography", "Bussiness & Money", 
				"Children's Book", "Computers & Technology", "Education & Teaching", "Reference", "Others");
		category.setItems(categories);
		//purchaseDateField.setText(DateUtil.format(book.getPurchaseDate()));
	}
	
	/**
	 * Set the stage of this dialog
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * handle  the ok click
	 * @throws Exception 
	 */
	@FXML
<<<<<<< HEAD
	public void handleOk() {
		try {
			if (isInputValid()) {
				String isbn = ISBNField.getText();
				gBook = new GoogleBookInfo(isbn);
				if (manuallyData == 1) {
					book.setTitle(title.getText());
					book.setPublisher(publisher.getText());
					book.setAuthors(author.getText());
				} else {
					book.setPublisher(gBook.getPublisher());
					book.setTitle(gBook.getTitle());
					book.setAuthors(gBook.getAuthor());
					book.setMediumCoverLink(gBook.getMediumImage());
					book.setSmallCoverLink(gBook.getSmallImage());
				}
				book.setISBN(isbn);
				book.setPrice(Double.parseDouble(priceField.getText()));
				book.setLocation(locationField.getText());
				book.setPurchaseDate(DateUtil.convert(purchaseDateField.getText()));
				book.setCategory(category.getValue());
				
				okClicked = true;
			
				dialogStage.close();
			} 
		} catch (Exception e) {
			Dialogs.create()
			.title("ISBN Not Found")
			.masthead("ISBN Not Found")
			.message("Can't load data from google. Please enter the data manually or check your ISBN number")
			.showError();
		}
		
	}
	
	/**
	 * HandelDataButton
	 * @param status
	 */
	@FXML
	private void handleDataButton() {
		if (manuallyData == 0) {
			handleManuallyData();
		}
		else {
			retrieveFromGoogle();
=======
	public void handleOk() throws Exception {
		if (isInputValid()) {
			String isbn = ISBNField.getText();
			gBook = new GoogleBookInfo(isbn);
			book.setTitle(gBook.getTitle());
			book.setISBN(isbn);
			book.setPublisher(gBook.getPublisher());
			book.setPrice(Double.parseDouble(priceField.getText()));
			book.setLocation(locationField.getText());
			book.setPurchaseDate(DateUtil.convert(purchaseDateField.getText()));
			book.setNote(noteArea.getText());
			book.setCategory(category.getValue());
			book.setMediumCoverLink(gBook.getMediumImage());
			book.setSmallCoverLink(gBook.getSmallImage());
			book.setAuthors(gBook.getAuthor());
			
			okClicked = true;
			
			dialogStage.close();
>>>>>>> 0740888cead9bed77a61d66c25354533f42e2284
		}
	}
	
	/**
<<<<<<< HEAD
	 * Manually enter data
	 */
	private void handleManuallyData() {
		dataButton.setText("Auto Data");
		titleLabel.setVisible(true);
		authorLabel.setVisible(true);
		publisherLabel.setVisible(true);
		
		publisher.setVisible(true);
		author.setVisible(true);
		title.setVisible(true);
		
		manuallyData = 1;
	}
	
	/**
	 * Retrieve from google
	 */
	private void retrieveFromGoogle() {
		dataButton.setText("Manual Data");
		titleLabel.setVisible(false);
		authorLabel.setVisible(false);
		publisherLabel.setVisible(false);
		
		publisher.setVisible(false);
		author.setVisible(false);
		title.setVisible(false);
		
		manuallyData = 0;
	}
	
	/**
=======
>>>>>>> 0740888cead9bed77a61d66c25354533f42e2284
	 * handle the cancel is clicked
	 */
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	/**
	 * returns true if the user click ok, otherwise false
	 * 
	 * @return true if clicked ok
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * validate input
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if ((ISBNField.getText() == null) || (ISBNField.getText().length() == 0)) {
			errorMessage += "ISBN is required\n";
		}
		if ((category.getValue().equalsIgnoreCase("-Select-"))) {
			errorMessage += "Category is required\n";
		}
		
		if (errorMessage.length() == 0 || errorMessage == null) {
			return true;
		}
		else {
			Dialogs.create()
					.title("Missing Information")
					.masthead("Missing information")
					.message(errorMessage)
					.showError();
			return false;
		}
	}
	/**
	 * set the book to be edited dialog
	 * 
	 * @param book
	 */
	public void setBook(Book book) {
		this.book = book;
		
		ISBNField.setText(book.getISBN());
		priceField.setText(Double.toString(book.getPrice()));
		locationField.setText(book.getLocation());
		purchaseDateField.setText(DateUtil.format(book.getPurchaseDate()));
		purchaseDateField.setPromptText("mm/dd/yyyy");
		category.setValue(book.getCategory());
<<<<<<< HEAD
=======
		noteArea.setText(book.getNote());
>>>>>>> 0740888cead9bed77a61d66c25354533f42e2284
	}
}
