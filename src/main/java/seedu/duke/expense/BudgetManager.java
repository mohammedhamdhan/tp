package seedu.duke.expense;

import java.util.ArrayList;
import java.util.List;
import seedu.duke.messages.Messages;
import seedu.duke.storage.DataStorage;

/**
 * Manages a collection of expenses and provides operations on them.
 */
public class BudgetManager {
    Messages messages = new Messages();
    private List<Expense> expenses;

    /**
     * Constructs a BudgetManager with an empty list of expenses.
     */
    public BudgetManager() {
        this.expenses = DataStorage.loadExpenses();
    }

    /**
     * Adds an expense to the collection.
     *
     * @param expense the expense to add
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        DataStorage.saveExpenses(expenses);
    }

    /**
     * Deletes an expense at the specified index.
     *
     * @param index the index of the expense to delete
     * @return the deleted expense
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Expense deleteExpense(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= expenses.size()) {
            throw new IndexOutOfBoundsException(messages.invalidIndexMessage());
        }
        Expense deletedExpense = expenses.remove(index);
        DataStorage.saveExpenses(expenses);
        return deletedExpense;
    }

    /**
     * Edits an expense at the specified index.
     *
     * @param index   the index of the expense to edit
     * @param title   the new title (null to keep existing)
     * @param description the new description (null to keep existing)
     * @param amount  the new amount (negative to keep existing)
     * @return the edited expense
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Expense editExpense(int index, String title, String description, double amount) 
            throws IndexOutOfBoundsException {
        if (index < 0 || index >= expenses.size()) {
            throw new IndexOutOfBoundsException(messages.invalidIndexMessage());
        }
        
        Expense expense = expenses.get(index);
        
        if (title != null) {
            expense.setTitle(title);
        }
        
        if (description != null) {
            expense.setDescription(description);
        }
        
        if (amount >= 0) {
            expense.setAmount(amount);
        }
        
        DataStorage.saveExpenses(expenses);
        return expense;
    }

    /**
     * Gets all expenses.
     *
     * @return the list of expenses
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * Gets the number of expenses.
     *
     * @return the number of expenses
     */
    public int getExpenseCount() {
        return expenses.size();
    }

    /**
     * Gets the number of unsettled expenses.
     *
     * @return the number of unsettled expenses
     */
    public int getUnsettledExpenseCount() {
        int numberOfUnsettledExpenses = 0;
        for (Expense expense : expenses) {
            if (!expense.getDone()) {
                numberOfUnsettledExpenses++;
            }
        }
        return numberOfUnsettledExpenses;
    }

    /**
     * Calculates the total balance (sum of all unsettled expense amounts).
     *
     * @return the total balance
     */
    public double getTotalBalance() {
        double total = 0;
        for (Expense expense : expenses) {
            if(!expense.getDone()) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    /**
     * Gets an expense at the specified index.
     *
     * @param index the index of the expense to get
     * @return the expense at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Expense getExpense(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= expenses.size()) {
            throw new IndexOutOfBoundsException(messages.invalidIndexMessage());
        }
        return expenses.get(index);
    }

    /**
     * Marks an expense at the specified index.
     *
     * @param index the index of the expense to mark
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void markExpense(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= expenses.size()) {
            throw new IndexOutOfBoundsException(messages.invalidIndexMessage());
        }
        expenses.get(index).setDone(true);
        saveAllExpenses();
    }

    /**
     * Unmarks an expense at the specified index.
     *
     * @param index the index of the expense to unmark
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void unmarkExpense(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= expenses.size()) {
            throw new IndexOutOfBoundsException(messages.invalidIndexMessage());
        }
        expenses.get(index).setDone(false);
        saveAllExpenses();
    }

    /**
     * Saves all expenses to storage.
     */
    public void saveAllExpenses() {
        DataStorage.saveExpenses(expenses);
    }

    //@@author matthewyeo1
    /**
     * Sets the amount of an expense at the given index to 0.0.
     *
     * @param index the index of the expense to update
     * @return the updated expense
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Expense setExpenseAmountToZero(int index) throws IndexOutOfBoundsException {
        List<Expense> expenses = getAllExpenses(); // Get the current list of expenses
        if (index < 0 || index >= expenses.size()) {
            throw new IndexOutOfBoundsException("Invalid expense index.");
        }

        // Retrieve the expense at the given index
        Expense expense = expenses.get(index);

        // Set the expense amount to 0.0
        expense.setAmount(0.0);

        // Save the updated expenses to the file
        saveAllExpenses();

        return expense;
    }
    //@@author
} 
