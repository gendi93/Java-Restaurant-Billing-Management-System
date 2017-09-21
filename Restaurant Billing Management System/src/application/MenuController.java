package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import application.Item;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * This class allows the manager to edit, add or delete menu items and then saves these items to the menu file.
 */
public class MenuController {

	/** The item table. */
	@FXML
	private TableView itemTable;
	
	/** Textfield for adding menu item names.*/
	@FXML
	private TextField addItem;
	
	/** Textfield for adding menu item descriptions.*/
	@FXML
	private TextField addDescription;
	
	/** Textfield for adding menu item prices. */
	@FXML
	private TextField addPrice;
	
	/** List for storing the updated menu items. */
	static ObservableList<Item> newData = FXCollections.observableArrayList();
	
	/** List for storing the menu items read from the file. */
	static ObservableList<Item> importedData = FXCollections.observableArrayList();

	/**
	 * Adds menu items after checking they comply with the regular expressions.
	 *
	 * @param event the event
	 */
	public void add(ActionEvent event) {
		Boolean itemMatches = addItem.getText().matches("[A-Z][a-z ,.0-9%!&?A-Z]*");
		Boolean descriptionMatches = addDescription.getText().matches("[A-Z][a-z ,.0-9%!&?A-Z]*");
		Boolean priceMatches = addPrice.getText().matches("(([1-9][0-9]*((.)[0-9][0-9])?|((0.)[0-9][0-9])))");
		if (!itemMatches || !descriptionMatches || !priceMatches) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Incorrect Input");
			alert.setContentText("Please input valid arguments");
			alert.showAndWait();
		} else {
			itemTable.getItems().add(new Item(addItem.getText(), addDescription.getText(), addPrice.getText()));
			newData.add(new Item(addItem.getText(), addDescription.getText(), addPrice.getText()));
			addItem.clear();
			addDescription.clear();
			addPrice.clear();
		}
	}

	/**
	 * This method fills the table with the menu items that have been read from the menu file.
	 *
	 * @param data The list of menu items from the menu file.
	 */
	public void populateMenu(ObservableList<Item> data) {
		itemTable.setEditable(true);

		TableColumn itemCol = new TableColumn("Item");
		itemCol.setMinWidth(100);
		itemCol.setCellValueFactory(new PropertyValueFactory<Item, String>("item"));
		itemCol.setCellFactory(TextFieldTableCell.forTableColumn());
		itemCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
			public void handle(CellEditEvent<Item, String> t) {
				((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setItem(t.getNewValue());
			}
		});

		TableColumn descriptionCol = new TableColumn("Description");
		descriptionCol.setMinWidth(320);
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));
		descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		descriptionCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
			public void handle(CellEditEvent<Item, String> t) {
				((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescription(t.getNewValue());
			}
		});

		TableColumn priceCol = new TableColumn("Price");
		priceCol.setMinWidth(45);
		priceCol.setCellValueFactory(new PropertyValueFactory<Item, String>("price"));
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		priceCol.setOnEditCommit(new EventHandler<CellEditEvent<Item, String>>() {
			public void handle(CellEditEvent<Item, String> t) {
				((Item) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice(t.getNewValue());
			}
		});

		TableColumn<Item, Item> deleteCol = new TableColumn<>("");
		deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		deleteCol.setCellFactory(param -> new TableCell<Item, Item>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(Item item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					getTableView().getItems().remove(item);
					newData.remove(item);
				});
			}
		});

		for (Item i : data) {
			newData.add(i);
		}
		
		itemTable.setItems(data);
		itemTable.getColumns().addAll(itemCol, descriptionCol, priceCol, deleteCol);
	}

	/**
	 * Go back to the main page.
	 *
	 * @param event the event
	 * @throws Exception the exception
	 */
	public void goHome(ActionEvent event) throws Exception {
		writeExcel();
		Stage managerLogin = (Stage) itemTable.getScene().getWindow();
		managerLogin.close();
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

	/**
	 * Write the updated menu items to the menu file.
	 *
	 * @throws Exception the exception
	 */
	public void writeExcel() throws Exception {
		Writer writer = null;
		try {
			File file = new File(System.getProperty("user.dir") + "\\Menu.csv");
			if (file.exists()) {
				Files.delete(Paths.get(System.getProperty("user.dir") + "\\Menu.csv"));
			}
			writer = new BufferedWriter(new FileWriter(file));
			for (Item item : newData) {
				String text = item.getItem() + "," + item.getDescription() + "," + item.getPrice() + "\n";
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
	 * Read the existing menu items from the menu file.
	 *
	 * @throws Exception the exception
	 */
	public void readExcel() throws Exception {
		try {
			BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\Menu.csv"));
			String dataLine;
			importedData.clear();
			newData.clear();
			while ((dataLine = in.readLine()) != null) {
				for (int i = 0; i < 3; i += 3) {
					String[] dataValues = dataLine.split(",");
					importedData.add(new Item(dataValues[i], dataValues[i + 1], dataValues[i + 2]));
				}
			}
			in.close();
		} catch (Exception e) {
		} finally {
			populateMenu(importedData);
		}
	}
}