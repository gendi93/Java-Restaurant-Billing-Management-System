package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class allows the user to login to their account and takes them to the
 * manager main page if they are a amanager, or a waiter main page otherwise.
 */
public class LoginController {

	/** The txt manager username. */
	@FXML
	private TextField txtManagerUsername;

	/** The txt employee username. */
	@FXML
	private TextField txtEmployeeUsername;

	/** The txt manager password. */
	@FXML
	private TextField txtManagerPassword;

	/** The txt employee password. */
	@FXML
	private TextField txtEmployeePassword;

	/** The lbl manager login status. */
	@FXML
	private Label lblManagerLoginStatus;

	/** The lbl employee login status. */
	@FXML
	private Label lblEmployeeLoginStatus;

	/**
	 * This method first checks whether the input boxes are blank in which case
	 * a error message is displayed. If data has been entered, the input
	 * username is checked against the stored username for that user and the
	 * same is done for the password. If the input text matches the username and
	 * password pair for the current user, the manager main page is opened.
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void ManagerLogin(ActionEvent event) throws Exception {
		application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
		if (!txtManagerUsername.getText().equals("")) {
			if (!txtManagerPassword.getText().equals("")) {
				for (Employee e : application.Main.employeeList) {
					if (e.isManager) {
						if (txtManagerUsername.getText().equals(e.firstName)) {
							if (txtManagerPassword.getText().equals(e.password)) {
								Stage managerLogin = (Stage) txtManagerUsername.getScene().getWindow();
								managerLogin.close();
								Stage primaryStage = new Stage();
								FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager Main.fxml"));
								Parent root = loader.load();
								Scene scene = new Scene(root);
								scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
								primaryStage.setScene(scene);
								primaryStage.show();
								ManagerMainController controller = (ManagerMainController) loader.getController();
								controller.SetLabelText(e.firstName);
								application.Main.user = e.firstName;
							} else {
								lblManagerLoginStatus.setText("Incorrect Password");
							}
						} else {
							lblManagerLoginStatus.setText("Incorrect Username");
						}
					}
				}
			} else {
				lblManagerLoginStatus.setText("Please enter a password");
			}
		} else {
			lblManagerLoginStatus.setText("Please enter a username");
		}
	}

	/**
	 * This method first checks whether the input boxes are blank in which case
	 * a error message is displayed. If data has been entered, the input
	 * username is checked against the stored username for that user and the
	 * same is done for the password. If the input text matches the username and
	 * password pair for the current user, the waiter main page is opened
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void EmployeeLogin(ActionEvent event) throws Exception {
		application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
		if (!txtEmployeeUsername.getText().equals("")) {
			if (!txtEmployeePassword.getText().equals("")) {
				for (Employee e : application.Main.employeeList) {
					if (!e.isManager) {
						if (txtEmployeeUsername.getText().equals(e.firstName)) {
							if (txtEmployeePassword.getText().equals(e.password)) {
								Stage employeeLogin = (Stage) txtEmployeeUsername.getScene().getWindow();
								employeeLogin.close();
								Stage primaryStage = new Stage();
								FXMLLoader loader = new FXMLLoader(getClass().getResource("Waiter Main.fxml"));
								Parent root = loader.load();
								Scene scene = new Scene(root);
								scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
								primaryStage.setScene(scene);
								primaryStage.show();
								WaiterMainController controller = (WaiterMainController) loader.getController();
								controller.SetLabelText(e.firstName);
								application.Main.user = e.firstName;
							} else {
								lblEmployeeLoginStatus.setText("Incorrect Password");
							}
						} else {
							lblEmployeeLoginStatus.setText("Incorrect Username");
						}
					}
				}
			} else {
				lblManagerLoginStatus.setText("Please enter a password");
			}
		} else {
			lblManagerLoginStatus.setText("Please enter a username");
		}
	}

	/**
	 * Back manager.
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void BackManager(ActionEvent event) throws Exception {
		Stage managerLogin = (Stage) txtManagerUsername.getScene().getWindow();
		managerLogin.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Welcome Page.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Back employee.
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void BackEmployee(ActionEvent event) throws Exception {
		Stage employeeLogin = (Stage) txtEmployeeUsername.getScene().getWindow();
		employeeLogin.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Welcome Page.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
