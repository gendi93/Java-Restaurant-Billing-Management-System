package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class produces a list of existing orders for managers to view employee
 * activities.
 */
public class OrderTableController {

	/** The order table. */
	@FXML
	private TableView orderTable;

	/**
	 * The list of existing orders only with information relevant to managers.
	 */
	static ObservableList<EmployeeOrder> orders = FXCollections.observableArrayList();

	/** The list of existing orders with all relevant information. */
	static ObservableList<Order> orders2 = FXCollections.observableArrayList();

	/**
	 * This method reads relevant information from the existing orders and then
	 * fills the table.
	 */
	public void initiate() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\Orders.csv"));
			String dataLine;
			orders.clear();
			while ((dataLine = in.readLine()) != null) {
				for (int i = 0; i < 3; i += 4) {
					String[] dataValues = dataLine.split(",");
					orders.add(new EmployeeOrder(dataValues[i + 4], dataValues[i + 2], dataValues[i + 5],
							dataValues[i + 3]));
				}
			}
			in.close();
		} catch (Exception e) {
		}

		TableColumn orderNumberCol = new TableColumn("Order Number");
		orderNumberCol.setMinWidth(100);
		orderNumberCol.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumber"));
		orderNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn tableNumberCol = new TableColumn("Table Number");
		tableNumberCol.setMinWidth(60);
		tableNumberCol.setCellValueFactory(new PropertyValueFactory<Order, String>("tableNumber"));
		tableNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn employeeCol = new TableColumn("Employee");
		employeeCol.setMinWidth(260);
		employeeCol.setCellValueFactory(new PropertyValueFactory<Order, String>("employee"));
		employeeCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn priceCol = new TableColumn("Sum Total");
		priceCol.setMinWidth(45);
		priceCol.setCellValueFactory(new PropertyValueFactory<Order, String>("total"));
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());

		orderTable.setItems(orders);
		orderTable.getColumns().addAll(orderNumberCol, tableNumberCol, employeeCol, priceCol);
	}

	/**
	 * This method opens a file chooser and allows the user to select a file to
	 * read orders from.
	 *
	 * @param event the event
	 * @throws Exception
	 *             the exception
	 */
	public void importOrders(ActionEvent event) throws Exception {
		Stage orders = (Stage) orderTable.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File dir = new File(System.getProperty("user.dir"));
		fileChooser.setInitialDirectory(dir);
		File file = fileChooser.showOpenDialog(orders);
		if (file != null) {
			readOrders(file);
		}
	}

	/**
	 * This method reads information from the existing orders in the specified
	 * file.
	 *
	 * @param file
	 *            the file
	 * @throws Exception
	 *             the exception
	 */
	public void readOrders(File file) throws Exception {
		orders.clear();
		orders2.clear();
		String path = file.getAbsolutePath();
		File f = new File(System.getProperty("user.dir") + "\\Orders.csv");
		String p = f.getAbsolutePath();
		try {
			BufferedReader in = new BufferedReader(new FileReader(p));
			String dataLine;
			while ((dataLine = in.readLine()) != null) {
				for (int i = 0; i < 3; i += 4) {
					String[] dataValues = dataLine.split(",");
					orders.add(new EmployeeOrder(dataValues[i + 4], dataValues[i + 2], dataValues[i + 5],
							dataValues[i + 3]));
					orders2.add(new Order(dataValues[i], dataValues[i + 1], Integer.parseInt(dataValues[i + 2]),
							dataValues[i + 3], dataValues[i + 4], dataValues[i + 5]));
					application.Main.orderList
							.add(new Order(dataValues[i], dataValues[i + 1], Integer.parseInt(dataValues[i + 2]),
									dataValues[i + 3], dataValues[i + 4], dataValues[i + 5]));
				}
			}
			in.close();
		} catch (Exception e) {
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String dataLine;
			while ((dataLine = in.readLine()) != null) {
				application.Main.numberOfOrder++;
				for (int i = 0; i < 3; i += 4) {
					String[] dataValues = dataLine.split(",");
					orders.add(new EmployeeOrder("# " + String.valueOf(application.Main.numberOfOrder), dataValues[i + 2], dataValues[i + 5],
							dataValues[i + 3]));
					orders2.add(new Order(dataValues[i], dataValues[i + 1], Integer.parseInt(dataValues[i + 2]),
							dataValues[i + 3], "# " + String.valueOf(application.Main.numberOfOrder), dataValues[i + 5]));
					application.Main.orderList
							.add(new Order(dataValues[i], dataValues[i + 1], Integer.parseInt(dataValues[i + 2]),
									dataValues[i + 3], dataValues[i + 4], dataValues[i + 5]));
				}
			}
			in.close();
		} catch (Exception e) {
		}
		application.Main.orderList = orders2;
		Writer writer = null;
		try {
			Files.delete(Paths.get(System.getProperty("user.dir") + "\\Orders.csv"));
			File fl = new File(System.getProperty("user.dir") + "\\Orders.csv");
			fl.createNewFile();
			writer = new BufferedWriter(new FileWriter(fl));
			for (Order o : orders2) {
				String text = o.getFile() + "," + o.getTimeOfOrder() + "," + o.getTableNumber() + "," + o.getTotal()
						+ "," + o.getOrderNumber() + "," + o.getEmployee() + "\n";
				writer.write(text);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
			application.Main.populateList(System.getProperty("user.dir") + "\\Orders.csv", "Orders");
		}

		for (Order o : orders2) {
			Writer w = null;
			try {
				ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();
				String fi = null;
				try {
					fi = o.getFile();
					BufferedReader in = new BufferedReader(new FileReader(fi));
					String dataLine;
					while ((dataLine = in.readLine()) != null) {
						for (int i = 0; i < 3; i += 4) {
							String[] dataValues = dataLine.split(",");
							orderItems.add(
									new OrderItem(dataValues[i], dataValues[i + 1], dataValues[i + 2], dataValues[i + 3]));
						}
					}
					in.close();
				} catch (Exception e) {
				}
				
				Files.delete(Paths.get(fi));
				String [] order = o.getOrderNumber().split("# ");
				File fi2 = new File(System.getProperty("user.dir") + "\\Order " + order[1] + ".csv");
				fi2.createNewFile();
				w = new BufferedWriter(new FileWriter(fi2));
				
				for (OrderItem orderItem : orderItems) {
					String text = orderItem.getOrderItem() + "," + orderItem.getQuantity() + "," + orderItem.getComment()
							+ "," + orderItem.getPrice() + "\n";
					w.write(text);
				}
				o.setFile(fi2.getAbsolutePath());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			w.close();
		}
		
		
		TableColumn orderNumberCol = new TableColumn("Order Number");
		orderNumberCol.setMinWidth(100);
		orderNumberCol.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumber"));
		orderNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn tableNumberCol = new TableColumn("Table Number");
		tableNumberCol.setMinWidth(60);
		tableNumberCol.setCellValueFactory(new PropertyValueFactory<Order, String>("tableNumber"));
		tableNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn employeeCol = new TableColumn("Employee");
		employeeCol.setMinWidth(260);
		employeeCol.setCellValueFactory(new PropertyValueFactory<Order, String>("employee"));
		employeeCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn priceCol = new TableColumn("Sum Total");
		priceCol.setMinWidth(45);
		priceCol.setCellValueFactory(new PropertyValueFactory<Order, String>("total"));
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());

		orderTable.setItems(orders);
		orderTable.getColumns().addAll(orderNumberCol, tableNumberCol, employeeCol, priceCol);
	}

	/**
	 * This method allows the user to go back to the manager main page.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void goHome() throws Exception {
		Stage orders = (Stage) orderTable.getScene().getWindow();
		orders.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager Main.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		ManagerMainController controller = (ManagerMainController) loader.getController();
		controller.SetLabelText("user");
	}
}