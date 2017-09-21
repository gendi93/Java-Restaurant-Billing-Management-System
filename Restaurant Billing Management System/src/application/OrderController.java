package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class creates a new order object as long as there are ordered items.
 * When the order is saved it is appended to the list of orders and a file is
 * created specifically for the order storing its content. The waiter can at any
 * point access the same order and edit, add or delete its items.
 */
public class OrderController {

	/** The order table. */
	@FXML
	private TableView order;

	/** The order number. */
	@FXML
	private Label orderNumber;

	/** The total price. */
	@FXML
	private Label totalPrice;

	/** The table number. */
	@FXML
	private Label tableNumber;

	/** The special request textbox. */
	@FXML
	private TextField commentBox;

	/** The combo box holding menu items. */
	@FXML
	private ComboBox comboBoxItems;

	/** Variable for holding the index of the selected item. */
	static int itemIndex = -1;

	/** Variable for holding the index of the duplicate item in the order. */
	static int orderItemIndex;

	/** The list storing order items read from the order file*/
	static ObservableList<OrderItem> newData = FXCollections.observableArrayList();

	/** The list storing updated order items from the current order*/
	static ObservableList<OrderItem> newerData = FXCollections.observableArrayList();

	/** The list storing menu items read from the menu file*/
	static ObservableList<Item> importedData = FXCollections.observableArrayList();

	/** The list storing the name of the items read from the order file*/
	static ObservableList<String> importedItemStrings = FXCollections.observableArrayList();

	/** The list storing all the existing orders*/
	static ObservableList<Order> orders = FXCollections.observableArrayList();

	/**
	 * Gets the names of the items present in the menu.
	 */
	public void getItems() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\Menu.csv"));
			String dataLine;
			importedData.clear();
			importedItemStrings.clear();
			while ((dataLine = in.readLine()) != null) {
				for (int i = 0; i < 3; i += 3) {
					String[] dataValues = dataLine.split(",");
					importedData.add(new Item(dataValues[i], dataValues[i + 1], dataValues[i + 2]));
				}
			}
			in.close();
		} catch (Exception e) {
		} finally {
			for (Item i : importedData) {
				importedItemStrings.add(i.getItem());
			}
		}
	}

	/**
	 * Exits back to the waiter main page after saving the current data.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void exit() throws Exception {
		if (newerData.size() == 0) {
			String tableLabel = tableNumber.getText();
			String[] tableArray = tableLabel.split("Table: ");
			int tableNum = Integer.parseInt(tableArray[1]);
			String file = "";
			orders = application.Main.orderList;
			for (Order o : application.Main.orderList) {
				if (o.getTableNumber() == tableNum) {
					file = o.getFile();
					application.Main.orderList.remove(o);
					orders.remove(o);
					application.Main.numberOfOrder--;
					break;
				}
			}
			Files.delete(Paths.get(file));
		}
		String tableLabel = tableNumber.getText();
		String[] tableArray = tableLabel.split("Table: ");
		int tableNum = Integer.parseInt(tableArray[1]);
		String employee = "";
		application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
		for (Employee e : application.Main.employeeList) {
			if (application.Main.user.equals(e.getFirstName())) {
				employee = e.getFirstName() + " " + e.getLastName();
				break;
			}
		}
		for (Order o : application.Main.orderList) {
			if (o.getTableNumber() == tableNum) {
				o.setEmployee(employee);
				int index = application.Main.orderList.indexOf(o);
				orders.get(index).setEmployee(employee);
				break;
			}
		}
		writeOrderList();
		Stage order = (Stage) orderNumber.getScene().getWindow();
		order.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Table View.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		TableViewController controller = (TableViewController) loader.getController();
		controller.placeOrder(getTableNumbers());
	}

	/**
	 * Gets the table numbers of all existing orders.
	 *
	 * @return the table numbers
	 */
	public ArrayList<Integer> getTableNumbers() {
		ArrayList<Integer> tables = new ArrayList<Integer>();
		for (Order o : orders) {
			tables.add(o.getTableNumber());
		}
		return tables;
	}

	/**
	 * This method reads the order items from existing order files.
	 *
	 * @param tableNum
	 *            the table num
	 */
	public void readOrder(int tableNum) {
		orders = application.Main.orderList;
		getItems();
		newerData.clear();
		comboBoxItems.getItems().clear();
		comboBoxItems.getItems().addAll(importedItemStrings);
		comboBoxItems.getSelectionModel().selectFirst();
		Boolean orderExists = false;
		int orderIndex = -1;
		for (Order o : orders) {
			if (o.getTableNumber() == tableNum) {
				orderExists = true;
				orderIndex = orders.indexOf(o);
			}
		}
		if (!orderExists) {
			try {
				application.Main.numberOfOrder++;
				String s = System.getProperty("user.dir");
				String stamp = "Order " + String.valueOf(application.Main.numberOfOrder);
				File dir = new File(s);
				dir.mkdirs();
				String file = dir + "\\" + stamp + ".csv";
				LocalDateTime currentTime = LocalDateTime.now();
				int hour = currentTime.getHour();
				int minute = currentTime.getMinute();
				int second = currentTime.getSecond();
				String orderTime = (hour + ":" + minute + ":" + second);
				orderNumber.setText("# " + String.valueOf(application.Main.numberOfOrder));
				tableNumber.setText("Table: " + tableNum);
				totalPrice.setText("0.00");

				String employee = "";
				application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
				for (Employee e : application.Main.employeeList) {
					if (application.Main.user.equals(e.getFirstName())) {
						employee = e.getFirstName() + " " + e.getLastName();
						break;
					}
				}

				orders.add(new Order(file, orderTime, tableNum, totalPrice.getText(), orderNumber.getText(), employee));
				writeOrderList();
				File permfile = new File(dir, stamp + ".csv");
				permfile.createNewFile();
			} catch (Exception e) {
			}
		} else {
			try {
				String file = "";
				for (Order o : orders) {
					if (o.getTableNumber() == tableNum) {
						file = o.getFile();
					}
				}
				Order currentOrder = orders.get(orderIndex);
				orderNumber.setText(currentOrder.getOrderNumber());
				tableNumber.setText("Table: " + tableNum);
				totalPrice.setText(String.format("%.2f", Double.parseDouble(currentOrder.getTotal())));
				BufferedReader in = new BufferedReader(new FileReader(file));
				String dataLine;
				newData.clear();
				newerData.clear();
				while ((dataLine = in.readLine()) != null) {
					for (int i = 0; i < 3; i += 4) {
						String[] dataValues = dataLine.split(",");
						newData.add(
								new OrderItem(dataValues[i], dataValues[i + 1], dataValues[i + 2], dataValues[i + 3]));
					}
				}
				in.close();
			} catch (Exception e) {
			} finally {
				populateOrder(newData);
			}
		}
	}

	/**
	 * This method deletes the current order after asking for confirmation.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void deleteOrder() throws Exception {
		ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
		ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to continue?", yes, no);
		alert.setHeaderText("You are about to delete this order.");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == yes) {
			for (Order o : orders) {
				if (orderNumber.getText().equals(o.getOrderNumber())) {
					Files.delete(Paths.get(o.getFile()));
					orders.remove(o);
					writeOrderList();
					Stage order = (Stage) totalPrice.getScene().getWindow();
					order.close();
					exit();
					break;
				}
			}
		}
	}

	/**
	 * This method fills the order table with the items read from the order file.
	 *
	 * @param orderData
	 *            the order data
	 */
	public void populateOrder(ObservableList<OrderItem> orderData) {
		order.setEditable(true);

		TableColumn orderItemCol = new TableColumn("Item");
		orderItemCol.setMinWidth(100);
		orderItemCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("orderItem"));
		orderItemCol.setCellFactory(TextFieldTableCell.forTableColumn());
		orderItemCol.setEditable(false);

		TableColumn quantityCol = new TableColumn("Quantity");
		quantityCol.setMinWidth(60);
		quantityCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("quantity"));
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
		quantityCol.setOnEditCommit(new EventHandler<CellEditEvent<OrderItem, String>>() {
			public void handle(CellEditEvent<OrderItem, String> t) {
				Boolean quantityMatches = t.getNewValue().matches("[1-9][0-9]*");
				if (!quantityMatches) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Incorrect Input");
					alert.setContentText("Please input valid arguments");
					alert.showAndWait();
				} else {
					OrderItem orderItem = (OrderItem) t.getTableView().getItems().get(t.getTablePosition().getRow());
					double oldPrice = Double.parseDouble(orderItem.getPrice())
							* Integer.parseInt(orderItem.getQuantity());
					orderItem.setQuantity(t.getNewValue());
					double newPrice = Double.parseDouble(orderItem.getPrice())
							* Integer.parseInt(orderItem.getQuantity());
					totalPrice.setText(
							String.format("%.2f", Double.parseDouble(totalPrice.getText()) - oldPrice + newPrice));
					try {
						String tableLabel = tableNumber.getText();
						String[] tableArray = tableLabel.split("Table: ");
						int tableNum = Integer.parseInt(tableArray[1]);
						String file = "";
						for (Order o : orders) {
							if (o.getTableNumber() == tableNum) {
								file = o.getFile();
							}
						}
						writeOrder(file);
					} catch (Exception e) {
					}
				}
			}
		});

		TableColumn commentCol = new TableColumn("Special Request");
		commentCol.setMinWidth(260);
		commentCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("comment"));
		commentCol.setCellFactory(TextFieldTableCell.forTableColumn());
		commentCol.setOnEditCommit(new EventHandler<CellEditEvent<OrderItem, String>>() {
			public void handle(CellEditEvent<OrderItem, String> t) {
				((OrderItem) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setComment(t.getNewValue());
				try {
					String tableLabel = tableNumber.getText();
					String[] tableArray = tableLabel.split("Table: ");
					int tableNum = Integer.parseInt(tableArray[1]);
					String file = "";
					for (Order o : orders) {
						if (o.getTableNumber() == tableNum) {
							file = o.getFile();
						}
					}
					writeOrder(file);
				} catch (Exception e) {
				}
			}
		});

		TableColumn priceCol = new TableColumn("Price");
		priceCol.setMinWidth(45);
		priceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("price"));
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		priceCol.setEditable(false);

		TableColumn<OrderItem, OrderItem> deleteCol = new TableColumn<>("");
		deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		deleteCol.setCellFactory(param -> new TableCell<OrderItem, OrderItem>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(OrderItem orderItem, boolean empty) {
				super.updateItem(orderItem, empty);
				if (orderItem == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					double oldPrice = Double.parseDouble(orderItem.getPrice())
							* Integer.parseInt(orderItem.getQuantity());
					totalPrice.setText(String.format("%.2f", Double.parseDouble(totalPrice.getText()) - oldPrice));
					getTableView().getItems().remove(orderItem);
					newerData.remove(orderItem);
					try {
						String tableLabel = tableNumber.getText();
						String[] tableArray = tableLabel.split("Table: ");
						int tableNum = Integer.parseInt(tableArray[1]);
						String file = "";
						for (Order o : orders) {
							if (o.getTableNumber() == tableNum) {
								file = o.getFile();
							}
						}
						writeOrder(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
		for (OrderItem o : orderData) {
			newerData.add(o);
		}

		order.setItems(orderData);
		order.getColumns().addAll(orderItemCol, quantityCol, commentCol, priceCol, deleteCol);
	}

	/**
	 * This method allows the user to add items to the order.
	 *
	 * @param event
	 *            the event
	 * @throws Exception
	 *             the exception
	 */
	public void addItem(ActionEvent event) throws Exception {
		getItems();
		String tableLabel = tableNumber.getText();
		String[] tableArray = tableLabel.split("Table: ");
		int tableNum = Integer.parseInt(tableArray[1]);
		String file = "";
		for (Order o : orders) {
			if (o.getTableNumber() == tableNum) {
				file = o.getFile();
			}
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String dataLine;
			newData.clear();
			newerData.clear();
			while ((dataLine = in.readLine()) != null) {
				for (int i = 0; i < 3; i += 4) {
					String[] dataValues = dataLine.split(",");
					newData.add(new OrderItem(dataValues[i], dataValues[i + 1], dataValues[i + 2], dataValues[i + 3]));
				}
			}
			in.close();
		} catch (Exception e) {
		} finally {
			populateOrder(newData);
		}
		String newItemString = (String) comboBoxItems.getSelectionModel().getSelectedItem();
		for (int i = 0; i < importedData.size(); i++) {
			if (importedData.get(i).getItem().equals(newItemString)) {
				itemIndex = i;
			}
		}
		orderItemIndex = -1;
		for (int i = 0; i < newerData.size(); i++) {
			if (newerData.get(i).getOrderItem().equals(newItemString) && newerData.get(i).getComment().equals("")) {
				orderItemIndex = i;
			}
		}
		if (orderItemIndex != -1) {
			if (commentBox.getText().equals("")) {
				OrderItem o = newerData.get(orderItemIndex);
				o.setQuantity(String.valueOf(Integer.parseInt(o.getQuantity()) + 1));
				Item newItem = importedData.get(itemIndex);
				double newPrice = Double.parseDouble(newItem.getPrice());
				totalPrice.setText(String.format("%.2f", Double.parseDouble(totalPrice.getText()) + newPrice));
			} else {
				Item newItem = importedData.get(itemIndex);
				double newPrice = Double.parseDouble(newItem.getPrice());
				totalPrice.setText(String.format("%.2f", Double.parseDouble(totalPrice.getText()) + newPrice));
				newerData.add(new OrderItem(newItem.getItem(), "1", commentBox.getText(), newItem.getPrice()));
				order.getItems().add(new OrderItem(newItem.getItem(), "1", commentBox.getText(), newItem.getPrice()));
				commentBox.clear();
			}
		} else {
			Item newItem = importedData.get(itemIndex);
			double newPrice = Double.parseDouble(newItem.getPrice());
			totalPrice.setText(String.format("%.2f", Double.parseDouble(totalPrice.getText()) + newPrice));
			newerData.add(new OrderItem(newItem.getItem(), "1", commentBox.getText(), newItem.getPrice()));
			order.getItems().add(new OrderItem(newItem.getItem(), "1", commentBox.getText(), newItem.getPrice()));
			commentBox.clear();
		}
		writeOrder(file);
	}

	/**
	 * This method writes the current order items to the order file.
	 *
	 * @param file
	 *            the file
	 * @throws Exception
	 *             the exception
	 */
	public void writeOrder(String file) throws Exception {
		Writer writer = null;
		for (Order o : orders) {
			if (o.getFile().equals(file)) {
				o.setTotal(totalPrice.getText());
			}
		}
		try {
			Files.delete(Paths.get(file));
			File f = new File(file);
			f.createNewFile();
			writer = new BufferedWriter(new FileWriter(f));
			for (OrderItem orderItem : newerData) {
				String text = orderItem.getOrderItem() + "," + orderItem.getQuantity() + "," + orderItem.getComment()
						+ "," + orderItem.getPrice() + "\n";
				writer.write(text);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}
	}

	/**
	 * This method updates the list of orders file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void writeOrderList() throws Exception {
		Writer writer = null;
		try {
			Files.delete(Paths.get(System.getProperty("user.dir") + "\\Orders.csv"));
			File f = new File(System.getProperty("user.dir") + "\\Orders.csv");
			f.createNewFile();
			writer = new BufferedWriter(new FileWriter(f));
			for (Order o : orders) {
				String text = o.getFile() + "," + o.getTimeOfOrder() + "," + o.getTableNumber() + "," + o.getTotal()
						+ "," + o.getOrderNumber() + "," + o.getEmployee() + "\n";
				o.getTotal();
				writer.write(text);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
			application.Main.populateList(System.getProperty("user.dir") + "\\Orders.csv", "Orders");
		}
	}
}
