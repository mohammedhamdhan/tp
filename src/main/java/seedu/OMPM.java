package seedu;
import java.util.Scanner;
import seedu.ui.UI;
import seedu.messages.Messages;
import seedu.help_page.HelpPage;

public class OMPM {
    /**
     * Main entry-point for the O$P$ application.
     */

    private seedu.ui.UI ui;
    private Scanner scanner;
    private seedu.messages.Messages messages;
    private seedu.help_page.HelpPage helpPage;

    public OMPM () {};

    public static void main(String[] args) {
        new OMPM().run();
    }

    public void run() {
        scanner = new Scanner(System.in);
        messages = new Messages();
        helpPage = new HelpPage();
        ui = new UI(scanner, messages, helpPage);
        
        messages.displayWelcomeMessage();
        helpPage.displayCommandList();
        messages.setDivider();
        ui.handleUserInput();
    }
}
