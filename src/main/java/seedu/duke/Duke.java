package seedu.duke;

import java.util.Scanner;

import seedu.duke.commands.ExpenseCommand;
import seedu.duke.commands.Commands;
import seedu.duke.expense.BudgetManager;
import seedu.duke.menu.HelpPage;
import seedu.duke.messages.Messages;
import seedu.duke.storage.DataStorage;
import seedu.duke.ui.UI;

public class Duke {
    private final String storageFilePath;
    private final BudgetManager budgetManager;

    public Duke(String fileName) {
        this.storageFilePath = fileName;
        DataStorage.ensureFileExists();
        this.budgetManager = new BudgetManager();
    }

    public static void main(String[] args) {
        new Duke(DataStorage.DATA_FILE).run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        Messages messages = new Messages();
        HelpPage helpPage = new HelpPage();
        Commands commands = new Commands();
        ExpenseCommand expenseCommand = new ExpenseCommand(budgetManager, scanner);
        UI ui = new UI(scanner, messages, helpPage, storageFilePath, expenseCommand, commands);

        messages.displayWelcomeMessage();
        helpPage.displayCommandList();
        messages.setDivider();
        ui.handleUserInput();
    }
}


