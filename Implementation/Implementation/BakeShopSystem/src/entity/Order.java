package entity;

import java.util.Date;
import java.util.List;

public class Order {
	private int orderId;
	private Staff staff; // created by staff
	private Shop shop;
	private Date date; // date 
	private List<Item> item; // split by ,
	private List<Integer> quantity; // split by ,
	private List<Integer> price; // split by |
	private String status; //READY, PREPARE, DONE
	private String customerName;
	private String customerPhone;
	private String type;
	private double totalCost;

	public Order(int orderId, Staff staff, Shop shop, Date date, List<Item> item, List<Integer> quantity, List<Integer> price, String status, String customerName, String customerPhone, String type, double totalCost) {
		this.orderId = orderId;
		this.staff = staff;
		this.shop = shop;
		this.date = date;
		this.item = item;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.type = type;
		this.totalCost = totalCost;
	}

	public Order() {
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public List<Integer> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Integer> quantity) {
		this.quantity = quantity;
	}

	public List<Integer> getPrice() {
		return price;
	}

	public void setPrice(List<Integer> price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", staff=" + staff.toString() +
				", shop=" + shop.toString() +
				", date=" + date +
				", item=" + item.toString() +
				", quantity=" + quantity.toString() +
				", price=" + price.toString() +
				", status='" + status + '\'' +
				", customerName='" + customerName + '\'' +
				", customerPhone='" + customerPhone + '\'' +
				", type='" + type + '\'' +
				", totalCost=" + totalCost +
				"}\n";
	}
}
