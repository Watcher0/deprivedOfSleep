package ServerModule.commands;

import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MovieNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;

/**
 * 'remove_by_id' command. Removes the element by its ID.
 */
public class RemoveByIdCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Integer id = Integer.parseInt(argument);
            Movie movie = collectionManager.getById(id);
            if (movie == null) throw new MovieNotFoundException();
            collectionManager.removeFromCollection(movie);
            ResponseOutputer.append("Кино успешно удалено!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NumberFormatException exception) {
            ResponseOutputer.append("ID должен быть представлен числом!\n");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.append("Кино с таким ID в коллекции нет!\n");
        }
        return false;
    }
}