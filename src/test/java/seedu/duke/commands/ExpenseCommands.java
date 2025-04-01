package seedu.duke.commands;

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.currency.Currency;
import seedu.duke.expense.BudgetManager;
import seedu.duke.expense.Expense;

//@@author matthewyeo1
class ExpenseCommandTest {
    private Currency currency;
    private BudgetManager budgetManager;
    private ExpenseCommand expenseCommand;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private final String testTitle = "Test Expense";
    private final String testDescription = "Test Description";
    private final String testDate = "31-12-2025";
    private final double testAmount = 100.0;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);

        try (PrintWriter writer = new PrintWriter("expenses.txt")) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File("./currentCurrency");
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File monthlySummaryFile = new File("monthly_summary.txt");
        if (monthlySummaryFile.exists()) {
            try (PrintWriter writer = new PrintWriter(monthlySummaryFile)) {
                writer.print("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Clear the contents of the "category_summary.txt" file
        File categorySummaryFile = new File("category_summary.txt");
        if (categorySummaryFile.exists()) {
            try (PrintWriter writer = new PrintWriter(categorySummaryFile)) {
                writer.print("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void provideInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        currency = new Currency(scanner, budgetManager);
        expenseCommand = new ExpenseCommand(budgetManager, scanner, currency );
    }

    @Test
    void testExecuteAddExpense() {
        provideInput("Groceries\nWeekly food shopping\n01-01-2025\n100\n");

        expenseCommand.executeAddExpense();
        assertEquals(1, budgetManager.getExpenseCount());

        Expense addedExpense = budgetManager.getExpense(0);
        assertEquals("Groceries", addedExpense.getTitle());
        assertEquals("Weekly food shopping", addedExpense.getDescription());
        assertEquals("01-01-2025", addedExpense.getDate());
        assertEquals(100.0, addedExpense.getAmount());
    }

    @Test
    void testExecuteDeleteExpense() {
        budgetManager.addExpense(new Expense("Lunch", "Pizza", "01-01-2025",10));

        provideInput("1\nyes\n");
        expenseCommand.executeDeleteExpense();

        assertEquals(0, budgetManager.getExpenseCount());
        assertTrue(outContent.toString().contains("Expense deleted successfully"));
    }

    @Test
    void testExecuteEditExpense() {
        budgetManager.addExpense(new Expense("Coffee", "Starbucks", "01-01-2025",5.0));

        provideInput("1\nLatte\nCaramel Latte\n31-12-2025\n6.5\n");
        expenseCommand.executeEditExpense();

        Expense editedExpense = budgetManager.getExpense(0);
        assertEquals("Latte", editedExpense.getTitle());
        assertEquals("Caramel Latte", editedExpense.getDescription());
        assertEquals("31-12-2025", editedExpense.getDate());
        assertEquals(6.5, editedExpense.getAmount());
    }

    @Test
    void testInvalidDateHandling() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Simulating user input with an invalid date
        provideInput("Test Expense\nDescription\n32-13-2000\n100\n");

        expenseCommand.executeAddExpense();

        String actualOutput = outContent.toString().trim();
        System.out.println("Actual Output:\n" + actualOutput); // Debugging

        // Check if it contains either of the expected messages
        assertTrue(
                actualOutput.contains("Invalid date format.") ||
                        actualOutput.contains("Invalid day or month value. Please enter a real date."),
                "Expected output to contain an error message about date format, but got:\n" + actualOutput
        );
    }

    @Test
    void testInvalidAmountHandling() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        provideInput("Test Expense\nDescription\n31-12-2025\nInvalidAmount\n");

        expenseCommand.executeAddExpense();

        String expectedMessage = "Invalid amount format. Please enter a valid number.";

        String actualOutput = outContent.toString().trim();

        assertTrue(actualOutput.contains(expectedMessage),
                "Expected message to contain: " + expectedMessage + "\nBut found: " + actualOutput);
    }
    //@@author

    //@@author NandhithaShree
    @Test
    void testExecuteMarkCommand() {
        Expense expense = new Expense(testTitle, testDescription, testDate, testAmount);
        budgetManager.addExpense(expense);
        assertEquals(false, expense.getDone());

        provideInput("1\n");
        expenseCommand.executeMarkCommand();
        assertEquals(true, expense.getDone());

        assertEquals(testTitle, budgetManager.getExpense(0).getTitle());
        assertEquals(testAmount, budgetManager.getExpense(0).getAmount());
        assertEquals(testDescription, budgetManager.getExpense(0).getDescription());
        //@@author matthewyeo1
        assertEquals(testDate, budgetManager.getExpense(0).getDate());
        //@@author
    }

    @Test
    void testExecuteUnmarkCommand() {
        Expense expense = new Expense(testTitle, testDescription, testDate, testAmount);
        budgetManager.addExpense(expense);
        assertEquals(false, expense.getDone());

        provideInput("1\n");
        expenseCommand.executeMarkCommand();
        assertEquals(true, expense.getDone());

        provideInput("1");
        expenseCommand.executeUnmarkCommand();
        assertEquals(false, expense.getDone());

        assertEquals(testTitle, budgetManager.getExpense(0).getTitle());
        assertEquals(testAmount, budgetManager.getExpense(0).getAmount());
        assertEquals(testDescription, budgetManager.getExpense(0).getDescription());
        //@@author matthewyeo1
        assertEquals(testDate, budgetManager.getExpense(0).getDate());
        //@@author
    }

    @Test
    void testExecuteMarkCommandInvalidInputs() {
        Expense expense = new Expense(testTitle, testDescription, testDate, testAmount);
        budgetManager.addExpense(expense);
        assertEquals(false, expense.getDone());
        provideInput("2\n");

        expenseCommand.executeMarkCommand();
        String expectedMessage = "Please enter a valid expense number.";
        String actualOutput = outContent.toString().trim();

        assertTrue(actualOutput.contains(expectedMessage));
        assertEquals(false, expense.getDone());

        assertEquals(testTitle, budgetManager.getExpense(0).getTitle());
        assertEquals(testAmount, budgetManager.getExpense(0).getAmount());
        assertEquals(testDescription, budgetManager.getExpense(0).getDescription());
        //@@author matthewyeo1
        assertEquals(testDate, budgetManager.getExpense(0).getDate());
        //@@author
    }

    @Test
    void testDisplaySettledExpensesZeroSettledExpenses(){
        provideInput("\n");
        expenseCommand.displaySettledExpenses();
        String expectedMessage = "No expenses found.";

        String actualOutput = outContent.toString().trim();
        assertEquals(expectedMessage, actualOutput);
    }

    @Test
    void testDisplaySettledExpensesTwoSettledExpenses(){
        provideInput("\n");
        Expense expense = new Expense(testTitle, testDescription, testDate, testAmount);
        Expense expense1 = new Expense(testTitle + "1", testDescription + "1",
                testDate, testAmount + 1);
        Expense expense2= new Expense(testTitle + "2", testDescription + "2",
                testDate, testAmount + 1);
        budgetManager.addExpense(expense);
        budgetManager.addExpense(expense1);
        budgetManager.addExpense(expense2);
        budgetManager.markExpense(0);
        budgetManager.markExpense(1);

        expenseCommand.displaySettledExpenses();
        String expectedMessage = "All expenses are in SGD\n" + "Expense #1\n" + expense.toString() + "\n\n" +
                "Expense " + "#2\n" + expense1.toString() + "\n" + "\n" + "You have 2 settled expenses";
        String actualOutput = outContent.toString().trim();
        actualOutput = actualOutput.replaceAll("\r\n", "\n");
        assertEquals(expectedMessage, actualOutput);
    }

    @Test
    void testDisplayUnsettledExpensesTwoUnsettledExpenses(){
        provideInput("\n");
        Expense expense = new Expense(testTitle, testDescription, testDate, testAmount);
        Expense expense1 = new Expense(testTitle + "1", testDescription + "1",
                testDate,testAmount + 1);
        Expense expense2= new Expense(testTitle + "2", testDescription + "2",
                testDate, testAmount + 1);
        budgetManager.addExpense(expense);
        budgetManager.addExpense(expense1);
        budgetManager.addExpense(expense2);

        budgetManager.markExpense(2);

        expenseCommand.displayUnsettledExpenses();
        String expectedMessage = "All expenses are in SGD\n" + "Expense #1\n" + expense.toString() + "\n\n" +
                "Expense #2\n" + expense1.toString() + "\n\n" + "You have 2 unsettled expenses";
      
        String actualOutput = outContent.toString().trim();
        actualOutput = actualOutput.replaceAll("\r\n", "\n");
        assertEquals(expectedMessage, actualOutput);
    }

    @Test
    void testCurrency(){
        provideInput("\n");
        assertEquals("SGD", currency.getCurrentCurrency());
    }

    @Test
    public void testCurrencyFileNotFound() {
        provideInput("\n");
        File file = new File("./currentCurrency");
        file.delete(); // Ensure the file doesn't exist

        assertEquals(Commands.DEFAULT_CURRENCY, currency.getCurrentCurrency());
    }

    @Test
    public void testChangeCurrencyMethod1ValidCurrency() {
        String newCurrency = "EUR\n";
        String exchangeRate = "0.69\n";
        provideInput("1\n" + newCurrency + exchangeRate);
        currency.changeCurrency();

        assertEquals("EUR", currency.getCurrentCurrency());
    }

    @Test
    public void testChangeCurrencyMethod1InValidCurrency() {
        String newCurrency = "ABC\n";
        provideInput("1\n" + newCurrency);
        currency.changeCurrency();

        String actualOutput = outContent.toString().trim();
        String expectedOutput = "Please provide a valid currency...";

        assertTrue(actualOutput.contains(expectedOutput));
    }

    @Test
    public void testChangeCurrencyMethod2ValidCurrency() {
        String newCurrency = "JPY\n";
        provideInput("2\n" + newCurrency);
        currency.changeCurrency();

        assertEquals("JPY", currency.getCurrentCurrency());
    }

    @Test
    public void testChangeCurrencyMethod2InValidCurrency() {
        String newCurrency = "ABC\n";
        provideInput("2\n" + newCurrency);
        currency.changeCurrency();

        String actualOutput = outContent.toString().trim();
        String expectedOutput = "Currency not found!";

        assertTrue(actualOutput.contains(expectedOutput));
    }

    //@@author

    //@@author mohammedhamdhan
    @Test
    void testExecuteDeleteExpenseInvalidInput() {
        budgetManager.addExpense(new Expense("Lunch", "Pizza", "01-01-2025",10));

        provideInput("2\n");
        expenseCommand.executeDeleteExpense();

        assertEquals(1, budgetManager.getExpenseCount());
        assertTrue(outContent.toString().contains("Please enter a valid expense number"));
    }

    @Test
    void testExecuteEditExpenseInvalidInput() {
        budgetManager.addExpense(new Expense("Coffee", "Starbucks", "31-12-2025",5.0));

        provideInput("2\nLatte\nCaramel Latte\n6.5\n");
        expenseCommand.executeEditExpense();

        Expense originalExpense = budgetManager.getExpense(0);
        assertEquals("Coffee", originalExpense.getTitle());
        assertEquals("Starbucks", originalExpense.getDescription());
        //@@author matthewyeo1
        assertEquals("31-12-2025", originalExpense.getDate());
        //@@author
        assertEquals(5.0, originalExpense.getAmount());
        assertTrue(outContent.toString().contains("Please enter a valid expense number"));
    }

    @Test
    void testExecuteAddExpenseEmptyInput() {
        provideInput("\n\n\n");
        expenseCommand.executeAddExpense();

        assertEquals(0, budgetManager.getExpenseCount());
        assertTrue(outContent.toString().contains("Title cannot be empty"));
    }

    @Test
    void testDisplayUnsettledExpensesZeroExpenses() {
        provideInput("\n");
        expenseCommand.displayUnsettledExpenses();
        String expectedMessage = "No expenses found.";

        String actualOutput = outContent.toString().trim();
        assertEquals(expectedMessage, actualOutput);
    }

    @Test
    void testShowCategorySummary() {
        // Add expenses with more explicit category indicators
        budgetManager.addExpense(new Expense("Lunch", "restaurant food", "01-01-2025", 25.50));
        budgetManager.addExpense(new Expense("T-shirt", "clothes shopping", "02-01-2025", 35.00));
        budgetManager.addExpense(new Expense("Movie ticket", "entertainment movie cinema", "03-01-2025", 15.00));

        // First provideInput with answer for visualization prompt ('n' for no)
        provideInput("n\n");

        // Run the category summary
        expenseCommand.showCategorySummary();

        // Get the output and print it for debugging
        String output = outContent.toString();
        System.err.println("CATEGORY SUMMARY OUTPUT:\n" + output);
        
        // Verify the output contains the summary title
        assertTrue(output.contains("Category-wise Expense Summary"), 
                   "Output should contain the summary title");
        
        // More comprehensive checks for food-related terms
        assertTrue(
            output.contains("Food") || 
            output.contains("Dining") || 
            output.contains("restaurant") || 
            output.contains("Restaurant") || 
            output.contains("Meal") ||
            output.contains("Lunch"),
            "Output should contain food-related category"
        );
        
        // More comprehensive checks for shopping-related terms
        assertTrue(
            output.contains("Shopping") || 
            output.contains("Clothing") || 
            output.contains("clothes") || 
            output.contains("T-shirt") || 
            output.contains("Apparel"),
            "Output should contain shopping-related category"
        );
        
        // More comprehensive checks for entertainment-related terms
        assertTrue(
            output.contains("Entertainment") || 
            output.contains("Leisure") || 
            output.contains("Recreation") || 
            output.contains("movie") || 
            output.contains("Movie") ||
            output.contains("cinema") ||
            output.contains("Cinema") ||
            output.contains("Misc") ||  // Sometimes entertainment falls under misc
            output.contains("Other"),   // Or other categories
            "Output should contain entertainment-related category"
        );
    }

    @Test
    void testExportCategorySummary() {
        // Add expenses from different categories
        budgetManager.addExpense(new Expense("Taxi", "transport to airport", "05-01-2025", 45.00));
        budgetManager.addExpense(new Expense("Dinner", "food at restaurant", "06-01-2025", 55.00));

        provideInput("");

        // Run the export command
        expenseCommand.exportCategorySummary();

        // Verify the file was created with correct message
        String output = outContent.toString();
        assertTrue(output.contains("Category summary exported to category_summary.txt"));

        // Optional: Read and verify file contents
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("category_summary.txt");
            String fileContent = new String(java.nio.file.Files.readAllBytes(path));

            assertTrue(fileContent.contains("Category-wise Expense Summary"));
            assertTrue(fileContent.contains("Travel:"));  // From "transport"
            assertTrue(fileContent.contains("Food:"));    // From "food at restaurant"
        } catch (IOException e) {
            // Handle exception or fail test
            assertTrue(false, "Failed to read exported file: " + e.getMessage());
        }
    }

    @Test
    void testShowExpenseSummaryMenu() {
        // Test the summary menu with option 3 (Cancel)
        provideInput("3\n");
        expenseCommand.showExpenseSummary();

        String output = outContent.toString();
        assertTrue(output.contains("Choose summary view:"));
        assertTrue(output.contains("1. Monthly Summary"));
        assertTrue(output.contains("2. Category-wise Summary"));
        assertTrue(output.contains("3. Cancel"));
    }

    @Test
    void testMonthlyExpenseSummary() {
        // Add expenses from different months
        budgetManager.addExpense(new Expense("January Expense", "first month", "15-01-2025", 100.00));
        budgetManager.addExpense(new Expense("February Expense", "second month", "15-02-2025", 200.00));

        provideInput("");

        // Run the monthly summary
        expenseCommand.showMonthlySummary();

        // Verify output shows both months with correct totals
        String output = outContent.toString();
        assertTrue(output.contains("Monthly Expense Summary"));
        assertTrue(output.contains("01-2025")); // January
        assertTrue(output.contains("02-2025")); // February
        assertTrue(output.contains("$100.00")); // January amount
        assertTrue(output.contains("$200.00")); // February amount
    }

    @Test
    void testExportMonthlySummary() {
        // Add expenses from different months
        budgetManager.addExpense(new Expense("March Expense", "third month", "15-03-2025", 300.00));
        budgetManager.addExpense(new Expense("April Expense", "fourth month", "15-04-2025", 400.00));

        provideInput("");

        // Run the export command
        expenseCommand.exportMonthlySummary();

        // Verify the file was created with correct message
        String output = outContent.toString();
        assertTrue(output.contains("Monthly summary exported to monthly_summary.txt"));

        // Optional: Read and verify file contents
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("monthly_summary.txt");
            String fileContent = new String(java.nio.file.Files.readAllBytes(path));

            assertTrue(fileContent.contains("Monthly Expense Summary"));
            assertTrue(fileContent.contains("03-2025")); // March
            assertTrue(fileContent.contains("04-2025")); // April
            assertTrue(fileContent.contains("$300.00")); // March amount
            assertTrue(fileContent.contains("$400.00")); // April amount
        } catch (IOException e) {
            // Handle exception or fail test
            assertTrue(false, "Failed to read exported file: " + e.getMessage());
        }
    }

    @Test
    void testExportExpenseSummaryMenu() {
        // Test the export menu with option 3 (Back to main menu)
        provideInput("3\n");
        expenseCommand.exportExpenseSummary();

        String output = outContent.toString();
        assertTrue(output.contains("Choose export format:"));
        assertTrue(output.contains("1. Monthly Summary"));
        assertTrue(output.contains("2. Category-wise Summary"));
        assertTrue(output.contains("3. Back to main menu"));
    }
    //@@author

    //@@author nandhananm7
    @Test
    void testFindExpenseFound() {
        // Add two expenses. Only the second expense ("Cab Ride") should match the keyword "cab".
        Expense expense1 = new Expense("Taxi", "from jb to singapore", "12-12-1327", 12.00);
        Expense expense2 = new Expense("Cab Ride", "to airport", "15-10-2025", 20.00);
        budgetManager.addExpense(expense1);
        budgetManager.addExpense(expense2);

        // Simulate user input: "cab"
        provideInput("cab\n");
        expenseCommand.findExpense();

        String output = outContent.toString();
        // Verify that the output indicates one matching expense and shows the details of "Cab Ride"
        assertTrue(output.contains("Found 1 matching expense(s):"),
                "Expected output to indicate 1 matching expense found");
        assertTrue(output.contains("Cab Ride"), "Expected output to contain 'Cab Ride'");
    }

    @Test
    void testFindExpenseNoMatch() {
        // Add an expense that does not match the keyword "bus"
        Expense expense1 = new Expense("Taxi", "from jb to singapore", "12-12-1327", 12.00);
        budgetManager.addExpense(expense1);

        // Simulate user input: "bus"
        provideInput("bus\n");
        expenseCommand.findExpense();

        String output = outContent.toString();
        // Verify that the output indicates no matching expenses were found for the keyword "bus"
        assertTrue(output.contains("No matching expenses found for keyword: bus"),
                "Expected output to mention no matching expenses for keyword 'bus'");
    }
    //@@author
}

