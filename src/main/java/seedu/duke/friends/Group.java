package seedu.duke.friends;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<Friend> friends;

    public Group(String name) {
        this.name = name;
        this.friends = new ArrayList<>();
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public boolean removeFriend(String friendName) {
        friends.remove(friendName);
        return true;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public String getName() {
        return name;
    }
}
