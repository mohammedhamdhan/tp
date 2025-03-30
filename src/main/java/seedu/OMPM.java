package seedu;

import java.util.Scanner;
import seedu.duke.commands.ExpenseCommand;
import seedu.duke.commands.FriendsCommands;
import seedu.duke.commands.SplitCommand;
import seedu.duke.currency.Currency;
import seedu.duke.expense.BudgetManager;
import seedu.duke.friends.GroupManager;
import seedu.duke.menu.HelpPage;
import seedu.duke.messages.Messages;
import seedu.duke.summary.ExpenseClassifier;
import seedu.duke.ui.UI;
import seedu.duke.commands.Commands;


public class OMPM {

    private UI ui;
    private Scanner scanner;
    private Messages messages;
    private HelpPage helpPage;
    private Commands commands;
    private ExpenseClassifier expenseClassifier;

    public OMPM () {
        scanner = new Scanner(System.in);
        messages = new Messages();
        helpPage = new HelpPage();
        commands = new Commands();
        expenseClassifier = new ExpenseClassifier();
    }

    public static void main(String[] args) {
        new OMPM().run();
    }

    public void run() {
        GroupManager groupManager = new GroupManager();  // Create an instance of GroupManager
        FriendsCommands friendsCommand = new FriendsCommands(groupManager);
        SplitCommand splitCommand = new SplitCommand(scanner, groupManager, friendsCommand);

        Currency currency = new Currency(scanner, new BudgetManager());
        ExpenseCommand expenseCommand = new ExpenseCommand(new BudgetManager(), scanner, currency);
        ExpenseClassifier expenseClassifier = new ExpenseClassifier();

        ui = new UI(scanner, 
        messages, 
        helpPage, 
        "data/expenses.txt", 
        expenseCommand, 
        commands, 
        friendsCommand, 
        splitCommand,
        currency,
        expenseClassifier
        );

        // Display welcome message and command list
        messages.displayWelcomeMessage();
        helpPage.displayCommandList();
        messages.setDivider();

        ui.handleUserInput();
    }
}
