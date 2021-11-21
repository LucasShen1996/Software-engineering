package entity;

import java.util.Date;

public class Report {
    private Date addDate;

    public Report(Date addDate) {
        this.addDate = addDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Report() {
    }

    @Override
    public String toString() {
        return "Report{" +
                "addDate=" + addDate +
                '}';
    }
}
