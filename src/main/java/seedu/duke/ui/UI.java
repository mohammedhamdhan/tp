package seedu.duke.ui;

import java.util.Scanner;

import seedu.duke.commands.ExpenseCommand;
import seedu.duke.menu.HelpPage;
import seedu.duke.messages.Messages;
import seedu.duke.expense.BudgetManager;

public class UI {
    private final Scanner scanner;
    private final Messages messages;
    private final HelpPage helpPage;
    private final String storageFilePath;
    private final ExpenseCommand expenseCommand;
    private final BudgetManager budgetManager;
    private boolean isRunning;

    public UI(Scanner scanner, Messages messages, HelpPage helpPage, String storageFilePath, 
              ExpenseCommand expenseCommand) {
        this.scanner = scanner;
        this.messages = messages;
        this.helpPage = helpPage;
        this.storageFilePath = storageFilePath;
        this.expenseCommand = expenseCommand;
        this.budgetManager = expenseCommand.getBudgetManager();
        this.isRunning = true;
    }

    public void handleUserInput() {
        while (isRunning) {
            System.out.print("Enter command: ");
            String userInput = scanner.nextLine();
            processCommand(userInput);
        }
    }

    private void processCommand(String userInput) {
        String command = userInput.trim().toLowerCase();
        
        switch (command) {
        case "help":
            helpPage.displayCommandList();
            break;
        case "exit":
            budgetManager.saveAllExpenses();
            messages.displayExitMessage();
            isRunning = false;
            break;
        case "add":
            expenseCommand.executeAddExpense();
            break;
        case "list":
            expenseCommand.displayAllExpenses();
            break;
        case "delete":
            expenseCommand.executeDeleteExpense();
            break;
        case "edit":
            expenseCommand.executeEditExpense();
            break;
        case "balance":
            expenseCommand.showBalanceOverview();
            break;
        default:
            System.out.println("Invalid command. Type 'help' to see available commands.");
            break;
        }
        
        if (isRunning) {
            messages.setDivider();
        }
    }
}


