package application;

// TODO: Auto-generated Javadoc
/**
 * This class represents and employee and holds data about them.
 */
public class Employee {
	
	/** The first name. */
	String firstName;
	
	/** The last name. */
	String lastName;
	
	/** The mobile number. */
	String mobileNumber;
	
	/** The password. */
	String password;
	
	/** The salary. */
	String salary;
	
	/** The is manager. */
	Boolean isManager;
	
	/**
	 * Instantiates a new employee.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param mobileNumber the mobile number
	 * @param password the password
	 * @param salary the salary
	 * @param isManager the is manager
	 */
	Employee(String firstName, String lastName, String mobileNumber, String password, String salary, Boolean isManager) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.salary = salary;
		this.isManager = isManager;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the mobile number.
	 *
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return this.mobileNumber;
	}
	
	/**
	 * Sets the mobile number.
	 *
	 * @param mobileNumber the new mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the salary.
	 *
	 * @return the salary
	 */
	public String getSalary() {
		return this.salary;
	}
	
	/**
	 * Sets the salary.
	 *
	 * @param salary the new salary
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Boolean getStatus() {
		return this.isManager;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param isManager the new status
	 */
	public void setStatus(Boolean isManager) {
		this.isManager = isManager;
	}
}
