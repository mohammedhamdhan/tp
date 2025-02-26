package seedu.help_page;
import seedu.commands.Commands;
import seedu.messages.Messages;

public class HelpPage {
    private seedu.messages.Messages messages;

    public void displayCommandList() {
        messages = new Messages();
        messages.setDivider();
        String commandList = Commands.HELP + ": " + Commands.getCommandDescription(Commands.HELP) 
                            + System.lineSeparator()
                            + Commands.CATEGORIES + ": " + Commands.getCommandDescription(Commands.CATEGORIES)
                            + System.lineSeparator()
                            + Commands.EXIT_APP + ": " + Commands.getCommandDescription(Commands.EXIT_APP) 
                            + System.lineSeparator();

        System.out.println("COMMANDS: " + System.lineSeparator());
        System.out.println(commandList);
    }
}
