# expense-tracker
Java console app solution for tracking your expenses. A simple expense tracker to manage your financies.
Java console app solution for the expense-tracker [challenge](https://roadmap.sh/projects/expense-tracker) from [roadmap.sh](https://roadmap.sh/).

## Features

- **Add expense**: add new expense with description and amount of money.
- **List expenses**: show a list of your expenses.
- **Summarize expenses**: show a summary of total expenses or summary per month.
- **Delete expense**: delete a expense by its id.
- **Update expense**: update a description or/and an amount of expense by its id.

## Installation

To run this application, follow these steps:

1. Clone this repository:
    ```bash
    git clone https://github.com/Runoler/expense-tracker.git
    ```

2. Navigate to the project directory:
    ```bash
    cd expense-tracker
    ```

3. Compile program:
    ```bash
    javac ExpenseTracker.java
    ```

4. Run the application (check "--help" flag):
    ```bash
    java ExpenseTracker [action] [--flag]
    ```
## Usage

Run the application with actions and flags.

## Available commands

- **java ExpenseTracker --help**: Displays a list of commands.
- **java ExpenseTracker add --description "description" --amount [double]**: Add a new expense with following description and amount of money.
- **java ExpenseTracker list**: Displays a list of all your expenses.
- **java ExpenseTracker summary**: Displays a total summary.
- **java ExpenseTracker summary --month [1-12]**: Displays a summary per following month.
- **java ExpenseTracker delete --id [id]**: Delete an expense by id.
- **java ExpenseTracker update --id [id] --description "description" --amount [double]**: Update an expense by its id with following description or/and amount.

### Example
```bash
java ExpenseTracker add --description "Lunch" --amount 20
Expense added successfully (ID: 1)

java ExpenseTracker list
ID      Date            Description             Amount
1       2024-11-08      Lunch                   20,00 BYN

java ExpenseTracker update --id 1 --amount 15

java ExpenseTracker list
ID      Date            Description             Amount
1       2024-11-08      Lunch                   15,00 BYN

java ExpenseTracker delete --id 1
Expense deleted successfully
```
