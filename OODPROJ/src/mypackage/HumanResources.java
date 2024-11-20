package mypackage;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class implements Human resources functionality to our system
 */
public class HumanResources extends User {
  // We want a particular employment to correspond to a scale
  private static LinkedHashMap<String, Integer> hashMap;
  private ArrayList<Integer> scales;
  private ArrayList<String> positions;

  // The string can be our job titles
  // The array of ints can be all the scales associated with that specific job
  // title
  /**
   * This constructor makes a human resources employee and initialises a hashmap
   * corresponding to the max scale for every position
   *
   * @throws FileNotFoundException
   */
  public HumanResources(String username, String password)
      throws FileNotFoundException {
    super(username, password);
    hashMap = new LinkedHashMap<>();
    scales = new ArrayList<>();
    positions = new ArrayList<>();
    Scanner sc =
        new Scanner(new File("OODPROJ/src/mypackage/salary_scales.csv"));

    // SETTING THE DELIMITER
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();

      line = line.trim();
      String[] lines = line.split(",");
      String field = lines[0];
      String Employee = lines[1];
      int salary = Integer.parseInt(lines[2]);
      int scale = Integer.parseInt(lines[3]);
      int promotion = Integer.parseInt(lines[4]);
      int employeeID = Integer.parseInt(lines[5]);
      scales.add(scale);
      positions.add(Employee);
    }
    for (int i = 0; i < scales.size(); i++) {

      hashMap.put(positions.get(i), scales.get(i));
    }

    // myWriter.write(name + "," + id + "," + field + "," + role + "," + scale
    // +"\n");
    // JOE,1731007147,PRESIDENTIAL,PRESIDENT,1
    // System.out.println(hashMap);
  }

  // TODO: don't know if this is the right thing to check for max scale
  // Fuck you Adam and your naming scheme :)
  public static LinkedHashMap<String, Integer> getHashMap() { return hashMap; }

  /**
   * This method changes the promotion id of an employee to 1 iff they are at
   * the max scale of their position and they exist in our employees database
   *
   * @throws Exception
   */

  public void setPromotion() throws Exception {
    Scanner sc = new Scanner(System.in);
    ArrayList<Integer> IDS = new ArrayList<>();
    System.out.println(
        "Enter the ID of the employee which you wish to promote");
    String DesiredId = sc.nextLine();

    // We are asking the human resources person the id of the person they want
    // to promote An employee can only be promoted if they are at the max scale
    // We need to check if they are at the max scale
    Scanner sc2 =
        new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
    sc2.useDelimiter("\n");
    int rowCounter = 0;
    while (sc2.hasNext()) {
      String line = sc2.next();
      line = line.trim();
      String[] lines = line.split(",");
      String id = lines[2];
      int scale = Integer.parseInt(lines[5]);
      String position = lines[4];
      // String name = lines[0];
      // String field = lines[3];
      // If the id entry for a specific row belongs to the person we want to
      // promote
      if (id.equals(DesiredId)) {
        if (position.equals("PRESIDENT") || position.equals("VICE PRESIDENT")) {
          System.out.println(
              "Promotion not allowed for PRESIDENT or VICE PRESIDENT.");
          break;
        }
        // If our hashmap has their position
        if (hashMap.containsKey(position)) {
          // If they are at the max scale
          if (hashMap.get(position) == scale) {
            System.out.println(rowCounter);
            promptPromotion(rowCounter);
            break;
          }
        }
      }
      rowCounter++;
    }
    // sc.close();
    // sc2.close();
  }

  /**
   * UI implementation for a HR employee
   */
  @Override
  public void displayOptions() {
    Scanner input = new Scanner(System.in);
    System.out.println("\nHuman Resources Options:");
    System.out.println("1. View Employee Details");
    System.out.println("2. Promote Employee");
    System.out.println("3. Logout");
    int command = input.nextInt();
    switch (command) {
    case 1:
      System.out.println("Employees:");
      viewEmployeeList();
      displayOptions();
      break;
    case 2:
      try {
        setPromotion();
      } catch (Exception e) {
        System.out.println("Selected Employee cannot be promoted");
      }
      displayOptions();
      break;
    case 3:
      System.out.println("Logging out");
      Userbase.Login();
      // System.exit(0);
    default:
      System.out.println("Incorrect Input");
      displayOptions();
      break;
    }
    input.close();
  }

  // WE currently have logic in place to detect when an employee is promotable
  // i.e if they are at the max scale Now we just need to make logic to actually
  // promote them We could do this by saving all employee info deleting all the
  // instances of the promoted employee from our database Then we could just
  // place our new promoted employee to our database
  /**
   * This method rewrites an employees promotion id
   *
   * @param targetRow
   * @throws IOException
   */

  public void promptPromotion(int targetRow) throws IOException {
    String filePath = "OODPROJ/src/mypackage/employee_database.csv";
    String newValue = "1";
    int targetCol = 6;
    // Step 1: Read all rows from the CSV
    ArrayList<String[]> csvData = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] row = line.split(",");
        csvData.add(row);
      }
    }

    // Step 2: Edit the specific cell
    if (targetRow < csvData.size() &&
        targetCol < csvData.get(targetRow).length) {
      csvData.get(targetRow)[targetCol] = newValue;
    } else {
      System.out.println("Invalid row/column index.");
      return;
    }

    // Step 3: Write the updated data back to the CSV
    try (FileWriter writer = new FileWriter(filePath)) {
      for (String[] row : csvData) {
        writer.write(String.join(",", row));
        writer.write("\n"); // Add a newline after each row
      }
    }
  }

  /**
   * This method displays all the employees in our database
   *
   * @throws FileNotFoundException
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
      sc.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}
