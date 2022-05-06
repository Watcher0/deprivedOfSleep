package ServerModule.commands;

import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.MovieLite;

/**
 * 'add' command. Adds a new element to the collection.
 */
public class AddCommand extends Command{
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
            collectionManager.addToCollection(new Movie(
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
            ResponseOutputer.append("Кино успешно добавлено в коллекцию!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}
