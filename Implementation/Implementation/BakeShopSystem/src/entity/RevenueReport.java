package entity;

import java.util.Date;
import java.util.List;

public class RevenueReport extends Report{
    private double revenue;
    private long totalSellCoffee;
    private long totalSellBean;
    private long totalSellFood;
    private String bestSell;
    private List<Date> bestSellDay;

    public RevenueReport(Date addDate, double  revenue, long totalSellCoffee, long totalSellBean, long totalSellFood, String bestSellItem, List<Date> bestSellDay) {
        super(addDate);
        this.revenue = revenue;
        this.totalSellCoffee = totalSellCoffee;
        this.totalSellFood = totalSellFood;
        this.totalSellBean= totalSellBean;
        this.bestSell = bestSellItem;
        this.bestSellDay = bestSellDay;
    }


    public RevenueReport(Date addDate) {
        super(addDate);
    }

    public RevenueReport() {

    }

    public double  getRevenue() {
        return revenue;
    }

    public long getTotalSellCoffee() {
        return totalSellCoffee;
    }

    public void setTotalSellCoffee(long totalSellCoffee) {
        this.totalSellCoffee = totalSellCoffee;
    }

    public long getTotalSellBean() {
        return totalSellBean;
    }

    public void setTotalSellBean(long totalSellBean) {
        this.totalSellBean = totalSellBean;
    }

    public long getTotalSellFood() {
        return totalSellFood;
    }

    public void setTotalSellFood(long totalSellFood) {
        this.totalSellFood = totalSellFood;
    }

    public String getBestSellItem() {
        return bestSell;
    }

    public List<Date> getBestSellDay() {
        return bestSellDay;
    }

    public void setBestSellDay(List<Date> bestSellDay) {
        this.bestSellDay = bestSellDay;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getBestSell() {
        return bestSell;
    }

    public void setBestSell(String bestSell) {
        this.bestSell = bestSell;
    }

    @Override
    public String toString() {
        return "RevenueReport{" +
                "revenue=" + revenue +
                ", totalSellCoffee=" + totalSellCoffee +
                ", totalSellBean=" + totalSellBean +
                ", totalSellFood=" + totalSellFood +
                ", bestSellFood=" + bestSell +
                ", bestSellDayFirst=" + bestSellDay.get(0) +
                ", bestSellDaySecond=" + bestSellDay.get(1) +
                ", bestSellDayThird=" + bestSellDay.get(2) +
                ", bestSellDayFourth=" + bestSellDay.get(3) +
                '}';
    }
}
