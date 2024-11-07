package Controllers;

import Models.Expense;
import Services.ExpenseService.IExpenseService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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
            System.out.println("Wrong arguments");
            return;
        }
        if (args[1].equals("--description") && args[3].equals("--amount")) {
            double amount = 0;
            try {
                amount = Double.parseDouble(args[4]);
                if (amount <= 0) {
                    System.out.println("\"--amount\" must be a positive number");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("\"--amount\" must be a positive number");
            }
            int expenseId = expenseService.createExpense(args[2], amount);
            System.out.printf("Expense added successfully (ID: %d)", expenseId);
        }
    }

    public void getList(String[] args) {
        if (args.length > 1) {
            System.out.println("Wrong arguments");
            return;
        }
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
                    System.out.println("Wrong month");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("\"--month\" must be a positive number in range 1-12");
                return;
            }
            double summary = expenseService.getSummary(month);

            System.out.printf("Total expenses for %s: %.2f BYN", months.get(month), summary);
        } else {
            System.out.println("Wrong arguments");
        }
    }

    public void delete(String[] args) {
        if (args.length != 3 || !args[1].equals("--id")) {
            System.out.println("Wrong arguments");
        }
        int id;
        try {
            id = Integer.parseInt(args[2]);
            if (id < 1) {
                System.out.println("ID must be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID must be a positive number");
            return;
        }
        try {
            expenseService.deleteExpense(id);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Expense deleted successfully");
    }

    public void update(String[] args) {

        List<String> arguments = Arrays.stream(args).toList();
        if (arguments.size() % 2 == 0
                || arguments.stream().filter(str -> str.equals("--id")).count() != 1
                || arguments.stream().filter(str -> str.equals("--description")).count() > 1
                || arguments.stream().filter(str -> str.equals("--amount")).count() > 1) {
            System.out.println("Wrong arguments");
            return;
        }

        Expense request;
        try {
            int id = Integer.parseInt(arguments.get(arguments.indexOf("--id")+1));
            if (id < 1) {
                System.out.println("ID must be a positive number");
                return;
            }
            Expense expense = expenseService.getExpense(id);
            request = new Expense(
                    expense.getId(),
                    expense.getDescription(),
                    expense.getAmount()
            );
        } catch (NumberFormatException e) {
            System.out.println("ID must be a positive number");
            return;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return;
        }

        int descriptionIndex = arguments.indexOf("--description");
        if (descriptionIndex != -1) {
            if (descriptionIndex == arguments.size()-1) {
                System.out.println("Wrong arguments");
                return;
            }
            String description = arguments.get(descriptionIndex+1);
            request.setDescription(description);
        }
        int amountIndex = arguments.indexOf("--amount");
        if (amountIndex != -1) {
            if (descriptionIndex == arguments.size()-1) {
                System.out.println("Wrong arguments");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(arguments.get(amountIndex+1));
                if (amount < 0) {
                    System.out.println("Amount must be a positive number");
                    return;
                }
                request.setAmount(amount);
            } catch (NumberFormatException e) {
                System.out.println("Amount must be a positive number");
                return;
            }
        }
        expenseService.updateExpense(request);
    }
}
