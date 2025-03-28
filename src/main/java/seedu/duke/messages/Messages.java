package seedu.duke.messages;

//@@author matthewyeo1
public class Messages {

    /**
     * System message upon program start-up.
     */
    public void displayWelcomeMessage() {
        setDivider();
        System.out.println("Welcome to O$P$ expense-tracker! How can I help you?");
    }

    /**
     * User input indicator.
     */
    public void enterCommandMessage() {
        System.out.print("Enter command: ");
    }

    /**
     * System message for NULL input.
     */
    public void emptyInputMessage() {
        System.out.println("No input detected. Exiting program...");
    }

    /**
     * Displays a broken line separating each user input command.
     */
    public void setDivider() {
        System.out.println("\n" + "_".repeat(80) + "\n");
    }

    /**
     * ERROR tag for various types of errors.
     */
    public static String errorMessageTag() {
        return "ERROR: ";
    }

    /**
     * Displays an error message when the user inputs an invalid index.
     */
    public String invalidIndexMessage() {
        return "Invalid expense index.";
    }

    /**
     * Displays the exit message when the user exits the program.
     */
    public void displayExitMessage() {
        System.out.println("Thank you for using the Expense Manager. Goodbye!");
    }

    /**
     * Displays an error message when the user inputs an invalid command.
     */
    public void displayInvalidCommandMessage() {
        System.out.println("Invalid command.");
    }

    /**
     * Displays message upon data file creation.
     */
    public static void createNewFileMessage() {
        System.out.println("Created a new file!");
    }

    /**
     * Displays message for a group with no members.
     */
    public String displayEmptyGroupMessage() {
        return "No members in this group.";
    }

    /**
     * Displays message for missing groups.txt file.
     */
    public void displayMissingFileExceptionMessage() {
        System.out.println("Owed amounts file not found. No amounts to display.");
    }

    /**
     * Displays message for invalid amounts.
     */
    public void displayInvalidAmountExceptionMessage() {
        System.out.println("Error parsing owed amounts. Some amounts may not be displayed.");
    }

    /**
     * Displays message for missing group.
     */
    public void displayMissingGroupMessage() {
        System.out.println("Group not found.");
    }
}
//@@author
