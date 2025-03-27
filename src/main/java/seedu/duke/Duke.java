//@@author matthewyeo1
package seedu.duke;

import java.util.Scanner;

import seedu.duke.commands.ExpenseCommand;
import seedu.duke.commands.FriendsCommands;
import seedu.duke.commands.SplitCommand;
import seedu.duke.commands.Commands;
import seedu.duke.currency.Currency;
import seedu.duke.expense.BudgetManager;
import seedu.duke.friends.GroupManager;
import seedu.duke.menu.HelpPage;
import seedu.duke.messages.Messages;
import seedu.duke.storage.DataStorage;
import seedu.duke.ui.UI;
import seedu.duke.summary.ExpenseClassifier;

/**
 * Main class of application.
 */
public class Duke {
    private final String storageFilePath;
    private final BudgetManager budgetManager;

    public Duke(String fileName) {
        this.storageFilePath = fileName;
        DataStorage.ensureFileExists();
        this.budgetManager = new BudgetManager();
    }

    public static void main(String[] args) {
        new Duke(DataStorage.dataFile).run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        Messages messages = new Messages();
        HelpPage helpPage = new HelpPage();
        Commands commands = new Commands();

        GroupManager groupManager = new GroupManager();
        FriendsCommands friendsCommand = new FriendsCommands(groupManager);
        SplitCommand splitCommand = new SplitCommand(scanner, groupManager);
        
        ExpenseCommand expenseCommand = new ExpenseCommand(budgetManager, scanner, currency);

        ExpenseCommand expenseCommand = new ExpenseCommand(budgetManager, scanner);
        GroupManager groupManager = new GroupManager();
        FriendsCommands friendsCommand = new FriendsCommands(groupManager);
        SplitCommand splitCommand = new SplitCommand(scanner, groupManager);
        ExpenseClassifier expenseClassifier = new ExpenseClassifier();
        Currency currency = new Currency(scanner, budgetManager);

        UI ui = new UI(scanner,
            messages,
            helpPage,
            storageFilePath,
            expenseCommand,
            commands,
            friendsCommand,
            splitCommand,
            currency,
            expenseClassifier
        );

        messages.displayWelcomeMessage();
        helpPage.displayCommandList();
        messages.setDivider();
        ui.handleUserInput();
    }
}
//@@author

