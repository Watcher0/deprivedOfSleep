package ServerModule.commands;

import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import ServerModule.util.CollectionManager;
import common.exceptions.WrongAmountOfArgumentsException;
import common.util.MovieLite;

/**
 * 'add_if_min' command. Adds a new element to the collection if it's min.
 */
public class AddIfMinCommand extends Command{
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty()|| objectArgument == null) throw new WrongAmountOfArgumentsException();
            MovieLite movie = (MovieLite) objectArgument;
            boolean added = collectionManager.addIfMinToCollection(new Movie(
                            collectionManager.generateNextId(),
                            movie.getName(),
                            movie.getCoordinates(),
                            movie.getOscarsCount(),
                            movie.getGoldenPalmCount(),
                            movie.getGenre(),
                            movie.getMpaaRating(),
                            movie.getOperator()
                    )
            );
            if (added) {
                ResponseOutputer.append("Кино успешно добавлено в коллекцию!\n");
            } else {
                ResponseOutputer.append("Кино не добавлено в коллекцию!\n");
            }
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}
