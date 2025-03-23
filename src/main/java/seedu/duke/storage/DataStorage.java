//@@author matthewyeo1
package seedu.duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import seedu.duke.expense.Expense;
import seedu.duke.messages.Messages;

public class DataStorage {
    public static String dataFile = "expenses.txt";
    private static final String SEPARATOR = "|";

    /**
     * Ensures that the data file exists.
     */
    public static void ensureFileExists() {
        File file = new File(dataFile);
        try {
            if (file.createNewFile()) {
                Messages.createNewFileMessage();
            }
        } catch (IOException e) {
            System.out.println(Messages.errorMessageTag() + " Error creating data file: " + e.getMessage());
        }
        //@@author matthewyeo1
        assert file.exists() : "Data file should exist after ensuring";
        //@@author
    }

    /**
     * Saves expenses to the data file.
     *
     * @param expenses the list of expenses to save
     * @return true if saving was successful, false otherwise
     */
    public static boolean saveExpenses(List<Expense> expenses) {
        //@@author matthewyeo1
        assert expenses != null : "Expenses list should not be null";
        //@@author
        try (FileWriter writer = new FileWriter(dataFile)) {
            for (Expense expense : expenses) {
                writer.write(expense.getTitle() + SEPARATOR
                        + expense.getDescription() + SEPARATOR
                        //@@author matthewyeo1
                        + expense.getDate() + SEPARATOR
                        //@@author
                        + expense.getAmount() + SEPARATOR
                        + expense.getDone() + System.lineSeparator());
            }
            return true;
        } catch (IOException e) {
            System.out.println(Messages.errorMessageTag() + " Error saving expenses: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads expenses from the data file.
     *
     * @return the list of loaded expenses
     */
    public static List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(dataFile);
        
        if (!file.exists()) {
            return expenses;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNext()) {
                return expenses;
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\" + SEPARATOR);

                if (parts.length == 5) {
                    String title = parts[0];
                    String description = parts[1];
                    String date = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    boolean isDone = true;
                    if (parts[4].equals("false")) {
                        isDone = false;
                    }
                    expenses.add(new Expense(title, description, date, amount, isDone));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(Messages.errorMessageTag() + " Error loading expenses: " + e.getMessage());
        }
        
        return expenses;
    }

    /**
     * Clears the contents of the data file (for testing purposes).
     */
    public static void resetExpenses() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println(Messages.errorMessageTag() + " Error resetting expenses: " + e.getMessage());
        }
        //@@author matthewyeo1
        assert new File(dataFile).length() == 0 : "Data file should be empty after reset";
        //@@author
    }
}
//@@author
