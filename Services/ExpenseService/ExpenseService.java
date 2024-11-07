package Services.ExpenseService;

import Models.Expense;

import javax.management.openmbean.InvalidKeyException;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class ExpenseService implements IExpenseService {

    private int idCounter;
    private List<Expense> expenses;

    private static final String dataFilePath = "data.bin";

    private static ExpenseService expenseServiceInstance;

    private ExpenseService() {
    }

    public static ExpenseService getInstance() {
        if (expenseServiceInstance == null)
            expenseServiceInstance = new ExpenseService();
        return expenseServiceInstance;
    }

    @Override
    public int createExpense(String description, double amount) {
        idCounter++;
        Expense expense = new Expense(idCounter, description, amount);
        this.expenses.add(expense);
        return expense.getId();
    }

    @Override
    public void updateExpense(Expense updateRequest) {
        Expense expense = this.getExpense(updateRequest.getId());
        expense.setDescription(updateRequest.getDescription());
        expense.setAmount(updateRequest.getAmount());

    }

    @Override
    public void deleteExpense(int id) throws IllegalArgumentException, NoSuchElementException {
        if (id < 1)
            throw new IllegalArgumentException("Error: ID must be a positive number.");

        boolean deleted = this.expenses.removeIf(expense -> expense.getId() == id);

        if (!deleted)
            throw new NoSuchElementException(String.format("Error: expense (ID: %d) not found.", id));
    }

    @Override
    public Expense getExpense(int id) throws NoSuchElementException {
        if (id < 1)
            throw new InvalidKeyException("Error: ID must be a positive number.");

        return this.expenses.stream()
                .filter(expense -> expense.getId() == id)
                .findFirst().orElseThrow(() -> new NoSuchElementException(String.format("Error: expense (ID: %d) not found.", id)));
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
                .filter(exp -> exp.getDate().getMonth()+1 == month && exp.getDate().getYear() == currentDate.getYear()).toList();
        for (Expense expense : monthExpenses) {
            summary += expense.getAmount();
        }
        return summary;
    }

    @Override
    public void loadData() {
        try {

            File dataFile = new File(ExpenseService.dataFilePath);
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            FileInputStream fis = new FileInputStream(dataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            idCounter = ois.readInt();
            expenses = (List<Expense>) ois.readObject();

        } catch (EOFException e) {
            idCounter = 0;
            expenses = new ArrayList<>();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveData() {
        try {

            File dataFile = new File(ExpenseService.dataFilePath);
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(dataFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeInt(idCounter);
            oos.writeObject(expenses);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
