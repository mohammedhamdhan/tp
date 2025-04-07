package seedu.duke.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import seedu.duke.friends.Friend;
import seedu.duke.friends.GroupManager;
import seedu.duke.expense.Expense;
import seedu.duke.storage.DataStorage;

public class SplitCommand {
    private Scanner scanner;
    private GroupManager groupManager;
    private FriendsCommands friendsCommands;

    public SplitCommand(Scanner scanner, GroupManager groupManager, FriendsCommands friendsCommands) {
        if (scanner == null || groupManager == null || friendsCommands == null) {
            throw new IllegalArgumentException("Scanner, GroupManager, and FriendsCommands must not be null.");
        }
        this.scanner = scanner;
        this.groupManager = groupManager;
        this.friendsCommands = friendsCommands;
    }

    /**
     * Executes the split command using the unified format:
     */
    public void executeSplit(String command) {
        try {
            if (command == null || command.trim().isEmpty()) {
                throw new IllegalArgumentException("Command cannot be empty.");
            }
            // Split command using "/" as delimiter.
            String[] parts = command.trim().split(" */");
            if (parts.length != 4) {
                throw new IllegalArgumentException(
                    "Invalid format. Usage: split/<equal|assign>/<expense index>/<group name>");
            }
            String commandWord = parts[0].trim();
            if (!commandWord.equalsIgnoreCase("split")) {
                throw new IllegalArgumentException("Invalid format. Usage: split/<equal|assign>/<expense index>/<group name>");
            }
            String splitOption = parts[1].trim().toLowerCase();
            if (!splitOption.equals("equal") && !splitOption.equals("assign")) {
                throw new IllegalArgumentException("Invalid split option: '" + splitOption +
                    "'. Use 'equal' for an equal split or 'assign' for manual splitting.");
            }
            String expenseIndexStr = parts[2].trim();
            int expIndex;
            try {
                int index = Integer.parseInt(expenseIndexStr);
                if (index < 1) {
                    throw new IllegalArgumentException("Expense index must be a positive integer.");
                }
                expIndex = index - 1; // converting to 0-based index
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid expense index: '" + expenseIndexStr + "'");
            }
            String groupName = parts[3].trim();
            if (groupName.isEmpty()) {
                throw new IllegalArgumentException("Group name cannot be empty.");
            }
            List < Expense > expenses = DataStorage.loadExpenses();
            if (expenses.isEmpty()) {
                throw new IllegalArgumentException("No expenses available to split.");
            }
            if (expIndex >= expenses.size()) {
                throw new IllegalArgumentException("Expense index out of range: " + (expIndex + 1));
            }
            Expense selectedExpense = expenses.get(expIndex);
            double totalAmount = selectedExpense.getAmount();
            System.out.println("Selected expense amount: " + String.format("%.2f", totalAmount));

            if (!groupManager.groupExists(groupName)) {
                throw new IllegalArgumentException("Group '" + groupName + "' not found.");
            }
            List < Friend > members = groupManager.getGroupMembers(groupName);
            if (members == null || members.isEmpty()) {
                throw new IllegalArgumentException("No members in group '" +
                    groupName + "'. Cannot perform split.");
            }

            // Prevent duplicate splits: 
            //check if the expense has already been split for this group.
            boolean alreadySplit = false;
            File owesFile = new File(OwesStorage.owesFile);
            if (owesFile.exists()) {
                try {
                    List < String > lines = Files.readAllLines(owesFile.toPath());
                    for (String line: lines) {
                        if (line.contains("Expense: " + selectedExpense.getTitle()) &&
                            line.contains("Date: " + selectedExpense.getDate()) &&
                            line.contains("Group: " + groupName)) {
                            alreadySplit = true;
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading owes file: " + e.getMessage());
                    return;
                }
            }
            if (alreadySplit) {
                System.out.println("Error: This expense has already been split for group '" + groupName + "'.");
                return;
            }

            if (splitOption.equals("equal")) {
                int numMembers = members.size();
                double share = totalAmount / numMembers;
                System.out.println("Splitting " + totalAmount + " equally among " + numMembers +
                    " members of group \"" + groupName + "\":");
                for (Friend member: members) {
                    String assignment = createTransactionRecord(selectedExpense, groupName, member.getName(), share);
                    System.out.println(assignment);
                    OwesStorage.appendOwes(assignment);
                }
                System.out.println("Updated list of transactions!");
                friendsCommands.viewGroupDirect(groupName);
            } else { // "assign" option for manual split
                String method = "";
                while (true) {
                    System.out.print("Type '/a' for absolute amounts OR '/p' for percentages: ");
                    if (!scanner.hasNextLine()) {
                        System.out.println("No input available. Exiting split command.");
                        return;
                    }
                    method = scanner.nextLine().trim().toLowerCase();
                    if (method.equals("/a") || method.equals("/p")) {
                        break;
                    } else {
                        System.out.println("Invalid format. Usage: </a|/p>");
                    }
                }
                if (method.equals("/a")) {
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
                                System.out.println("Invalid amount format for " + member.getName() +
                                    ". Please enter a numeric value.");
                                continue;
                            }
                            if (assigned < 0) {
                                System.out.println("Assigned amount for " + member.getName() +
                                    " cannot be negative. Try again.");
                            } else if (assigned > remaining) {
                                System.out.println("Assigned amount for " + member.getName() +
                                    " exceeds remaining amount of " + String.format("%.2f", remaining) +
                                    ". Try again.");
                            } else {
                                remaining -= assigned;
                                String assignment = createTransactionRecord(selectedExpense,
                                    groupName, member.getName(), assigned);
                                System.out.println(assignment);
                                OwesStorage.appendOwes(assignment);
                                break;
                            }
                        }
                    }
                    System.out.println("Updated list of transactions!");
                    // Call viewGroupDirect after manual absolute splitting.
                    friendsCommands.viewGroupDirect(groupName);
                } else { // method equals "/p" for percentage splitting
                    double remainingPercentage = 100.0;
                    System.out.println("Total expense is " + totalAmount +
                        ". You can assign up to 100% in total.");
                    for (Friend member: members) {
                        while (true) {
                            System.out.println("Remaining percentage: " +
                                String.format("%.2f", remainingPercentage) + "%");
                            System.out.print("Enter percentage for " + member.getName() + ": ");
                            String percStr = scanner.nextLine().trim();
                            double percent;
                            try {
                                percent = Double.parseDouble(percStr);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid percentage format for " + member.getName() +
                                    ". Please enter a numeric value.");
                                continue;
                            }
                            if (percent < 0 || percent > remainingPercentage) {
                                System.out.println("Percentage for " + member.getName() +
                                    " must be between 0 and " + String.format("%.2f", remainingPercentage) +
                                    ". Try again.");
                            } else {
                                double assignedAmount = totalAmount * (percent / 100.0);
                                String assignment = createTransactionRecord(selectedExpense,
                                    groupName, member.getName(), assignedAmount);
                                System.out.println(assignment);
                                OwesStorage.appendOwes(assignment);
                                remainingPercentage -= percent;
                                break;
                            }
                        }
                    }
                    System.out.println("Updated list of transactions!");
                    // Call viewGroupDirect after manual percentage splitting.
                    friendsCommands.viewGroupDirect(groupName);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Creates a detailed transaction record to be stored.
     *
     * @param expense    The expense that was split.
     * @param groupName  The name of the group.
     * @param memberName The member who owes an amount.
     * @param amount     The amount assigned.
     * @return A formatted transaction string.
     */
    private String createTransactionRecord(Expense expense,
        String groupName, String memberName, double amount) {
        return "Transaction: Expense: " + expense.getTitle() +
            ", Date: " + expense.getDate() +
            ", Group: " + groupName +
            ", Member: " + memberName +
            " owes: " + String.format("%.2f", amount);
    }

    /**
     * Nested class to handle owes file operations.
     */
    public static class OwesStorage {
        public static String owesFile = "owedAmounts.txt";
        public static void appendOwes(String text) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(owesFile, true))) {
                writer.println(text);
            } catch (IOException e) {
                System.out.println("Error writing to owes file: " + e.getMessage());
            }
        }
    }

    /**
     * Inner static class that lists owed transactions for a particular member in a group.
     * It gracefully handles any unexpected or malformed records in the owed amounts file.
     */
    public static class OwedTransactionsLister {


        /**
         * Lists all owed transactions for a particular member in a group.
         *
         * @param memberName The name of the member.
         * @param groupName  The name of the group.
         */
        public void listOwedTransactions(String memberName, String groupName) {
            File file = new File(OwesStorage.owesFile);
            if (!file.exists()) {
                System.out.println("No owed transactions found. The file does not exist.");
                return;
            }
            try {
                List < String > allLines = Files.readAllLines(file.toPath());
                boolean found = false;
                System.out.println("Owed transactions for member '" + memberName + "' in group '" + groupName + "':");
                for (String line: allLines) {
                    if (line == null || line.trim().isEmpty()) {
                        continue; // skip blank lines
                    }
                    // Check if the line follows the expected detailed format.
                    if (!line.startsWith("Transaction: Expense:")) {
                        System.out.println("Warning: Skipping unrecognized record: " + line);
                        continue;
                    }
                    // Check if the record pertains to the given member and group.
                    if (line.contains("Group: " + groupName) && line.contains("Member: " + memberName)) {
                        System.out.println(line);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No transactions found for member '" +
                        memberName + "' in group '" + groupName + "'.");
                }
            } catch (IOException e) {
                System.out.println("Error reading owed transactions: " + e.getMessage());
            }
        }
    }
}
