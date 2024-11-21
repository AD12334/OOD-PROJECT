package mypackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Time {
  protected  static LocalDate currentTime;
  private boolean hasScaled;
  private String lastentry;

  public Time() {
    this.currentTime = LocalDate.now();
    this.hasScaled = false;
  }
//TODO Make it change stuff in employees_database
  public void timeCheck(LocalDate ct) throws IOException {
    if (ct.getMonthValue() == 10) {
      if (!hasScaled) {
        for (User user : Userbase.getUsersbase().values()) {
          if (user instanceof HourlyEmployee hourly) {
            for (Map.Entry<String, Integer> entry :
                 HumanResources.getHashMap().entrySet()) {
              // matching the employee's field to postion of the hashmap
              // then checking if their scale is lower than max
              if (entry.getKey().equals(hourly.getRole()) &&
                  entry.getValue() > hourly.getScale()) {
                    int scale = hourly.getScale() + 1;
                hourly.setScale(scale);
                //WRITE STUFF
                String id = hourly.getEmployeeID();
                UpdateScale(scale, id);
              }
            }
          } else if (user instanceof Employee fullTime) {
            for (Map.Entry<String, Integer> entry :
                 HumanResources.getHashMap().entrySet()) {
              // matching the employee's field to postion of the hashmap
              // then checking if their scale is lower than max
              if (entry.getKey().equals(fullTime.getField()) &&
                  entry.getValue() > fullTime.getScale()) {
                    int scale = fullTime.getScale() + 1;
                fullTime.setScale(scale);

                //WRITE STUFF
                String id = fullTime.getEmployeeID();
                UpdateScale(scale, id);

              }
            }
          }
        }
        hasScaled = true;
      }
    } else {
      hasScaled = false;
    }
    if (ct.getDayOfMonth() == 25) {
      // iterates through hashmap & checks if they are hourly/full time
      for (User user : new ArrayList<>(Userbase.getUsersbase().values())) {
        System.out.println("user: " + user);
        if (user instanceof Employee emp) {
          if(emp.getField().equals("ULAC") || emp.getField().equals("ULAC2")){
            HourlyEmployee hourly = new HourlyEmployee(emp.getName(),   emp.getUsername(), "t" + emp.getEmployeeID(), emp.getSalary(), emp.getField(), emp.getRole(), emp.getScale());
            System.out.println("New month is " + currentTime.getMonth());
            String Month = ct.getMonth().toString();
            String year = ct.getYear() + "";
            System.out.println((Month + year));
           
            

           
          }
        } else if (user instanceof Employee fullTime) {
          String month = ct.getMonth().toString();
          String day = ct.getDayOfWeek().toString();
          try {
            BasicPayslip payslip = new BasicPayslip(fullTime.getEmployeeID(),
                                                    fullTime.getPassword());
            payslip.FullTimePayslip(fullTime.getEmployeeID(), month, day,
                                    ct.getYear());
          } catch (Exception e) {
            System.err.println("could not create payslip");
          }
        }
      }
    }
  }

  // call this in main to simulate days passing
  public void moveDays(int length) throws IOException {
    System.out.println("We are moving forward by " + length + " days");
    ArrayList<LocalDate> months = new ArrayList<LocalDate>();
    if(length > 0){
      LocalDate previousTime = currentTime;
      currentTime = currentTime.plusDays(length);
      timeCheck(currentTime);
      if (previousTime.getDayOfMonth() >= 25){
        previousTime = previousTime.plusMonths(1);
      }
      while(currentTime.getMonthValue() > previousTime.getMonthValue() || currentTime.getYear() > previousTime.getYear()){
        months.add(previousTime);
        System.out.println(previousTime.getMonth().toString());
        previousTime = previousTime.plusMonths(1);
      }
      
    }
    for (User user : new ArrayList<>(Userbase.getUsersbase().values())) {
      if (user instanceof Employee emp) {
        if(emp.getField().equals("ULAC") || emp.getField().equals("ULAC2")){
          HourlyEmployee hourly = new HourlyEmployee(emp.getName(),   emp.getUsername(), "t" + emp.getEmployeeID(), emp.getSalary(), emp.getField(), emp.getRole(), emp.getScale());
          System.out.println(hourly.getEmployeeID());
          TimeWriter(months, emp.getEmployeeID());

        }
      }
    }
  }
  public void UpdateScale(int scale,String employeeid) throws IOException {
     Scanner sc2 = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
     int targetRow = 0;
     sc2.useDelimiter("\n");
     while (sc2.hasNext()){
      String line = sc2.next();
        line = line.trim();
        String[] lines = line.split(",");
        String Employeeid = lines[1] ;
        targetRow++;
        if (Employeeid.equals(employeeid)){
          break;
        }
     }
        String filePath = "OODPROJ/src/mypackage/employee_database.csv";
        String newValue = scale +"" ;
        int targetCol = 5; // Change the scale to the new scale 

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
            writer.close();
        }
       
      
    }
    public LocalDate getCurrentDate(){
      return currentTime;
    }
    public void TimeWriter(ArrayList<LocalDate> arr, String id) throws IOException{

      FileWriter myWriter2 = new FileWriter("OODPROJ/src/mypackage/Hourlyemployeehours/t" + id + "Hours.csv", true);
      for(int i =0; i < arr.size();i++){
      
       myWriter2.write( arr.get(i).getMonth().toString() + "" + arr.get(i).getYear() + ",0,0,0,0 " +  "\n");

      }
      myWriter2.close();
    }
}

