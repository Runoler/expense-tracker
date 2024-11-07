import Controllers.ExpenseController;
import Services.ExpenseService.ExpenseService;
import Services.ExpenseService.IExpenseService;

public class ExpenseTracker {
    public static void main(String[] args) {
        IExpenseService expenseService = ExpenseService.getInstance();
        expenseService.loadData();
        ExpenseController expenseController = new ExpenseController(expenseService);

        int argsCount = args.length;
        if (argsCount > 0) {
            switch (args[0]) {
                case "add":
                    expenseController.add(args);
                    break;
                case "list":
                    expenseController.getList(args);
                    break;
                case "summary":
                    expenseController.getSummary(args);
                    break;
                case "delete":
                    expenseController.delete(args);
                    break;
                case "update":
                    expenseController.update(args);
                    break;
            }
        }
        expenseService.saveData();
    }

}
