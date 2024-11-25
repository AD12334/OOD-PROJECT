package mypackage;

/**
 * This class implements a login exception
 */
public class LoginException extends RuntimeException {
    LoginException(String message) {
        super("Login not recognised,  " + message);

    }
}
