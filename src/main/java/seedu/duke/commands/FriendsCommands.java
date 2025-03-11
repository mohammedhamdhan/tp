package seedu.duke.commands;

import seedu.duke.friends.GroupManager;
import seedu.duke.friends.Friend;
import seedu.duke.storage.GroupStorage;
import java.util.Scanner;

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
            if (name.equalsIgnoreCase("done")) break;  // Exit when 'done' is entered
            groupManager.addFriendToGroup(groupName, new Friend(name, groupName));
        }

        groupManager.saveGroups();  // Save the updated groups using GroupManager
        System.out.println("Group created successfully!");
    }

    public void viewGroup(String groupName) {
        if (groupManager.groupExists(groupName)) {
            System.out.println("Group: " + groupName);
            groupManager.viewGroupMembers(groupName);
        } else {
            System.out.println("Group not found.");
        }
    }

}