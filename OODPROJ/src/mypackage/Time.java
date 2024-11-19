package mypackage;

import java.time.LocalDate;
import java.util.HashMap;
import mypackage.BasicPayslip;
import mypackage.HourlyEmployee;

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
        // TODO: scale up every employee

        hasScaled = true;
      }
    } else {
      hasScaled = false;
    }

    if (currentTime.getDayOfMonth() == 25) {
      // TODO: send payslips to every employee
      HashMap users = Userbase.getUsersbase();
      // FullTimePayslips
      // HourlyPayslips
      for (User user : Userbase.getUsersbase().values()) {
        if (user instanceof HourlyEmployee) {
          // no hours will be submitted so idk
        } else if (user instanceof Employee) {
          Employee fullTime = (Employee) user;
          String month = currentTime.getMonth().toString();
          String day = currentTime.getDayOfWeek().toString();
          try {
            BasicPayslip payslip = new BasicPayslip(fullTime.getEmployeeID(),
                fullTime.getPassword());
            payslip.FullTimePayslip(fullTime.getEmployeeID(), month, day,
                currentTime.getYear());
          } catch (Exception e) {
            // TODO: handle exception
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
      // I think we should be able to check if the date was changed
      // automatically instead of manually calling a timeCheck()
      // If the system was running, there would be no way of it changing time
      // other than this method being called explicitly
    }
  }
}
