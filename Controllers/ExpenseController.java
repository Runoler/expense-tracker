package Controllers;

import Models.Expense;
import Services.ExpenseService.IExpenseService;

import java.util.ArrayList;
import java.util.Date;
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
            int expenseId = expenseService.createExpense(args[2], Integer.parseInt(args[4]));
            System.out.printf("Expense added successfully (ID: %d)", expenseId);
        }
    }

    public void getList(String[] args) {
    }

    public void getSummary(String[] args) {
        if (args.length == 1) {
            double summary = expenseService.getSummary();
            System.out.printf("Total expenses: %.2f BYN", summary);
        } else if (args.length == 3 && args[1].equals("--month")) {
            int month = 0;
            try {
                month = Integer.parseInt(args[2]);
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
