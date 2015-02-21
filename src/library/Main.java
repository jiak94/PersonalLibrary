package library;
	
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import library.model.Book;
import library.model.BookListWrapper;
import library.view.*;


public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	private static ObservableList<Book> bookData = FXCollections.observableArrayList();
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Library");
		
		
		//Set the application Icon
		this.primaryStage.getIcons().add(new Image("file:Resources/images/Library.png"));
		
		initRootLayout();
		showBookOverview();
	}
	/**
	 * Constructor
	 */
	public Main() {
	}
	
	/**
	 * initialize the rootlayout
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			
			rootLayout = (BorderPane) loader.load();
			
			//Show the scene containing the root layout
			 Scene scene = new Scene(rootLayout);
	         primaryStage.setScene(scene);
	         
	      // Give the DateUtil access to the main app.
	         RootLayoutController controller = loader.getController();
	         controller.setMainApp(this);
	         
	         primaryStage.show();
	         
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Try to load last opened person file.
	    File file = getBookFilePath();
	    if (file != null) {
	        loadBookDataFromFile(file);
	    }
	}
	
	/**
	 * initialize the book overview
	 */
	public void showBookOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/BookOverview.fxml"));
			AnchorPane bookOverview = (AnchorPane) loader.load();
			
			//Set the book overview to the center of the root layout;
			rootLayout.setCenter(bookOverview);
			
			BookOverviewController controller = loader.getController();
			controller.setMain(this);
			//controller.addFilter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * return book data
	 * @return bookData
	 */
	public static ObservableList<Book> getBookData() {
		return bookData;
	}
	
	/**
	 * return the main stage
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Open the dialog when user click edit
	 * @param book object
	 * @return true if the user click OK
	 */
	public boolean showBookEditDialog(Book book) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class
					.getResource("view/BookEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Book");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the DateUtil.
			BookEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setBook(book);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Open the dialog when user click edit
	 * @param book object
	 * @return true if the user click OK
	 */
	public boolean showNewBookDialog(Book book) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class
					.getResource("view/NewBookDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Book");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the DateUtil.
			NewBookDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setBook(book);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Open the statistic dialog
	 */
	public void showCategoryStatistic() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(("view/CategoryStatistic.fxml")));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Category Statistic");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			//set the books into the DateUtil
			CategoryStatisticController controller = loader.getController();
			controller.setBookData(bookData);
			
			dialogStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the book file preference
	 */
	public File getBookFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Set the file path of the currently loaded file.
	 */
	public void setBookFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("Library - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("Library");
	    }
	}
	
	/**
	 * Loads book data from the XML File.
	 * @param file
	 */
	public void loadBookDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BookListWrapper.class);
			
			Unmarshaller um = context.createUnmarshaller();
			
			//Reading XML from the file and unmarshalling.
			BookListWrapper wrapper = (BookListWrapper)um.unmarshal(file);
			
			bookData.clear();
			bookData.addAll(wrapper.getBooks());
			
			//Save the file path to the registry
			setBookFilePath(file);
			
		} catch (Exception e) {
			if (file.exists()) {
				Dialogs.create()
					.title("Info")
					.masthead("Your library is currently empty\n" + file.getPath())
					.showException(e);
			}else {
				getBookData().clear();
				setBookFilePath(null);
			}
		}
	}
	
	/**
	 * Saves the current book data to the specified file
	 * @param file
	 */
	public void saveBookDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BookListWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        
	        //wrapping data
	        BookListWrapper wrapper = new BookListWrapper();
	        wrapper.setBooks(bookData);
	        
	        m.marshal(wrapper, file);
	        
	        setBookFilePath(file);
		} catch (Exception e) {
			Dialogs.create()
					.title("Error")
					.masthead("Could not save data to file:\n" + file.getPath())
					.showException(e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
