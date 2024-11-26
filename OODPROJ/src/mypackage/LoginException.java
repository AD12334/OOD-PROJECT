package mypackage;

/**
 * This class implements a login exception which is invoked if a login is invalid
 */
public class LoginException extends RuntimeException {
    LoginException(String message) {
        super("Login not recognised,  " + message);

    }
}
