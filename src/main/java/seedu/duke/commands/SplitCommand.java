package seedu.duke.commands;
import seedu.duke.friends.Friend;
import seedu.duke.friends.GroupManager;
import seedu.duke.expense.Expense;
import seedu.duke.storage.DataStorage;


import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The SplitCommand class provides functionality for splitting an existing expense among group members
 * and appending a list of owed amounts to a file.
 * It allows the user to select an expense from the stored expenses and then choose between:
 *     Splitting the expense equally among all members of a specified group.
 *     Manually assigning amounts for each member in a group. For each member, the user can choose
 *         to assign a flat amount (/a) or a percentage (/p) of the total expense. For percentages,
 *         the owed amount is computed and the computed value is written.
 * The resulting owed amounts are appended to the file.
 */
public class SplitCommand {
    private Scanner scanner;
    private GroupManager groupManager;
    private FriendsCommands friendsCommands;

    /**
     * Constructs a new SplitCommand instance.
     *
     * @param scanner the Scanner for reading user input.
     * @param groupManager the GroupManager to retrieve group information.
     */
    public SplitCommand(Scanner scanner, GroupManager groupManager, FriendsCommands friendsCommands) {
        assert scanner != null : "Scanner must not be null";
        assert groupManager != null : "GroupManager must not be null";
        assert friendsCommands != null : "FriendsCommands must not be null";
        this.scanner = scanner;
        this.groupManager = groupManager;
        this.friendsCommands = friendsCommands;
    }

    /**
     * * Executes the expense splitting feature.
     * The user is first prompted to choose a splitting option:
     * [1] Equal split: Select an expense and divide its amount equally among all members of a group.
     * [2] Manual split: Select an expense and then, for each member in a specified group,
     * assign a flat amount (/a) or a percentage (/p) to determine what they owe.
     * [x] Cancel the operation.
     * The computed owed amounts are appended to a file.
     */
    public void executeSplit() {
        System.out.println("[1] Split equally among all members of the selected group");
        System.out.println("[2] Manually assign amounts for each member in a group");
        System.out.println("[x]: Cancel");
        System.out.print("Enter option: ");

        String option = scanner.nextLine().trim();
        if (option.equalsIgnoreCase("x")) {
            System.out.println("Split cancelled.");
            return;
        }

        // Load existing expenses from storage.
        List < Expense > expenses = DataStorage.loadExpenses();
        assert expenses != null : "Expenses list should not be null";
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses available to split.");
            return;
        }

        // Display list of expenses.
        System.out.println("Available expenses:");
        for (int i = 0; i < expenses.size(); i++) {
            Expense exp = expenses.get(i);
            assert exp != null : "Expense at index " + i + " should not be null";
            System.out.println((i + 1) + ". " +
                exp.getTitle() + " | Amount: " +
                String.format("%.2f", exp.getAmount()));
        }
        
        System.out.print("Enter expense number to split: ");
        String expenseInput = scanner.nextLine().trim();
        int expIndex;
        try {
            expIndex = Integer.parseInt(expenseInput) - 1;
            if (expIndex < 0 || expIndex >= expenses.size()) {
                System.out.println("Invalid expense number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        Expense selectedExpense = expenses.get(expIndex);
        double totalAmount = selectedExpense.getAmount();
        System.out.println("Selected expense amount: " + String.format("%.2f", totalAmount));

        if (option.equals("1")) {
            // Equal split option.
            System.out.print("Enter group name for equal split: ");
            String groupName = scanner.nextLine().trim();
            assert !groupName.isEmpty() : "Group name must not be empty";
            if (!groupManager.groupExists(groupName)) {
                System.out.println("Group not found.");
                return;
            }
            List < Friend > members = groupManager.getGroupMembers(groupName);
            if (members == null || members.isEmpty()) {
                System.out.println("No members in group. Cancelling split.");
                return;
            }

            int numMembers = members.size();
            double share = totalAmount / numMembers;
            assert share >= 0 : "Calculated share must be non-negative";
            System.out.println("Splitting " + totalAmount + " equally among " + numMembers +
                " members of group \"" + groupName + "\":");
            for (Friend member: members) {
                String assignment = " - " + member.getName() +
                    " owes: " + String.format("%.2f", share) + "\n";
                System.out.print(assignment);
                // Save each member's owed amount in real-time.
                OwesStorage.appendOwes(assignment);
            }
            System.out.println("Updated list of transactions!");

            // viewGroupDirect trigger for showing the balance
            System.out.println("Here is the updated balance for group: " + groupName);
            friendsCommands.viewGroupDirect(groupName);
        } else if (option.equals("2")) {
            // Manual split option.
            System.out.print("Enter group name for manual split: ");
            String groupName = scanner.nextLine().trim();
            assert !groupName.isEmpty() : "Group name must not be empty";
            if (!groupManager.groupExists(groupName)) {
                System.out.println("Group not found.");
                return;
            }

            List < Friend > members = groupManager.getGroupMembers(groupName);
            if (members == null || members.isEmpty()) {
                System.out.println("No members in group. Cancelling split.");
                return;
            }

            // Ask the user to pick absolute or percentage for the entire group
            System.out.print("Type '/a' for absolute amounts OR '/p' for percentages: ");
            String method = scanner.nextLine().trim().toLowerCase();
            if (!method.equals("/a") && !method.equals("/p")) {
                System.out.println("Invalid method. Cancelling split.");
                return;
            }

            if (method.equals("/a")) {
                // Absolute amounts
                double remaining = totalAmount;
                System.out.println("Total expense amount to split: " + totalAmount);
                System.out.println("You can assign up to " + remaining + " in total.");

                for (Friend member: members) {
                    while (true) {
                        System.out.println("Remaining expense: " + String.format("%.2f", remaining));
                        System.out.print("Enter amount for " + member.getName() + ": ");
                        String amtStr = scanner.nextLine().trim();

                        double assigned;
                        try {
                            assigned = Double.parseDouble(amtStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount. Please try again.");
                            continue;
                        }

                        // Check if the user exceeded the remaining amount
                        if (assigned < 0) {
                            System.out.println("Assigned amount cannot be negative. Try again.");
                        } else if (assigned > remaining) {
                            System.out.println("Cannot exceed remaining amount of " +
                                String.format("%.2f", remaining) + ". Try again.");
                        } else {
                            // Valid assignment
                            remaining -= assigned;
                            String assignment = " - " + member.getName() +
                                " owes: " + String.format("%.2f", assigned) + "\n";
                            System.out.print(assignment);
                            OwesStorage.appendOwes(assignment);
                            break; // Proceed to next member
                        }
                    }
                }
                System.out.println("Updated list of transactions!");
            } else {
                // Percentage
                double remainingPercentage = 100.0;
                System.out.println("Total expense is " + totalAmount +
                    ". You can assign up to 100% in total. Each assignment reduces the remaining percentage.");

                for (Friend member: members) {
                    while (true) {
                        System.out.println("Remaining percentage: " + String.format("%.2f", remainingPercentage) + "%");
                        System.out.print("Enter percentage for " + member.getName() + ": ");
                        String percStr = scanner.nextLine().trim();

                        double percent;
                        try {
                            percent = Double.parseDouble(percStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid percentage format. Please try again.");
                            continue;
                        }

                        if (percent < 0 || percent > remainingPercentage) {
                            System.out.println("Percentage must be between 0 and " +
                                String.format("%.2f", remainingPercentage) + ". Try again.");
                        } else {
                            //valid assignment
                            double assignedAmount = totalAmount * (percent / 100.0);
                            String assignment = " - " + member.getName() +
                                " owes: " + String.format("%.2f", assignedAmount) + "\n";
                            System.out.print(assignment);
                            OwesStorage.appendOwes(assignment);

                            remainingPercentage -= percent;
                            break; //move on to next member
                        }
                    }
                }
                System.out.println("Updated list of transactions!");

                //call viewGroupDirect:
                System.out.println("Here is the updated balance for group: " + groupName);
                friendsCommands.viewGroupDirect(groupName);
            }
        } else {
            System.out.println("Invalid option. Cancelling split.");
        }
    }


    /**
     * The OwesStorage class provides functionality to append a list of owed amounts to a file.
     */
    public static class OwesStorage {
        public static String owesFile = "owedAmounts.txt";

        /**
         * Appends the owes data to a file.
         *
         * @param owesData a string representing the amounts owed (each on a new line).
         * @return true if the data was successfully appended, false otherwise.
         */
        public static boolean appendOwes(String owesData) {
            File file = new File(owesFile);
            try (FileWriter writer = new FileWriter(file, true)) { // 'true' enables append mode.
                writer.write(owesData);
                return true;
            } catch (IOException e) {
                System.out.println("Error appending owed data: " + e.getMessage());
                return false;
            }
        }
    }
}
