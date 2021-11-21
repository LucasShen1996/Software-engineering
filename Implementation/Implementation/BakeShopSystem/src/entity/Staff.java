package entity;

import java.util.List;

public class Staff {
	private int staffId;
	private String name;
	private String email;
	private String password;
	private String type; // Staff || Manager || Owner
	private String TFN;
	private String street;
	private String city;
	private String state;
	private String phone;
	private int postal;
	private String status; // in || out
	private List<Shop> shoplist; // the shop staff belongs to
	public Staff(int staffId, String name, String email, String password, String type, String tFN, String street,
			String city, String state, String phone, int postal, String status, List<Shop> shoplist) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
		TFN = tFN;
		this.street = street;
		this.city = city;
		this.state = state;
		this.phone = phone;
		this.postal = postal;
		this.status = status;
		this.shoplist = shoplist;
	}
	public Staff() {
		super();
	}
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTFN() {
		return TFN;
	}
	public void setTFN(String tFN) {
		TFN = tFN;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPostal() {
		return postal;
	}
	public void setPostal(int postal) {
		this.postal = postal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Shop> getShoplist() {
		return shoplist;
	}
	public void setShoplist(List<Shop> shoplist) {
		this.shoplist = shoplist;
	}
	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", type=" + type + ", TFN=" + TFN + ", street=" + street + ", city=" + city + ", state=" + state
				+ ", phone=" + phone + ", postal=" + postal + ", status=" + status + ", shoplist=" + shoplist.toString() + "]";
	}
	
}
