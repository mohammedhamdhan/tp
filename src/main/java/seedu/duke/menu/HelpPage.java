package seedu.duke.menu;

/**
 * Represents the help page that displays available commands.
 */
public class HelpPage {
    /**
     * Displays the list of available commands.
     */
    public void displayCommandList() {
        System.out.println("AVAILABLE COMMANDS:");
        System.out.println("------------------");
        
        System.out.println("help");
        System.out.println("  Description: Displays this help message");
        System.out.println("  Usage: help");
        System.out.println();
        
        System.out.println("add");
        System.out.println("  Description: Add a new expense");
        System.out.println("  Usage: add");
        System.out.println("  You will be prompted to enter:");
        System.out.println("    - Title: Short name for the expense");
        System.out.println("    - Description: Detailed information about the expense");
        System.out.println("    - Amount: The monetary value (must be a positive number)");
        System.out.println();

        System.out.println("mark");
        System.out.println("  Description: mark an expense as settled");
        System.out.println("  Usage: mark");
        System.out.println("  You will be prompted to enter: expense number");
        System.out.println();

        System.out.println("unmark");
        System.out.println("  Description: unmark an expense to become an unsettled expense");
        System.out.println("  Usage: unmark");
        System.out.println("  You will be prompted to enter: expense number");
        System.out.println();

        System.out.println("list");
        System.out.println("  Description: List all expenses");
        System.out.println("  Usage: list");
        System.out.println();

        System.out.println("list-settled");
        System.out.println("  Description: List all settled expenses");
        System.out.println("  Usage: list-settled");
        System.out.println();

        System.out.println("list-unsettled");
        System.out.println("  Description: List all unsettled expenses");
        System.out.println("  Usage: list-unsettled");
        System.out.println();

        System.out.println("delete");
        System.out.println("  Description: Delete an existing expense");
        System.out.println("  Usage: delete");
        System.out.println("  You will be shown the list of expenses and prompted to enter");
        System.out.println("  the index of the expense to delete");
        System.out.println();
        
        System.out.println("edit");
        System.out.println("  Description: Edit an existing expense");
        System.out.println("  Usage: edit");
        System.out.println("  You will be shown the list of expenses and prompted to enter:");
        System.out.println("    - Index of the expense to edit");
        System.out.println("    - New title (press Enter to keep current)");
        System.out.println("    - New description (press Enter to keep current)");
        System.out.println("    - New amount (press Enter to keep current)");
        System.out.println();
        
        System.out.println("balance");
        System.out.println("  Description: Show the balance overview (total expenses and amount owed)");
        System.out.println("  Usage: balance");
        System.out.println();
        
        System.out.println("exit");
        System.out.println("  Description: Exit the program");
        System.out.println("  Usage: exit");
    }
}
