package application;

import java.io.IOException;
import java.util.Optional;

import application.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class allows the user to navigate to all the pages available to the manager.
 */
public class ManagerMainController {
	
	/** The employee list button. */
	@FXML
	private Button employees;
	
	/** The search button. */
	@FXML
	private Button orderList;
	
	/** The menu button. */
	@FXML
	private Button menu;
	
	/** The logout button. */
	@FXML
	private Button logout;
	
	/** The welcome message. */
	@FXML
	private Label lblWelcomeManager;
	
	/**
	 * Sets the welcome message text.
	 *
	 * @param name the name
	 */
	public void SetLabelText(String name) {
		lblWelcomeManager.setText("Welcome " + name + "!");
	}

	/**
	 * Open employee list page.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void OpenEmployeeListPage(ActionEvent event) throws Exception {
		Stage managerMain = (Stage) employees.getScene().getWindow();
		managerMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Waiter List.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		WaiterListController controller = (WaiterListController) loader.getController();
		controller.readEmployees();
	}

	/**
	 * Open activity log.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void OpenOrderListPage(ActionEvent event) throws Exception {
		Stage managerMain = (Stage) orderList.getScene().getWindow();
		managerMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Order Table.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		OrderTableController controller = (OrderTableController) loader.getController();
		controller.initiate();
	}

	/**
	 * Open menu.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void OpenMenu(ActionEvent event) throws Exception {
		Stage managerMain = (Stage) menu.getScene().getWindow();
		managerMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		MenuController controller = (MenuController) loader.getController();
		controller.readExcel();
	}
	
	/**
	 * Open order search.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void OpenOrderSearch(ActionEvent event) throws Exception {
		Stage managerMain = (Stage) employees.getScene().getWindow();
		managerMain.close();
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

		if (result.get() == yes) {
			Stage managerMain = (Stage) logout.getScene().getWindow();
			managerMain.close();
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
		Stage managerMain = (Stage) logout.getScene().getWindow();
		managerMain.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Change Password.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
