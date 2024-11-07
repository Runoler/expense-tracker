package Services.ExpenseService;

import Models.Expense;

import java.util.List;
import java.util.NoSuchElementException;

public interface IExpenseService {

    public int createExpense(String description, double amount);
    public void updateExpense(Expense updateRequest);
    public void deleteExpense(int id);
    public Expense getExpense(int id) throws NoSuchElementException;
    public List<Expense> getExpenseList();
    public double getSummary();
    public double getSummary(int month);

    public void loadData();
    public void saveData();
}
