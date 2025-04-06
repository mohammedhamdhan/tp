package seedu.duke.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
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
    private FriendsCommands friendsCommand;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    /**
     * A simple test implementation for GroupManager that stores groups in a list.
     */
    class TestGroupManager extends GroupManager {
        private List < Group > groups = new ArrayList < > ();

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
        // Initialize BudgetManager (which loads expenses via DataStorage) 
        //clear any existing expenses.
        budgetManager = new BudgetManager();
        DataStorage.resetExpenses();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Use our simple test implementation for GroupManager.
        groupManager = new TestGroupManager();

        // Override the file used by SplitCommand to avoid modifying production files.
        SplitCommand.OwesStorage.owesFile = "testOwedAmounts.txt";
        // Clear the test owes file.
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
        // Clear expenses and test owes file.
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
     * Helper method to provide input for subsequent prompts.
     */
    void provideInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner( in );
        friendsCommand = new FriendsCommands(groupManager);
        splitCommand = new SplitCommand(scanner, groupManager, friendsCommand);
    }

    @Test
    void testInvalidCommandFormat() {
        // Command missing the required "/" delimiters.
        provideInput("");
        splitCommand.executeSplit("splitassign/1/testgroup");
        String output = outContent.toString();
        assertTrue(output.contains("Invalid command format"), "Invalid command format.");
    }

    @Test
    void testNegativeExpenseIndex() {
        // Command with negative expense index.
        provideInput("");
        splitCommand.executeSplit("split/equal/-1/testgroup");
        String output = outContent.toString();
        assertTrue(output.contains("Expense index is not a positive integer"),
            "Negative expense index.");
    }

    @Test
    void testNonNumericExpenseIndex() {
        // Command with non-numeric expense index.
        provideInput("");
        splitCommand.executeSplit("split/equal/abc/testgroup");
        String output = outContent.toString();
        assertTrue(output.contains("Invalid expense index:"),
            "Expected error for non-numeric expense index.");
    }

    @Test
    void testNoExpensesAvailable() {
        // With no expenses added, splitting should fail.
        provideInput("");
        splitCommand.executeSplit("split/equal/1/testgroup");
        String output = outContent.toString();
        assertTrue(output.contains("No expenses available to split"),
            "Expected error when no expenses exist.");
    }

    @Test
    void testGroupNotFound() {
        // Add an expense so that expense exists.
        Expense expense = new Expense("Lunch", "Meal", "01-01-2025", 100.0);
        budgetManager.addExpense(expense);
        // No group "friends" exists.
        provideInput("");
        splitCommand.executeSplit("split/equal/1/friends");
        String output = outContent.toString();
        assertTrue(output.contains("Group 'friends' not found"),
            "Expected error for missing group.");
    }

    @Test
    void testEmptyGroup() {
        // Add an expense.
        Expense expense = new Expense("Lunch", "Meal", "01-01-2025", 100.0);
        budgetManager.addExpense(expense);
        // Create group "friends" but with no members.
        ((TestGroupManager) groupManager).addGroup("friends", new ArrayList < > ());
        provideInput("");
        splitCommand.executeSplit("split/equal/1/friends");
        String output = outContent.toString();
        assertTrue(output.contains("No members in group 'friends'"),
            "Expected error for empty group.");
    }

    @Test
    void testEqualSplitValid() {
        // Add an expense.
        Expense expense = new Expense("Lunch", "Meal", "01-01-2025", 100.0);
        budgetManager.addExpense(expense);
        // Create group "friends" with two members.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);
        provideInput(""); // No further input needed.
        splitCommand.executeSplit("split/equal/1/friends");
        String output = outContent.toString();
        // Expected detailed transaction record for each member.
        String expectedAlice = "Transaction: Expense: Lunch, Date: 01-01-2025, Group: friends, Member: Alice owes: 50.00";
        String expectedBob = "Transaction: Expense: Lunch, Date: 01-01-2025, Group: friends, Member: Bob owes: 50.00";
        assertTrue(output.contains(expectedAlice), "Expected Alice to owe 50.00.");
        assertTrue(output.contains(expectedBob), "Expected Bob to owe 50.00.");

        // Verify file content.
        File owesFile = new File(SplitCommand.OwesStorage.owesFile);
        assertTrue(owesFile.exists(), "Expected owes file to exist after equal split.");
        try {
            String fileContent = new String(Files.readAllBytes(owesFile.toPath()));
            String expectedContent = expectedAlice + "\n" + expectedBob + "\n";
            assertEquals(expectedContent, fileContent, "File content does not match for equal split.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDuplicateSplitPrevention() {
        // Add an expense.
        Expense expense = new Expense("Dinner", "Evening meal", "31-12-2025", 120.0);
        budgetManager.addExpense(expense);
        // Create group "friends" with two members.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);
        // First valid split.
        provideInput("");
        splitCommand.executeSplit("split/equal/1/friends");
        // Clear output before attempting duplicate split.
        outContent.reset();
        // Attempt duplicate split on same expense and group.
        provideInput("");
        splitCommand.executeSplit("split/equal/1/friends");
        String output = outContent.toString();
        assertTrue(output.contains("This expense has already been split for group 'friends'"),
            "Expected duplicate split prevention message.");
    }

    @Test
    void testManualSplitAbsoluteValid() {
        // Add an expense.
        Expense expense = new Expense("Dinner", "Evening meal", "31-12-2025", 100.0);
        budgetManager.addExpense(expense);
        // Create group "friends" with two members.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);
        // Provide input for manual splitting with absolute amounts:
        // Method: "/a", then for Alice: 30, for Bob: 70.
        String input = "/a\n30\n70\n";
        provideInput(input);
        splitCommand.executeSplit("split/assign/1/friends");
        String output = outContent.toString();
        String expectedAlice = "Transaction: Expense: Dinner, Date: 31-12-2025, Group: friends, Member: Alice owes: 30.00";
        String expectedBob = "Transaction: Expense: Dinner, Date: 31-12-2025, Group: friends, Member: Bob owes: 70.00";
        assertTrue(output.contains(expectedAlice), "Expected Alice to owe 30.00.");
        assertTrue(output.contains(expectedBob), "Expected Bob to owe 70.00.");

        // Verify file content.
        File owesFile = new File(SplitCommand.OwesStorage.owesFile);
        try {
            String fileContent = new String(Files.readAllBytes(owesFile.toPath()));
            String expectedContent = expectedAlice + "\n" + expectedBob + "\n";
            assertEquals(expectedContent, fileContent, "File content does not match for manual absolute split.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testManualSplitPercentageValid() {
        // Add an expense.
        Expense expense = new Expense("Brunch", "Late morning meal", "15-02-2025", 200.0);
        budgetManager.addExpense(expense);
        // Create group "friends" with three members.
        List < Friend > members = Arrays.asList(
            new Friend("Alice", "friends"),
            new Friend("Bob", "friends"),
            new Friend("Charlie", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);
        // Provide input for manual splitting with percentages:
        // Method: "/p", then assign: Alice 20%, Bob 30%, Charlie 50%.
        String input = "/p\n20\n30\n50\n";
        provideInput(input);
        splitCommand.executeSplit("split/assign/1/friends");
        String output = outContent.toString();
        double aliceShare = 200.0 * (20.0 / 100.0);
        double bobShare = 200.0 * (30.0 / 100.0);
        double charlieShare = 200.0 * (50.0 / 100.0);
        String expectedAlice = "Transaction: Expense: Brunch, Date: 15-02-2025, Group: friends, Member: Alice owes: " +
            String.format("%.2f", aliceShare);
        String expectedBob = "Transaction: Expense: Brunch, Date: 15-02-2025, Group: friends, Member: Bob owes: " +
            String.format("%.2f", bobShare);
        String expectedCharlie = "Transaction: Expense: Brunch, Date: 15-02-2025, Group: friends, Member: Charlie owes: " +
            String.format("%.2f", charlieShare);
        assertTrue(output.contains(expectedAlice), "Incorrect share for Alice.");
        assertTrue(output.contains(expectedBob), "Incorrect share for Bob.");
        assertTrue(output.contains(expectedCharlie), "Incorrect share for Charlie.");

        File owesFile = new File(SplitCommand.OwesStorage.owesFile);
        try {
            String fileContent = new String(Files.readAllBytes(owesFile.toPath()));
            String expectedContent = expectedAlice + "\n" +
                expectedBob + "\n" +
                expectedCharlie + "\n";
            assertEquals(expectedContent, fileContent, "File content does not match for manual percentage split.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testManualSplitInvalidMethodThenValid() {
        // Add an expense.
        Expense expense = new Expense("Snack", "Light bite", "10-03-2025", 50.0);
        budgetManager.addExpense(expense);
        // Create group "friends" with one member.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);
        // Provide input: first an invalid method, then valid method "/a", then amount.
        String input = "invalid_method\n/ a\n/a\n25\n";
        provideInput(input);
        splitCommand.executeSplit("split/assign/1/friends");
        String output = outContent.toString();
        assertTrue(output.contains("Invalid method. Please enter '/a' for absolute amounts or '/p' for percentages."),
            "Expected re-prompt for invalid method.");
        // Check that eventually a valid split occurs (Alice owes 25.00).
        String expectedAlice = "Transaction: Expense: Snack, Date: 10-03-2025, Group: friends, Member: Alice owes: 25.00";
        assertTrue(output.contains(expectedAlice), "Expected Alice to owe 25.00 after valid method input.");
    }

    @Test
    void testManualSplitAbsoluteInvalidNumericThenValid() {
        // Add an expense.
        Expense expense = new Expense("Dinner", "Evening meal", "31-12-2025", 100.0);
        budgetManager.addExpense(expense);
        // Create group "friends" with two members.
        List < Friend > members = Arrays.asList(new Friend("Alice", "friends"), new Friend("Bob", "friends"));
        ((TestGroupManager) groupManager).addGroup("friends", members);
        // For absolute splitting, simulate non-numeric input for Alice then valid input.
        String input = "/a\nabc\n30\n70\n";
        provideInput(input);
        splitCommand.executeSplit("split/assign/1/friends");
        String output = outContent.toString();
        assertTrue(output.contains("Invalid amount format for Alice"), "Expected invalid numeric input error for Alice.");
        String expectedAlice = "Transaction: Expense: Dinner, Date: 31-12-2025, Group: friends, Member: Alice owes: 30.00";
        String expectedBob = "Transaction: Expense: Dinner, Date: 31-12-2025, Group: friends, Member: Bob owes: 70.00";
        assertTrue(output.contains(expectedAlice), "Expected Alice to owe 30.00 after re-entry.");
        assertTrue(output.contains(expectedBob), "Expected Bob to owe 70.00 after re-entry.");
    }
}
