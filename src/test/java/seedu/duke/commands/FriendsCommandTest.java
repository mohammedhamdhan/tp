package seedu.duke.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.friends.Friend;
import seedu.duke.friends.GroupManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FriendsCommandTest {
    private FriendsCommands friendsCommands;
    private GroupManager groupManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        groupManager = new GroupManager();
        friendsCommands = new FriendsCommands(groupManager);
        System.setOut(new PrintStream(outContent)); // Capture console output
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn); // Restore original System.in
        outContent.reset();       // Clear output buffer
    }

    /**
     * Helper method to provide simulated input.
     * This sets System.in to the given input and reinitializes friendsCommands so that its Scanner uses it.
     *
     * @param input the simulated user input
     */
    void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        friendsCommands = new FriendsCommands(groupManager);
    }

    @Test
    void testViewGroup_ExistingGroup() {
        groupManager = new GroupManager();
        groupManager.addFriendToGroup("TestGroup", new Friend("Alice", "TestGroup"));
        provideInput("TestGroup\n");
        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.viewGroup();

        assertTrue(outContent.toString().contains("Group: TestGroup"));
    }

    @Test
    void testViewGroup_NonExistentGroup() {
        groupManager = new GroupManager();
        provideInput("NonExistingGroup\n");
        friendsCommands = new FriendsCommands(groupManager);

        friendsCommands.viewGroup();

        assertTrue(outContent.toString().contains("Group not found"));
    }

    @Test
    void testViewAllGroups_WithGroups() {
        groupManager.addFriendToGroup("Group1", new Friend("Alice", "Group1"));
        groupManager.addFriendToGroup("Group2", new Friend("Bob", "Group2"));

        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.viewAllGroups();

        assertTrue(outContent.toString().contains("Group1"));
        assertTrue(outContent.toString().contains("Group2"));
    }

    @Test
    void testAddMember_ToExistingGroup() {
        groupManager = new GroupManager();
        groupManager.addFriendToGroup("TestGroup", new Friend("Alice", "TestGroup"));
        provideInput("Bob\nTestGroup\n");
        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.addMember();

        List<Friend> friends = groupManager.getGroupMembers("TestGroup");
        assertEquals(2, friends.size());
    }

    @Test
    void testAddMember_ToNonExistentGroup_CreatesGroup() {
        groupManager = new GroupManager();
        provideInput("Charlie\nNewGroup\nyes\n");
        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.addMember();

        assertTrue(groupManager.groupExists("NewGroup"));
        List<Friend> friends = groupManager.getGroupMembers("NewGroup");
        assertEquals(1, friends.size());
    }

    @Test
    void testAddMember_ToNonExistentGroup_UserRejects() {
        groupManager = new GroupManager();
        provideInput("Charlie\nNonExistingGroup\nno\n");
        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.addMember();

        assertFalse(groupManager.groupExists("NonExistingGroup"));
        assertTrue(outContent.toString().contains("Operation cancelled"));
    }

    @Test
    void testRemoveMember_FromExistingGroup() {
        groupManager = new GroupManager();
        groupManager.addFriendToGroup("TestGroup", new Friend("Alice", "TestGroup"));
        provideInput("Alice\nTestGroup\n");
        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.removeMember();

        List<Friend> members = groupManager.getGroupMembers("TestGroup");
        assertTrue(members.isEmpty(), "Alice should be removed from TestGroup");
    }

    @Test
    void testRemoveMember_FromNonExistentGroup() {
        groupManager = new GroupManager();
        provideInput("Alice\nNonExistingGroup\n");
        friendsCommands = new FriendsCommands(groupManager);
        friendsCommands.removeMember();

        assertTrue(outContent.toString().contains("Group does not exist"));
    }
}
