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
    public Expense editExpense(int index, String title, String description, String date, double amount)
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

        if (date != null) {
            expense.setDate(date);
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
        assert numberOfUnsettledExpenses >= 0 : "number of unsettled expenses should not be negative";
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

    //@@author NandhithaShree
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
     * Updates the amounts of all expenses by applying the given exchange rate to each expense.
     * This method multiplies the amount of each expense by the provided exchange rate and updates the expense.
     *
     * @param finalExchangeRate The exchange rate to be applied to the expense amounts.
     *                         The expense amount will be multiplied by this rate.
     */
    public void editExpenseCurrency(Double finalExchangeRate){

        if (getExpenseCount() == 0) {
            return;
        }

        for(int i = 0; i < getExpenseCount(); i++){
            Expense expense = expenses.get(i);
            editExpense(i, expense.getTitle(), expense.getDescription(), expense.getDate(), expense.getAmount()*finalExchangeRate);
        }
    }
    //@@author

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
