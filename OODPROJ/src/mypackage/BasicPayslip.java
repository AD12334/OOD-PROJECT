package mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class is used to make payslips for employees
 */
public class BasicPayslip {
    private int monthIndex;
    private int dayofpayment;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> passwordArrayList = new ArrayList<>();
    private ArrayList<String> roles = new ArrayList<>();
    private ArrayList<Integer> scales = new ArrayList<>();
    private ArrayList<String> fields = new ArrayList<>();
    private String[] monthsArray = new String[] { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
            "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };

    /**
     * This Constructor makes a payslip for an employee
     * <p>
     * This Constructor scans our employee database for the given id and password;
     * if the employee exists we get their field, position,scale etc.;
     * Using this info we determine if they are hourly paid or not;
     * We then prompt the employee to enter the time period for which they wish to
     * view a payslip for
     * If the input is valid we generate a payslip
     * 
     * @param Employeeid Employeeid for the employee that wishes to view a payslip
     * @param Password   Password of the employee that corresponds to this
     *                   employeeid
     * @throws FileNotFoundException
     */
    public BasicPayslip(String Employeeid, String Password) throws FileNotFoundException {
        String year_s = "";
        int year = -1;
        Scanner sc2 = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv")); // One scanner for reading
                                                                                            // off of our database
        sc2.useDelimiter("\n");
        while (sc2.hasNext()) {
            String line = sc2.next();

            line = line.trim();
            String[] lines = line.split(",");
            String id = lines[1];
            String field = lines[3];
            fields.add(field);
            ids.add(id);
            String role = lines[4];
            roles.add(role);
            int scale = Integer.parseInt(lines[5]);
            scales.add(scale);

            String password = lines[2];
            passwordArrayList.add(password);

        }
        if (!ids.contains(Employeeid) || !passwordArrayList.contains(Password)) {
          // System.out.println("Login not recognised");
        } else {
            int index = ids.indexOf(Employeeid); // This corresponds to the row index of our employee id
            int scale = scales.get(index);
            String field = fields.get(index);
            String role = roles.get(index);
            System.out.println("Payslip for role: " + role + " scale: " + scale + " Field: " + field);
            if (field.equals("ULAC") || field.equals("ULAC2")) {
                System.out.println("You are an hourly paid employee");

                Scanner sc = new Scanner(System.in);
                System.out.println("Select the year which you wish to view a payslip for");// Another scanner for
                                                                                           // accepting user input
                try{
                     year_s = sc.nextLine();
                     while (Integer.parseInt(year_s) <= 2019){
                        System.out.println("Error employee payslips are only available from 2020 onwards");
                        System.out.println("Please enter a valid year");
                        year_s = sc.nextLine();

                     }
                }catch (Exception e){
                    System.out.println("Invalid input");
                    BasicPayslip payslip = new BasicPayslip(Employeeid, Password);

                }
                
                if (year_s.equals("q") || year_s.equals("Q")) {
                    System.out.println("Cancelled payslip generation");
                    return;
                }
                try{
                 year = Integer.parseInt(year_s);
                }
                catch (NumberFormatException e) {
                    // Handle invalid number format
                    System.out.println("Invalid input. Please enter a valid year.");
                    return; // Exit the method after invalid input
                }

                if (Time.getCurrentDate() != null) {
                    while (year > Time.getCurrentDate().getYear()) { // If the employee wants to view a payslip
                                                                          // that
                        // they cant
                        // have possibly earned that year or in previous years then
                        // it doesnt exist
                        System.out.println("No payslip available for specified year please enter a valid year");
                        String year_s2 = sc.nextLine();
                        if (year_s2.equals("q") || year_s2.equals("Q")) {
                            System.out.println("Cancelled payslip generation");
                            return;
                        }
                        try {
                            year = Integer.parseInt(year_s2);
                        } catch (Exception e) {
                            System.out.println("INVALID YEAR FORMAT");
                        BasicPayslip payslip = new BasicPayslip(Employeeid, Password);
                        }
                        

                    }
                } else if (Time.getCurrentDate() == null) {
                    while (year > Time.getCurrentDate().getYear()) { // If the employee wants to view a payslip
                                                               // that
                        // they cant
                        // have possibly earned that year or in previous years then
                        // it doesnt exist
                        System.out.println("No payslip available for specified year please enter a valid year");
                        String year_s2 = sc.nextLine();
                        if (year_s2.equals("q") || year_s2.equals("Q")) {
                            System.out.println("Cancelled payslip generation");
                            return;
                        }
                        year = Integer.parseInt(year_s2);

                    }

                }

                sc.nextLine(); // Int does not leave a newline character so we gotta go to the next line
                System.out
                        .println("Select which month you would like to view a payslip for..... " + monthoptions(year));
                String month = sc.nextLine().toUpperCase();
                if (month.equals("q") || month.equals("Q")) {
                    System.out.println("Cancelled payslip generation");
                    return;
                }
                while (!monthoptions(year).contains(month)) { // If the employee enters an invalid month then we should
                                                              // keep pestering them until we get a valid one
                    System.out.println("Please select a valid month for which you wish to view a payslip for");
                    month = sc.nextLine().toUpperCase();
                    if (month.equals("q") || month.equals("Q")) {
                        return;
                    }

                }
                String day = dayOfPayment(year, month);
                System.out.println(Employeeid);
                System.out.println("Fetching payslip for " + month);
                try {
                    HourlyPayslip(Employeeid, month, day, year);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            } else {

                Scanner sc = new Scanner(System.in);
                System.out.println("Select the year which you wish to view a payslip for");// Another scanner for
                                                                                           // accepting user input

                                                                                         // accepting user input
                try{
                     year_s = sc.nextLine();
                     while (Integer.parseInt(year_s) <= 2019){
                        System.out.println("Error employee payslips are only available from 2020 onwards");
                        System.out.println("Please enter a valid year");
                        year_s = sc.nextLine();

                     }
                }catch (Exception e){
                    System.out.println("Invalid input");
                    BasicPayslip payslip = new BasicPayslip(Employeeid, Password);

                }
                if (year_s.equals("q") || year_s.equals("Q")) {
                    System.out.println("Cancelled payslip generation");
                    return;
                }
                try{
                    year = Integer.parseInt(year_s);
                   }
                   catch (NumberFormatException e) {
                       // Handle invalid number format
                       System.out.println("Invalid input. Please enter a valid year.");
                       return; // Exit the method after invalid input
                   }
                int targetyear;
                if (mypackage.Time.getCurrentDate() == null) {
                    targetyear = LocalDate.now().getYear();
                } else {
                    targetyear = Time.getCurrentDate().getYear();
                }
                while (year > targetyear) { // If the employee wants to view a payslip that they cant
                                            // have possibly earned that year or in previous years then
                                            // it doesnt exist
                    System.out.println("No payslip available for specified year please enter a valid year");
                    String year_s2 = sc.nextLine();
                    if (year_s2.equals("q") || year_s2.equals("Q")) {
                        System.out.println("Cancelled payslip generation");
                        return;
                    }
                    try {
                        year = Integer.parseInt(year_s2);
                    } catch (Exception e) {
                        System.out.println("INVALID YEAR FORMAT");
                        BasicPayslip payslip = new BasicPayslip(Employeeid, Password);
                    }
                   

                }
                sc.nextLine(); // Int does not leave a newline character so we gotta go to the next line

                System.out
                        .println("Select which month you would like to view a payslip for..... " + monthoptions(year));
                String month = sc.nextLine().toUpperCase();
                if (month.equals("q") || month.equals("Q")) {
                    System.out.println("Cancelled payslip generation");
                    return;
                }
                while (!monthoptions(year).contains(month)) { // If the employee enters an invalid month then we should
                                                              // keep pestering them until we get a valid one
                    System.out.println("Please select a valid month for which you wish to view a payslip for");
                    month = sc.nextLine().toUpperCase();
                    if (month.equals("q") || month.equals("Q")) {
                        System.out.println("Cancelled payslip generation");
                        return;
                    }

                }
                String day = dayOfPayment(year, month);
                System.out.println("Fetching payslip for " + month);
                try {
                    FullTimePayslip(Employeeid, month, day, year);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            // String id,String month,String day,String year

            /*
             * System.out.println(
             * "---------------------------------------------------------------------------------------"
             * );
             * System.out.println("Date: " + LocalDate.now());
             * System.out.println("Name: John Doe");
             * System.out.println("PRSI CLASS: A");
             * System.out.println("Field: " + Employee.getField());
             * System.out.println("Employee: " + Employee.getEmployee());
             * System.out.println("Scale: " + Employee.getScale());
             * System.out.println("Gross annual pay: " + Employee.getSalary());
             * System.out.println("Gross monthly pay: " + Employee.getSalary() / 12);
             * System.out.println("PAYE: " + Employee.calculatePAYE());
             * System.out.println("PRSI: " + Employee.calculatePRSI());
             * System.out.println("USC: " + Employee.calculateUSC());
             * System.out.println(
             * "Total Deductions: " + (Employee.calculatePAYE() + Employee.calculatePRSI() +
             * Employee.calculateUSC()));
             * System.out.println("Net pay: " + Employee.calculateNetPay());
             * System.out.println("Pay method: PayPath");
             * System.out.println(
             * "----------------------------------------------------------------------------------------"
             * );
             */}
    }

    /**
     * Generates a payslip for an hourly paid employee
     * <p>
     * reads their hours worked from their hours.csv and calculates generates a
     * payslip for the employee with all relevant tax workings complete
     * prints off the payslip to console aswell as to the employees payslip file
     * 
     * @param id    The id of the employee
     * @param month The month for which we are requesting a payslip
     * @param day   The day for which we are requesting a payslip
     * @param year  The year for which we are requesting a payslip
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void HourlyPayslip(String id, String month, String day, int year) throws FileNotFoundException, IOException {
        Scanner sc2 = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv")); // One scanner for reading
                                                                                            // off of our database
        sc2.useDelimiter("\n");

        while (sc2.hasNext()) {
            String line = sc2.next();
            FileWriter myWriter = new FileWriter("OODPROJ/src/mypackage/employeepayslips/" + id + ".txt", true);

            line = line.trim();
            String[] lines = line.split(",");
            if (lines[1].equals(id)) {
                String name = lines[0];
                String field = lines[3];

                String role = lines[4];

                int scale = Integer.parseInt(lines[5]);
                // int promotionid = Integer.parseInt(lines[6]);

                HourlyEmployee Employee = new HourlyEmployee(name, name, id, year, field, role, scale);
                // System.out.println("WHAT MONTH");
                // Scanner sc3 = new Scanner(System.in);
                // String month2 = sc3.nextLine();

                // Employee.submithours(month2, year, id);

                float hoursworked1;
                float hoursworked2;
                float hoursworked3;
                float hoursworked4;
                // Submission for monthly hours for hourly paid employees

                try {
                    hoursworked1 = Employee.gethour1(month, "" + year);
                    checkHour(hoursworked1);
                } catch (HoursException e) {
                    System.out.println("Invalid hours worked during the first week of the current working period" + e);
                    hoursworked1 = 0;
                }
                double paye1 = Employee.calculatePAYE(hoursworked1);
                double prsi1 = Employee.calculatePRSI(hoursworked1);
                double usc1 = Employee.calculateUSC(hoursworked1);

                try {
                    hoursworked2 = Employee.gethour2(month, "" + year);
                    checkHour(hoursworked2);
                } catch (HoursException e) {
                    System.out.println("Invalid hours worked during the second week of the current working period" + e);
                    hoursworked2 = 0;

                }
                double paye2 = Employee.calculatePAYE(hoursworked2);
                double prsi2 = Employee.calculatePRSI(hoursworked2);
                double usc2 = Employee.calculateUSC(hoursworked2);

                try {
                    hoursworked3 = Employee.gethour3(month, "" + year);
                    checkHour(hoursworked3);
                } catch (HoursException e) {
                    System.out.println("Invalid hours worked during the third week of the current working period" + e);
                    hoursworked3 = 0;
                }
                double paye3 = Employee.calculatePAYE(hoursworked3);
                double prsi3 = Employee.calculatePRSI(hoursworked3);
                double usc3 = Employee.calculateUSC(hoursworked3);

                try {
                    hoursworked4 = Employee.gethour4(month, "" + year);
                    checkHour(hoursworked4);
                } catch (HoursException e) {
                    System.out.println("Invalid hours worked during the final week of the current working period" + e);
                    hoursworked4 = 0;
                }
                double paye4 = Employee.calculatePAYE(hoursworked4);
                double prsi4 = Employee.calculatePRSI(hoursworked4);
                double usc4 = Employee.calculateUSC(hoursworked4);
                double totalworked = hoursworked1 + hoursworked2 + hoursworked3 + hoursworked4;
                totalworked = Math.round(totalworked * 100) / 100;
                double totalpaye = paye1 + paye2 + paye3 + paye4;
                totalpaye = Math.round(totalpaye * 100) / 100;
                double totalprsi = prsi1 + prsi2 + prsi3 + prsi4;
                double totalusc = usc1 + usc2 + usc3 + usc4;
                totalusc = Math.round(totalusc * 100.0) / 100.0;
                double totaldeductions = totalpaye + totalprsi + totalusc;
                totaldeductions = Math.round(totaldeductions * 100.0) / 100.0;
                double netpay = Employee.getHourlyPay() * totalworked - totaldeductions;
                netpay = Math.round(netpay * 100.0) / 100.0;

                System.out.println(
                        "---------------------------------------------------------------------------------------");
                System.out.println(
                        "Date: " + day + "/" + (java.util.Arrays.asList(monthsArray).indexOf(month) + 1) + "/" + year);
                System.out.println("Name: " + name);
                System.out.println("Hours Worked: " + totalworked);
                System.out.println("PRSI CLASS: A");
                System.out.println("Field: " + Employee.getField());
                System.out.println("Position: " + Employee.getRole());
                System.out.println("Scale: " + Employee.getScale());
                System.out.println("Gross hourly pay: " + Employee.getHourlyPay());
                System.out.println("PAYE: " + totalpaye);
                System.out.println("PRSI: " + totalprsi);
                System.out.println("USC: " + totalusc);
                System.out.println("Gross pay: " + Employee.getHourlyPay() * totalworked);
                System.out.println("Total Deductions: " + (totaldeductions));
                System.out.println("Net pay: " + (netpay));
                System.out.println("Pay method: PayPath");
                System.out.println(
                        "----------------------------------------------------------------------------------------");

                myWriter.write("---------------------------------------------------------------------------------------"
                        + "\n" + "Date: " + day + "/" + (java.util.Arrays.asList(monthsArray).indexOf(month) + 1) + "/"
                        + year + "\n" +
                        "Name: " + name + "\n" +
                        "Hours Worked: " + totalworked + "\n" +
                        "PRSI CLASS: A" + "\n" +
                        "Field: " + Employee.getField() + "\n" +
                        "Position: " + Employee.getRole() + "\n" +
                        "Scale: " + Employee.getScale() + "\n" +
                        "Gross hourly pay: " + Employee.getHourlyPay() + "\n" +
                        "PAYE: " + totalpaye + "\n" +
                        "PRSI: " + totalprsi + "\n" +
                        "USC: " + totalusc + "\n" +
                        "Gross pay: " + Employee.getHourlyPay() * totalworked + "\n" +
                        "Total Deductions: " + (totaldeductions) + "\n" +
                        "Net pay: " + (netpay) + "\n" +
                        "Pay method: PayPath" + "\n" +
                        "----------------------------------------------------------------------------------------");
                myWriter.close();
            }
        }
    }

    /**
     * Generates a payslip for an annually paid employee
     * <p>
     * Generates a payslip for the employee with all relevant tax workings complete
     * prints off the payslip to console aswell as to the employees payslip file
     * 
     * @param id    The id of the employee
     * @param month The month for which we are requesting a payslip
     * @param day   The day for which we are requesting a payslip
     * @param year  The year for which we are requesting a payslip
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void FullTimePayslip(String id, String month, String day, int year) throws IOException {
        Scanner sc2 = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv")); // One scanner for reading
                                                                                            // off of our database
        sc2.useDelimiter("\n");

        while (sc2.hasNext()) {
            String line = sc2.next();
            FileWriter myWriter = new FileWriter("OODPROJ/src/mypackage/employeepayslips/" + id + ".txt", true);

            line = line.trim();
            String[] lines = line.split(",");
            if (lines[1].equals(id)) {
                String name = lines[0];
                String field = lines[3];

                String role = lines[4];

                int scale = Integer.parseInt(lines[5]);

                Employee employee = new Employee(name, "t" + id, id, field, role, scale);
                int GrossAnnual = employee.getSalary();
                int GrossMonthly = Math.round((GrossAnnual / 12) * 100) / 100;
                GrossAnnual = Math.round(GrossAnnual * 100) / 100;
                int Paye = (int) Math.round(employee.calculatePAYE() / 12.0);
                int Prsi = (int) Math.round(employee.calculatePRSI() / 12.0);
                int USC = (int) Math.round(employee.calculateUSC() / 12.0);
                int netpay = (int) Math.round(employee.calculateNetPay() / 12.0);
                System.out.println(
                        "---------------------------------------------------------------------------------------");
                System.out.println(
                        "Date: " + day + "/" + (java.util.Arrays.asList(monthsArray).indexOf(month) + 1) + "/" + year);
                System.out.println("Name: " + name);
                System.out.println("PRSI CLASS: A");
                System.out.println("Field: " + field);
                System.out.println("Position: " + role);
                System.out.println("Scale: " + scale);
                System.out.println("Gross annual pay: " + GrossAnnual);
                System.out.println("Gross monthly pay: " + GrossMonthly);
                System.out.println("PAYE: " + Paye);
                System.out.println("PRSI: " + Prsi);
                System.out.println("USC: " + USC);
                System.out.println(
                        "Total Deductions: " + (Paye + USC + Prsi));
                System.out.println("Net pay: " + netpay);
                System.out.println("Pay method: PayPath");
                System.out.println(
                        "----------------------------------------------------------------------------------------");

                System.out.println("Payslip is now available to viewed in CSV format also");

                myWriter.write(
                        "---------------------------------------------------------------------------------------" + "\n"
                                +
                                "Date: " + day + "/" + (java.util.Arrays.asList(monthsArray).indexOf(month) + 1) + "/"
                                + year + "\n" +
                                "Name: " + name + "\n" +
                                "PRSI CLASS: A" + "\n" +
                                "Field: " + field + "\n" +
                                "Position: " + role + "\n" +
                                "Scale: " + scale + "\n" +
                                "Gross annual pay: " + GrossAnnual + "\n" +
                                "Gross monthly pay: " + GrossMonthly / 12 + "\n" +
                                "PAYE: " + Paye + "\n" +
                                "PRSI: " + Prsi + "\n" +
                                "USC: " + USC + "\n" +

                                "Total Deductions: "
                                + (Paye + Prsi + USC)
                                + "\n" +
                                "Net pay: " + netpay + "\n" +
                                "Pay method: PayPath" + "\n" +
                                "----------------------------------------------------------------------------------------");

            }

            myWriter.close();

        }
    }

    /**
     * An employee shouldnt work more than 48 hours a week
     * 
     * @param hour
     * @return
     * @throws HoursException
     */
    public float checkHour(float hour) throws HoursException {
        if ((hour) < 0 || hour > 48) {

            throw new HoursException("Hour is not valid");
            // An employee cant work less than 0 hours and
            // An employee should not work more than 48 hours a week
            // Does an employee get paid double time if they work more than 39 hours a week?

        }
        return hour;
    }

    /**
     * This method makes it easier for the employee to enter in the month in the
     * required format
     * 
     * @param year
     * @return
     */
    public ArrayList<String> monthoptions(int year) {

        ArrayList<String> monthoptions = new ArrayList<>();
        // This method is used to make it easier for the employee to enter in the month
        // in the required format
        int targetyear;
        if (Time.getCurrentDate() == null) {
            targetyear = LocalDate.now().getYear();
        } else {
            targetyear = Time.getCurrentDate().getYear();
            // System.out.println("I WORK");
        }
        // System.out.println("TARGET YEAR IS " + targetyear);
        LinkedHashMap<Integer, String> months = new LinkedHashMap<>();//Order of months matters
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
        if (year < targetyear) {
            for (int i = 1; i < 13; i++) {
                if (LocalDate.now().isAfter(LocalDate.of(year, i, 25))) {// If the year is in the past then the employee
                                                                         // can view all payslips for all months
                    monthoptions.add(months.get(i));
                }

            }
            return monthoptions; // If we want to view all payslips for a previous year then all months are
                                 // available to view
        } else if (year == targetyear && Time.getCurrentDate() == null) {// If the year we want to view payslips
                                                                              // for is the current year
            String Month = LocalDate.now().getMonth().toString(); // Find the current month

            for (int i = 1; i < 13; i++) {
                if (months.get(i).equals(Month)) { // Iterate through our hashmap keys and check if their value is equal
                                                   // to the current month
                    monthIndex = i; // If the value of the month is the current month then we have our month index
                    // The month index just corresponds to the number of months that have passed so
                    // far in the year
                }
            }
            for (int i = 1; i < monthIndex + 1; i++) {
                LocalDate date = LocalDate.of(year, i, 25);
                String day = date.getDayOfWeek().toString();
                // All Monthly Staff, Hourly, Retired Members of Staff and Scholarships are paid
                // on the 25th of the month.
                // (Please note if payment date falls on a Saturday then pay day is Friday
                // (24th) and if falls on a Sunday then pay day is Monday(26th)).
                if (day.equals("SATURDAY")) {
                    dayofpayment = 24;
                } else if (day.equals("SUNDAY")) {
                    dayofpayment = 26;
                } else {
                    dayofpayment = 25;
                }
                if (LocalDate.now().isAfter(LocalDate.of(year, i, dayofpayment))
                        || LocalDate.now().equals(LocalDate.of(year, i, dayofpayment))) { // If
                    // the
                    // current
                    // day
                    // is
                    // past
                    // the
                    // 25th of a certain month then we
                    // can print a payslip (the day has
                    // past)
                    monthoptions.add(months.get(i));
                }

            }
            return monthoptions;
        } else if (year == targetyear && Time.getCurrentDate() != null) {
            String Month = Time.getCurrentDate().getMonth().toString().toUpperCase(); // Find the current month
            System.out.println(Month);
           
            for (int i = 1; i < 13; i++) {
                if (months.get(i).equals(Month)) { // Iterate through our hashmap keys and check if their value is equal
                                                   // to the current month
                    monthIndex = i; // If the value of the month is the current month then we have our month index
                    // The month index just corresponds to the number of months that have passed so
                    // far in the year
                    // System.out.println("Month index is " + monthIndex);
                }
            }
            for (int i = 1; i < monthIndex + 1; i++) {
                LocalDate date = LocalDate.of(year, i, 25);
                String day = date.getDayOfWeek().toString();
                // All Monthly Staff, Hourly, Retired Members of Staff and Scholarships are paid
                // on the 25th of the month.
                // (Please note if payment date falls on a Saturday then pay day is Friday
                // (24th) and if falls on a Sunday then pay day is Monday(26th)).
                if (day.equals("SATURDAY")) {
                    dayofpayment = 24;
                } else if (day.equals("SUNDAY")) {
                    dayofpayment = 26;
                } else {
                    dayofpayment = 25;
                }
                if (Time.getCurrentDate().isAfter(LocalDate.of(year, i, dayofpayment))) { // If the current day is
                                                                                               // past the
                    // 25th of a certain month then we
                    // can print a payslip (the day has
                    // past)
                    monthoptions.add(months.get(i));

                }

            }
        }
        return monthoptions;
    }

    /**
     * Returns the day on which an employee is paid for a given month in a year
     * 
     * @param year
     * @param month
     * @return
     */
    public String dayOfPayment(int year, String month) {

        int monthno = Month.valueOf(month.toUpperCase()).getValue(); // Convert month name to an integer
        String dayOfPayment = "25";

        LocalDate date = LocalDate.of(year, monthno, 25);

        switch (date.getDayOfWeek()) {
            case SATURDAY:
                dayOfPayment = "24";
                break;
            case SUNDAY:
                dayOfPayment = "26";
                break;
            default:
                dayOfPayment = "25";
                break;
        }

        return dayOfPayment;
    }

}