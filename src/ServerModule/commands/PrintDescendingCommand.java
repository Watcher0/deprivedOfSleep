package ServerModule.commands;

import ServerModule.util.ResponseOutputer;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;

/**
 * Command 'print_ascending'. Prints collection descending.
 */
public class PrintDescendingCommand extends Command {
    private CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            ResponseOutputer.append(collectionManager.descending());
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}
