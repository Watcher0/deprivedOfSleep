package ServerModule.commands;


import ServerModule.util.ResponseOutputer;
import common.data.*;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MovieNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.MovieLite;

import java.time.LocalDate;

/**
 * 'update' command. Updates the information about selected studyGroup.
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            int id = Integer.parseInt(argument);
            Movie movie = collectionManager.getById(id);
            MovieLite movieLite = (MovieLite) objectArgument;

            if (movie == null) throw new MovieNotFoundException();

            String name = movieLite.getName() == null ? movie.getName() : movieLite.getName();
            Coordinates coordinates = movieLite.getCoordinates() == null ? movie.getCoordinates() : movieLite.getCoordinates();
            LocalDate creationDate = movie.getCreationDate();
            Long oscarsCount = movieLite.getOscarsCount() == -1 ? movie.getOscarsCount() : movieLite.getOscarsCount();
            Integer goldenPalmCount = movieLite.getGoldenPalmCount() == -1 ? movie.getGoldenPalmCount() : movieLite.getGoldenPalmCount();
            MovieGenre genre = movieLite.getGenre() == null ? movie.getGenre() : movieLite.getGenre();
            MpaaRating mpaaRating = movieLite.getMpaaRating() == null ? movie.getMpaaRating() : movieLite.getMpaaRating();
            Person operator = movieLite.getOperator() == null ? movie.getOperator() : movieLite.getOperator();

            collectionManager.removeFromCollection(movie);

            collectionManager.addToCollection(new Movie(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    oscarsCount,
                    goldenPalmCount,
                    genre,
                    mpaaRating,
                    operator
            ));
            ResponseOutputer.append("Кино успешно изменено!\n");
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
