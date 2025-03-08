package seedu.duke.expense;

/**
 * Represents an individual expense with title, description, and amount.
 */
public class Expense {
    private String title;
    private String description;
    private double amount;

    /**
     * Constructs an Expense object with the given title, description, and amount.
     *
     * @param title       A short name or summary of the expense
     * @param description Detailed information about the expense
     * @param amount      The monetary value of the expense
     */
    public Expense(String title, String description, double amount) {
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    /**
     * Returns the title of the expense.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the expense.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the expense.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the expense.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the expense.
     *
     * @param amount the new amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns a string representation of the expense.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "Title: " + title + "\n"
                + "Description: " + description + "\n"
                + "Amount: $" + String.format("%.2f", amount);
    }
} 
