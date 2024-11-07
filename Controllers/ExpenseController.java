package Controllers;

import Models.Expense;
import Services.ExpenseService.IExpenseService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseController {

    private final IExpenseService expenseService;
    private static final List<String> months = List.of(
            "",
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    );

    public ExpenseController(IExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public void add(String[] args) {
        if (args.length != 5)
        {
            System.out.println("Wrong amount of arguments.");
            return;
        }
        if (args[1].equals("--description") && args[3].equals("--amount")) {
            double amount = 0;
            try {
                amount = Double.parseDouble(args[4]);
            } catch (NumberFormatException e) {
                System.out.println("\"--amount\" takes a number.");
            }
            int expenseId = expenseService.createExpense(args[2], amount);
            System.out.printf("Expense added successfully (ID: %d)", expenseId);
        }
    }

    public void getList(String[] args) {
        System.out.printf("%-5s\t%-12s\t%-20s\t%-10s\n", "ID", "Date", "Description", "Amount");
        List<Expense> expenses = expenseService.getExpenseList();
        for (Expense expense : expenses) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            System.out.printf("%-5d\t%-12s\t%-20s\t%.2f BYN\n",
                    expense.getId(),
                    df.format(expense.getDate()),
                    expense.getDescription(),
                    expense.getAmount());
        }
    }

    public void getSummary(String[] args) {
        if (args.length == 1) {
            double summary = expenseService.getSummary();
            System.out.printf("Total expenses: %.2f BYN", summary);
        } else if (args.length == 3 && args[1].equals("--month")) {
            int month;
            try {
                month = Integer.parseInt(args[2]);
                if (month < 1 || month > 12)
                {
                    System.out.println("Wrong month.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("\"--month\" takes an integer number.");
                return;
            }
            double summary = expenseService.getSummary(month);

            System.out.printf("Total expenses for %s: %.2f BYN", months.get(month), summary);
        }
    }

    public void delete(String[] args) {
    }

    public void update(String[] args) {
    }
}
