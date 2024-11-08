import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Userbase {
    private HashMap<String, User> usersbase; // Store User objects
    private boolean loggedin = false;

    public Userbase() throws FileNotFoundException {
        usersbase = new HashMap<>();
        Scanner sc = new Scanner(new File("OODPROJ/src/employee_database.csv"));
        sc.useDelimiter("\n");

        while (sc.hasNext()) {
            String line = sc.next().trim();
            String[] lines = line.split(",");
            String username = lines[0];
            String id = lines[1];
            String password = lines[2];
            String field = lines[3];
            String role = lines[4];
            int scale = Integer.parseInt(lines[5]);
            // Declare User object only if a valid role is found
            User user = null;
            if (role.equalsIgnoreCase("Admin")) {
                user = new Admin(username, password);
            } else if (role.equalsIgnoreCase("Employee")) {
                user = new Employee(username, id, field, role, scale);
            } else if (role.equalsIgnoreCase("HumanResources")) {
                // user = new HumanResources(username, id);
            }

            // Only add to usersbase if user is not null
            if (user != null) {
                usersbase.put(username, user);
            }
        }
        sc.close();
    }

    public void checkLogin(String username) throws LoginException {
        if (usersbase.containsKey(username)) {
            System.out.println("Username is valid");
        } else {
            throw new LoginException("Username is not valid");
        }
    }

    public void Login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = sc.nextLine();
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();

        try {
            checkLogin(username);
            checkPassword(username, password);
            if (loggedin) {
                // Call the displayOptions method based on user type
                User user = usersbase.get(username);
                user.displayOptions(); // Polymorphic call to the correct displayOptions()
            }

        } catch (LoginException e) {
            System.out.println(e.getMessage());
            Login();
        } catch (Exception e) {
            System.out.println("Invalid password. Try again.");
            Login();
        }
        sc.close();
    }

    public void checkPassword(String username, String password) throws Exception {
        User user = usersbase.get(username);
        if (user.getPassword().equals(password)) {
            System.out.println("Password is valid");
            System.out.println("Logged in successfully");
            loggedin = true;
        } else {
            throw new Exception("Incorrect password");
        }
    }
}