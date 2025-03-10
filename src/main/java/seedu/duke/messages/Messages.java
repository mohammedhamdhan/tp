package seedu.duke.messages;

public class Messages {

    public void displayWelcomeMessage() {
        setDivider();
        System.out.println("Welcome to O$P$ expense-tracker! How can I help you?");
    }

    public void enterCommandMessage() {
        System.out.print("Enter command: ");
    }

    public void emptyInputMessage() {
        System.out.println("No input detected. Exiting program...");
    }

    public void setDivider() {
        System.out.println("\n" + "_".repeat(80) + "\n");
    }

    public static String errorMessageTag() {
        return "ERROR: ";
    }

    public void exitAppMessage() {
        System.out.println("Invalid command. Type 'help' to see available commands.");
    }

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

