package mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class represents all functions to be usuable by an admin on our system;
 * an admin is a system user
 */
public class Admin extends User {
  public Admin(String username, String password) { super(username, password); }

  /**
   * This method is used to implement UI features for an admin user
   */

  @Override
  public void displayOptions() {
    System.out.println("\nAdmin Options:");
    System.out.println("1. Add New Employee");
    System.out.println("2. View Employee List");
    System.out.println("3. Simulate time forward");
    System.out.println("4. Logout");
    // Other admin-specific options

    Scanner input = new Scanner(System.in);
    String command = input.nextLine();
    if (command.equalsIgnoreCase("q")) {
      displayOptions();
      return;
    }
    switch (command) {
    case "1": // Add New Employee
      try {
        addEmployee();
        displayOptions();
      } catch (Exception e) {
        System.out.println("Please enter valid fields");
        displayOptions();
      }
      break;
    case "2": // View Employee List
      System.out.println("Employees: ");
      viewEmployeeList();
      displayOptions();
      break;

    case "3":
      Time time =  Time.getInstance();
      Scanner sc3 = new Scanner(System.in);
      System.out.println(
          "Enter the number of days which you want to move forward!");
      try {
        String daymovement_s = sc3.nextLine();
        if (daymovement_s.equalsIgnoreCase("q")) {
          displayOptions();
          return;
        }
        int daymovement = Integer.parseInt(daymovement_s);
        ArrayList<LocalDate> arr;
        time.moveDays(daymovement);

      } catch (Exception e) {
        System.out.println("Please enter a valid integer number of days");
      } finally {
        System.out.println("The current day is " + time.getCurrentDate());
        displayOptions();
      }
      break;
    case "4": // Logout
      System.out.println("Logging out");
      Userbase.Login();
      // System.exit(0);
      break;
    default:
      System.out.println("Please enter a valid Command");
      displayOptions();
      break;
    }
  }

  /**
   * This method enables us to add new employees to our database
   * <p>
   * When creating an employee we need files to store payslips so we also create
   * a payslip.csv to store their payslips, Hourly employees are also created
   * with an hours.csv to store their hours worked since 2020 We cant have more
   * than one president or one vice president at a given time
   *
   * @throws IOException if the file path is invalid throw an error
   */

  public void addEmployee() throws IOException {
    FileWriter myWriter =
        new FileWriter("OODPROJ/src/mypackage/employee_database.csv", true);

    System.out.println("Input employee details");
    System.out.println("-----------------------------------------------------"
                       + "-----------------------------------");

    // Get employee details from user input
    Scanner sc = new Scanner(System.in);
    String role;
    String scale_s;
    System.out.println("Enter employee name:");
    String name = sc.nextLine().toUpperCase();
    if (name.equalsIgnoreCase("q")) {
      displayOptions();
      return;
    }

    String id = generateEmployeeID();

    System.out.println("Enter employee field from options " + Fields());
    String field = sc.nextLine().toUpperCase();
    if (field.equalsIgnoreCase("q")) {
      displayOptions();
      return;
    }

    while (!Fields().contains(field)) {
      System.out.println("INVALID FIELD ENTERED");
      System.out.println("Enter employee field from options " + Fields());
      field = sc.nextLine().toUpperCase();
      if (field.equalsIgnoreCase("q")) {
        displayOptions();
        return;
      }
    }
    if (field.equals("ULAC") || field.equals("ULAC2")) {
      System.out.println("Please note that you are adding a part time "
                         + "employee to the database");
      System.out.println("Enter employee role from options " +
                         Positions(field));
      role = sc.nextLine().toUpperCase();
      if (role.equalsIgnoreCase("q")) {
        displayOptions();
        return;
      }
      while (!Positions(field).contains(role)) {
        System.out.println(
            role + " is not a valid occupation for the chosen field: " + field);
        System.out.println("Please enter a valid occupation from the list " +
                           Positions(field));
        role = sc.nextLine().toUpperCase();
        if (role.equalsIgnoreCase("q")) {
          displayOptions();
          return;
        }
      }
      System.out.println("Enter employee scale from options " +
                         Scales(field, role));
      scale_s = sc.nextLine();
      if (scale_s.equalsIgnoreCase("q")) {
        displayOptions();
        return;
      }
      int scale = Integer.parseInt(scale_s);
      while (!Scales(field, role).contains(scale)) {
        System.out.println(
            scale + " is not a valid scale entry for the occupation " + role);
        System.out.println(
            "Please enter a valid scale entry from the list shown " +
            Scales(field, role));
        scale_s = sc.nextLine();
        if (scale_s.equalsIgnoreCase("q")) {
          displayOptions();
          return;
        }
        scale = Integer.parseInt(scale_s);
      }

      // name, username, id (which is also the password), field, role, scale
      myWriter.write(name + ",t" + id + "," + id + "," + field + "," + role +
                     "," + scale + ",0"
                     + ",HOURLY"
                     + "\n");
      File file =
          new File("OODPROJ/src/mypackage/employeepayslips/t" + id + ".csv");
      file.createNewFile();
      File file2 = new File("OODPROJ/src/mypackage/Hourlyemployeehours/t" + id +
                            "Hours.csv");
      file2.createNewFile();
      FileWriter myWriter2 = new FileWriter(
          "OODPROJ/src/mypackage/Hourlyemployeehours/t" + id + "Hours.csv",
          true);
      // Must populate the csv with some sample data
      myWriter2.write(occupyCSV());
      myWriter2.close();
      // JANUARY2023,10,20,20,20
      // FEBUARY2023,10,40,10,11
      // MARCH2024,23,12,23,30

    } else {
      System.out.println("Enter employee role from options " +
                         Positions(field));
      role = sc.nextLine().toUpperCase();
      if (role.equalsIgnoreCase("q")) {
        displayOptions();
        return;
      }
      while (!Positions(field).contains(role)) {
        System.out.println(
            role + " is not a valid occupation for the chosen field: " + field);
        System.out.println("Please enter a valid occupation from the list " +
                           Positions(field));
        role = sc.nextLine().toUpperCase();
        if (role.equalsIgnoreCase("q")) {
          displayOptions();
          return;
        }
      }

      // Check if the role is president or vice president
      if (role.equals("PRESIDENT") && checkIfPresidentExists()) {
        System.out.println(
            "A president already exists. Cannot add another president.");
        myWriter.close();
        return;
      } else if (role.equals("VICE PRESIDENT") &&
                 checkIfVicePresidentExists()) {
        System.out.println("A vice president already exists. Cannot add "
                           + "another vice president.");
        myWriter.close();
        return;
      }

      System.out.println("Enter employee scale from options " +
                         Scales(field, role));
      scale_s = sc.nextLine();
      if (scale_s.equalsIgnoreCase("q")) {
        displayOptions();
        return;
      }
      int scale = Integer.parseInt(scale_s);
      while (!Scales(field, role).contains(scale)) {
        System.out.println(
            scale + " is not a valid scale entry for the occupation " + role);
        System.out.println(
            "Please enter a valid scale entry from the list shown " +
            Scales(field, role));
        scale_s = sc.nextLine();
        if (scale_s.equalsIgnoreCase("q")) {
          displayOptions();
          return;
        }
        scale = Integer.parseInt(scale_s);
      }

      // name, username, id (which is also the password), field, role, scale
      myWriter.write(name + ",t" + id + "," + id + "," + field + "," + role +
                     "," + scale + ",0"
                     + "\n");
      File file =
          new File("OODPROJ/src/mypackage/employeepayslips/t" + id + ".csv");
      file.createNewFile();
    }

    System.out.println(
        "Employee has been successfully registered to the database ");

    myWriter.close();
  }

  /**
   * Creates a unique employee ID for each created employee
   * <p>
   * Uses the first eight digits of unixTime to create a unique employee id for
   * each new created employee
   *
   * @return Returns a unique employee id
   */
  // Helper method to generate a unique employeeID
  private String generateEmployeeID() {
    Date now = new Date();
    Long unixTime = now.getTime() / 1000;
    String id = unixTime.toString().substring(0, 8); // Get the first 8 digits
    return checkUnique(id);
  }

  private String checkUnique(String id) {
    for (User user : new ArrayList<>(Userbase.getUsersbase().values())) {
      if (user instanceof Employee) {
        Employee emp = (Employee)user;
        if (id.equals(emp.getEmployeeID())) {
          Date now = new Date();
          Long unixTime = now.getTime() / 1000;
          String newId =
              unixTime.toString().substring(0, 8); // Get the first 8 digits
          checkUnique(newId);
        }
      }
    }
    return id;
  }

  /**
   * This method makes it easier for an admin to add an employee to our database
   * <p>
   * This method reads all the distinct fields in our csv and returns all the
   * possible options for which an employee can belong to
   *
   * @return Returns a list of possible employee fields
   * @throws FileNotFoundException
   */
  public ArrayList<String> Fields() throws FileNotFoundException {
    ArrayList<String> fields = new ArrayList<>();
    Scanner sc =
        new Scanner(new File("OODPROJ/src/mypackage/salary_scales.csv"));

    // SETTING THE DELIMITER
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();

      line = line.trim();
      String[] lines = line.split(",");

      // Get each field name, if it isnt on our list then put it on our list
      String field = lines[0];
      if (fields.contains(field) == false) {
        fields.add(field);
      }
    }
    return fields;
  }

  /**
   * This method makes it easier for an admin to enter a valid position
   *
   * @param Field Used to determine the positions for a certain field
   * @return Returns a list of all possible positions for a given field
   * @throws FileNotFoundException if the file is not found we return an eroor
   */

  public ArrayList<String> Positions(String Field)
      // This makes it easier for the admin to enter the field in the right
      // format
      throws FileNotFoundException {
    ArrayList<String> positions = new ArrayList<>();
    Scanner sc =
        new Scanner(new File("OODPROJ/src/mypackage/salary_scales.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Get each field and the corresponding positions
      String position = lines[1];
      String field = lines[0];
      if (positions.contains(position) == false && field.equals(Field)) {
        positions.add(position);
      }
    }
    return positions;
  }

  /**
   * Displays all the possible scales for a position
   *
   * @param Field    String representing an employees employment field
   * @param Position String representing an employees position
   * @return Returns all the possible scales for a given position within a field
   * @throws FileNotFoundException if the file is not found throw an error
   */

  public ArrayList<Integer> Scales(String Field, String Position)
      throws FileNotFoundException {
    ArrayList<Integer> Scales = new ArrayList<>();
    Scanner sc =
        new Scanner(new File("OODPROJ/src/mypackage/salary_scales.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Get each field and the corresponding positions
      String position = lines[1];
      int scale = Integer.parseInt(lines[3]);
      String field = lines[0];
      if (Scales.contains(position) == false && field.equals(Field) &&
          position.equals(Position)) {
        Scales.add(scale);
      }
    }
    return Scales;
  }

  /**
   * Used to check if our employee database has a president
   *
   * @return Returns true or false if employee_database.csv has a president
   * @throws FileNotFoundException
   */

  public Boolean checkIfPresidentExists() throws FileNotFoundException {
    Scanner sc =
        new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Get each field and the corresponding positions
      String position = lines[4];
      if (position.equals("PRESIDENT")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Used to check if our employee database has a vice president
   *
   * @return Returns true or false if employee_database.csv has a vice president
   * @throws FileNotFoundException
   */
  public Boolean checkIfVicePresidentExists() throws FileNotFoundException {
    Scanner sc =
        new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Get each field and the corresponding positions
      String position = lines[4];
      if (position.equals("VICE PRESIDENT")) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method is used to generate random hours worked in the past(since 2020)
   * for hourly paid employees
   * <p>
   * Generates random hours between 0 and 40 for an hourly paid employee up to
   * the current month and year (dependent on current day)
   *
   * @return returns the arraylist of randomly generated hours.
   */
  public String occupyCSV() {
    int startYear = 2020;
    int currentYear = LocalDate.now().getYear();
    int currentMonth = LocalDate.now().getMonthValue();
    LocalDate now = LocalDate.now();
    String output = "";

    LinkedHashMap<Integer, String> months = new LinkedHashMap<>();
    months.put(1, "JANUARY");
    months.put(2, "FEBRUARY");
    months.put(3, "MARCH");
    months.put(4, "APRIL");
    months.put(5, "MAY");
    months.put(6, "JUNE");
    months.put(7, "JULY");
    months.put(8, "AUGUST");
    months.put(9, "SEPTEMBER");
    months.put(10, "OCTOBER");
    months.put(11, "NOVEMBER");
    months.put(12, "DECEMBER");

    for (int year = startYear; year <= currentYear; year++) {
      int endMonth = (year == currentYear) ? currentMonth : 12;

      for (int month = 1; month <= endMonth; month++) {
        LocalDate checkDate = LocalDate.of(year, month, 25);

        // Only add the month if the current date is after the 25th of that
        // month
        if (now.isAfter(checkDate)) {
          float val1 = (int)(Math.random() * 40);
          float val2 = (int)(Math.random() * 40);
          float val3 = (int)(Math.random() * 40);
          float val4 = (int)(Math.random() * 40);
          output += months.get(month) + year + "," + val1 + "," + val2 + "," +
                    val3 + "," + val4 + "\n";
        }
      }
    }

    return output;
  }

  /**
   * This method is used to view all employees in our database
   * <p>
   * This method reads the employee_database.csv file and displays all the
   * employees in our database
   *
   * @throws FileNotFoundException if the file is not found throw an error
   */
  public void viewEmployeeList() {
    try {
      Scanner sc =
          new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
      sc.useDelimiter("\n");
      while (sc.hasNext()) {
        String line = sc.next();
        line = line.trim();
        String[] lines = line.split(",");
        String name = lines[0];
        String username = lines[1];
        String employeeID = lines[2];
        String field = lines[3];
        String role = lines[4];
        int scale = Integer.parseInt(lines[5]);
        System.out.println("Name: " + name + ", Username: " + username +
                           ", Employee ID: " + employeeID + ", Field: " +
                           field + ", Role: " + role + ", Scale: " + scale);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}
