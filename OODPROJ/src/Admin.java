import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Admin extends User {
  public Admin(String username, String password) {
    super(username, password);
  }

  @Override
  public void displayOptions() {
    System.out.println("Admin Options:");
    System.out.println("1. Add New Employee");
    System.out.println("2. View Employee List");
    System.out.println("3. Logout");
    // Other admin-specific options

    Scanner input = new Scanner(System.in);
    int command = input.nextInt();
    switch (command) {
      case 1:
        try {
          addEmployee();
          displayOptions();
        } catch (Exception e) {
          System.out.println("Please enter valid fields");
          displayOptions();
        }
      case 2:
        System.out.println("Employees: ");
        displayOptions();
        break;
      case 3:
        System.out.println("Logging out");
        System.exit(0);
      default:
        System.out.println("Please enter a valid Command");
        displayOptions();
        break;
    }
  }

  public void addEmployee() throws IOException {
    FileWriter myWriter = new FileWriter("OODPROJ/src/employee_database.csv", true);
    

    System.out.println("Input employee details");
    System.out.println("-----------------------------------------------------"
        + "-----------------------------------");

    // Get employee details from user input
    Scanner sc = new Scanner(System.in);
    String role;
    int scale;
    System.out.println("Enter employee name:");
    String name = sc.nextLine().toUpperCase();

    String id = generateEmployeeID();

    System.out.println("Enter employee field from options " + Fields());
    String field = sc.nextLine().toUpperCase();
    while (!Fields().contains(field)) {
      System.out.println("INVALID FIELD ENTERED");
      System.out.println("Enter employee field from options " + Fields());
      field = sc.nextLine().toUpperCase();
    }
    if (field.equals("ULAC") || field.equals("ULAC2")) {
      System.out.println("Please note that you are adding a part time " +
          "employee to the database");
      System.out.println("Enter employee role from options " +
          Positions(field));
      role = sc.nextLine().toUpperCase();
      while (!Positions(field).contains(role)) {
        System.out.println(
            role + " is not a valid occupation for the chosen field: " + field);
        System.out.println("Please enter a valid occupation from the list " +
            Positions(field));
        role = sc.nextLine().toUpperCase();
      }
      System.out.println("Enter employee scale from options " +
          Scales(field, role));
      scale = sc.nextInt();
      while (!Scales(field, role).contains(scale)) {
        System.out.println(
            scale + " is not a valid scale entry for the occupation " + role);
        System.out.println(
            "Please enter a valid scale entry from the list shown " +
                Scales(field, role));
        scale = sc.nextInt();
      }

      // name, username, id (which is also the password), field, role, scale
      myWriter.write(name + ",t" + id + "," + id + "," + field + "," + role +
          "," + scale + ",0"
          + ",HOURLY"
          + "\n");
          File file = new File("OODPROJ/src/employeepayslips/t" + id +".csv");
          file.createNewFile();
          File file2 = new File("OODPROJ/src/Hourlyemployeehours/t" + id +"Hours.csv");
          file2.createNewFile();
          FileWriter myWriter2 = new FileWriter("OODPROJ/src/Hourlyemployeehours/t" + id + "Hours.csv", true);
          //Must populate the csv with some sample data
          myWriter2.write(occupyCSV());
          myWriter2.close();
          //JANUARY2023,10,20,20,20
          //FEBUARY2023,10,40,10,11
          //MARCH2024,23,12,23,30



    } else {
      System.out.println("Enter employee role from options " +
          Positions(field));
      role = sc.nextLine().toUpperCase();
      while (!Positions(field).contains(role)) {
        System.out.println(
            role + " is not a valid occupation for the chosen field: " + field);
        System.out.println("Please enter a valid occupation from the list " +
            Positions(field));
        role = sc.nextLine().toUpperCase();
      }

      // Check if the role is president or vice president
      if (role.equals("PRESIDENT") && checkIfPresidentExists()) {
        System.out.println(
            "A president already exists. Cannot add another president.");
        sc.close();
        myWriter.close();
        return;
      } else if (role.equals("VICE PRESIDENT") &&
          checkIfVicePresidentExists()) {
        System.out.println("A vice president already exists. Cannot add " +
            "another vice president.");
        sc.close();
        myWriter.close();
        return;
      }

      System.out.println("Enter employee scale from options " +
          Scales(field, role));
      scale = sc.nextInt();
      while (!Scales(field, role).contains(scale)) {
        System.out.println(
            scale + " is not a valid scale entry for the occupation " + role);
        System.out.println(
            "Please enter a valid scale entry from the list shown " +
                Scales(field, role));
        scale = sc.nextInt();
      }

      // name, username, id (which is also the password), field, role, scale
      myWriter.write(name + ",t" + id + "," + id + "," + field + "," + role +
          "," + scale + ",0"
          + "\n"); 
          File file = new File("OODPROJ/src/employeepayslips/t" + id +".csv");
          file.createNewFile();
    }

    System.out.println(
        "Employee has been successfully registered to the database ");

    sc.close();
    myWriter.close();
  }

  // Helper method to generate a unique employeeID
  private String generateEmployeeID() {
    Date now = new Date();
    Long unixTime = now.getTime() / 1000;
    return unixTime.toString().substring(0, 8); // Get the first 8 digits
  }

  public ArrayList<String> Fields() throws FileNotFoundException {
    ArrayList<String> fields = new ArrayList<>();
    Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));

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
    sc.close();
    return fields;
  }

  public ArrayList<String> Positions(String Field)
      // This makes it easier for the admin to enter the field in the right
      // format
      throws FileNotFoundException {
    ArrayList<String> positions = new ArrayList<>();
    Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));
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
    sc.close();
    return positions;
  }

  public ArrayList<Integer> Scales(String Field, String Position)
      throws FileNotFoundException {
    ArrayList<Integer> Scales = new ArrayList<>();
    Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));
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
    sc.close();
    return Scales;
  }

  public Boolean checkIfPresidentExists() throws FileNotFoundException {
    Scanner sc = new Scanner(new File("OODPROJ/src/employee_database.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Get each field and the corresponding positions
      String position = lines[4];
      if (position.equals("PRESIDENT")) {
        sc.close();
        return true;
      }
    }
    sc.close();
    return false;
  }

  public Boolean checkIfVicePresidentExists() throws FileNotFoundException {
    Scanner sc = new Scanner(new File("OODPROJ/src/employee_database.csv"));
    sc.useDelimiter(",");
    sc.useDelimiter("\n");
    while (sc.hasNext()) {
      String line = sc.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Get each field and the corresponding positions
      String position = lines[4];
      if (position.equals("VICE PRESIDENT")) {
        sc.close();
        return true;
      }
    }
    sc.close();
    return false;
  }
  public String occupyCSV(){
    int year = 2020;
    String output = "";
      LinkedHashMap<Integer,String> months = new LinkedHashMap<>();
        months.put(1,"JANUARY");
        months.put(2,"FEBRUARY");
        months.put(3,"MARCH");
        months.put(4,"APRIL");
        months.put(5,"MAY");
        months.put(6,"JUNE");
        months.put(7,"JULY");
        months.put(8,"AUGUST");
        months.put(9,"SEPTEMBER");
        months.put(10,"OCTOBER");
        months.put(11,"NOVEMBER");
        months.put(12,"DECEMBER");
        for (int j=0;j<LocalDate.now().getYear()-year+1;j++){
    for (int i = 1;i<13;i++){
  if(LocalDate.now().isAfter(LocalDate.of(year +j ,i,25))){
    float val1 = (int)(Math.random()* 40);
    float val2 = (int)(Math.random()* 40);
    float val3 = (int)(Math.random()* 40);
    float val4 = (int)(Math.random()* 40);
    int currentyear = year + j;
    output += months.get(i) + currentyear +","+ val1 +","+val2 +"," + val3 +"," +val4 +"\n";

  }
  if(LocalDate.now().isBefore(LocalDate.of(year +j ,i,25))){
    int currentyear = year + j;
    output += months.get(i) + currentyear +","+ 0 +","+0 +"," + 0 +"," +0 +"\n";
}
  }

}
return output;
  }

}
