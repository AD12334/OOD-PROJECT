
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Userbase {
  private String username;
  private String password;
  private HashMap<String, User>
      usersbase; // our userbase with key usernames and values user
  private String type;

  public Userbase() { usersbase = new HashMap<>(); }

  public void addUser(User user) {
    username = user.getUsername();
    password = user.getPassword();
    type = user.getType();

    usersbase.put(username, new User(username, password, type));
  }

  public String getType() { return type; }

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
    sc.close();
    try {
      checkLogin(username);
      try {
        checkPassword(password);

      } catch (InputMismatchException e) {
        System.out.println("Password is incorrect");
        Login();
      }

    } catch (LoginException e) {
      System.out.println(e);
      System.out.println("Username is not valid");
      Login();
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Username is not valid");
      Login();
    }
    if (usersbase.get(username).getType() == "Employee") {
      EmployeeUI();
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
      System.out.println("Logged in successfully as " +
                         typeMeaning(usersbase.get(username)));
    } else {
      throw new Exception();
    }
  }

  // maybe need an hourly employee method
  public void EmployeeUI() {
    Scanner input = new Scanner(System.in);
    int command;
    System.out.println("1)View latest payslip   2)Log out");
    command = input.nextInt();
    input.close();
    if (command == 1) {
      System.out.println("this is your payslip");
      EmployeeUI();
    } else if (command == 2) {
      System.out.println("Logging out");
      return;
    }
  }

  public void HrUI() {
    Scanner input = new Scanner(System.in);
    int command;
    System.out.println("1)List all employees   2)Promote Employee   3)Fire "
                       + "Employee   4)Logout");
    command = input.nextInt();
    input.close();
    if (command == 1) {
      System.out.println("all employees");
      HrUI();
    } else if (command == 2) {
      System.out.println("Promoting employee [number]");
      HrUI();
    } else if (command == 3) {
      System.out.println("Logging out");
      return;
    }
  }

  public void AdminUI() {
    Scanner input = new Scanner(System.in);
    int command;
    System.out.println("1)List all payslips   2)List all employees 3)Logout");
    command = input.nextInt();
    input.close();
    if (command == 1) {
      System.out.println("payslips");
      AdminUI();
    } else if (command == 2) {
      System.out.println("All employees");
      AdminUI();
    } else if (command == 3) {
      System.out.println("Logging out");
      return;
    }
  }
}
