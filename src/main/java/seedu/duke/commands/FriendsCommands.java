package seedu.duke.commands;
import seedu.duke.friends.Group;
import seedu.duke.friends.GroupManager;
import seedu.duke.friends.Friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

//@@author nandhananm7
public class FriendsCommands {
    private GroupManager groupManager;
    private Scanner scanner;

    public FriendsCommands(GroupManager groupManager) {
        this.groupManager = groupManager;
        this.scanner = new Scanner(System.in);
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // This regex allows letters, numbers, and spaces.
        return name.matches("[A-Za-z0-9 ]+");
    }

    public void createGroup() {
        String groupName;

        while (true) {
            System.out.print("Enter the group name: ");
            if (!scanner.hasNextLine()) {
                System.out.println("[ERROR] Unexpected end of input while reading group name.");
                return;
            }
            groupName = scanner.nextLine().trim();
            if (!isValidName(groupName)) {
                System.out.println("Invalid group name. Name cannot be empty/contain special characters. Try again :)");
            } else {
                break;
            }
        }

        System.out.println("Who would you like to add to the group? (Type 'done' to finish)");

        while (true) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                break; // Exit when 'done' is entered
            }

            if (!isValidName(name)) {
                System.out.println("Invalid name. It cannot be empty/ contain special characters. Try again :)");
                continue;
            }

            groupManager.addFriendToGroup(groupName, new Friend(name, groupName));
        }


        groupManager.saveGroups(); // Save the updated groups using GroupManager
        System.out.println("Group created successfully!");
    }

    // Edited to ensure sum up values from owedAmounts.txt instead of showing the last value
    public void viewGroup() {
        System.out.print("Enter the group name to view: ");
        String groupName = scanner.nextLine().trim();

        // Check if this group exists
        if (!groupManager.groupExists(groupName)) {
            System.out.println("Group not found");
            return;
        }
        System.out.println("Group: " + groupName);

        // Load owedAmounts.txt into owedAmounts map
        java.util.Map < String, Double > owedAmounts = new java.util.HashMap < > ();
        File file = new File("owedAmounts.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.startsWith("- ")) { // Format for data storage, delimiter
                    String[] parts = line.split(" owes: ");
                    if (parts.length == 2) {
                        // Extract the member's name and the owed amount
                        String name = parts[0].substring(2).trim(); //remove "- "
                        double amount = Double.parseDouble(parts[1].trim());

                        // Accumulate amounts for that name, do NOT overwrite
                        double existing = owedAmounts.getOrDefault(name, 0.0);
                        owedAmounts.put(name, existing + amount);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Exception for file not found
            System.out.println("owedAmounts.txt file not found. No expense data available.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing expense data.");
        }

        // Get group members and print their name with expense.
        List < Friend > members = groupManager.getGroupMembers(groupName);
        if (members.isEmpty()) {
            System.out.println("No members in this group.");
        } else {
            System.out.println("Members:");
            for (Friend friend: members) {
                String friendName = friend.getName();
                // If the friend appears in owedAmounts, show the sum total, else 0.00
                double totalOwed = owedAmounts.getOrDefault(friendName, 0.0);
                System.out.println(friendName + " - Expense: $" + String.format("%.2f", totalOwed));
            }
        }
    }

    //@@author Ashertan256
    public void viewGroupDirect(String groupName) {
        // Exactly the same as viewGroup but WITHOUT requiring the user to input the group name. 
        //Called directly from the split command, to show the sum of amounts. 
        //Should not be callable from user input side.

        Map < String, Double > owedAmounts = new HashMap < > ();
        File file = new File("owedAmounts.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.startsWith("- ")) {
                    String[] parts = line.split(" owes: ");
                    if (parts.length == 2) {
                        String name = parts[0].substring(2).trim();
                        double amount = Double.parseDouble(parts[1].trim());
                        owedAmounts.put(name, owedAmounts.getOrDefault(name, 0.0) + amount);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Owed amounts file not found. No expense data available.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing expense data.");
        }

        // Retrieve the group’s members and print their total so far
        List < Friend > members = groupManager.getGroupMembers(groupName);
        if (members.isEmpty()) {
            System.out.println("No members in this group.");
        } else {
            System.out.println("Members:");
            for (Friend friend: members) {
                String name = friend.getName();
                double totalOwed = owedAmounts.getOrDefault(name, 0.0);
                System.out.println(name + " - Expense: $" + String.format("%.2f", totalOwed));
            }
        }
    }

    //@@author


    //@@author nandhananm7
    public void viewAllGroups() {
        if (groupManager.getGroups().isEmpty()) {
            System.out.println("You have no groups.");
        } else {
            for (Group group: groupManager.getGroups()) {
                System.out.println(group);
            }
        }
    }


    public void addMember() {
        String name;
        while (true) {
            System.out.print("Enter the name of the member to add: ");
            name = scanner.nextLine().trim();
            if (!isValidName(name)) {
                System.out.println("Invalid name. Name cannot be empty/contain special characters.Try again :)");
            } else {
                break;
            }
        }

        String groupName;
        while (true) {
            System.out.print("Enter the group name: ");
            groupName = scanner.nextLine().trim();
            if (!isValidName(groupName)) {
                System.out.println("Invalid group name. Name cannot be empty/contain special characters.Try again :)");
            } else {
                break;
            }
        }

        if (groupManager.groupExists(groupName)) {
            groupManager.addFriendToGroup(groupName, new Friend(name, groupName));
            groupManager.saveGroups(); // Save the updated group data
            System.out.println(name + " has been added to " + groupName);
        } else {
            System.out.print("Group does not exist. Would you like to create this group first? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y")) {
                groupManager.addFriendToGroup(groupName, new Friend(name, groupName)); // Directly add the friend
                groupManager.saveGroups(); // Save the new group and member
                System.out.println("Group " + groupName + " has been created and " + name + " has been added.");
            } else {
                System.out.println("Operation cancelled. " + name + " was not added.");
            }
        }
    }

    public void removeMember() {
        System.out.print("Enter the name of the member to remove: ");
        String memberName = scanner.nextLine().trim();

        System.out.print("Enter the group name from which to remove " + memberName + ": ");
        String groupName = scanner.nextLine().trim();

        if (!groupManager.groupExists(groupName)) {
            System.out.println("Group does not exist.");
            return;
        }

        System.out.print("Are you sure you want to remove " + memberName + " from " + groupName + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Operation cancelled. " + memberName + " was not removed from " + groupName);
            return;
        }

        boolean removed = false;
        for (Group group: groupManager.getGroups()) {
            if (group.getName().equals(groupName)) {
                removed = group.removeFriend(memberName);
                break;
            }
        }

        if (removed) {
            groupManager.saveGroups(); // Save the updated group data
            System.out.println(memberName + " has been removed from " + groupName);
        } else {
            System.out.println(memberName + " is not in " + groupName);
        }
    }

    public void removeGroup() {
        System.out.print("Enter the name of the group to remove: ");
        String groupName = scanner.nextLine().trim();

        if (!groupManager.groupExists(groupName)) {
            System.out.println("Group does not exist.");
            return;
        }

        System.out.print("Are you sure you want to remove the group " + groupName + "? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("y")) {
            System.out.println("Operation cancelled. Group " + groupName + " was not removed.");
            return;
        }

        groupManager.removeGroup(groupName);
        groupManager.saveGroups(); // Save the updated groups list
        System.out.println("Group '" + groupName + "' has been removed successfully.");
    }

}
//@@author
