import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Userbase {
    private HashMap<String, User> usersbase; // Store User objects
    private boolean loggedin = false;

    public Userbase() throws FileNotFoundException {
        usersbase = new HashMap<>();

        // Add the Admin and HumanResources users directly
        usersbase.put("admin", new Admin("admin", "admin123"));
        // usersbase.put("hr", new HumanResources("hr", "hr123"));

        // Load all employee records from the employee_database.csv file
        Scanner sc = new Scanner(new File("OODPROJ/src/employee_database.csv"));
        sc.useDelimiter("\n");

        while (sc.hasNext()) {
            String line = sc.next().trim();
            String[] lines = line.split(",");
            String username = lines[0];
            String id = lines[1];
            String password = lines[2];
            String field = lines[3];
            String role = lines[4]; // Specific role within Employee category
            int scale = Integer.parseInt(lines[5]);

            // Treat every entry in the CSV as an Employee
            User employee = new Employee(username, id, field, role, scale);
            usersbase.put(username, employee);
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
                // Polymorphic call to displayOptions based on user type
                User user = usersbase.get(username);
                user.displayOptions();
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
