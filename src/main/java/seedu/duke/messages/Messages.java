package seedu.duke.messages;

public class Messages {

    public void displayWelcomeMessage() {
        setDivider();
        System.out.println("Welcome to O$P$ expense-tracker! How can I help you?");
    }

    public void setDivider() {
        System.out.println("\n" + "_".repeat(80) + "\n");
    }

    public void invalidCommandMessage() {
        System.out.println(errorMessageTag() + "Invalid command");
    }

    public void exitAppMessage() {
        System.out.println("Have a nice day!");
    }

    public static void createNewFileMessage(String filePath) {
        System.out.println("Storage file created: " + filePath);
    }

    public static String errorMessageTag() {
        return "ERROR: ";
    }

    public static void loadDataMessage(String filePath) {
        System.out.println("Loading data from: " + filePath  + ": \n");
    }

    public static void emptyDataFileMessage() {
        System.out.println("No previous data found.");
    }

    /**
     * Displays the exit message when the user exits the program.
     */
    public void displayExitMessage() {
        System.out.println("Thank you for using the Expense Manager. Goodbye!");
    }
}

