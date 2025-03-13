package seedu.duke.friends;

import seedu.duke.storage.GroupStorage;
import java.util.List;

public class GroupManager {
    private List<Group> groups;

    public GroupManager() {
        this.groups = GroupStorage.loadGroups();  // Load the existing groups from storage
    }

    // Add a friend to a group
    public void addFriendToGroup(String groupName, Friend friend) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                group.addFriend(friend);
                return;
            }
        }
        // If group doesn't exist, create a new one and add the friend
        Group newGroup = new Group(groupName);
        newGroup.addFriend(friend);
        groups.add(newGroup);
    }

    // Save the updated list of groups
    public void saveGroups() {
        GroupStorage.saveGroups(groups);
    }

    // Get all groups
    public List<Group> getGroups() {
        return groups;
    }
}
