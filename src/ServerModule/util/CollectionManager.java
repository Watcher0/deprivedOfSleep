package ServerModule.util;

import common.data.Movie;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Класс, описывающий коллекцию и определяющий взаимодействие с ней
 */
public class CollectionManager {
    private HashSet<Movie> myCollection = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
    }

    /**
     * @return The collection itself.
     */
    public HashSet<Movie> getCollection() {
        return myCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return myCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return myCollection.size();
    }

    /**
     * Remove movies greater than the selected one.
     * @param movie A Movie to compare with.
     */
    public void removeGreater(Movie movie) {
        myCollection.removeIf(movie1 -> movie1.compareTo(movie) > 0);
    }

    /**
     * @param id ID of the studyGroup.
     * @return movie by his ID or null if movie isn't found.
     */
    public Movie getById(Integer id) {
        for (Movie movie : myCollection) {
            if (movie.getId() == id) return movie;
        }
        return null;
    }

    /**
     * Adds a new movie to collection.
     * @param movie A Movie to add.
     */
    public void addToCollection(Movie movie) {
        myCollection.add(movie);
    }

    /**
     * Adds a new movie to collection if it's min movie.
     * @param movie A Movie to add.
     */
    public boolean addIfMinToCollection(Movie movie) {
        Movie minMovie = myCollection.stream().min(Comparator.comparing(Movie::getOscarsCount)).orElseThrow(NoSuchElementException::new);

        if (movie.getOscarsCount() < minMovie.getOscarsCount()) {
            myCollection.add(movie);
            return true;
        }

        return false;
    }

    public String ascending() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        List<Movie> sortedCollection = myCollection.stream().sorted(Comparator.comparing(Movie::getOscarsCount)).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (Movie movie : sortedCollection) {
            info.append(movie);
            info.append("\n\n");
        }
        return info.toString();
    }

    public String descending() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        List<Movie> sortedCollection = myCollection.stream().sorted(Comparator.comparing(Movie::getOscarsCount)).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (Movie movie : sortedCollection) {
            info.append(movie);
            info.append("\n\n");
        }
        return info.toString();
    }

    public String descendingMpaaRating() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        List<Movie> sortedCollection = myCollection.stream().sorted(Comparator.comparing(Movie::getOscarsCount)).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        StringBuilder info = new StringBuilder();
        for (Movie movie : sortedCollection) {
            info.append(movie.getMpaaRating());
            info.append("\n\n");
        }
        return info.toString();
    }

    /**
     * Removes a new movie to collection.
     * @param movie A Movie to remove.
     */
    public void removeFromCollection(Movie movie) {
        myCollection.remove(movie);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        myCollection.clear();
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        fileManager.writeCollection(myCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    public void loadCollection() throws FileNotFoundException {
        myCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * @param collection myCollection
     * @return The last element of the collection or null if collection is empty
     */
    public Movie getLast(Collection<Movie> collection) {
        if (myCollection.isEmpty()) {return null;}
        else {
            Movie last = null;
            int max = 1;
            for (Movie m : collection) {
                if (m.getId() > max) last = m;
            }
            return last;
        }
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public Integer generateNextId() {
        if (myCollection.isEmpty()) return 1;
        return getLast(myCollection).getId() + 1;
    }

    @Override
    public String toString() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        List<Movie> printCollection = myCollection.stream().sorted(Comparator.comparing(Movie::getCoordinates)).collect(Collectors.toList());

        StringBuilder info = new StringBuilder();
        for (Movie movie : printCollection) {
            info.append(movie);
            info.append("\n\n");
        }
        return info.toString();
    }
}
