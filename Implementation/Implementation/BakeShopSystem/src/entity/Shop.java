package entity;

import java.util.Date;

public class Shop {
	private int shopId;
	private String name;
	private String street;
	private String city;
	private String state;
	private int postal;
	private String phone;
	private int emplyeeNumber;
	private Date openDate;
	
	public Shop(int shopId, String name, String street, String city, String state, int postal, String phone,
			int emplyeeNumber, Date openDate) {
		super();
		this.shopId = shopId;
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
		this.postal = postal;
		this.phone = phone;
		this.emplyeeNumber = emplyeeNumber;
		this.openDate = openDate;
	}
	public Shop() {
		super();
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPostal() {
		return postal;
	}
	public void setPostal(int postal) {
		this.postal = postal;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getEmplyeeNumber() {
		return emplyeeNumber;
	}
	public void setEmplyeeNumber(int emplyeeNumber) {
		this.emplyeeNumber = emplyeeNumber;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", name=" + name + ", street=" + street + ", city=" + city + ", state="
				+ state + ", postal=" + postal + ", phone=" + phone + ", emplyeeNumber=" + emplyeeNumber + ", openDate="
				+ openDate + "]";
	}
	
}
