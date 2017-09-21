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
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class displays a list of searched orders in separate tables and allows only managers to export them to files.
 */
public class OrderListController {
	
	/** The vertical box in which new order tables are placed. */
	@FXML
	private VBox vBox;
	
	/** The home button. */
	@FXML
	private Button home;
	
	/** The main container for components on the page. */
	@FXML
	private AnchorPane main;

	/** The list containing orders contained in the search query. */
	static ObservableList<Order> searchedOrders = FXCollections.observableArrayList();
	
	/** The list containing items ordered in a specific order. */
	ObservableList<ObservableList<OrderItem>> allOrderItems = FXCollections.observableArrayList();
	
	/** The order counter for exported orders. */
	static int numberOfOrder = 0;

	/**
	 * This method creates order tables, fills them with the items contained within and then adds them to the page.
	 *
	 * @param searchedOrderList the list containing orders contained in the search query.
	 */
	public void populateList(ObservableList<Order> searchedOrderList) {
		searchedOrders = searchedOrderList;
		for (Employee e : application.Main.employeeList) {
			if (application.Main.user.equals(e.getFirstName())) {
				if (e.isManager) {
					Button export = new Button("Export Orders");
					export.setLayoutX(25);
					export.setLayoutY(511);
					main.getChildren().add(export);
					export.setOnAction(event -> {
						try {
							exportOrders();
						} catch (Exception exception) {
						}
					});
					break;
				}
			}
		}
		for (Order o : searchedOrderList) {
			ObservableList<OrderItem> orderedItems = FXCollections.observableArrayList();
			AnchorPane a = new AnchorPane();
			a.setMinWidth(200);
			a.setMinHeight(200);
			a.setLayoutX(0);
			a.setLayoutY(0);

			Label orderNumber = new Label();
			orderNumber.setLayoutX(0);
			orderNumber.setLayoutY(175);
			orderNumber.setMinHeight(25);

			Label totalPrice = new Label();
			totalPrice.setLayoutX(370);
			totalPrice.setLayoutY(175);
			totalPrice.setMinHeight(25);

			Label tableNumber = new Label();
			tableNumber.setLayoutX(200);
			tableNumber.setLayoutY(175);
			tableNumber.setMinHeight(25);

			Label currency = new Label("£");
			currency.setLayoutX(362);
			currency.setLayoutY(175);
			currency.setMinHeight(25);

			try {
				String file = "";
				file = o.getFile();

				orderNumber.setText(o.getOrderNumber());
				tableNumber.setText("Table: " + o.getTableNumber());
				totalPrice.setText(o.getTotal());
				BufferedReader in = new BufferedReader(new FileReader(file));
				String dataLine;
				while ((dataLine = in.readLine()) != null) {
					for (int i = 0; i < 3; i += 4) {
						String[] dataValues = dataLine.split(",");
						orderedItems.add(
								new OrderItem(dataValues[i], dataValues[i + 1], dataValues[i + 2], dataValues[i + 3]));
					}
				}
				in.close();
			} catch (Exception e) {
			}

			allOrderItems.add(orderedItems);

			TableView order = new TableView();

			order.setLayoutX(0);
			order.setLayoutY(0);
			order.setPrefWidth(535);
			order.setPrefHeight(175);

			TableColumn orderItemCol = new TableColumn("Item");
			orderItemCol.setMinWidth(100);
			orderItemCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("orderItem"));
			orderItemCol.setCellFactory(TextFieldTableCell.forTableColumn());

			TableColumn quantityCol = new TableColumn("Quantity");
			quantityCol.setMinWidth(60);
			quantityCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("quantity"));
			quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());

			TableColumn commentCol = new TableColumn("Special Request");
			commentCol.setMinWidth(260);
			commentCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("comment"));
			commentCol.setCellFactory(TextFieldTableCell.forTableColumn());

			TableColumn priceCol = new TableColumn("Price");
			priceCol.setMinWidth(45);
			priceCol.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("price"));
			priceCol.setCellFactory(TextFieldTableCell.forTableColumn());

			order.setItems(orderedItems);
			order.getColumns().addAll(orderItemCol, quantityCol, commentCol, priceCol);

			a.getChildren().addAll(order, orderNumber, tableNumber, totalPrice, currency);
			vBox.getChildren().add(a);
			orderedItems = null;
		}
	}

	/**
	 * This method exports orders to their own separate files.
	 *
	 * @throws Exception the exception
	 */
	public void exportOrders() throws Exception {
		for (Order o : searchedOrders) {
			numberOfOrder++;
			String s = System.getProperty("user.dir");
			String stamp = "\\Exported Order " + String.valueOf(numberOfOrder);
			File dir = new File(s);
			dir.mkdirs();
			Writer writer = null;
			try {
				File permfile = new File(dir, stamp + ".csv");
				permfile.createNewFile();
				writer = new BufferedWriter(new FileWriter(permfile));
				for (OrderItem orderItem : allOrderItems.get(searchedOrders.indexOf(o))) {
					String text = orderItem.getOrderItem() + "," + orderItem.getQuantity() + ","
							+ orderItem.getComment() + "," + orderItem.getPrice() + "\n";
					writer.write(text);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				writer.flush();
				writer.close();
			}
		}
		Writer writer = null;
		try {
			File permfile = new File(System.getProperty("user.dir") + "\\Exported Orders.csv");
			permfile.createNewFile();
			writer = new BufferedWriter(new FileWriter(permfile));
			for (Order o : searchedOrders) {
				String [] order = o.getOrderNumber().split("# ");
				o.setFile(System.getProperty("user.dir") + "\\Exported Order " + order[1] + ".csv");
				String text = o.getFile() + "," + o.getTimeOfOrder() + "," + o.getTableNumber()
						+ "," + o.getTotal() + "," + o.getOrderNumber() + "," + o.getEmployee() + "\n";
				o.getTotal();
				writer.write(text);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Export Successful");
		alert.setContentText("The orders have been exported");
		alert.showAndWait();
	}

	/**
	 * This method allows the user to return to the search page.
	 *
	 * @throws Exception the exception
	 */
	public void goHome() throws Exception {
		numberOfOrder = 0;
		searchedOrders.clear();
		Stage orderList = (Stage) home.getScene().getWindow();
		orderList.close();
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

}
