package seedu.duke.commands;

import seedu.duke.friends.Group;
import seedu.duke.friends.GroupManager;
import seedu.duke.friends.Friend;

import java.util.List;
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

    public void createGroup() {
        System.out.print("Enter the group name: ");
        String groupName = scanner.nextLine().trim();  // Get the group name from the user

        System.out.println("Who would you like to add to the group? (Type 'done' to finish)");

        while (true) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                break;  // Exit when 'done' is entered
            }
            groupManager.addFriendToGroup(groupName, new Friend(name, groupName));
        }

        groupManager.saveGroups();  // Save the updated groups using GroupManager
        System.out.println("Group created successfully!");
    }

    public void viewGroup() {
        System.out.print("Enter the group name to view: ");
        String groupName = scanner.nextLine().trim();

        if (groupManager.groupExists(groupName)) {
            System.out.println("Group: " + groupName);

            // Load owed amounts from file into a map.
            java.util.Map<String, Double> owedAmounts = new java.util.HashMap<>();
            File file = new File("owedAmounts.txt");
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.startsWith("- ")) {
                        String[] parts = line.split(" owes: ");
                        if (parts.length == 2) {
                            String name = parts[0].substring(2).trim();
                            double amount = Double.parseDouble(parts[1].trim());
                            owedAmounts.put(name, amount);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Owed amounts file not found. No expense data available.");
            } catch (NumberFormatException e) {
                System.out.println("Error parsing expense data.");
            }

            // Get group members and print their name with expense.
            List<Friend> members = groupManager.getGroupMembers(groupName);
            if (members.isEmpty()) {
                System.out.println("No members in this group.");
            } else {
                System.out.println("Members:");
                for (Friend friend : members) {
                    String name = friend.getName();
                    double expense = owedAmounts.getOrDefault(name, 0.0);
                    System.out.println(name + " - Expense: $" + String.format("%.2f", expense));
                }
            }
        } else {
            System.out.println("Group not found.");
        }
    }


    public void viewAllGroups() {
        if (groupManager.getGroups().isEmpty()) {
            System.out.println("You have no groups.");
        } else {
            for (Group group : groupManager.getGroups()) {
                System.out.println(group);
            }
        }
    }


    public void addMember() {
        System.out.print("Enter the name of the member to add: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter the group name: ");
        String groupName = scanner.nextLine().trim();

        if (groupManager.groupExists(groupName)) {
            groupManager.addFriendToGroup(groupName, new Friend(name, groupName));
            groupManager.saveGroups();  // Save the updated group data
            System.out.println(name + " has been added to " + groupName);
        } else {
            System.out.print("Group does not exist. Would you like to create this group first? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                groupManager.addFriendToGroup(groupName, new Friend(name, groupName));  // Directly add the friend
                groupManager.saveGroups();  // Save the new group and member
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

        boolean removed = false;
        for (Group group : groupManager.getGroups()) {
            if (group.getName().equals(groupName)) {
                removed = group.removeFriend(memberName);
                break;
            }
        }

        if (removed) {
            groupManager.saveGroups();  // Save the updated group data
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

        groupManager.removeGroup(groupName);
        groupManager.saveGroups();  // Save the updated groups list
        System.out.println("Group '" + groupName + "' has been removed successfully.");
    }

}
//@@author
