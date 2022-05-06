package ServerModule.util;


import common.data.*;
import common.exceptions.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;

public class FileManager {
    private final String filePath;
    private int resultSize = 0;
    static String exceptionPointer;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writes collection to a file.
     * @param collection Collection to write.
     */
    public void writeCollection(HashSet<Movie> collection) {
        try {
            FileWriter fileWriter = new FileWriter("output.csv", false);
            for (Movie movie : collection) {
                String s;

                String stringGoldenPalm;
                if (movie.getGoldenPalmCount() == null) {
                    stringGoldenPalm = "";
                } else {
                    stringGoldenPalm = movie.getGoldenPalmCount().toString();
                }

                String stringMpaaRating;
                if (movie.getMpaaRating() == null) {
                    stringMpaaRating = "";
                } else {
                    stringMpaaRating = movie.getMpaaRating().toString();
                }

                String stringBirthday;
                if (movie.getOperator().getBirthday() == null) {
                    stringBirthday = "";
                } else {
                    stringBirthday = movie.getOperator().getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE);
                }
                s = movie.getId().toString() + "," + movie.getName() + "," + movie.getCoordinates().toString()
                        + "," + movie.getOscarsCount().toString() + "," + stringGoldenPalm +
                        "," + movie.getGenre() + "," + stringMpaaRating + "," + movie.getOperator().getName() + "," + stringBirthday + "," +
                        movie.getOperator().getHeight() + "," + movie.getOperator().getPassportID() + "," + movie.getOperator().getLocation() + "\n";
                fileWriter.write(s);

            }
            fileWriter.flush();

            System.out.println("Коллекция успешно сохранена в файл!");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения в файл!");
        }
    }

    /**
     * Reads collection from a file.
     * @return Readed collection.
     */
    public HashSet<Movie> readCollection() throws FileNotFoundException {
        String[][] values = getValuesFromFile(new File(filePath));
        HashSet<Movie> movieHashSet = new HashSet<>();
        for(int i = 0;i<resultSize;i++) {
            try {
                Integer id = Integer.parseInt(values[i][0]);
                String name = FieldSetterClass.getMovieName(values[i][1]);
                Coordinates coordinates = FieldSetterClass.getMovieCoordinates(values[i][2]);
                Long oscarsCount = FieldSetterClass.getMovieOscarsCount(values[i][3]);
                Integer goldenPalmCount = FieldSetterClass.getMovieGoldenPalmCount(values[i][4]);
                MovieGenre movieGenre = FieldSetterClass.getMovieGenre(values[i][5]);
                MpaaRating mpaaRating = FieldSetterClass.getMovieMpaaRating(values[i][6]);
                String operatorName = FieldSetterClass.getOperatorName(values[i][7]);
                LocalDate operatorBirthday = FieldSetterClass.getOperatorBirthday(values[i][8]);
                long operatorHeight = FieldSetterClass.getOperatorHeight(values[i][9]);
                String operatorPassportID = FieldSetterClass.getOperatorPassportID(values[i][10]);
                Location operatorLocation = FieldSetterClass.getOperatorLocation(values[i][11]);

                Person movieOperator = new Person(operatorName, operatorBirthday, operatorHeight, operatorPassportID, operatorLocation);
                Movie movie = new Movie(id, name, coordinates, oscarsCount, goldenPalmCount, movieGenre, mpaaRating, movieOperator);
                movieHashSet.add(movie);
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение в поле " + exceptionPointer + " в строке " + i);
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            } catch (NumberOutOfBoundsException f) {
                System.out.println("Значение не входящее в указанные рамки в поле " + exceptionPointer + " в строке" + i);
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            } catch (WrongAmountOfCoordinatesException g) {
                System.out.println("Неверное количество координат в поле " + exceptionPointer + " в строке " + i);
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            }
        }
        ResponseOutputer.append("Коллекция успешно загружена!\n");
        return movieHashSet;
    }

    private String[][] getValuesFromFile(File file) throws FileNotFoundException, IndexOutOfBoundsException {
        String[][] result = new String[1000][12];
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String s = reader.nextLine();
                String[] resultLine = normalizeString(s);
                for (int i = 0;i<12;i++){
                    result[resultSize][i] = resultLine[i];
                }
                resultSize += 1;
            }
        }
        catch (WrongLineSizeInFileException e){
            System.out.println("Неверное количество значений в одной из строк файла");
            System.out.println("Объект из этой строки не будет добавлен в коллекцию");
        }
        return result;
    }

    private String[] normalizeString(String s) throws WrongLineSizeInFileException{
        boolean status = false;
        s = "," + s;
        String[] result = new String[12];
        for(int i = 0;i<12;i++){
            result[i] = "";
        }
        int resultSize = -1;
        for(int i = 0;i<s.length();i++){

            if (resultSize == 12){break;}

            if (s.charAt(i) == ',' && !status && s.charAt(i+1) == '"'){
                status = true;
                resultSize += 1;
            }
            else if(s.charAt(i) == ',' && status){
                result[resultSize] += s.charAt(i);
            }
            else if(status && s.charAt(i) == '"' && s.charAt(i+1) == ','){
                status = false;
            }
            else if(!status && s.charAt(i) == ','){
                resultSize += 1;
            }
            else if(s.charAt(i) != '"'){
                result[resultSize] += s.charAt(i);
            }
        }
        if (resultSize != 11){
            throw new WrongLineSizeInFileException();
        }
        return result;
    }

}
