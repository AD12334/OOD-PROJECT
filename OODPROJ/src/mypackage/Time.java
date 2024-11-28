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
    private static Time instance;
    private static LocalDate currentTime;
    private boolean hasScaled;
    private String lastentry;
    private ArrayList<String> userids;

    // Private constructor to prevent instantiation
    /**
     * Creates an instance of the Time object with currenttime set to the present
     * <p> Creates a userid arraylist
     */
    private Time() {
        currentTime = LocalDate.now();
        hasScaled = false;
        userids = new ArrayList<String>();
    }

    // Public method to provide access to the instance
    /**
     * Ensures an instance of time is created else it creates an instance of time
     * @return
     */
    public static Time getInstance() {
        if (instance == null) {
            instance = new Time();
        }
        return instance;
    }

    /**
     * This method deals with scaling up employees and printing payslips based on
     * the day and time defined by this class
     * Every October if possible we increase the scale of an employee by 1
     * Every 25th we make a new payslip available to an emplofyee
     * 
     * @param ct Current time
     * @throws IOException
     */
    public void timeCheck(LocalDate ct, ArrayList<LocalDate> months) throws IOException {
      for (User user : Userbase.getUsersbase().values()) { // For each user in our userbase
        if (user instanceof Employee hourly) { // If they are an employee
          // System.out.println(((Employee) user).getEmployeeID());
          
          for (Map.Entry<String, Integer> entry : HumanResources.getHashMap().entrySet()) {
            if (entry.getKey().equals(hourly.getRole())) {
              if( entry.getValue() > hourly.getScale() + OctoberCheck(months)){
                int scale = hourly.getScale() + OctoberCheck(months);
                hourly.setScale(scale);

                String id = hourly.getEmployeeID();
                UpdateScale(scale, id);// Update their scale in the employee_database.csv
              } else {
                int scale = entry.getValue();
                hourly.setScale(scale);

                String id = hourly.getEmployeeID();
                UpdateScale(scale, id);// Update their scale in the employee_database.csv
              }
                // At the moment when we move forward in time we need to chek if we've moved past October in order to scale up our employees
                //However we arent setting the hasScaled flag back to false at any moment     
            }
          }
        }
      }
      if (ct.getDayOfMonth() == 25) { // If the day of the month is the 25th we must issue new payslips when
                                      // moving forward in time
          // iterates through hashmap & checks if they are hourly/full time
        for (User user : new ArrayList<>(Userbase.getUsersbase().values())) {
          if (user instanceof Employee fullTime) { // If our user is a full time employee
            String month = ct.getMonth().toString();
            String day = ct.getDayOfWeek().toString();
            try {
              // Generate a payslip for them based on the new day and time etc.
             // BasicPayslip payslip = new BasicPayslip(fullTime.getEmployeeID(),
                     // fullTime.getPassword());
             // payslip.FullTimePayslip(fullTime.getEmployeeID(), month, day, 
             // ct.getYear());
            } catch (Exception e) {
                System.err.println("could not create payslip");
            }
          }
        }
      }
    }

    /**
     * This method simulates the forward movement of time in days
     * 
     * @param length The integer number of days we are moving forward by
     * @throws IOException
     */
    // call this in main to simulate days passing
    public void moveDays(int length) throws IOException {
        ArrayList<LocalDate> months = new ArrayList<LocalDate>(); // An arraylist to hold the amount of months passed
        if (length > 0) { // If we are movng forward any number of days
            LocalDate previousTime = currentTime; // The day which we are moving from is the present moment
            currentTime = currentTime.plusDays(length); // The day we are moving to is the present plus x number of days
           
            if (previousTime.getDayOfMonth() >= 25) { /// If we are past the 25th then we add months *******
                previousTime = previousTime.plusMonths(1);
            }
            previousTime = previousTime.plusDays(1 - previousTime.getDayOfMonth());
            while (currentTime.getMonthValue() >= previousTime.getMonthValue() ||
                    currentTime.getYear() > previousTime.getYear()) {
                System.out.println(previousTime);
                months.add(previousTime);
                previousTime = previousTime.plusMonths(1);
            }
            timeCheck(currentTime, months);// Call timecheck to scale up employees or print new payslips if needed
        }
        // When moving forward in time we need to add new hours to our hourly employees
        // hours file
        for (int i = 0; i < Userbase.getUsersbase().keySet().toArray().length; i++) {
            // For the userids in our userbase
            if (Userbase.getUsersbase().keySet().toArray()[i].toString().startsWith("t")) {
                // If they begin with t (i.e they are an employee)
                Scanner sc = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
                // Open a scanner to iterate through our employee database
                sc.useDelimiter("\n");
                while (sc.hasNext()) {
                    String line = sc.next();
                    line = line.trim();
                    String[] lines = line.split(",");
                    String Employeeid = lines[1];

                    if (Employeeid.equals(Userbase.getUsersbase().keySet().toArray()[i].toString())) {
                        // Find the entry in our database that corresponds to the given employee id
                        if (lines[3].equals("ULAC") || lines[3].equals("ULAC2")) {
                            // If that employee is hourly paid
                            TimeWriter(months, lines[1]);
                            // Call the timewriter function to add some hours to their hours.csv file
                        }
                    }
                }
                // System.out.println(userids);
                /*
                 * if (user instanceof Employee emp) {
                 * //System.out.println(emp.getField() + emp.getName());
                 * 
                 * if (emp.getField().equals("ULAC") || emp.getField().equals("ULAC2")) {
                 * HourlyEmployee hourly = new HourlyEmployee(
                 * emp.getName(), emp.getUsername(), "t" + emp.getEmployeeID(),
                 * emp.getSalary(), emp.getField(), emp.getRole(), emp.getScale());
                 * TimeWriter(months, hourly.getEmployeeID());
                 * }
                 * }
                 */
            }
        }
    }
    /**
     * Counts the number of Octobers passed when simulating time forward
     * <p> this indicates the number of scales we must increase an employee by
     * @param months
     * @return
     */
        private int OctoberCheck( ArrayList<LocalDate> months){
          int count =0;
          for (int i =0;i < months.size();i++){
            if (months.get(i).getMonthValue() == 10){
              count++;
            }
          }
          return count;
        }
    /**
     * This method updates the scale of the employee as time passes
     * 
     * @param scale      The scale that we are moving our employee up to
     * @param employeeid The id of the employee we are scaling up
     * @throws IOException
     */

    public void UpdateScale(int scale, String employeeid) throws IOException {
        // Open a new scanner to search through our database
        Scanner sc2 = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
        // initialise the target row to 0 to iterate through our database rows propertly
        int targetRow = -1;
        employeeid = "t" + employeeid;
        sc2.useDelimiter("\n");
        while (sc2.hasNext()) {
            String line = sc2.next();
            line = line.trim();
            String[] lines = line.split(",");
            String Employeeid = lines[1];
            targetRow++;
            // If the employee id that we wish to scale is in our database exit the while
            // loop
            if (Employeeid.equals(employeeid)) {
                // System.out.println("hit");
                break;
            }
        }
        String filePath = "OODPROJ/src/mypackage/employee_database.csv";
        String newValue = scale + "";
        int targetCol = 5; // Change the scale to the new scale

        // Read all rows from the CSV
        ArrayList<String[]> csvData = new ArrayList<>();// Create an arraylist to hold all the entries from a specific
                                                        // row
        try (Scanner scanner = new Scanner(new File(filePath))) {// Make a scanner to iterate through the csv
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] row = line.split(",");// Split each row by commas
                csvData.add(row);// Add the row data to our csv
            }
        }

        // Edit the specific cell
        /*
         * System.out.println("Target row is " + targetRow);
         * System.out.println("Target column is " + targetCol);
         */
        if (targetRow < csvData.size() &&
                targetCol < csvData.get(targetRow).length) { // If our target row is within the bounds of our arraylist
            // If the target column (column 5) is within the bounds of the string array for
            // the row
            csvData.get(targetRow)[targetCol] = newValue;// Set the value at that position to the new scale
        } else {
            System.out.println("Invalid row/column index.");
            return;
        }

        // Write the updated data back to the CSV
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : csvData) { // Write our arraylist of Strings back to our csv with all our updated
                                           // valuees
                writer.write(String.join(",", row));
                writer.write("\n"); // Add a newline after each row
            }
            writer.close();
        }
    }

    /**
     * Getter for the current time as set by Time class
     * 
     * @return time from Time class
     */
    public static LocalDate getCurrentDate() {
        return currentTime;
    }

    /**
     * This method populates the entries of an employees hours csv with null values
     * @param ArrayList<LocalDate> This is an arraylist of the amount of months that
     *                             have passed since the present time
     * @param String               this is the employee id of the csv we are
     *                             updating
     */
    public void TimeWriter(ArrayList<LocalDate> arr, String id)
            throws IOException {
        FileWriter myWriter2 = new FileWriter(
                "OODPROJ/src/mypackage/Hourlyemployeehours/" + id + "Hours.csv", true);
                System.out.println(arr);
        for (int i = 0; i < arr.size(); i++) {
            myWriter2.write(arr.get(i).getMonth().toString() + "" +
                    arr.get(i).getYear() + ",0,0,0,0 "
                    + "\n"); // Give them all zeros
        }
        myWriter2.close();
    }
}
