package application;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class allows the user to search orders according to their number, their
 * table number, their price or the time of order.
 */
public class SearchController {

	/** The combobox with the options to search by. */
	@FXML
	private ComboBox searchByComboBox;

	/** The main window container. */
	@FXML
	private AnchorPane main;

	/** The list of orders contained within the search query. */
	static ObservableList<Order> searchedOrderList = FXCollections.observableArrayList();

	/** The list of existing orders. */
	static ObservableList<Order> orderList = FXCollections.observableArrayList();

	/** The list of options to search by. */
	static ObservableList<String> searchByList = FXCollections.observableArrayList();

	/** The list of options to search time by. */
	static ObservableList<String> whenList = FXCollections.observableArrayList();

	/** The list of options to search price by. */
	static ObservableList<String> muchList = FXCollections.observableArrayList();

	/** The list of table numbers of existing orders. */
	static ObservableList<String> tableList = FXCollections.observableArrayList();

	/** The list of existing order numbers. */
	static ObservableList<String> orderNumberList = FXCollections.observableArrayList();

	/** The chosen search by option. */
	static String searchByChoice;

	/** The choice combobox. */
	static ComboBox choiceBox = new ComboBox();

	/** The query text. */
	static TextField choiceField = new TextField();

	/** The choice listener. */
	static ChangeListener choiceListener;

	/**
	 * This method lays out the search by time comboboxes and query text field.
	 * It then creates a listener for the search by combobox, allowing the
	 * combobox and textbox layout to change depending on the chosen search by
	 * option.
	 */
	public void initiate() {
		searchByList.clear();
		choiceBox.getItems().clear();
		orderList = application.Main.orderList;
		searchByList.addAll("Time", "Price", "Order Number", "Table Number");
		searchByComboBox.getItems().addAll(searchByList);
		searchByComboBox.getSelectionModel().selectFirst();
		searchByChoice = (String) searchByComboBox.getSelectionModel().getSelectedItem();
		whenList.addAll("Before", "After", "Between");
		choiceBox.getItems().addAll(whenList);
		choiceBox.getSelectionModel().selectFirst();
		choiceBox.setLayoutX(25);
		choiceBox.setLayoutY(60);
		choiceField.setPromptText("HH:MM");
		choiceField.setLayoutX(25);
		choiceField.setLayoutY(95);
		main.getChildren().addAll(choiceBox, choiceField);
		choiceListener = new ChangeListener() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (ov.getValue().equals("Between")) {
					choiceField.setPromptText("(HH:MM and HH:MM)");
				} else {
					choiceField.setPromptText("(HH:MM)");
				}
			}
		};
		choiceBox.getSelectionModel().selectedItemProperty().addListener(choiceListener);

		searchByComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				searchByChoice = (String) ov.getValue();
				switch (searchByChoice) {
				case "Time":
					choiceBox.getSelectionModel().selectedItemProperty().removeListener(choiceListener);
					main.getChildren().removeAll(choiceBox, choiceField);
					whenList.clear();
					whenList.addAll("Before", "After", "Between");
					choiceBox.getItems().clear();
					choiceBox.getItems().addAll(whenList);
					choiceBox.getSelectionModel().selectFirst();
					choiceBox.setLayoutX(25);
					choiceBox.setLayoutY(60);
					choiceField.setPromptText("Time (HH:MM)");
					choiceField.setLayoutX(25);
					choiceField.setLayoutY(95);
					main.getChildren().addAll(choiceBox, choiceField);
					choiceListener = new ChangeListener() {
						@Override
						public void changed(ObservableValue ov, Object t, Object t1) {
							if (ov.getValue().equals("Between")) {
								choiceField.setPromptText("(HH:MM and HH:MM)");
							}
						}
					};
					choiceBox.getSelectionModel().selectedItemProperty().addListener(choiceListener);
					break;
				case "Price":
					choiceBox.getSelectionModel().selectedItemProperty().removeListener(choiceListener);
					main.getChildren().removeAll(choiceBox, choiceField);
					muchList.clear();
					muchList.addAll("Greater than", "Less than");
					choiceBox.getItems().clear();
					choiceBox.getItems().addAll(muchList);
					choiceBox.getSelectionModel().selectFirst();
					choiceBox.setLayoutX(25);
					choiceBox.setLayoutY(60);
					choiceField.setPromptText(" Order amount (##.##)");
					choiceField.setLayoutX(25);
					choiceField.setLayoutY(95);
					main.getChildren().addAll(choiceBox, choiceField);
					break;
				case "Order Number":
					choiceBox.getSelectionModel().selectedItemProperty().removeListener(choiceListener);
					main.getChildren().removeAll(choiceBox, choiceField);
					choiceBox.getItems().clear();
					orderNumberList.clear();
					for (Order o : orderList) {
						orderNumberList.add(o.getOrderNumber());
					}
					choiceBox.getItems().addAll(orderNumberList);
					choiceBox.getSelectionModel().selectFirst();
					choiceBox.setLayoutX(25);
					choiceBox.setLayoutY(60);
					main.getChildren().addAll(choiceBox);
					break;
				case "Table Number":
					choiceBox.getSelectionModel().selectedItemProperty().removeListener(choiceListener);
					main.getChildren().removeAll(choiceBox, choiceField);
					choiceBox.getItems().clear();
					tableList.clear();
					for (Order o : orderList) {
						tableList.add(String.valueOf(o.getTableNumber()));
					}
					choiceBox.getItems().addAll(tableList);
					choiceBox.getSelectionModel().selectFirst();
					choiceBox.setLayoutX(25);
					choiceBox.setLayoutY(60);
					main.getChildren().addAll(choiceBox);
					break;
				}
			}
		});

	}

	/**
	 * This method checks the regular expression of the search query and whether
	 * it is null in which case an error is displayed. If there is no error, the
	 * search option of the order is compared to the queried search option and a
	 * list of orders is produced. If the list contains orders the order list
	 * page is produced, otherwise an error is displayed.
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void search(ActionEvent event) throws Exception {
		String query;
		String[] splitQuery;
		switch (searchByChoice) {
		case "Time":
			if (choiceField.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Invalid query");
				alert.setContentText("Your've not entered a search query");
				alert.showAndWait();
			} else {
				Boolean queryMatches;
				switch ((String) choiceBox.getSelectionModel().getSelectedItem()) {
				case "Before":
					queryMatches = choiceField.getText().matches("([0-9]|[0][0-9]|[1][0-9]|[2][0-3])[:][0-5][0-9]");
					if (!queryMatches) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Incorrect Input");
						alert.setContentText("Please input valid arguments");
						alert.showAndWait();
					} else {
						searchedOrderList.clear();
						query = choiceField.getText();
						splitQuery = query.split(":");
						for (Order o : orderList) {
							String orderTime = o.getTimeOfOrder();
							String[] splitOrderTime = orderTime.split(":");
							if (Integer.valueOf(splitOrderTime[0]) < Integer.valueOf(splitQuery[0])) {
								searchedOrderList.add(o);
							} else if (Integer.valueOf(splitOrderTime[0]) == Integer.valueOf(splitQuery[0])) {
								if (Integer.valueOf(splitOrderTime[1]) < Integer.valueOf(splitQuery[1])) {
									searchedOrderList.add(o);
								}
							}
						}
					}
					break;
				case "After":
					queryMatches = choiceField.getText().matches("([0-9]|[0][0-9]|[1][0-9]|[2][0-3])[:][0-5][0-9]");
					if (!queryMatches) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Incorrect Input");
						alert.setContentText("Please input valid arguments");
						alert.showAndWait();
					} else {
						searchedOrderList.clear();
						query = choiceField.getText();
						splitQuery = query.split(":");
						for (Order o : orderList) {
							String orderTime = o.getTimeOfOrder();
							String[] splitOrderTime = orderTime.split(":");
							if (Integer.valueOf(splitOrderTime[0]) > Integer.valueOf(splitQuery[0])) {
								searchedOrderList.add(o);
							} else if (Integer.valueOf(splitOrderTime[0]) == Integer.valueOf(splitQuery[0])) {
								if (Integer.valueOf(splitOrderTime[1]) > Integer.valueOf(splitQuery[1])) {
									searchedOrderList.add(o);
								}
							}
						}
					}
					break;
				case "Between":
					searchedOrderList.clear();
					query = choiceField.getText();
					splitQuery = query.split(" and ");
					queryMatches = splitQuery[0].matches("([0-9]|[0][0-9]|[1][0-9]|[2][0-3])[:][0-5][0-9]");
					if (!queryMatches) {
						queryMatches = splitQuery[1].matches("([0-9]|[0][0-9]|[1][0-9]|[2][0-3])[:][0-5][0-9]");
					}
					if (!queryMatches) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Incorrect Input");
						alert.setContentText("Please input valid arguments");
						alert.showAndWait();
					} else {
						String[] lowerLimit = splitQuery[0].split(":");
						String[] upperLimit = splitQuery[1].split(":");
						for (Order o : orderList) {
							String orderTime = o.getTimeOfOrder();
							String[] splitOrderTime = orderTime.split(":");
							if (Integer.valueOf(splitOrderTime[0]) >= Integer.valueOf(lowerLimit[0])
									&& Integer.valueOf(splitOrderTime[0]) <= Integer.valueOf(upperLimit[0])) {
								if (Integer.valueOf(splitOrderTime[1]) > Integer.valueOf(lowerLimit[1])
										&& Integer.valueOf(splitOrderTime[1]) < Integer.valueOf(upperLimit[1])) {
									searchedOrderList.add(o);
								}
							}
						}
						break;
					}
				}
			}
			break;
		case "Price":
			if (choiceField.getText().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Invalid query");
				alert.setContentText("Your've not entered a search query");
				alert.showAndWait();
			} else {
				switch ((String) choiceBox.getSelectionModel().getSelectedItem()) {
				case "Greater than":
					Boolean queryMatches;
					queryMatches = choiceField.getText().matches("[1-9][0-9]*([.][0-9][0-9])?");
					if (!queryMatches) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Incorrect Input");
						alert.setContentText("Please input valid arguments");
						alert.showAndWait();
					} else {
						searchedOrderList.clear();
						query = choiceField.getText();
						for (Order o : orderList) {
							String orderTotal = o.getTotal();
							if (Double.valueOf(orderTotal) > Double.valueOf(query)) {
								searchedOrderList.add(o);
							}
						}
					}
					break;
				case "Less than":
					queryMatches = choiceField.getText().matches("[1-9][0-9]*([.][0-9][0-9])?");
					if (!queryMatches) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Incorrect Input");
						alert.setContentText("Please input valid arguments");
						alert.showAndWait();
					} else {
						searchedOrderList.clear();
						query = choiceField.getText();
						for (Order o : orderList) {
							String orderTotal = o.getTotal();
							if (Double.valueOf(orderTotal) < Double.valueOf(query)) {
								searchedOrderList.add(o);
							}
						}
						break;
					}
				}
			}
			break;
		case "Order Number":
			searchedOrderList.clear();
			query = (String) choiceBox.getSelectionModel().getSelectedItem();
			for (Order o : orderList) {
				String orderNumber = o.getOrderNumber();
				if (orderNumber.equals(query)) {
					searchedOrderList.add(o);
					break;
				}
			}
			break;
		case "Table Number":
			searchedOrderList.clear();
			query = (String) choiceBox.getSelectionModel().getSelectedItem();
			for (Order o : orderList) {
				String tableNumber = String.valueOf(o.getTableNumber());
				if (tableNumber.equals(query)) {
					searchedOrderList.add(o);
					break;
				}
			}
			break;
		}

		if (searchedOrderList.size() != 0) {
			orderList = null;
			searchByList = null;
			whenList = null;
			muchList = null;
			tableList = null;
			orderNumberList = null;
			searchByChoice = null;
			choiceBox = null;
			choiceField = null;
			choiceListener = null;
			orderList = FXCollections.observableArrayList();
			searchByList = FXCollections.observableArrayList();
			whenList = FXCollections.observableArrayList();
			muchList = FXCollections.observableArrayList();
			tableList = FXCollections.observableArrayList();
			orderNumberList = FXCollections.observableArrayList();
			searchByChoice = "";
			choiceBox = new ComboBox();
			choiceField = new TextField();
			Stage search = (Stage) searchByComboBox.getScene().getWindow();
			search.close();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Order List.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			OrderListController controller = (OrderListController) loader.getController();
			controller.populateList(searchedOrderList);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("No results");
			alert.setContentText("Your query yielded no results");
			alert.showAndWait();
		}
	}

	/**
	 * This method allows the user to go back to the main page.
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void back(ActionEvent event) throws Exception {
		searchedOrderList = null;
		orderList = null;
		searchByList = null;
		whenList = null;
		muchList = null;
		tableList = null;
		orderNumberList = null;
		searchByChoice = null;
		choiceBox = null;
		choiceField = null;
		choiceListener = null;
		searchedOrderList = FXCollections.observableArrayList();
		orderList = FXCollections.observableArrayList();
		searchByList = FXCollections.observableArrayList();
		whenList = FXCollections.observableArrayList();
		muchList = FXCollections.observableArrayList();
		tableList = FXCollections.observableArrayList();
		orderNumberList = FXCollections.observableArrayList();
		searchByChoice = "";
		choiceBox = new ComboBox();
		choiceField = new TextField();
		Stage search = (Stage) main.getScene().getWindow();
		search.close();
		for (Employee e : application.Main.employeeList) {
			if (application.Main.user.equals(e.getFirstName())) {
				if (e.isManager) {
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager Main.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();
					ManagerMainController controller = (ManagerMainController) loader.getController();
					controller.SetLabelText(application.Main.user);
				} else {
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
		}
	}
}
