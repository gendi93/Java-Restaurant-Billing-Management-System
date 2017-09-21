package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

// TODO: Auto-generated Javadoc
/**
 * This class allows the user to change their password and update their details
 * in the employees database.
 */
public class ChangePasswordController {

	/** The old password text. */
	@FXML
	private TextField oldPasswordText;

	/** The new password text. */
	@FXML
	private TextField newPasswordText;

	/** The password confirmation text.*/
	@FXML
	private TextField confirmPasswordText;

	/**
	 * This method will check if the user's input old password matches the old
	 * password saved in the file. If it is, it then compares whether the new
	 * password and its confirmation are the same. If they match, the method
	 * will then check whether this new password is at least 6 characters long
	 * and consists of numbers and letters only.
	 *
	 * @throws Exception
	 *             This is an IOException in case the employees file doesn't
	 *             exist.
	 */
	public void savePassword() throws Exception {
		application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
		Employee user = null;
		int userIndex = -1;
		for (Employee e : application.Main.employeeList) {
			if (e.firstName.equals(application.Main.user)) {
				userIndex = application.Main.employeeList.indexOf(e);
				user = e;
				break;
			}
		}
		if (user.getPassword().equals(oldPasswordText.getText())) {
			if (confirmPasswordText.getText().equals(newPasswordText.getText())) {
				Boolean passwordComplies = newPasswordText.getText()
						.matches("[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]*");
				if (!(newPasswordText.getText().length() < 6)) {
					if (passwordComplies) {
						application.Main.employeeList.get(userIndex).setPassword(newPasswordText.getText());
						application.Main.writeEmployees();
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Success!");
						alert.setContentText("Your password has been changed, you will now be logged out.");
						alert.showAndWait();
						Stage password = (Stage) oldPasswordText.getScene().getWindow();
						password.close();
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("Welcome Page.fxml"));
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();
					} else {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Illegal Characters");
						alert.setContentText("Your password can only consist of numbers and letters.");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Password Too Short");
					alert.setContentText("Your password needs to be at least 6 characters long.");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Password Mismatch");
				alert.setContentText("The two new passwords do not match.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Password Incorrect");
			alert.setContentText("The old password you entered is incorrect.");
			alert.showAndWait();
		}
	}

	/**
	 * This method takes the user back to the manager main page if they are a
	 * manager, and to the waiter main page otherwise.
	 * 
	 * @param event
	 *            This is a button click on the back button.
	 * @throws Exception
	 *             This is an IOException in case the employees file doesn't
	 *             exist.
	 */
	public void back(ActionEvent event) throws Exception {
		application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
		Employee user = null;
		for (Employee e : application.Main.employeeList) {
			if (e.firstName.equals(application.Main.user)) {
				user = e;
				break;
			}
		}
		if (user.getStatus()) {
			Stage password = (Stage) oldPasswordText.getScene().getWindow();
			password.close();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager Main.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			ManagerMainController controller = (ManagerMainController) loader.getController();
			controller.SetLabelText(user.firstName);
		} else {
			Stage password = (Stage) oldPasswordText.getScene().getWindow();
			password.close();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Waiter Main.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			WaiterMainController controller = (WaiterMainController) loader.getController();
			controller.SetLabelText(user.firstName);
		}
	}
}
