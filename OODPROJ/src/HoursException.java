public class HoursException extends Exception {
    HoursException(String message) {
      super("Please enter a valid number of hours: " + message);
    }
}
