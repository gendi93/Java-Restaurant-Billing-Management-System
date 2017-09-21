package application;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.String;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The main page of the restaurant management system. From here, managers and waiters can navigate to their login page.
 */
public class WelcomePageController {
	
	@FXML
	private Button managerLogin;
	
	/** The employee login button. */
	@FXML
	private Button employeeLogin;
	
	/**
	 * Opens the login page for managers.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void LoginPageManager(ActionEvent event) throws Exception {
		Stage welcome = (Stage) managerLogin.getScene().getWindow();
	    welcome.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Manager Login.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Opens the login page for waiters.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void LoginPageWaiter(ActionEvent event) throws Exception {
		Stage welcome = (Stage) employeeLogin.getScene().getWindow();
	    welcome.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Waiter Login.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
