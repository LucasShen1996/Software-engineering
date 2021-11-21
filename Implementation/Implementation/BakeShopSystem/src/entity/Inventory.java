package entity;

import java.util.Date;
import java.util.List;

public class Inventory {
    private Item item;
    private List<Shop> shop;
    private long itemQuantity;
    private Date addDate;

    public Inventory(Item item, List<Shop> shop, long itemQuantity, Date addDate) {
        this.item = item;
        this.shop = shop;
        this.itemQuantity = itemQuantity;
        this.addDate = addDate;
    }

    public Inventory() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Shop> getShop() {
        return shop;
    }

    public void setShop(List<Shop> shop) {
        this.shop = shop;
    }

    public long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "item=" + item +
                ", shop=" + shop +
                ", itemQuantity=" + itemQuantity +
                ", addDate=" + addDate +
                '}';
    }
}
