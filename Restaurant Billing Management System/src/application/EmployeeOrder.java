package application;

import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * This class stores only the relevant information needed from the order class.
 * The class objects are then placed into the activity log for managers to view.
 */
public class EmployeeOrder {

	/** The table number. */
	String tableNumber;

	/** The total. */
	String total;

	/** The order number. */
	String orderNumber;

	/** The employee. */
	String employee;

	/**
	 * Instantiates a new employee order.
	 *
	 * @param orderNumber
	 *            the order number
	 * @param tableNumber
	 *            the table number
	 * @param employee
	 *            the employee
	 * @param total
	 *            the total
	 */
	EmployeeOrder(String orderNumber, String tableNumber, String employee, String total) {
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
	public String getTableNumber() {
		return this.tableNumber;
	}

	/**
	 * Sets the table number.
	 *
	 * @param tableNumber
	 *            the new table number
	 */
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
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
	 * Sets the order number.
	 *
	 * @param orderNumber
	 *            the new order number
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public String getTotal() {
		return this.total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total
	 *            the new total
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
	 * @param employee
	 *            the new employee
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}
}