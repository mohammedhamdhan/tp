package seedu.duke.commands;

import java.io.*;
import java.util.Scanner;

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
        try (FileWriter writer = new FileWriter(file, false)) { // Open in overwrite mode
            writer.write(""); // Clear file contents
        } catch (IOException e) {
            e.printStackTrace();
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

        provideInput("1\n");
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
        String expectedMessage = "All expenses are in SGD\n" + "Expense #1\n" + expense.toString() + "\n\n" + "Expense #2\n" + expense1.toString()
                + "\n\n" + "You have 2 unsettled expenses";
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
    //@@author
}

