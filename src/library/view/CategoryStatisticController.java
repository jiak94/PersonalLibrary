package library.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import library.model.Book;


/**
 * show statistic of book category
 * @author Jiakuan
 *
 */
public class CategoryStatisticController {
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> categories = FXCollections.observableArrayList("Text Book", "Fiction", "Arts", "Bussiness", 
			"Children", "Technology", "Education", "Reference", "Others");
	
	/**
	 * Initializes the DateUtil class. Automatic called
	 */
	@FXML
	private void initialize() {
		//add the categories to xAxis;
		xAxis.setCategories(categories);
	}
	
	/**
	 * sets the books to show the statistic
	 * 
	 * @param books
	 */
	public void setBookData(List<Book> books) {
		int[] counter = new int[9];
		
		for(Book b : books) {
			String category = b.getCategory();
			if (category.equalsIgnoreCase("Text Book")) {
				counter[0]++;
			}
			else if (category.equalsIgnoreCase("Fiction")) {
				counter[1]++;
			}
			else if (category.equalsIgnoreCase("Arts & Photography")) {
				counter[2]++;
			}
			else if (category.equalsIgnoreCase("Bussiness & Money")) {
				counter[3]++;
			}
			else if (category.equalsIgnoreCase("Children's Book")) {
				counter[4]++;
			}
			else if (category.equalsIgnoreCase("Computers & Technology")) {
				counter[5]++;
			}
			else if (category.equalsIgnoreCase("Education & Teaching")) {
				counter[6]++;
			}
			else if (category.equalsIgnoreCase("Reference")) {
				counter[7]++;
			}
			else if (category.equalsIgnoreCase("Others")) {
				counter[8]++;
			}
		}
		
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		//create a XYChart.Data Object
		for (int i = 0; i < counter.length; i++) {
			series.getData().add(new XYChart.Data<>(categories.get(i), counter[i]));
		}
		
		barChart.getData().add(series);
	}
}
