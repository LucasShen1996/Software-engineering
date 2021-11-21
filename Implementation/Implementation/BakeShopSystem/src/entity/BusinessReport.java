package entity;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;

public class BusinessReport extends Report{
    private Date dayMostIncome;
    private List<Item> itemLowInventory;
    private int revenue;

    public BusinessReport(Date addDate, Date dayMostIncome, List<Item> itemLowInventory,int revenue) {
        super(addDate);
        this.dayMostIncome = dayMostIncome;
        this.itemLowInventory = itemLowInventory;
        this.revenue = revenue;
    }

    public BusinessReport(Date dayMostIncome, List<Item> itemLowInventory,int revenue) {
        this.dayMostIncome = dayMostIncome;
        this.itemLowInventory = itemLowInventory;
        this.revenue = revenue;
    }

    public BusinessReport(Date addDate) {
        super(addDate);
    }

    public Date getDayMostIncome() {
        return dayMostIncome;
    }

    public void setDayMostIncome(Date dayMostIncome) {
        this.dayMostIncome = dayMostIncome;
    }

    public List<Item> getItemLowInventory() {
        return itemLowInventory;
    }

    public void setItemLowInventory(List<Item> itemLowInventory) {
        this.itemLowInventory = itemLowInventory;
    }
    public void setRevenue(int revenue){
        this.revenue = revenue;
    }
    public int getRevenue(){
        return revenue;
    }

    @Override
    public String toString() {
        return "BusinessReport{" +
                "dayMostIncome=" + dayMostIncome +
                ", itemLowInventory=" + itemLowInventory +
                '}';
    }
}
