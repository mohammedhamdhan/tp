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
    public static String DATA_FILE = "expenses.txt";
    private static final String SEPARATOR = "|";

    /**
     * Ensures that the data file exists.
     */
    public static void ensureFileExists() {
        File file = new File(DATA_FILE);
        try {
            if (file.createNewFile()) {
                // Removed: Messages.createNewFileMessage(DATA_FILE);
            }
        } catch (IOException e) {
            System.out.println(Messages.errorMessageTag() + " Error creating data file: " + e.getMessage());
        }
    }

    /**
     * Saves expenses to the data file.
     *
     * @param expenses the list of expenses to save
     * @return true if saving was successful, false otherwise
     */
    public static boolean saveExpenses(List<Expense> expenses) {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            for (Expense expense : expenses) {
                writer.write(expense.getTitle() + SEPARATOR
                        + expense.getDescription() + SEPARATOR
                        + expense.getAmount() + System.lineSeparator());
            }
            // Removed: System.out.println("Expenses saved successfully.");
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
        File file = new File(DATA_FILE);
        
        if (!file.exists()) {
            // Removed: Messages.createNewFileMessage(DATA_FILE);
            return expenses;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNext()) {
                // Removed: Messages.emptyDataFileMessage();
                return expenses;
            }
            
            // Removed: Messages.loadDataMessage(DATA_FILE);
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\" + SEPARATOR);
                
                if (parts.length == 3) {
                    String title = parts[0];
                    String description = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    
                    expenses.add(new Expense(title, description, amount));
                }
            }
            
            // Removed: System.out.println("Loaded " + expenses.size() + " expenses.");
        } catch (FileNotFoundException e) {
            System.out.println(Messages.errorMessageTag() + " Error loading expenses: " + e.getMessage());
        }
        
        return expenses;
    }

    /**
     * Clears the contents of the data file (for testing purposes).
     */
    public static void resetExpenses() {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println(Messages.errorMessageTag() + " Error resetting expenses: " + e.getMessage());
        }
    }
}

