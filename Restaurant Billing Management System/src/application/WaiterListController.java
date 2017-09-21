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

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
 * This class displays the employees in the restaurant and allows managers to edit, add or delete employees.
 */
public class WaiterListController {
	
	/** The first name textbox. */
	@FXML
	private TextField firstNameText;
	
	/** The last name textbox. */
	@FXML
	private TextField lastNameText;
	
	/** The mobile number textbox. */
	@FXML
	private TextField mobileNumText;
	
	/** The salary textbox. */
	@FXML
	private TextField salaryText;
	
	/** The manager checkbox. */
	@FXML
	private CheckBox managerCheck;
	
	/** The employee table. */
	@FXML
	private TableView employeeTable;

	/** The list containing all the employees in the restaurant. */
	static ObservableList<Employee> employeeList = FXCollections.observableArrayList();

	/**
	 * This method adds the employee to the employee table and then saves this to the employees file.
	 *
	 * @throws Exception the exception
	 */
	public void addEmployee() throws Exception {
		Boolean firstNameMatches = firstNameText.getText().matches("[A-Z][a-zA-Z]*");
		Boolean lastNameMatches = lastNameText.getText().matches("[A-Z][a-zA-Z]*");
		Boolean mobileNumMatches = mobileNumText.getText()
				.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
		Boolean salaryMatches = salaryText.getText().matches("[1-9][0-9]*");
		if (!firstNameMatches || !lastNameMatches || !mobileNumMatches || !salaryMatches) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Incorrect Input");
			alert.setContentText("Please input valid arguments");
			alert.showAndWait();
		} else {
			employeeTable.getItems().add(new Employee(firstNameText.getText(), lastNameText.getText(),
					mobileNumText.getText(), "password", salaryText.getText(), managerCheck.isSelected()));
			firstNameText.clear();
			lastNameText.clear();
			mobileNumText.clear();
			salaryText.clear();
			managerCheck.setSelected(false);
			writeEmployees();
		}
	}

	/**
	 * This method exits to the main page.
	 *
	 * @throws Exception the exception
	 */
	public void exit() throws Exception {
		Stage employeeList = (Stage) employeeTable.getScene().getWindow();
		employeeList.close();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Manager Main.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		ManagerMainController controller = (ManagerMainController) loader.getController();
		controller.SetLabelText(application.Main.user);
	}

	/**
	 * This method reads the information about employees from the employees file.
	 */
	public void readEmployees() {
		application.Main.populateList(System.getProperty("user.dir") + "\\Employees.csv", "Employees");
		employeeList = application.Main.employeeList;

		employeeTable.setEditable(true);

		TableColumn firstNameCol = new TableColumn("First Name");
		firstNameCol.setMinWidth(117);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Employee, String>>() {
			public void handle(CellEditEvent<Employee, String> t) {
				Boolean firstNameMatches = t.getNewValue().matches("[A-Z][a-zA-Z]*");
				if (!firstNameMatches) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Incorrect Input");
					alert.setContentText("Please input valid arguments");
					alert.showAndWait();
				} else {
					((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setFirstName(t.getNewValue());
					try {
						writeEmployees();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setMinWidth(117);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Employee, String>>() {
			public void handle(CellEditEvent<Employee, String> t) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setLastName(t.getNewValue());
				try {
					writeEmployees();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		TableColumn mobileNumberCol = new TableColumn("Mobile Number");
		mobileNumberCol.setMinWidth(117);
		mobileNumberCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("mobileNumber"));
		mobileNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
		mobileNumberCol.setOnEditCommit(new EventHandler<CellEditEvent<Employee, String>>() {
			public void handle(CellEditEvent<Employee, String> t) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setMobileNumber(t.getNewValue());
				try {
					writeEmployees();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		TableColumn salaryCol = new TableColumn("Salary");
		salaryCol.setMinWidth(115);
		salaryCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
		salaryCol.setCellFactory(TextFieldTableCell.forTableColumn());
		salaryCol.setOnEditCommit(new EventHandler<CellEditEvent<Employee, String>>() {
			public void handle(CellEditEvent<Employee, String> t) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
				try {
					writeEmployees();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		TableColumn<Employee, Employee> deleteCol = new TableColumn<>("");
		deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		deleteCol.setCellFactory(param -> new TableCell<Employee, Employee>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(Employee employee, boolean empty) {
				super.updateItem(employee, empty);
				if (employee == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					getTableView().getItems().remove(employee);
					employeeList.remove(employee);
					try {
						writeEmployees();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});

		employeeTable.setItems(employeeList);
		employeeTable.getColumns().addAll(firstNameCol, lastNameCol, mobileNumberCol, salaryCol, deleteCol);
	}

	/**
	 * This method writes the current employee list in the table to the employees file.
	 *
	 * @throws Exception the exception
	 */
	public void writeEmployees() throws Exception {
		Writer writer = null;
		try {
			Files.delete(Paths.get(System.getProperty("user.dir") + "\\Employees.csv"));
			File f = new File(System.getProperty("user.dir") + "\\Employees.csv");
			f.createNewFile();
			writer = new BufferedWriter(new FileWriter(f));
			for (Employee e : employeeList) {
				String text = e.getFirstName() + "," + e.getLastName() + "," + e.getMobileNumber() + ","
						+ e.getPassword() + "," + e.getSalary() + "," + e.getStatus() + "\n";
				writer.write(text);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
		}
	}
}
