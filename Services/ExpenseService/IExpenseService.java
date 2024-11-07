package Services.ExpenseService;

import Models.Expense;

import java.util.List;
import java.util.NoSuchElementException;

public interface IExpenseService {

    int createExpense(String description, double amount);
    void updateExpense(Expense updateRequest);
    void deleteExpense(int id);
    Expense getExpense(int id) throws NoSuchElementException;
    List<Expense> getExpenseList();
    double getSummary();
    double getSummary(int month);

    void loadData();
    void saveData();
}
