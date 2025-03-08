package seedu.duke.commands;

import java.util.List;
import java.util.Scanner;

import seedu.duke.expense.BudgetManager;
import seedu.duke.expense.Expense;

/**
 * Handles expense-related commands entered by the user.
 */
public class ExpenseCommand {
    private BudgetManager budgetManager;
    private Scanner scanner;

    /**
     * Constructs an ExpenseCommand with the given BudgetManager and Scanner.
     *
     * @param budgetManager the budget manager to use
     * @param scanner       the scanner for user input
     */
    public ExpenseCommand(BudgetManager budgetManager, Scanner scanner) {
        this.budgetManager = budgetManager;
        this.scanner = scanner;
    }

    /**
     * Executes the add expense command.
     */
    public void executeAddExpense() {
        try {
            System.out.println("Enter expense title:");
            String title = scanner.nextLine().trim();
            
            System.out.println("Enter expense description:");
            String description = scanner.nextLine().trim();
            
            System.out.println("Enter expense amount:");
            double amount = Double.parseDouble(scanner.nextLine().trim());
            
            if (amount < 0) {
                System.out.println("Amount cannot be negative.");
                return;
            }
            
            Expense expense = new Expense(title, description, amount);
            budgetManager.addExpense(expense);
            
            System.out.println("Expense added successfully:");
            System.out.println(expense);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    /**
     * Executes the delete expense command.
     */
    public void executeDeleteExpense() {
        try {
            displayAllExpenses();
            
            if (budgetManager.getExpenseCount() == 0) {
                return;
            }
            
            System.out.println("Enter the index of the expense to delete:");
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1; // Convert to 0-based index
            
            Expense deletedExpense = budgetManager.deleteExpense(index);
            System.out.println("Expense deleted successfully:");
            System.out.println(deletedExpense);
        } catch (NumberFormatException e) {
            System.out.println("Invalid index format. Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error deleting expense: " + e.getMessage());
        }
    }

    /**
     * Executes the edit expense command.
     */
    public void executeEditExpense() {
        try {
            displayAllExpenses();
            
            if (budgetManager.getExpenseCount() == 0) {
                return;
            }
            
            System.out.println("Enter the index of the expense to edit:");
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1; // Convert to 0-based index
            
            // Display current details
            Expense currentExpense = budgetManager.getExpense(index);
            System.out.println("Current expense details:");
            System.out.println(currentExpense);
            
            // Get new details (leave blank to keep current)
            System.out.println("Enter new title (press Enter to keep current):");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                title = null;
            }
            
            System.out.println("Enter new description (press Enter to keep current):");
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                description = null;
            }
            
            System.out.println("Enter new amount (press Enter to keep current):");
            String amountInput = scanner.nextLine().trim();
            double amount = -1; // Sentinel value to indicate no change
            if (!amountInput.isEmpty()) {
                amount = Double.parseDouble(amountInput);
                if (amount < 0) {
                    System.out.println("Amount cannot be negative. Keeping current amount.");
                    amount = -1;
                }
            }
            
            Expense editedExpense = budgetManager.editExpense(index, title, description, amount);
            System.out.println("Expense edited successfully:");
            System.out.println(editedExpense);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error editing expense: " + e.getMessage());
        }
    }

    /**
     * Displays all expenses.
     */
    public void displayAllExpenses() {
        List<Expense> expenses = budgetManager.getAllExpenses();
        
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        
        System.out.println("List of Expenses:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println("Expense #" + (i + 1));
            System.out.println(expenses.get(i));
            System.out.println();
        }
    }

    /**
     * Shows the balance overview.
     */
    public void showBalanceOverview() {
        double totalBalance = budgetManager.getTotalBalance();
        System.out.println("Balance Overview");
        System.out.println("----------------");
        System.out.println("Total number of expenses: " + budgetManager.getExpenseCount());
        System.out.println("Total amount owed: $" + String.format("%.2f", totalBalance));
    }
} 