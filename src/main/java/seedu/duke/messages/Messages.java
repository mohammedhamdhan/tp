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
     * Displays an error message when the user inputs an invalid index.
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
}
//@@author
