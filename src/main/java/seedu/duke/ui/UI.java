//@@author matthewyeo1
package seedu.duke.ui;

import java.util.Scanner;

import seedu.duke.commands.Commands;

import seedu.duke.currency.Currency;
import seedu.duke.messages.Messages;

import seedu.duke.commands.ExpenseCommand;
import seedu.duke.commands.FriendsCommands;
import seedu.duke.commands.SplitCommand;
import seedu.duke.expense.BudgetManager;
import seedu.duke.summary.ExpenseClassifier;

/**
 * The UI class handles user interactions by processing input commands
 * and delegating tasks to respective command handlers.
 */
public class UI {
    private final Scanner scanner;
    private final Messages messages;
    private final String storageFilePath;
    private final ExpenseCommand expenseCommand;
    private final FriendsCommands friendsCommand;
    private final SplitCommand splitCommand;
    private Commands commands;
    private final BudgetManager budgetManager;
    private ExpenseClassifier expenseClassifier;
    private boolean isRunning;
    private final Currency currency;

    /**
     * Constructs a UI object for handling user input and interactions.
     *
     * @param scanner           Scanner for reading user input.
     * @param messages          Messages object for displaying messages.
     * @param storageFilePath   File path for data storage.
     * @param expenseCommand    Handles expense-related commands.
     * @param commands          Command constants.
     * @param friendsCommand    Handles friend group-related commands.
     * @param splitCommand      Handles expense splitting commands.
     * @param currency          Handles currency-related operations.
     * @param expenseClassifier Categorizes expenses for summary.
     */
    public UI(Scanner scanner, 
            Messages messages,
            String storageFilePath,
            ExpenseCommand expenseCommand, 
            Commands commands, 
            FriendsCommands friendsCommand, 
            SplitCommand splitCommand,
            Currency currency,
            ExpenseClassifier expenseClassifier) {
      
        this.scanner = scanner;
        this.messages = messages;
        this.storageFilePath = storageFilePath;
        this.expenseCommand = expenseCommand;
        this.commands = commands;
        this.budgetManager = expenseCommand.getBudgetManager();
        this.friendsCommand = friendsCommand;
        this.splitCommand = splitCommand;
        this.expenseClassifier = expenseClassifier;
        this.isRunning = true;
        this.currency = currency;
    }

    /**
     * Continuously listens for user input and processes commands accordingly.
     */
    public void handleUserInput() {
        while (isRunning) {
            messages.enterCommandMessage();
            
            // Check if input exists before reading
            if (scanner.hasNextLine()) {
                String userInput = scanner.nextLine();
                processCommand(userInput);
            } else {
                // Handle the case where there is no input available
                messages.emptyInputMessage();
                isRunning = false;
                break;
            }
            
            if (isRunning) {
                messages.setDivider();
            }
        }
    }

    /**
     * Processes a user command and executes the corresponding action.
     *
     * @param userInput The command entered by the user.
     */
    public void processCommand(String userInput) {
        String command = userInput.trim().toLowerCase();
        
        switch (command) {
        case Commands.HELP:
            messages.displayCommandList();
            break;
        case Commands.EXIT:
            // Save expenses before exiting
            budgetManager.saveAllExpenses();
            messages.displayExitMessage();
            isRunning = false;
            break;
        case Commands.ADD:
            expenseCommand.executeAddExpense();
            break;
        case Commands.LIST:
            expenseCommand.displayAllExpenses();
            break;
        case Commands.DELETE:
            expenseCommand.executeDeleteExpense();
            break;
        case Commands.EDIT:
            expenseCommand.executeEditExpense();
            break;
        case Commands.BALANCE:
            expenseCommand.showBalanceOverview();
            break;
        case Commands.MARK:
            expenseCommand.executeMarkCommand();
            break;
        case Commands.UNMARK:
            expenseCommand.executeUnmarkCommand();
            break;
        case Commands.SETTLED_LIST:
            expenseCommand.displaySettledExpenses();
            break;
        case Commands.UNSETTLED_LIST:
            expenseCommand.displayUnsettledExpenses();
            break;
        case Commands.CREATE_GROUP:
            friendsCommand.createGroup();
            break;
        case Commands.VIEW_GROUP:
            friendsCommand.viewGroup();
            break;
        case Commands.ADD_MEMBER:
            friendsCommand.addMember();
            break;
        case Commands.REMOVE_MEMBER:
            friendsCommand.removeMember();
            break;
        case Commands.VIEW_ALL_GROUPS:
            friendsCommand.viewAllGroups();
            break;
        case Commands.SPLIT:
            splitCommand.executeSplit();
            break;
        case Commands.REMOVE_GROUP:
            friendsCommand.removeGroup();
            break;
        case Commands.CHANGE_CURRENCY:
            currency.changeCurrency();
            break;
        case Commands.SUMMARY:
            expenseCommand.showExpenseSummary();
            break;
        case Commands.EXPORT:
            expenseCommand.exportExpenseSummary();
            break;
        case Commands.FIND:
            expenseCommand.findExpense();
            break;
        default:
            messages.displayInvalidCommandMessage();
            break;
        }
    }
}
//@@author
