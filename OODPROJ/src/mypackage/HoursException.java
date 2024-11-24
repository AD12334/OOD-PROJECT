package mypackage;

/**
 * This class is a custom exception class that is thrown when the user enters an
 * invalid number of hours.
 */
public class HoursException extends Exception {
    HoursException(String message) {
        super("Please enter a valid number of hours: " + message);
    }
}
