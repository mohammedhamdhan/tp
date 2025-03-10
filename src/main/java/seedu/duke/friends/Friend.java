package seedu.duke.friends;

public class Friend {
    private String group;
    private String name;

    public Friend(String group, String name) {
        this.group = group;
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }
}
