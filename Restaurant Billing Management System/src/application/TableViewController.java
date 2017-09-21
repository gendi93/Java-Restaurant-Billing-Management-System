package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class TableViewController.
 */
public class TableViewController {
	
	/** The home button. */
	@FXML
	private Button home;
	
	/** Table 1. */
	@FXML
	private Rectangle table1;
	
	/** Table 2. */
	@FXML
	private Rectangle table2;
	
	/** Table 3. */
	@FXML
	private Rectangle table3;
	
	/** Table 4. */
	@FXML
	private Rectangle table4;
	
	/** Table 5. */
	@FXML
	private Rectangle table5;
	
	/** Table 6. */
	@FXML
	private Rectangle table6;
	
	/** Table 7. */
	@FXML
	private Rectangle table7;
	
	/** Table 8. */
	@FXML
	private Rectangle table8;
	
	/** Table 9. */
	@FXML
	private Rectangle table9;
	
	/** Table 10. */
	@FXML
	private Rectangle table10;
	
	/** Table 11. */
	@FXML
	private Rectangle table11;
	
	/** The table number. */
	static int tableNumber;

	/**
	 * This method opens the order page and passes the clicked table number to it.
	 *
	 * @throws Exception the exception
	 */
	public void openOrder() throws Exception {
		Stage tableView = (Stage) home.getScene().getWindow();
		tableView.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		OrderController controller = (OrderController) loader.getController();
		controller.readOrder(tableNumber);
	}

	/**
	 * This method assigns the table number variable to 1.
	 *
	 * @throws Exception the exception
	 */
	public void table1() throws Exception {
		tableNumber = 1;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 2.
	 *
	 * @throws Exception the exception
	 */
	public void table2() throws Exception {
		tableNumber = 2;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 3.
	 *
	 * @throws Exception the exception
	 */
	public void table3() throws Exception {
		tableNumber = 3;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 4.
	 *
	 * @throws Exception the exception
	 */
	public void table4() throws Exception {
		tableNumber = 4;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 5.
	 *
	 * @throws Exception the exception
	 */
	public void table5() throws Exception {
		tableNumber = 5;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 6.
	 *
	 * @throws Exception the exception
	 */
	public void table6() throws Exception {
		tableNumber = 6;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 7.
	 *
	 * @throws Exception the exception
	 */
	public void table7() throws Exception {
		tableNumber = 7;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 8.
	 *
	 * @throws Exception the exception
	 */
	public void table8() throws Exception {
		tableNumber = 8;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 9.
	 *
	 * @throws Exception the exception
	 */
	public void table9() throws Exception {
		tableNumber = 9;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 10.
	 *
	 * @throws Exception the exception
	 */
	public void table10() throws Exception {
		tableNumber = 10;
		openOrder();
	}

	/**
	 * This method assigns the table number variable to 11.
	 *
	 * @throws Exception the exception
	 */
	public void table11() throws Exception {
		tableNumber = 11;
		openOrder();
	}

	/**
	 * This method looks up the table numbers of the existing orders and changes those tables' colours to red.
	 *
	 * @param tables the list of existing table numbers
	 */
	public void placeOrder(ArrayList<Integer> tables) {
		for (int i : tables) {
			switch (i) {
			case 1:
				table1.setFill(Color.RED);
				break;
			case 2:
				table2.setFill(Color.RED);
				break;
			case 3:
				table3.setFill(Color.RED);
				break;
			case 4:
				table4.setFill(Color.RED);
				break;
			case 5:
				table5.setFill(Color.RED);
				break;
			case 6:
				table6.setFill(Color.RED);
				break;
			case 7:
				table7.setFill(Color.RED);
				break;
			case 8:
				table8.setFill(Color.RED);
				break;
			case 9:
				table9.setFill(Color.RED);
				break;
			case 10:
				table10.setFill(Color.RED);
				break;
			case 11:
				table11.setFill(Color.RED);
				break;
			}
		}
	}
	
	/**
	 * This method takes the user back to the main page.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void goHome(ActionEvent event) throws Exception {
		Stage tableView = (Stage) home.getScene().getWindow();
		tableView.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Waiter Main.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		WaiterMainController controller = (WaiterMainController) loader.getController();
		controller.SetLabelText(application.Main.user);
	}
}
