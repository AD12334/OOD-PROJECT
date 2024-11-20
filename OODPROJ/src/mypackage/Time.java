package mypackage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.Position;
import mypackage.BasicPayslip;
import mypackage.HourlyEmployee;
import mypackage.HumanResources;
import mypackage.User;

public class Time {
  private static LocalDate currentTime;
  private boolean hasScaled;

  public Time() {
    currentTime = LocalDate.now();
    hasScaled = false;
  }

  public void timeCheck() {
    if (currentTime.getMonthValue() == 10) {
      if (!hasScaled) {
        for (User user : Userbase.getUsersbase().values()) {
          if (user instanceof HourlyEmployee) {
            HourlyEmployee hourly = (HourlyEmployee)user;
            for (Map.Entry<String, Integer> entry :
                 HumanResources.getHashMap().entrySet()) {
              // matching the employee's field to postion of the hashmap
              // then checking if their scale is lower than max
              if (entry.getKey().equals(hourly.getField()) &&
                  entry.getValue() > hourly.getScale()) {
                hourly.setScale(hourly.getScale() + 1);
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
                fullTime.setScale(fullTime.getScale() + 1);
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
  public void moveDays(int length) {
    for (int i = 0; i < length; i++) {
      currentTime.plusDays(1);
      timeCheck();
    }
  }
}
