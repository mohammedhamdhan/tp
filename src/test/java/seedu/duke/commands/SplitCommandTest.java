package seedu.duke.commands;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.expense.BudgetManager;
import seedu.duke.expense.Expense;
import seedu.duke.friends.Friend;
import seedu.duke.friends.Group;
import seedu.duke.friends.GroupManager;
import seedu.duke.storage.DataStorage;

class SplitCommandTest {
    private BudgetManager budgetManager;
    private SplitCommand splitCommand;
    private GroupManager groupManager; // our test group manager
    private FriendsCommands friendsCommand;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    /**
     * A simple test implementation for GroupManager that stores groups in a list.
     */
    class TestGroupManager extends GroupManager {
        private List < Group > groups = new ArrayList < > ();

        /**
         * Adds a group with the given name and members.
         */
        public void addGroup(String groupName, List < Friend > members) {
            Group group = new Group(groupName);
            for (Friend friend: members) {
                group.addFriend(friend);
            }
            groups.add(group);
        }

        @Override
        public boolean groupExists(String groupName) {
            for (Group group: groups) {
                if (group.getName().equals(groupName)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public List < Friend > getGroupMembers(String groupName) {
            for (Group group: groups) {
                if (group.getName().equals(groupName)) {
                    return group.getFriends();
                }
            }
            return new ArrayList < > ();
        }
    }

    @BeforeEach
    void setUp() {
        // Initialize a new BudgetManager (which loads expenses via DataStorage)
        budgetManager = new BudgetManager();
        // Clear any existing expenses.
        DataStorage.resetExpenses();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Use our simple test implementation for GroupManager.
        groupManager = new TestGroupManager();

        // Override the file used by SplitCommand to avoid modifying production files.
        SplitCommand.OwesStorage.owesFile = "testOwedAmounts.txt";
        // Clear the test owed amounts file.
        try (PrintWriter writer = new PrintWriter(SplitCommand.OwesStorage.owesFile)) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
        // Clear the expenses file and the test owed amounts file.
        try (PrintWriter writer = new PrintWriter("expenses.txt")) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(SplitCommand.OwesStorage.owesFile)) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to provide input and create a new SplitCommand instance.
     */
    void provideInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner( in );
        // Initialize friendsCommands using the groupManager.
        friendsCommand = new FriendsCommands(groupManager);
        splitCommand = new SplitCommand(scanner, groupManager, friendsCommand);
    }

    @Test
    void testExecuteSplitCancel() {
        provideInput("x\n");
        splitCommand.executeSplit();
        String output = outContent.toString();
        assertTrue(output.contains("Split cancelled."), "Expected cancellation message.");
    }

    @Test
    void testExecuteSplitNoExpenses() {
        // With no expense added, splitting should not proceed.
        provideInput("1\n");
        splitCommand.executeSplit();
        String output = outContent.toString();
        assertTrue(output.contains("No expenses available to split."), "Expected message when no expenses exist.");
    }

    @Test
    void testExecuteSplitEqualSplit() {
        // Add an expense so DataStorage.loadExpenses() returns it.
        Expense expense = new Expense("Lunch", "Meal", "01-01-2025", 100.0);
        budgetManager.addExpense(expense);

        // Create a group "friends" with two members.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);

        // Provide input:
        // Option: "1" for equal split,
        // Expense selection: "1",
        // Group name: "friends"
        provideInput("1\n1\nfriends\n");
        splitCommand.executeSplit();

        String output = outContent.toString();
        // Both friends should owe 50.00 each.
        assertTrue(output.contains("Alice owes: 50.00"), "Expected Alice to owe 50.00.");
        assertTrue(output.contains("Bob owes: 50.00"), "Expected Bob to owe 50.00.");

        // Additional asserts:
        File owesFile = new File(SplitCommand.OwesStorage.owesFile);
        assertTrue(owesFile.exists(), "Expected owes file to exist after equal split.");
        try {
            String fileContent = new String(java.nio.file.Files.readAllBytes(owesFile.toPath()));
            assertTrue(fileContent.contains("Alice owes: 50.00"), "Expected owes file to include Alice owes: 50.00.");
            // New assertEquals to check the exact file content.
            String expectedContent = " - Alice owes: 50.00\n - Bob owes: 50.00\n";
            assertEquals(expectedContent, fileContent, "File content does not match");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testExecuteSplitManualSplit() {
        // Add an expense so DataStorage.loadExpenses() returns it.
        Expense expense = new Expense("Dinner", "Evening meal", "31-12-2025", 100.0);
        budgetManager.addExpense(expense);

        // Create a group "friends" with two members.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);

        // Provide input for manual split:
        // Option: "2" for manual split,
        // Expense selection: "1",
        // Group name: "friends",
        // For Alice: choose flat amount (/a) with value 30,
        // For Bob: choose percentage (/p) with value 70.
        String input = "2\n1\nfriends\n" +
            "/a\n30\n" +
            "/p\n70\n";
        provideInput(input);
        splitCommand.executeSplit();

        String output = outContent.toString();
        assertTrue(output.contains("Alice owes: 30.00"), "Expected Alice to owe 30.00.");
        assertTrue(output.contains("Bob owes: 70.00"), "Expected Bob to owe 70.00.");

        // New assertEquals to check the exact file content for manual split.
        File owesFile = new File(SplitCommand.OwesStorage.owesFile);
        try {
            String fileContent = new String(java.nio.file.Files.readAllBytes(owesFile.toPath()));
            String expectedContent = " - Alice owes: 30.00\n - Bob owes: 70.00\n";
            assertEquals(expectedContent, fileContent, "File content does not match.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testExecuteSplitManualSplitPercentage() {
        // Add an expense so that DataStorage.loadExpenses() returns it.
        Expense expense = new Expense("Brunch", "Late morning meal", "15-02-2025", 200.0);
        budgetManager.addExpense(expense);

        // Create a group "friends" with three members.
        List < Friend > members = Arrays.asList(
            new Friend("Alice", "friends"),
            new Friend("Bob", "friends"),
            new Friend("Charlie", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);

        // Test manual split using percentage.
        // For each member, provide percentages that sum to 100.
        // For example, assign: Alice 20%, Bob 30%, Charlie 50%.
        String input = "2\n1\nfriends\n/p\n20\n/p\n30\n/p\n50\n";
        provideInput(input);
        splitCommand.executeSplit();

        String output = outContent.toString();
        // Calculate assigned amounts.
        double aliceShare = 200.0 * (20.0 / 100.0);
        double bobShare = 200.0 * (30.0 / 100.0);
        double charlieShare = 200.0 * (50.0 / 100.0);
        assertTrue(output.contains("Alice owes: " + String.format("%.2f", aliceShare)), "Incorrect share for Alice.");
        assertTrue(output.contains("Bob owes: " + String.format("%.2f", bobShare)), "Incorrect share for Bob.");
        assertTrue(output.contains("Charlie owes: " + String.format("%.2f", charlieShare)), "Incrrect share for Charlie.");

        File owesFile = new File(SplitCommand.OwesStorage.owesFile);
        try {
            String fileContent = new String(Files.readAllBytes(owesFile.toPath()));
            String expectedContent =
                " - Alice owes: " + String.format("%.2f", aliceShare) + "\n" +
                " - Bob owes: " + String.format("%.2f", bobShare) + "\n" +
                " - Charlie owes: " + String.format("%.2f", charlieShare) + "\n";
            assertEquals(expectedContent, fileContent, "File content does not match for manual percentage split.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void testExecuteSplitManualSplitInvalidMethod() {
        // Add an expense so that DataStorage.loadExpenses() returns it.
        Expense expense = new Expense("Snack", "Light bite", "10-03-2025", 50.0);
        budgetManager.addExpense(expense);

        // Create a group "friends" with one member.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);

        // Provide an invalid method (not /a or /p). The assertion in production code should trigger.
        // However, since assertions are generally disabled at runtime, we simulate by checking the output message.
        provideInput("2\n1\nfriends\ninvalid_method\n");
        splitCommand.executeSplit();

        String output = outContent.toString();
        // Expect the output to mention "Invalid method. Cancelling split." due to the check.
        assertTrue(output.contains("Invalid method. Cancelling split."), "Expected cancellation due to invalid method input.");
    }

    @Test
    void testExecuteSplitInvalidExpenseSelection() {
        // Add a single expense.
        Expense expense = new Expense("Snack", "Small bite", "23-08-2025", 20.0);
        budgetManager.addExpense(expense);

        // Provide an invalid expense number "5".
        provideInput("1\n5\n");
        splitCommand.executeSplit();

        String output = outContent.toString();
        assertTrue(output.contains("Invalid expense number."), "Expected message for invalid expense selection.");
    }
    @Test
    void testExecuteSplitWithInvalidNumericInputForAmount() {
        // Test that if the user enters non-numeric values during manual absolute splitting,
        // the program prompts for re-entry.
        Expense expense = new Expense("Dinner", "Evening meal", "31-12-2025", 100.0);
        budgetManager.addExpense(expense);
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);

        // For manual absolute splitting, simulate an invalid amount for the first member followed by a valid one.
        String input = "2\n1\nfriends\n/a\nabc\n30\n/a\n70\n";
        provideInput(input);
        splitCommand.executeSplit();

        String output = outContent.toString();
        // Check that the output contains a prompt for "Invalid amount. Please try again."
        assertTrue(output.contains("Invalid amount. Please try again."), "Expected message for invalid numeric input.");
        // Also check that final amounts are correctly processed.
        assertTrue(output.contains("Alice owes: 30.00"), "Expected Alice to owe 30.00 after re-entry.");
        assertTrue(output.contains("Bob owes: 70.00"), "Expected Bob to owe 70.00 after re-entry.");
    }
}
