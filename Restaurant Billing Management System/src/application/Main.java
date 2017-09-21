package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * This is the starting class which initiates the welcome page.
 * @author Yehia El Gendi
 */
public class Main extends Application {
	
	/** A counter for the number of orders placed.*/
	static int numberOfOrder = 0;
	
	/** A list of current restaurant employees.*/
	static ObservableList<Employee> employeeList = FXCollections.observableArrayList();
	
	/** A list of current live orders.*/
	static ObservableList<Order> orderList = FXCollections.observableArrayList();
	
	/** The current logged in user.*/
	static String user;
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome Page.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() {
		try {
			Files.delete(Paths.get("Orders.csv"));
			File f = new File(System.getProperty("user.dir") + "\\Orders.csv");
			f.createNewFile();
			String s = System.getProperty("user.dir");
			while (numberOfOrder > 0) {
				String stamp = "Order " + String.valueOf(numberOfOrder);
				File dir = new File(s);
				dir.mkdirs();
				String file = dir + "\\" + stamp + ".csv";
				File permfile = new File(dir, stamp + ".csv");
				Files.delete(Paths.get(permfile.getAbsolutePath()));
				numberOfOrder--;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * The main method.
	 *
	 * @param args The arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This class updates the order and employee list everytime an order or an employee is added.
	 *
	 * @param file The file from which to read.
	 * @param type The type of list that will be filled.
	 */
	public static void populateList(String file, String type) {
		if (type == "Employees") {
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				String dataLine;
				employeeList.clear();
				while ((dataLine = in.readLine()) != null) {
					for (int i = 0; i < 3; i += 6) {
						String[] dataValues = dataLine.split(",");
						employeeList.add(new Employee(dataValues[i], dataValues[i + 1], dataValues[i + 2],
								dataValues[i + 3], dataValues[i + 4], Boolean.valueOf(dataValues[i + 5])));
					}
				}
				in.close();
			} catch (Exception e) {
			}
		} else {
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				String dataLine;
				orderList.clear();
				while ((dataLine = in.readLine()) != null) {
					for (int i = 0; i < 3; i += 5) {
						String[] dataValues = dataLine.split(",");
						orderList.add(new Order(dataValues[i], dataValues[i + 1], Integer.parseInt(dataValues[i + 2]),
								dataValues[i + 3], dataValues[i + 4], dataValues[i+5]));
					}
				}
				in.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * This method updates the employees file. It is used after the user changes his password in order to update the file with the new password.
	 *
	 *@throws Exception This is an IOException in case the employees file doesn't exist.
	 */
	public static void writeEmployees() throws Exception {
		Writer writer = null;
		try {
			Files.delete(Paths.get("Employees.csv"));
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
