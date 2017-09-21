package application;

import javafx.beans.property.SimpleStringProperty;

// TODO: Auto-generated Javadoc
/**
 * This class holds information about the individual items in an order.
 */
public class OrderItem {
	
	/** The order item. */
	private final SimpleStringProperty orderItem;
	
	/** The quantity. */
	private SimpleStringProperty quantity;
	
	/** The special request. */
	private SimpleStringProperty comment;
	
	/** The price. */
	private final SimpleStringProperty price;
	
	/** The order number. */
	private int orderNumber = 0;
	
	/** The table number. */
	private int tableNumber = 0;

	/**
	 * Instantiates a new order item.
	 *
	 * @param i the order item name
	 * @param q the quantity
	 * @param c the special request
	 * @param p the price
	 */
	OrderItem(String i, String q, String c, String p) {
		this.orderItem = new SimpleStringProperty(i);
		this.quantity = new SimpleStringProperty(q);
		this.comment = new SimpleStringProperty(c);
		this.price = new SimpleStringProperty(p);
	}

	/**
	 * Gets the order item.
	 *
	 * @return the order item
	 */
	public String getOrderItem() {
		return orderItem.get();
	}

	/**
	 * Sets the order item.
	 *
	 * @param i the new order item
	 */
	public void setOrderItem(String i) {
		orderItem.set(i);
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity.get();
	}

	/**
	 * Sets the quantity.
	 *
	 * @param q the new quantity
	 */
	public void setQuantity(String q) {
		quantity.set(q);
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment.get();
	}

	/**
	 * Sets the comment.
	 *
	 * @param c the new comment
	 */
	public void setComment(String c) {
		comment.set(c);
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