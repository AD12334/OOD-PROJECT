
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Userbase {
  private String username;
  private String password;
  private HashMap<String, String> usersbase; // our userbase with key usernames and values user
  private String type;
  private int salary;
  private int scale;
  private String Employee;
  private String Field;
  private boolean loggedin = false;
  private int promotion;

  public Userbase() throws FileNotFoundException { usersbase = new HashMap<>();
    Scanner sc = new Scanner(new File("OODPROJ/src/employee_database.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();

      line = line.trim();
      String[] lines = line.split(",");
      String username = lines[0];
      System.out.println(username);
      //String id = lines[1];
      String field = lines[2];
      String position = lines[3];
      int scale = Integer.parseInt(lines[4]);
      String password = lines[5];
      usersbase.put(username,password);
      //  myWriter.write(name + ",t" + id + "," + field + "," + role + "," + scale + "," + id +
      //"\n");//The second instance of id is their password
      System.out.println(usersbase);
    }
  }

  // public void addUser(User user) throws FileNotFoundException {
  // username = user.getUsername();
  // int employment_id = user.getEmployeeID();
  // password = user.getPassword();
  // type = user.getType();
  // salary = user.getSalary();
  // scale = user.getScale();
  // Employee = user.getEmployee();
  // Field = user.getField();
  // promotion = user.getpromotionid();

  // usersbase.put(username, new User(username, password, type, employment_id));
  // }

 /* public String typeMeaning(User user) {
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
*/
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
      if (usersbase.get(username) == "Employee") {
        // TODO: call the display options
      } else if (usersbase.get(username).getType() == "Admin") {
        // TODO: call the display options
      } else if (usersbase.get(username).getType() == "HumanResources") {
        // TODO: call the display options
      }
    }*/
  }

  public void checkPassword(String password) throws Exception {
    String realpassword= usersbase.get(username);
    if (realpassword.equals(password)) {
      System.out.println("Password is valid");
      System.out.println("Logged in successfully");
                         
      /*System.out.println("Position: " + .getEmployee());
      System.out.println("Field: " + user.getField());

      System.out.println("Promotion: " + user.promotiondue());
      System.out.println("Scale: " + user.getScale());*/
      loggedin = true;
    } else {
      throw new Exception();
    }
  }
}
