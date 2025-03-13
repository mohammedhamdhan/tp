package seedu.duke.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.duke.expense.BudgetManager;
import seedu.duke.expense.Expense;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@@author matthewyeo1
class ExpenseCommandTest {
    private BudgetManager budgetManager;
    private ExpenseCommand expenseCommand;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

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
    }

    void provideInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        expenseCommand = new ExpenseCommand(budgetManager, scanner);
    }

    @Test
    void testExecuteAddExpense() {
        provideInput("Groceries\nWeekly food shopping\n100\n");

        expenseCommand.executeAddExpense();
        assertEquals(1, budgetManager.getExpenseCount());

        Expense addedExpense = budgetManager.getExpense(0);
        assertEquals("Groceries", addedExpense.getTitle());
        assertEquals("Weekly food shopping", addedExpense.getDescription());
        assertEquals(100.0, addedExpense.getAmount());
    }

    @Test
    void testExecuteDeleteExpense() {
        budgetManager.addExpense(new Expense("Lunch", "Pizza", 10));

        provideInput("1\n");
        expenseCommand.executeDeleteExpense();

        assertEquals(0, budgetManager.getExpenseCount());
        assertTrue(outContent.toString().contains("Expense deleted successfully"));
    }

    @Test
    void testExecuteEditExpense() {
        budgetManager.addExpense(new Expense("Coffee", "Starbucks", 5.0));

        provideInput("1\nLatte\nCaramel Latte\n6.5\n");
        expenseCommand.executeEditExpense();

        Expense editedExpense = budgetManager.getExpense(0);
        assertEquals("Latte", editedExpense.getTitle());
        assertEquals("Caramel Latte", editedExpense.getDescription());
        assertEquals(6.5, editedExpense.getAmount());
    }

    @Test
    void testInvalidInputHandling() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        provideInput("Test Expense\nDescription\nInvalid\n");

        expenseCommand.executeAddExpense();

        String expectedMessage = "Invalid amount format. Please enter a valid number.";

        String actualOutput = outContent.toString().trim();

        assertTrue(actualOutput.contains(expectedMessage),
                "Expected message to contain: " + expectedMessage + "\nBut found: " + actualOutput);
    }
}
//@@author
