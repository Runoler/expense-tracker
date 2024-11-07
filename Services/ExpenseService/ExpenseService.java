package Services.ExpenseService;

import Models.Expense;

import javax.management.openmbean.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class ExpenseService implements IExpenseService {

    private List<Expense> expenses;

    private static ExpenseService expenseServiceInstance;

    private ExpenseService() {
        expenses = new ArrayList<>();
    }

    public static ExpenseService getInstance() {
        if (expenseServiceInstance == null)
            expenseServiceInstance = new ExpenseService();
        return expenseServiceInstance;
    }

    @Override
    public int createExpense(String description, double amount) {
        Expense expense = new Expense(description, amount);
        this.expenses.add(expense);
        return expense.getId();
    }

    @Override
    public void updateExpense(int id, Expense expense) {

    }

    @Override
    public void deleteExpense(int id) throws InvalidKeyException, NoSuchElementException {
        if (id < 1)
            throw new InvalidKeyException("Error: ID must be a positive number.");

        boolean deleted = this.expenses.removeIf(expense -> expense.getId() == id);

        if (!deleted)
            throw new NoSuchElementException(String.format("Error: expense (id: %d) not found.", id));
    }

    @Override
    public Expense getExpense(int id) {
        if (id < 1)
            throw new InvalidKeyException("Error: ID must be a positive number.");

        return this.expenses.stream()
                .filter(expense -> expense.getId() == id)
                .findFirst().orElseThrow(() -> new NoSuchElementException(String.format("Error: expense (id: %d) not found.", id)));
    }

    @Override
    public List<Expense> getExpenseList() {
        return this.expenses;
    }

    @Override
    public double getSummary() {
        double summary = 0;
        for (Expense expense : expenses) {
            summary += expense.getAmount();
        }
        return summary;
    }

    @Override
    public double getSummary(int month) {
        double summary = 0;
        Date currentDate = new Date();
        List<Expense> monthExpenses = expenses.stream()
                .filter(exp -> exp.getDate().getMonth() == currentDate.getMonth()).toList();
        for (Expense expense : monthExpenses) {
            summary += expense.getAmount();
        }
        return summary;
    }
}
