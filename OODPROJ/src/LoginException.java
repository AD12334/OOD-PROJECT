public class LoginException extends RuntimeException {
  LoginException(String message) {
    super("Login not recognised, " + message);


  }
}
