
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Userbase {
    private String username;
    private String password;
    private HashMap<String, User> usersbase;// our userbase with key usernames and values user
    private String type;
    private int salary;
    private int scale;
    private String Employee;
    private String Field;
    private int promotion;

    public Userbase() {
        usersbase = new HashMap<>();
    }

    public void addUser(User user) throws FileNotFoundException {
        username = user.getUsername();
        int employment_id = user.getEmployeeID();
        password = user.getPassword();
        type = user.getType();
        salary = user.getSalary();
        scale = user.getScale();
        Employee = user.getEmployee();
        Field = user.getField();
        promotion = user.getpromotionid();


        usersbase.put(username, new User(username, password, type,employment_id));
    }


    public String getType() {
        return type;
    }

    public String typeMeaning(User user) {
        String c = user.getType();
        if (c.toUpperCase().equals("EMPLOYEE")) {
            return "Employee";
        } else if (c.toUpperCase().equals("ADMIN")) {
            return "Admin";
        } else if (c.toUpperCase().equals("HUMAN RESOURCES")) {
            return "Human Resources";
        }
        return "";
    }

    public void checkLogin(String username) throws LoginException {
        if (usersbase.keySet().contains(username)) {
            System.out.println("Username is valid");
        } else {
            throw new LoginException("username is not valid");
        }
    }

    public void Login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        username = sc.nextLine();
        System.out.println("Please enter your password: ");
        password = sc.nextLine();
        try {
            checkLogin(username);
            try {
                checkPassword(password);

            } catch (InputMismatchException e) {
                System.out.println("Password is incorrect");

            }

        } catch (LoginException e) {
            System.out.println(e);
            System.out.println("Username is not valid");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Username is not valid");
        }
        if (usersbase.get(username).getType() == "Employee") {
            System.out.println("View latest payslip");
        } else if (usersbase.get(username).getType() == "Admin") {
            System.out.println("View all payslips");
        } else if (usersbase.get(username).getType() == "HumanResources") {
            System.out.println("do hr stuff");
        }

    }

    public void checkPassword(String password) throws Exception {
        User user = usersbase.get(username);
        if (user.getPassword().equals(password)) {
            System.out.println("Password is valid");
            System.out.println("Logged in successfully as " + typeMeaning(usersbase.get(username)) + " Position: " + user.getEmployee());
        } else {
            throw new Exception();
        }
    }
}
