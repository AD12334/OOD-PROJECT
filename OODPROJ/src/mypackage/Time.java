package mypackage;

import java.time.LocalDate;

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
