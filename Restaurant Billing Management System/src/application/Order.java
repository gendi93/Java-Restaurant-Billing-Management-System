package application;

import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * This class stores the details of every new order.
 */
public class Order {
	
	/** The file location. */
	String file;
	
	/** The time of order. */
	String timeOfOrder;
	
	/** The table number. */
	int tableNumber;
	
	/** The total. */
	String total;
	
	/** The order number. */
	String orderNumber;
	
	/** The employee. */
	String employee;
	
	/**
	 * Instantiates a new order.
	 *
	 * @param file the file
	 * @param timeOfOrder the time of order
	 * @param tableNumber the table number
	 * @param total the total
	 * @param orderNumber the order number
	 * @param employee the employee
	 */
	Order(String file, String timeOfOrder, int tableNumber, String total, String orderNumber, String employee) {
		this.file = file;
		this.timeOfOrder = timeOfOrder;
		this.tableNumber = tableNumber;
		this.total = total;
		this.orderNumber = orderNumber;
		this.employee = employee;
	}
	
	/**
	 * Gets the table number.
	 *
	 * @return the table number
	 */
	public int getTableNumber() {
		return this.tableNumber;
	}
	
	/**
	 * Gets the file.
	 *
	 * @return the file
	 */
	public String getFile() {
		return this.file;
	}
	
	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * Gets the order number.
	 *
	 * @return the order number
	 */
	public String getOrderNumber() {
		return this.orderNumber;
	}
	
	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	
	/**
	 * Gets the time of order.
	 *
	 * @return the time of order
	 */
	public String getTimeOfOrder() {
		return this.timeOfOrder;
	}
	
	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	
	/**
	 * Gets the employee.
	 *
	 * @return the employee
	 */
	public String getEmployee() {
		return this.employee;
	}
	
	/**
	 * Sets the employee.
	 *
	 * @param employee the new employee
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}
}