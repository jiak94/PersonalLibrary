package library.view;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
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
    private TextArea noteArea;
    @FXML
    private TextField locationField;
    @FXML
    private TextField purchaseDateField;
	
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
		}
	}
	
	/**
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
		noteArea.setText(book.getNote());
	}
}
