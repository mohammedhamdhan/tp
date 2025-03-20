package seedu.duke.commands;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    /**
     * A simple test implementation for GroupManager that stores groups in a list.
     */
    class TestGroupManager extends GroupManager {
        private List<Group> groups = new ArrayList<>();

        /**
         * Adds a group with the given name and members.
         */
        public void addGroup(String groupName, List<Friend> members) {
            Group group = new Group(groupName);
            for (Friend friend : members) {
                group.addFriend(friend);
            }
            groups.add(group);
        }

        @Override
        public boolean groupExists(String groupName) {
            for (Group group : groups) {
                if (group.getName().equals(groupName)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public List<Friend> getGroupMembers(String groupName) {
            for (Group group : groups) {
                if (group.getName().equals(groupName)) {
                    return group.getFriends();
                }
            }
            return new ArrayList<>();
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
        Scanner scanner = new Scanner(in);
        splitCommand = new SplitCommand(scanner, groupManager);
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
        Expense expense = new Expense("Lunch", "Meal", 100.0);
        budgetManager.addExpense(expense);

        // Create a group "friends" with two members.
        List<Friend> members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
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
    }

    @Test
    void testExecuteSplitManualSplit() {
        // Add an expense so DataStorage.loadExpenses() returns it.
        Expense expense = new Expense("Dinner", "Evening meal", 100.0);
        budgetManager.addExpense(expense);

        // Create a group "friends" with two members.
        List<Friend> members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
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
    }

    @Test
    void testExecuteSplitInvalidExpenseSelection() {
        // Add a single expense.
        Expense expense = new Expense("Snack", "Small bite", 20.0);
        budgetManager.addExpense(expense);

        // Provide an invalid expense number "5".
        provideInput("1\n5\n");
        splitCommand.executeSplit();

        String output = outContent.toString();
        assertTrue(output.contains("Invalid expense number."), "Expected message for invalid expense selection.");
    }
}
