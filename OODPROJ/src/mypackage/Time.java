package mypackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Time {
  private static LocalDate currentTime;
  private boolean hasScaled;

  public Time() {
    currentTime = LocalDate.now();
    hasScaled = false;
  }
//TODO Make it change stuff in employees_database
  public void timeCheck() throws IOException {
    if (currentTime.getMonthValue() == 10) {
      if (!hasScaled) {
        for (User user : Userbase.getUsersbase().values()) {
          if (user instanceof HourlyEmployee) {
            HourlyEmployee hourly = (HourlyEmployee)user;
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
          } else if (user instanceof Employee) {
            Employee fullTime = (Employee)user;
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

    if (currentTime.getDayOfMonth() == 25) {
      // iterates through hashmap & checks if they are hourly/full time
      for (User user : Userbase.getUsersbase().values()) {
        if (user instanceof HourlyEmployee) {
          // no hours will be submitted so idk
          HourlyEmployee hourly = (HourlyEmployee)user;
          hourly.addSubmittable(currentTime);
        } else if (user instanceof Employee) {
          Employee fullTime = (Employee)user;
          String month = currentTime.getMonth().toString();
          String day = currentTime.getDayOfWeek().toString();
          try {
            BasicPayslip payslip = new BasicPayslip(fullTime.getEmployeeID(),
                                                    fullTime.getPassword());
            payslip.FullTimePayslip(fullTime.getEmployeeID(), month, day,
                                    currentTime.getYear());
          } catch (Exception e) {
            System.err.println("could not create payslip");
          }
        }
      }
    }
  }

  // call this in main to simulate days passing
  public void moveDays(int length) throws IOException {
    if(length > 0){
      for (int i = 0; i < length; i++) {
       currentTime = currentTime.plusDays(1);
        timeCheck();
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
        }
    }
    public LocalDate getCurrentDate(){
      return currentTime;
    }
}
