package seedu.ui;
import java.util.Scanner;
import seedu.messages.Messages;
import seedu.commands.Commands;
import seedu.help_page.HelpPage;

public class UI {
    private Scanner scanner;
    private Messages messages;
    private HelpPage helpPage;

    public UI (Scanner scanner, Messages messages, HelpPage helpPage) {
        this.scanner = scanner;
        this.messages = messages;
        this.helpPage = helpPage;
    };
    
    public void handleUserInput() {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(Commands.HELP)) {
                helpPage.displayCommandList();
                messages.setDivider();
            } else if (input.equalsIgnoreCase(Commands.CATEGORIES)) {
                messages.setDivider();
                
                messages.setDivider();
            } else if (input.contains(Commands.EXIT_APP)) {
                messages.setDivider();
                messages.exitAppMessage();
                messages.setDivider();
                break;
            } else {
                messages.setDivider();
                messages.invalidCommandMessage();
                messages.setDivider();
            }
        }
        scanner.close();
    }
}
