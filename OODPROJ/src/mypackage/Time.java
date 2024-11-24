package mypackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
/**
 * This class enables us to simulate moving forward in time
 */
public class Time {
  protected static LocalDate currentTime;
  private boolean hasScaled;
  private String lastentry;
  private ArrayList<String> userids;
/**
 * This constructor creates a Time object based on the current date and time
 */
  public Time() {
    currentTime = LocalDate.now();
    hasScaled = false;
    userids = new ArrayList<String>();
  }
/**
 * This method deals with scaling up employees and printing payslips based on the day and time defined by this class 
 * Every October if possible we increase the scale of an employee by 1 
 * Every 25th we make a new payslip available to an emplofyee
 * @param ct Current time
 * @throws IOException
 */
  public void timeCheck(LocalDate ct) throws IOException {
    if (ct.getMonthValue() >= 10) {//If its October
      if (!hasScaled) {//If we have not already scaled up the employee
        for (User user : Userbase.getUsersbase().values()) { //For each user in our userbase
          if (user instanceof Employee hourly) { // If they are an employee
            // System.out.println(((Employee) user).getEmployeeID());
            for (Map.Entry<String, Integer> entry :
                 HumanResources.getHashMap().entrySet()) {//Find their max scale in our hashmap
              // matching the employee's field to postion of the hashmap
              // then checking if their scale is lower than max
              if (entry.getKey().equals(hourly.getRole()) &&
                  entry.getValue() > hourly.getScale()) {//If their current scale is lower than their max possible scale then we can scale them uo
                int scale = hourly.getScale() + 1;
                hourly.setScale(scale);
               
                String id = hourly.getEmployeeID();
                UpdateScale(scale, id);//Update their scale in the employee_database.csv
              }
            }
          }
          hasScaled = true;
        }
      } else {
        hasScaled = false;
      }
      if (ct.getDayOfMonth() == 25) { //If the day of the month is the 25th we must issue new payslips when moving forward in time
        // iterates through hashmap & checks if they are hourly/full time
        for (User user : new ArrayList<>(Userbase.getUsersbase().values())) {
         // System.out.println("user: " + user);
         /* if (user instanceof Employee emp) { //If our use is an employee
            if (emp.getField().equals("ULAC") ||
                emp.getField().equals("ULAC2")) {
              HourlyEmployee hourly = new HourlyEmployee(
                  emp.getName(), emp.getUsername(), "t" + emp.getEmployeeID(),
                  emp.getSalary(), emp.getField(), emp.getRole(),
                  emp.getScale());
              // System.out.println("New month is " + currentTime.getMonth());
              String Month = ct.getMonth().toString();
              String year = ct.getYear() + "";
              //System.out.println((Month + year));
            }*/
            if (user instanceof Employee fullTime) { //If our user is a full time employee
            String month = ct.getMonth().toString();
            String day = ct.getDayOfWeek().toString();
            try {
              BasicPayslip payslip = new BasicPayslip(fullTime.getEmployeeID(),
                                                      fullTime.getPassword());
              payslip.FullTimePayslip(fullTime.getEmployeeID(), month, day,// Generate a payslip for them based on the new day and time etc.
                                      ct.getYear());
            } catch (Exception e) {
              System.err.println("could not create payslip");
            }
          }
        }
      }
    }
  }
/**
 * This method simulates the forward movement of time
 * @param length The integer number of days we are moving forward by 
 * @throws IOException
 */
  // call this in main to simulate days passing
  public void moveDays(int length) throws IOException {
    ArrayList<LocalDate> months = new ArrayList<LocalDate>(); //An arraylist to hold the amount of months passed
    if (length > 0) { //If we are movng forward any number of days
      LocalDate previousTime = currentTime; //The day which we are moving from is the present moment
      currentTime = currentTime.plusDays(length); //The day we are moving to is the present plus x number of days
      timeCheck(currentTime);//Call timecheck to scale up employees or print new payslips if needed
      if (previousTime.getDayOfMonth() >= 25) { ///If we are past the 25th then we add months *******
        previousTime = previousTime.plusMonths(1);
      }
      while (currentTime.getMonthValue() > previousTime.getMonthValue() ||
             currentTime.getYear() > previousTime.getYear()) {//
        months.add(previousTime);
        previousTime = previousTime.plusMonths(1);
      }
    }
    //When moving forward in time we need to add new hours to our hourly employees hours file
    for (int i = 0 ; i < Userbase.getUsersbase().keySet().toArray().length; i++) { 
      //For the userids in our userbase
      if(Userbase.getUsersbase().keySet().toArray()[i].toString().startsWith("t")){
        //If they begin with t (i.e they are an employee)
        Scanner sc = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
        //Open a scanner to iterate through our employee database
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
          String line = sc.next();
          line = line.trim();
          String[] lines = line.split(",");
          String Employeeid = lines[1];

          if (Employeeid.equals(Userbase.getUsersbase().keySet().toArray()[i].toString())) {
            //Find the entry in our database that corresponds to the given employee id
            if(lines[3].equals("ULAC")||lines[3].equals("ULAC2")){
              //If that employee is hourly paid
              TimeWriter(months, lines[1]);
              //Call the timewriter function to add some hours to their hours.csv file
            }
          }
      }
        //System.out.println(userids);
      /*if (user instanceof Employee emp) {
        //System.out.println(emp.getField() + emp.getName());

        if (emp.getField().equals("ULAC") || emp.getField().equals("ULAC2")) {
          HourlyEmployee hourly = new HourlyEmployee(
              emp.getName(), emp.getUsername(), "t" + emp.getEmployeeID(),
              emp.getSalary(), emp.getField(), emp.getRole(), emp.getScale());
          TimeWriter(months, hourly.getEmployeeID());
        }
      }*/
    }
  }
  }
  /**
   * This method updates the scale of the employee as time passes
   * @param scale The scale that we are moving our employee up to
   * @param employeeid The id of the employee we are scaling up
   * @throws IOException
   */

  public void UpdateScale(int scale, String employeeid) throws IOException {
    //Open a new scanner to search through our database
    Scanner sc2 =  new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
    //initialise the target row to 0 to iterate through our database rows propertly
    int targetRow = -1;
    employeeid = "t" + employeeid;
    sc2.useDelimiter("\n");
    while (sc2.hasNext()) {
      String line = sc2.next();
      line = line.trim();
      String[] lines = line.split(",");
      String Employeeid = lines[1];
      targetRow++;
      //If the employee id that we wish to scale is in our database exit the while loop
      if (Employeeid.equals(employeeid)) {
        // System.out.println("hit");
        break;
      }
    }
    String filePath = "OODPROJ/src/mypackage/employee_database.csv";
    String newValue = scale + "";
    int targetCol = 5; // Change the scale to the new scale

    // Read all rows from the CSV
    ArrayList<String[]> csvData = new ArrayList<>();//Create an arraylist to hold all the entries from a specific row
    try (Scanner scanner = new Scanner(new File(filePath))) {//Make a scanner to iterate through the csv
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] row = line.split(",");//Split each row by commas
        csvData.add(row);//Add the row data to our csv
      }
    }

    //  Edit the specific cell
    /*System.out.println("Target row is " + targetRow);
    System.out.println("Target column is " + targetCol);*/
    if (targetRow < csvData.size() &&
        targetCol < csvData.get(targetRow).length) { // If our target row is within the bounds of our arraylist
          //If the target column (column 5) is within the bounds of the string array for the row
      csvData.get(targetRow)[targetCol] = newValue;//Set the value at that position to the new scale
    } else {
      System.out.println("Invalid row/column index.");
      return;
    }

    //  Write the updated data back to the CSV
    try (FileWriter writer = new FileWriter(filePath)) {
      for (String[] row : csvData) { //Write our arraylist of Strings back to our csv with all our updated valuees
        writer.write(String.join(",", row));
        writer.write("\n"); // Add a newline after each row
      }
      writer.close();
    }
  }
/**
 * Getter for the current time as set by Time class
 * @return time from Time class
 */
  public LocalDate getCurrentDate() { return currentTime; }
/**
 * @param ArrayList<LocalDate> This is an array of the amount of months that have passed since the present time
 * @param String this is the employee id of the csv we are updating 
 */
  public void TimeWriter(ArrayList<LocalDate> arr, String id)
      throws IOException {
    FileWriter myWriter2 = new FileWriter(
        "OODPROJ/src/mypackage/Hourlyemployeehours/" + id + "Hours.csv", true);
    for (int i = 0; i < arr.size(); i++) {
      myWriter2.write(arr.get(i).getMonth().toString() + "" +
                      arr.get(i).getYear() + ",0,0,0,0 "
                      + "\n"); //Give them all zeros
    }
    myWriter2.close();
  }
}
