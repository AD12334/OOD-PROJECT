package mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class implements the userbase functionality to our system
 */
public class Userbase {
    private static HashMap<String, User> usersbase; // Store User objects
    private static boolean loggedin = false;

    public Userbase() throws FileNotFoundException {
        usersbase = new HashMap<>();

        // Add the Admin and HumanResources users directly
        usersbase.put("admin", new Admin("admin", "admin123"));
        usersbase.put("hr", new HumanResources("hr", "hr123"));

        loadEmployees();
    }

    /**
     * Login method for the system
     *
     * @throws FileNotFoundException
     */
    public static void Login() {
        loadEmployees(); // Reload employees in case of new additions or
                         // modifications
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPlease enter your username (or \"q\" to quit): ");
        String username = sc.nextLine();
        if (username.equals("q")) {
            System.exit(0);
        }
        System.out.println("Please enter your password: ");
        String password = sc.nextLine();

        try {
            checkLogin(username);
            checkPassword(username, password);
            if (loggedin) {
                // Polymorphic call to displayOptions based on user type

                User user = usersbase.get(username);

                if (user instanceof Employee) {

                    Employee emp = (Employee) user;
                    if (emp.getField().equals("ULAC") || emp.getField().equals("ULAC2")) {
                        // public HourlyEmployee(String name, String username, String employeeID,
                        // int salary, String field, String role, int scale)
                        // throws FileNotFoundException {
                        // super(name, username, employeeID, field, role, scale);

                        emp = new HourlyEmployee(emp.getName(), emp.getUsername(), "t" + emp.getEmployeeID(),
                                emp.getSalary(), emp.getField(), emp.getRole(), emp.getScale());
                        emp.displayOptions();

                    }

                }
                user.displayOptions();
            }

        } catch (LoginException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            // System.out.println("Invalid password. Try again.");
            System.out.println(e);
            Login();
        }
    }

    public static HashMap<String, User> getUsersbase() {
        return usersbase;
    }

    /**
     * Check if the username is valid
     *
     * @param username
     * @throws LoginException
     */
    public static void checkLogin(String username) throws LoginException {
        if (usersbase.containsKey(username)) {
            System.out.println("\nUsername is valid.");
        } else {
            throw new LoginException("\nUsername is not valid.");
        }
    }

    /**
     * Check if the password is valid
     *
     * @param username
     * @param password
     * @throws Exception
     */
    public static void checkPassword(String username, String password)
            throws Exception {
        User user = usersbase.get(username);
        if (user.getPassword().equals(password)) {
            System.out.println("Password is valid.");
            System.out.println("Logged in successfully.");
            loggedin = true;
        } else {
            throw new Exception("Incorrect password.");
        }
    }

    /**
     * Load all employee records from the employee_database.csv file
     *
     * @throws FileNotFoundException
     */
    public static void loadEmployees() {
        // Load all employee records from the employee_database.csv file
        try {
            Scanner sc = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
            sc.useDelimiter("\n");

            while (sc.hasNext()) {
                String line = sc.next().trim();
                String[] lines = line.split(",");
                String name = lines[0];
                String username = lines[1];
                String employeeID = lines[2];
                String field = lines[3];
                String role = lines[4]; // Specific role within Employee category
                int scale = Integer.parseInt(lines[5]);

                // Treat every entry in the CSV as an Employee
                User employee = new Employee(name, username, employeeID, field, role, scale);
                usersbase.put(username, employee);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
