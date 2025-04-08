//@@author nandhananm7
package seedu.duke.friends;

import seedu.duke.messages.Messages;
import seedu.duke.storage.GroupStorage;
import java.util.List;
import java.util.ArrayList;

public class GroupManager {
    private List<Group> groups;
    private Messages messages;

    /**
     * Constructs a GroupManager and loads existing groups from storage.
     */
    public GroupManager() {
        this.groups = GroupStorage.loadGroups();
    }

    /**
     * Adds a friend to an existing group or creates a new group if it doesn't exist.
     *
     * @param groupName the name of the group.
     * @param friend the friend to add.
     */
    public void addFriendToGroup(String groupName, Friend friend) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                group.addFriend(friend);
                return;
            }
        }
        Group newGroup = new Group(groupName);
        newGroup.addFriend(friend);
        groups.add(newGroup);
    }

    /**
     * Checks if a group exists.
     *
     * @param groupName the name of the group.
     * @return true if the group exists, false otherwise.
     */
    public boolean groupExists(String groupName) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a member exists in a specific group.
     *
     * @param groupName the name of the group.
     * @param memberName the name of the member.
     * @return true if the member exists in the group, false otherwise.
     */
    public boolean isMemberInGroup(String groupName, String memberName) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return group.isMemberInGroup(memberName);
            }
        }
        return false;
    }

    /**
     * Retrieves the members of a specified group.
     *
     * @param groupName the name of the group.
     * @return a list of friends in the group.
     */
    public List<Friend> getGroupMembers(String groupName) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return group.getFriends();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Removes a group by name and saves the updated list if successful.
     *
     * @param groupName the name of the group to remove.
     */
    public void removeGroup(String groupName) {
        boolean removed = groups.removeIf(group -> group.getName().equals(groupName));

        if (removed) {
            saveGroups();
        } else {
            messages.displayMissingGroupMessage();
        }
    }

    /**
     * Saves the current list of groups to storage.
     */
    public void saveGroups() {
        GroupStorage.saveGroups(groups);
    }

    /**
     * Retrieves all existing groups.
     *
     * @return the list of groups.
     */
    public List<Group> getGroups() {
        return groups;
    }
}
//@@author

