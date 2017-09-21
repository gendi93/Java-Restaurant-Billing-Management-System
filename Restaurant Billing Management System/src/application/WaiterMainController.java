package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class allows the user to navigate to any of the pages available to waiters.
 */
public class WaiterMainController {
	
	/** The table view button.*/
	@FXML
	private Button tableView;
	
	/** The order search button. */
	@FXML
	private Button orders;
	
	/** The logout button. */
	@FXML
	private Button logout;
	
	/** The welcome message text. */
	@FXML
	private Label lblWelcomeEmployee;

	/**
	 * Sets the welcome message text.
	 *
	 * @param name the name
	 */
	public void SetLabelText(String name) {
		lblWelcomeEmployee.setText("Welcome " + name + "!");
	}

	/**
	 * Open table view.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void OpenTableView(ActionEvent event) throws Exception {
		Stage employeeMain = (Stage) tableView.getScene().getWindow();
		employeeMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Table View.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		TableViewController controller = (TableViewController) loader.getController();
		ArrayList<Integer> tables = new ArrayList<Integer>();
		for (Order o : application.Main.orderList) {
			tables.add(o.getTableNumber());
		}
		controller.placeOrder(tables);
	}

	/**
	 * Open search page.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void OpenSearchPage(ActionEvent event) throws Exception {
		Stage employeeMain = (Stage) orders.getScene().getWindow();
		employeeMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		SearchController controller = (SearchController) loader.getController();
		controller.initiate();
	}

	/**
	 * Logout.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void Logout(ActionEvent event) throws Exception {
		ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to continue?", yes, no);
		alert.setHeaderText("You are about to log out and return to the welcome page.");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == yes){
			Stage employeeMain = (Stage) logout.getScene().getWindow();
			employeeMain.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Welcome Page.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
	
	/**
	 * Change password.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void changePassword(ActionEvent event) throws Exception {
		Stage employeeMain = (Stage) logout.getScene().getWindow();
		employeeMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Change Password.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
