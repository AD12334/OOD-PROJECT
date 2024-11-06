
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Userbase {
  private String username;
  private String password;
  private HashMap<String, User> usersbase; // our userbase with key usernames and values user
  private String type;
  private int salary;
  private int scale;
  private String Employee;
  private String Field;
  private boolean loggedin = false;
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

    usersbase.put(username, new User(username, password, type, employment_id));
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

      } catch (Exception e) {
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
    if (loggedin) {
      if (usersbase.get(username).getType() == "Employee") {
        FullTimeEmployeeUI();
      } else if (usersbase.get(username).getType() == "Admin") {
        AdminUI();
      } else if (usersbase.get(username).getType() == "HumanResources") {
        HrUI();
      }
    }
  }

  public void checkPassword(String password) throws Exception {
    User user = usersbase.get(username);
    if (user.getPassword().equals(password)) {
      System.out.println("Password is valid");
      System.out.println("Logged in successfully as " +
          typeMeaning(usersbase.get(username)));
      System.out.println("Position: " + user.getEmployee());
      System.out.println("Field: " + user.getField());

      System.out.println("Promotion: " + user.promotiondue());
      System.out.println("Scale: " + user.getScale());
      loggedin = true;
    } else {
      throw new Exception();
    }
  }

  public void HourlyEmployeeUI() {
    Scanner input = new Scanner(System.in);
    System.out.println("1)View payslip  2)Submit Hours  3)Logout");
    int command = input.nextInt();
    switch (command) {
      case 1:
        System.out.println("payslip");
        HourlyEmployeeUI();
        break;
      case 2:
        System.out.println("submit hours:");
        HourlyEmployeeUI();
        break;
      case 3:
        System.out.println("Logging out");
        System.exit(0);
        break;
      default:
        System.out.println("Incorrect Input");
        HourlyEmployeeUI();
        break;
    }
  }

  public void FullTimeEmployeeUI() {
    Scanner input = new Scanner(System.in);
    System.out.println("1)View payslip  4)Logout");
    int command = input.nextInt();
    switch (command) {
      case 1:
        System.out.println("payslip");
        FullTimeEmployeeUI();
        break;
      case 2:
        System.out.println("Logging out");
        System.exit(0);
        break;
      default:
        System.out.println("Incorrect Input");
        FullTimeEmployeeUI();
        break;
    }
  }

  public void HrUI() {
    Scanner input = new Scanner(System.in);
    System.out.println("1)View employee details 2)Promote Employee  3)Logout");
    int command = input.nextInt();
    switch (command) {
      case 1:
        System.out.println("List of Employees");
        HrUI();
        break;
      case 2:
        System.out.println("Select employee number");
        HrUI();
        break;
      case 3:
        System.out.println("Loggin out");
        System.exit(0);
        break;
      default:
        System.out.println("Incorrect Input");
        HrUI();
        break;
    }
  }

  public void AdminUI() {
    Scanner input = new Scanner(System.in);
    System.out.println(
        "1)Add New Employee 2)Delete Existing Employee 3)Logout");
    int command = input.nextInt();
    switch (command) {
      case 1:
        System.out.println("List details");
        AdminUI();
        break;
      case 2:
        System.out.println("Select Employee");
        AdminUI();
        break;
      case 3:
        System.out.println("Logging out");
        System.exit(0);
        break;
      default:
        System.out.println("Incorrect Input");
        AdminUI();
        break;
    }
  }
}
