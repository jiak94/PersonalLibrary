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


/**
 * Dialog to edit details of person
 * @author Jiakuan
 */
public class BookEditDialogController {
	
	@FXML
    private TextField titleField;
    @FXML
    private TextField ISBNField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField purchaseDateField;
    @FXML
    private TextArea noteArea;
    @FXML
    private ChoiceBox<String> category;
	
	private Stage dialogStage;
	private Book book;
	private boolean okClicked = false;
	
	
	/**
	 * automatically initializes function
	 */
	@FXML
	public void initialize() {
		ObservableList<String> categories = FXCollections.observableArrayList("-Select-", "Text Book", "Fiction", "Arts & Photography", "Bussiness & Money", 
				"Children's Book", "Computers & Technology", "Education & Teaching", "Reference", "Others");
		category.setItems(categories);
	}
	
	/**
	 * Sync With google
	 * @param book
	 * @throws Exception 
	 */
	@FXML
	public void syncWithGoogle() throws Exception {
		GoogleBookInfo gBook = new GoogleBookInfo(book.getISBN());
		
		book.setTitle(gBook.getTitle());
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
	
	/**
	 * Set the stage of this dialog
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * set the book to be edited dialog
	 * 
	 * @param book
	 */
	public void setBook(Book book) {
		this.book = book;
		
		titleField.setText(book.getTitle());
		ISBNField.setText(book.getISBN());
		publisherField.setText(book.getPublisher());
		priceField.setText(Double.toString(book.getPrice()));
		locationField.setText(book.getLocation());
		purchaseDateField.setText(DateUtil.format(book.getPurchaseDate()));
		purchaseDateField.setPromptText("mm/dd/yyyy");
		category.setValue(book.getCategory());
		noteArea.setText(book.getNote());
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
	 * handle  the ok click
	 */
	@FXML
	public void handleOk() {
		if (isInputValid()) {
			book.setTitle(titleField.getText());
			book.setISBN(ISBNField.getText());
			book.setPublisher(publisherField.getText());
			book.setPrice(Double.parseDouble(priceField.getText()));
			book.setLocation(locationField.getText());
			book.setPurchaseDate(DateUtil.convert(purchaseDateField.getText()));
			book.setNote(noteArea.getText());
			book.setCategory(category.getValue());
			
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
	 * validate input
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if ((titleField.getText() == null) || (titleField.getText().length() == 0)) {
			errorMessage += "Title is required\n";
		}
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
}
