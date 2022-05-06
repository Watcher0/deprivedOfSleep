package ServerModule.util;

import common.data.Coordinates;
import common.data.Location;
import common.data.MovieGenre;
import common.data.MpaaRating;
import common.exceptions.NumberOutOfBoundsException;
import common.exceptions.UsedIdException;
import common.exceptions.WrongAmountOfCoordinatesException;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import java.time.LocalDate;

/**
 * Класс, устанавливающий значения полей объекта класса Movie
 */
public class FieldSetterClass {

    /**
     * Метод, устанавливающий значение movieName
     * @param s необработанная строка
     * @return groupName
     */
    public static String getMovieName(String s){
        FileManager.exceptionPointer = "name";
        ValidationClass.checkString(s, false);
        String name = new String(s);
        return name;
    }
    /**
     * Метод, устанавливающий значение groupCoordinates
     * @param s необработанная строка
     * @return groupCoordinates
     */
    public static Coordinates getMovieCoordinates(String s) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        FileManager.exceptionPointer = "coordinates";
        ValidationClass.checkCoordinates(s, false);
        Coordinates coordinates = Coordinates.valueOf(s);
        if (s.equals("")){return null;}
        return coordinates;
    }
    /**
     * Метод, устанавливающий значение groupStudentsCount
     * @param s необработанная строка
     * @return groupStudentsCount
     */
    public static Long getMovieOscarsCount(String s) throws NumberOutOfBoundsException{
        FileManager.exceptionPointer = "OscarsCount";
        ValidationClass.checkLong(s, false, true, 0L);
        Long studentsCount = Long.valueOf(s);
        if (s.equals("")){return null;}
        return studentsCount;
    }
    /**
     * Метод, устанавливающий значение groupExpelledStudents
     * @param s необработанная строка
     * @return groupExpelledStudents
     */
    public static Integer getMovieGoldenPalmCount(String s) throws NumberOutOfBoundsException{
        FileManager.exceptionPointer = "goldenPalmCount";
        ValidationClass.checkInt(s, true, true, 0);
        if (s.equals("")){return null;}
        Integer expelledStudents = Integer.valueOf(s);
        return expelledStudents;
    }
    /**
     * Метод, устанавливающий значение groupShouldBeExpelled
     * @param s необработанная строка
     * @return groupShouldBeExpelled
     */
    public static MovieGenre getMovieGenre(String s){
        FileManager.exceptionPointer = "genre";
        ValidationClass.checkGenre(s, false);
        if (s.equals("")){return null;}
        MovieGenre movieGenre = MovieGenre.valueOf(s);
        return movieGenre;
    }
    /**
     * Метод, устанавливающий значение groupSemesterEnum
     * @param s необработанная строка
     * @return groupSemesterEnum
     */
    public static MpaaRating getMovieMpaaRating(String s){
        FileManager.exceptionPointer = "mpaaRating";
        ValidationClass.checkMpaaRating(s, true);
        if (s.equals("")){return null;}
        MpaaRating mpaaRating = MpaaRating.valueOf(s);
        return mpaaRating;
    }
    /**
     * Метод, устанавливающий значение adminName
     * @param s необработанная строка
     * @return adminName
     */
    public static String getOperatorName(String s){
        FileManager.exceptionPointer = "movieOperator - name";
        ValidationClass.checkString(s, false);
        if (s.equals("")){return null;}
        return s;
    }
    /**
     * Метод, устанавливающий значение adminHeight
     * @param s необработанная строка
     * @return adminHeight
     */
    public static Long getOperatorHeight(String s) throws NumberOutOfBoundsException{
        FileManager.exceptionPointer = "movieOperator - height";
        ValidationClass.checkLong(s, false, true, 0L);
        if (s.equals("")){return null;}
        Long height = Long.valueOf(s);
        return height;
    }
    /**
     * Метод, устанавливающий значение adminHairColor
     * @param s необработанная строка
     * @return adminHairColor
     */
    public static LocalDate getOperatorBirthday(String s){
        FileManager.exceptionPointer = "movieOperator - birthday";
        ValidationClass.checkDate(s, true);
        if (s.equals("")){return null;}
        LocalDate birthday = LocalDate.parse(s, ISO_LOCAL_DATE);
        return birthday;
    }
    /**
     * Метод, устанавливающий значение adminNationality
     * @param s необработанная строка
     * @return adminNationality
     */
    public static String getOperatorPassportID(String s){
        FileManager.exceptionPointer = "movieOperator - passportID";
        ValidationClass.checkString(s, false);
        if (s.equals("")){return null;}
        return s;
    }
    /**
     * Метод, устанавливающий значение adminLocation
     * @param s необработанная строка
     * @return adminLocation
     */
    public static Location getOperatorLocation(String s) throws WrongAmountOfCoordinatesException, NumberOutOfBoundsException {
        FileManager.exceptionPointer = "movieOperator - location";
        ValidationClass.checkLocation(s, false);
        if (s.equals("")){return null;}
        Location location = Location.valueOf(s);
        return location;
    }
}
