package entity;

import java.util.Date;
import java.util.List;

public class Item {
	private String itemId;
	private String name;
	private double price;
	private long stock;
	private String type;
	private List<Shop> shopList;
	private Date addDate;

	public Item(String itemId, String name, double price, long stock, String type, List<Shop> shopList, Date addDate) {
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.type = type;
		this.shopList = shopList;
		this.addDate = addDate;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public long getStock() {
		return stock;
	}

	public String getType() {
		return type;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public Date getAddDate() {
		return addDate;
	}

	public Item() {
	}

	@Override
	public String toString() {
		return "Item{" +
				"itemId='" + itemId + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", stock=" + stock +
				", type='" + type + '\'' +
				", shopList=" + shopList +
				", addDate=" + addDate +
				"}\n";
	}
}
