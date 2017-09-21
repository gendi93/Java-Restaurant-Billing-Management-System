package application;

import javafx.beans.property.SimpleStringProperty;

// TODO: Auto-generated Javadoc
/**
 * This class defines items in the menu.
 */
public class Item {
	
	/** The item. */
	private final SimpleStringProperty item;
	
	/** The description. */
	private final SimpleStringProperty description;
	
	/** The price. */
	private final SimpleStringProperty price;

	/**
	 * Instantiates a new item.
	 *
	 * @param i the i
	 * @param d the d
	 * @param p the p
	 */
	Item(String i, String d, String p) {
		this.item = new SimpleStringProperty(i);
		this.description = new SimpleStringProperty(d);
		this.price = new SimpleStringProperty(p);
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public String getItem() {
		return item.get();
	}

	/**
	 * Sets the item.
	 *
	 * @param i the new item
	 */
	public void setItem(String i) {
		item.set(i);
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description.get();
	}

	/**
	 * Sets the description.
	 *
	 * @param d the new description
	 */
	public void setDescription(String d) {
		description.set(d);
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public String getPrice() {
		return price.get();
	}

	/**
	 * Sets the price.
	 *
	 * @param p the new price
	 */
	public void setPrice(String p) {
		price.set(p);
	}
}