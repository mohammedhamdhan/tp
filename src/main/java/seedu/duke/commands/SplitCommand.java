package seedu.duke.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import seedu.duke.expense.Expense;
import seedu.duke.friends.Friend;
import seedu.duke.friends.GroupManager;
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
            String[] parts = command.trim().split("/");
            if (parts.length != 4) {
                throw new IllegalArgumentException(
                    "Invalid command format: expected 'split /<equal|assign>/<expense index>/<group name>' but got: " +
                    command);
            }
            String commandWord = parts[0].trim();
            if (!commandWord.equalsIgnoreCase("split")) {
                throw new IllegalArgumentException("Invalid command: expected 'split' but got: " + commandWord);
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
            
            // Set the group name on the expense before checking for duplicate splits
            selectedExpense.setGroupName(groupName);

            // Prevent duplicate splits: 
            //check if the expense has already been split for this group.
            boolean alreadySplit = false;
            File owesFile = new File(OwesStorage.owesFile);
            if (owesFile.exists() && owesFile.length() > 0) {
                try {
                    List<String> lines = Files.readAllLines(owesFile.toPath());
                    for (String line : lines) {
                        if (line.contains("Expense: " + selectedExpense.getTitle()) &&
                            line.contains("Date: " + selectedExpense.getDate()) &&
                            line.contains("Group: " + groupName)) {
                            alreadySplit = true;
                            System.out.println("This expense has already been split for group '" + groupName + "'");
                            return;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error checking for duplicate splits: " + e.getMessage());
                    return;
                }
            }

            if (splitOption.equals("equal")) {
                handleEqualSplit(selectedExpense, members);
            } else { // "assign" option for manual split
                handleManualSplit(selectedExpense, members, scanner);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleEqualSplit(Expense expense, List<Friend> members) {
        double totalAmount = expense.getAmount();
        double share = totalAmount / members.size();
        
        // Get the group name from the first member
        String groupName = members.isEmpty() ? null : members.get(0).getGroup();
        // Set group name on the expense
        expense.setGroupName(groupName);
        
        for (Friend member : members) {
            String transaction = String.format("Transaction: Expense: %s, Date: %s, Group: %s, Member: %s owes: %.2f",
                expense.getTitle(), expense.getDate(), expense.getGroupName(), member.getName(), share);
            OwesStorage.saveTransaction(transaction);
            System.out.println(transaction);
        }
    }

    private void handleManualSplit(Expense expense, List<Friend> members, Scanner scanner) {
        System.out.println("Enter '/a' for absolute amounts or '/p' for percentages:");
        String method = scanner.nextLine().trim();
        
        if (method.equals("/a")) {
            handleAbsoluteSplit(expense, members, scanner);
        } else if (method.equals("/p")) {
            handlePercentageSplit(expense, members, scanner);
        } else {
            System.out.println("Invalid method. Please enter '/a' for absolute amounts or '/p' for percentages.");
            handleManualSplit(expense, members, scanner);
        }
    }

    private void handleAbsoluteSplit(Expense expense, List<Friend> members, Scanner scanner) {
        double totalAmount = expense.getAmount();
        double remainingAmount = totalAmount;
        
        // Get the group name from the first member
        String groupName = members.isEmpty() ? null : members.get(0).getGroup();
        // Set group name on the expense
        expense.setGroupName(groupName);
        
        for (int i = 0; i < members.size() - 1; i++) {
            Friend member = members.get(i);
            System.out.println("Enter amount for " + member.getName() + ":");
            String input = scanner.nextLine().trim();
            
            try {
                double amount = Double.parseDouble(input);
                if (amount < 0 || amount > remainingAmount) {
                    System.out.println("Invalid amount. Please enter a value between 0 and " + remainingAmount);
                    i--; // Retry for this member
                    continue;
                }
                
                String transaction = String.format("Transaction: Expense: %s, Date: %s, Group: %s, " +
                                "Member: %s owes: %.2f",
                    expense.getTitle(), expense.getDate(), expense.getGroupName(), member.getName(), amount);
                OwesStorage.saveTransaction(transaction);
                System.out.println(transaction);
                
                remainingAmount -= amount;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount format for " + member.getName());
                i--; // Retry for this member
            }
        }
        
        // Last member gets the remaining amount
        Friend lastMember = members.get(members.size() - 1);
        String transaction = String.format("Transaction: Expense: %s, Date: %s, Group: %s, Member: %s owes: %.2f",
            expense.getTitle(), expense.getDate(), expense.getGroupName(), lastMember.getName(), remainingAmount);
        OwesStorage.saveTransaction(transaction);
        System.out.println(transaction);
    }

    private void handlePercentageSplit(Expense expense, List<Friend> members, Scanner scanner) {
        double totalAmount = expense.getAmount();
        double remainingPercentage = 100.0;
        
        // Get the group name from the first member
        String groupName = members.isEmpty() ? null : members.get(0).getGroup();
        // Set group name on the expense
        expense.setGroupName(groupName);
        
        for (int i = 0; i < members.size() - 1; i++) {
            Friend member = members.get(i);
            System.out.println("Enter percentage for " + member.getName() + ":");
            String input = scanner.nextLine().trim();
            
            try {
                double percentage = Double.parseDouble(input);
                if (percentage < 0 || percentage > remainingPercentage) {
                    System.out.println("Invalid percentage. Please enter a value between 0 and " + remainingPercentage);
                    i--; // Retry for this member
                    continue;
                }
                
                double amount = totalAmount * (percentage / 100.0);
                String transaction = String.format("Transaction: Expense: %s," +
                                " Date: %s, Group: %s, Member: %s owes: %.2f",
                    expense.getTitle(), expense.getDate(), expense.getGroupName(), member.getName(), amount);
                OwesStorage.saveTransaction(transaction);
                System.out.println(transaction);
                
                remainingPercentage -= percentage;
            } catch (NumberFormatException e) {
                System.out.println("Invalid percentage format for " + member.getName());
                i--; // Retry for this member
            }
        }
        
        // Last member gets the remaining percentage
        Friend lastMember = members.get(members.size() - 1);
        double amount = totalAmount * (remainingPercentage / 100.0);
        String transaction = String.format("Transaction: Expense: %s, Date: %s, Group: %s, Member: %s owes: %.2f",
            expense.getTitle(), expense.getDate(), expense.getGroupName(), lastMember.getName(), amount);
        OwesStorage.saveTransaction(transaction);
        System.out.println(transaction);
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

        public static void saveTransaction(String transaction) {
            appendOwes(transaction);
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
