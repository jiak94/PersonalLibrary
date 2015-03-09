package library.view;


import java.io.File;

import org.controlsfx.dialog.Dialogs;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import library.Main;
/**
 * control the root layout
 * @author Jiakuan
 *
 */
public class RootLayoutController {
	//reference to main application
	private Main main;
	
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }
    
    /**
     * Creates an empty library.
     */
    @FXML
    public void handleNew() {
        main.getBookData().clear();
        main.setBookFilePath(null);
    }
    
    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null) {
            main.loadBookDataFromFile(file);
        }
    }
    
    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    public void handleSave() {
        File personFile = main.getBookFilePath();
        if (personFile != null) {
            main.saveBookDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }
    
    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            main.saveBookDataToFile(file);
        }
    }
    /**
     * Opens a Category Statistic window
     */
    @FXML
    private void handleCategoryStatistic() {
    	main.showCategoryStatistic();
    }
    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Dialogs.create()
            .title("Library")
            .masthead("About")
            .message("Author: Jiakuan Li\n"
            		+ "Website: www.jiakuansfamily.com")
            .showInformation();
    }
    
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
    	handleSave();
        System.exit(0);
    }
}
