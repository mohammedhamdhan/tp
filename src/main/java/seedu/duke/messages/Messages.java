//@@author matthewyeo1
package seedu.duke.messages;

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
    public static void setDivider() {
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

    /**
     * Displays the list of available commands.
     */
    public void displayCommandList() {
        System.out.println("""
            AVAILABLE COMMANDS:
            ------------------
            help
              Description: Displays this help message
              Usage: help

            add
              Description: Add a new expense and optionally include a description
              Usage: add/<title>/<date>/<amount>

            mark
              Description: Mark an expense as settled
              Usage: mark
              You will be prompted to enter: expense number

            unmark
              Description: Unmark an expense to become an unsettled expense
              Usage: unmark
              You will be prompted to enter: expense number

            list
              Description: List all expenses
              Usage: list

            list-settled
              Description: List all settled expenses
              Usage: list-settled

            list-unsettled
              Description: List all unsettled expenses
              Usage: list-unsettled

            delete
              Description: Delete an existing expense
              Usage: delete<expense ID>

            edit
              Description: Edit an existing expense and optionally change the description
              Usage: edit/<expense ID>/<new title>/<new date>/<new amount>
        
            balance
              Description: Show the balance overview (total expenses and amount owed)
              Usage: balance

            create-group
              Description: Create a new group and add members to it
              Usage: create-group
              You will be prompted to:
                - Enter group name
                - Enter members to add one by one
                - Enter done to create and save the group

            view-group
              Description: View the members of a specific group
              Usage: view-group
              You will be prompted to enter:
                - Enter group name

            add-member
              Description: Add a member to an existing group/ create a new group and add
              Usage: add-member
              You will be prompted to enter:
                - Enter name of new member
                - Enter name group to add to
                    If the group exists, adds to group. Else prompts the user to create a new group first

            remove-group
              Description: Remove a member from a group
              Usage: remove-group
              You will be prompted to enter:
                - Enter name of member to remove
                - Enter group to remove member from

            my-groups
              Description: View the members of a specific group
              Usage: view-group
              Shows all the members and groups

            split
              Description: Split an expense between the members of an existing group
              Usage: split
              You will be prompted to enter:
                - Expense
                - Group to split it among

            summary
              Description: View expense summaries in different formats
              Usage: summary
              You will be prompted to choose:
                - Monthly Summary: Shows total expenses and count per month
                - Category-wise Summary: Shows total expenses and count per category

            export
              Description: Export expense summaries to text files
              Usage: export
              You will be prompted to choose:
                - Monthly Summary: Exports to monthly_summary.txt
                - Category-wise Summary: Exports to category_summary.txt

            change-currency
              Description: Change all your expenses to a different currency
              Usage: change-currency
              You will be prompted to enter:
                - Please enter a number
              Enter 1 to enter your own exchange rate
              Enter 2 to use an estimated exchange rate
                - Please enter a currency to change to
              Enter currency based on ISO 4217 standard (e.g., SGD, USD, JPY)
                - Please input your exchange rate from USD to a new currency (if you picked 1)
              Enter the exchange rate you'd like to use

            sort-list
              Description: Choose how you would like to view your expenses
              Usage: sort-list N, where N is 1,2,3 or 4
                - Sort expenses for viewing by:
                [1] Title (ascending alphabetically)
                [2] Title (descending alphabetically)
                [3] Amount (ascending)
                [4] Amount (descending)
    
              Enter 1 to show the expense of which the first letter of the title comes last lexicographically on top
              Enter 2 to show the expense of which the first letter of the title comes first lexicographically on top
              Enter 3 to show the expense with the smallest amount on top
              Enter 4 to show the expense with the largest amount on top

            exit
              Description: Exit the program
              Usage: exit
            """);
    }
}
//@@author
