package Models;

import java.io.Serializable;
import java.time.Clock;
import java.util.Date;

public class Expense implements Serializable {
    private int id;
    private Date date;
    private String description;
    private double amount;

    public Expense(int id, String description, double amount) {
        this.id = id;
        this.date = Date.from(Clock.systemUTC().instant());
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
