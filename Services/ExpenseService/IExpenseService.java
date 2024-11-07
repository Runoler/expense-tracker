package Services.ExpenseService;

import Models.Expense;

import java.util.List;

public interface IExpenseService {

    public int createExpense(String description, double amount);
    public void updateExpense(int id, Expense expense);
    public void deleteExpense(int id);
    public Expense getExpense(int id);
    public List<Expense> getExpenseList();
    public double getSummary();
    public double getSummary(int month);
}
