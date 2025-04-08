//@@author nandhananm7
package seedu.duke.friends;

public class Friend {
    private String group;
    private String name;

    /**
     * Represents a friend belonging to a group.
     *
     * @param name  the name of the friend.
     * @param group the group to which the friend belongs.
     */
    public Friend(String name, String group) {
        this.group = group;
        this.name = name;
    }

    /**
     * Retrieves the group of the friend.
     *
     * @return the group name.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Retrieves the name of the friend.
     *
     * @return the friend's name.
     */
    public String getName() {
        return name;
    }
}
//@@author
