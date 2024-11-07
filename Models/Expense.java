package Models;

import java.time.Clock;
import java.util.Date;

public class Expense {
    private static int EXPENSE_COUNT = 0;
    private int id;
    private Date date;
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        EXPENSE_COUNT++;
        this.id = EXPENSE_COUNT;
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

    public double getAmount() {
        return amount;
    }
}
