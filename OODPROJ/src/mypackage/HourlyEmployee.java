package mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class provides methods to handle hourly payed employee conditions
 */

public class HourlyEmployee extends Employee {
    LinkedHashMap<String, float[]> hours = new LinkedHashMap<>();

    public HourlyEmployee(String name, String username, String employeeID,
            int salary, String field, String role, int scale) throws FileNotFoundException {
        super(name, username, employeeID, field, role, scale);
        System.out.println(username);
        System.out.println(employeeID);
        Scanner sc = new Scanner(new File("OODPROJ/src/mypackage/Hourlyemployeehours/" + employeeID + "Hours.csv"));
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();
            line = line.trim();
            String[] lines = line.split(",");
            String key = lines[0];
            float hours1 = Float.parseFloat(lines[1]);
            float hours2 = Float.parseFloat(lines[2]);
            float hours3 = Float.parseFloat(lines[3]);
            float hours4 = Float.parseFloat(lines[4]);
            float[] worked = new float[] { hours1, hours2, hours3, hours4 };
            hours.put(key, worked);
            // System.out.println(hours);
            // for (int i = 0 ; i < 4;i++){
            // float[] arr = hours.get(key);
            // System.out.println(arr[i]);
            // }

        }
        sc.close();
        // Get each field and the corresponding positions

        /*
         * hours.put("JANUARY",new float[]{32,40,12,23});
         * hours.put("FEBRUARY",new float[]{32,40,12,23});
         * hours.put("MARCH",new float[]{32,40,12,23});
         * hours.put("APRIL",new float[]{32,40,12,23});
         * hours.put("MAY",new float[]{32,40,12,23});
         * hours.put("JUNE",new float[]{32,40,12,23});
         * hours.put("JULY",new float[]{32,40,12,23});
         * hours.put("AUGUST",new float[]{32,40,12,23});
         * hours.put("SEPTEMBER",new float[]{32,40,12,23});
         * hours.put("OCTOBER",new float[]{32,40,12,23});
         * hours.put("NOVEMBER",new float[]{32,40,12,23});
         * hours.put("DECEMBER",null);
         * //This only works for the current year 2024
         */// , if an employee want to view all payslips from 2023 or behind then we need
           // to account for december, if an employee wants to view all payslips from 2025
           // they must all be null
           // Make another CSV file for hourly employees that stores all the hours that
           // they worked???

        // Make this so that the hourly employee constructor reads the hourlyemployee
        // csv and then stores the number of hours they worked in a hashmap
    }

    /**
     * This method recognises the employees attempt to submit hours.
     * If its possible to submit hours for the given time then we update the value
     * of the employees's hours in their csv file
     * 
     * @param month,      This is the month for which our employee is submitting
     *                    their hours for
     * @param year,       The combination of our month and year gives us the key for
     *                    our hashmap
     * @param employeeid, This is the employee id of the person submitting their
     *                    hours
     * 
     */

    public void submithours(String month, int year, String employeeid) throws IOException {
        int monthno = Month.valueOf(month.toUpperCase()).getValue(); // Convert month name to an integer
        int targetRow = -1;
        String key = month + year;

        // Check if current date is before the 23rd of the month and if no data for the
        // month exists
        if (LocalDate.now().isBefore(LocalDate.of(year, monthno, 10))
                && Arrays.equals(hours.get(key), new float[] { 0.0f, 0.0f, 0.0f, 0.0f })) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the number of hours worked during week 1 of the working period");
            float hours1 = Float.parseFloat(sc.nextLine());
            System.out.println("Enter the number of hours worked during week 2 of the working period");
            float hours2 = Float.parseFloat(sc.nextLine());
            System.out.println("Enter the number of hours worked during week 3 of the working period");
            float hours3 = Float.parseFloat(sc.nextLine());
            System.out.println("Enter the number of hours worked during week 4 of the working period");
            float hours4 = Float.parseFloat(sc.nextLine());

            float[] worked = new float[] { hours1, hours2, hours3, hours4 };
            hours.put(month + "" + year, worked);

            String filePath = "OODPROJ/src/mypackage/Hourlyemployeehours/" + employeeid + "Hours.csv";
            String[] workedValues = new String[] { String.valueOf(hours1), String.valueOf(hours2),
                    String.valueOf(hours3), String.valueOf(hours4) };
            String targetKey = month + year;

            // Step 1: Read all rows from the CSV
            ArrayList<String[]> csvData = new ArrayList<>();
            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] row = line.split(",");
                    csvData.add(row);
                }
            }

            // Find the target row where the month-year matches
            for (int i = 0; i < csvData.size(); i++) {
                if (csvData.get(i)[0].equals(targetKey)) {
                    targetRow = i; // Assign targetRow if match found
                    break;
                }
            }

            // Update the appropriate columns in the row assumes data starts from column 1
            for (int j = 0; j < workedValues.length; j++) {
                if (targetRow < csvData.size() && 1 + j < csvData.get(targetRow).length) {
                    csvData.get(targetRow)[1 + j] = workedValues[j]; // Update values starting from column 1
                } else {
                    System.out.println("Invalid row/column index.");
                    return;
                }
            }

            // Write the updated data back to the CSV
            try (FileWriter writer = new FileWriter(filePath)) {
                for (String[] row : csvData) {
                    writer.write(String.join(",", row));
                    writer.write("\n"); // Add a newline after each row
                }
            }

            System.out.println("Hours submitted successfully.");

        } else if (LocalDate.now().isAfter(LocalDate.of(year, monthno, 10))
                && Arrays.equals(hours.get(key), new float[] { 0.0f, 0.0f, 0.0f, 0.0f })) {
            System.out.println("Unfortunately you have missed the deadline to submit your hours for the given month");

        } else if (LocalDate.now().isBefore(LocalDate.of(year, monthno, 10))
                && !Arrays.equals(hours.get(key), new float[] { 0.0f, 0.0f, 0.0f, 0.0f })) {
            System.out.println(
                    "You have already submitted your hours report for this month would you like to edit these hours Y/N?");
            Scanner sc = new Scanner(System.in);
            String response = sc.nextLine().toUpperCase();
            while (!response.equals("Y") && !response.equals("N")) {
                System.out.println("Please enter Y or N");
                response = sc.nextLine();
            }
            if (response.equals("Y")) {
                System.out.println("Enter the number of hours worked during week 1 of the working period");
                float hours1 = sc.nextFloat();
                System.out.println("Enter the number of hours worked during week 2 of the working period");
                float hours2 = sc.nextFloat();
                System.out.println("Enter the number of hours worked during week 3 of the working period");
                float hours3 = sc.nextFloat();
                System.out.println("Enter the number of hours worked during week 4 of the working period");
                float hours4 = sc.nextFloat();
                float[] worked = new float[] { hours1, hours2, hours3, hours4 };
                hours.put(month, worked);

            } else {
                System.exit(0);
            }
        } else if (LocalDate.now().isAfter(LocalDate.of(year, monthno, 10))
                && !Arrays.equals(hours.get(key), new float[] { 0.0f, 0.0f, 0.0f, 0.0f })) {
            System.out.println("Hours cannot be edited for the given month");
        }

    }

    /**
     * Used to get hours of a certain week
     * 
     * @param month, month of the hour
     * @param year,  year of the month
     * @return this returns the number of hours worked during the first week of the
     *         specified month and year
     */
    public float gethour1(String month, String year) {
        float[] arr = hours.get(month + year);
        return arr[0];
    }

    /**
     * Used to get hours of a certain week
     * 
     * @param month, month of the hour
     * @param year,  year of the month
     * @return this returns the number of hours worked during the second week of the
     *         specified month and year
     */
    public float gethour2(String month, String year) {
        float[] arr = hours.get(month + year);
        return arr[1];
    }

    /**
     * Used to get hours of a certain week
     * 
     * @param month, month of the hour
     * @param year,  year of the month
     * @return this returns the number of hours worked during the third week of the
     *         specified month and year
     */
    public float gethour3(String month, String year) {
        float[] arr = hours.get(month + year);
        return arr[2];
    }

    /**
     * Used to get hours of a certain week
     * 
     * @param month, month of the hour
     * @param year,  year of the month
     * @return this returns the number of hours worked during the final week of the
     *         specified month and year
     */
    public float gethour4(String month, String year) {
        float[] arr = hours.get(month + year);
        return arr[3];
    }

    /**
     * This method is used to display UI options for hourly paid employees
     */
    @Override
    public void displayOptions() throws Exception {
        System.out.println("Employee Options:");
        System.out.println("1. View Personal Details");
        System.out.println("2. View Payslip");
        System.out.println("3. Log Hours");
        System.out.println("4. Logout");

        // Add any other options specific to employees
        Scanner input = new Scanner(System.in);
        int command = input.nextInt();
        switch (command) {
            case 1:
                // TODO: personal details
                displayOptions();
            case 2:
                viewPayslip();
                displayOptions();
                break;
            case 3:
                // TODO: log hours
                break;
            case 4:
                System.out.println("Logging out");
                System.exit(0);
                break;
            default:
                System.out.println("Please enter a valid Command");
                displayOptions();
                break;
        }
    }

    /**
     * This method calculates the amount of PRSI paid per week
     * 
     * @param hours The number of hours worked during a given week
     * @return The amount of PRSI to be paid that week is returned
     */

    public double calculatePRSI(float hours) {
        double prsi = 0;
        double weeklyPay = getHourlyPay() * hours;
        double credit = 0;
        // https://www.gov.ie/en/publication/14ecbe-the-different-classes-of-pay-related-social-insurance-prsi/#class-a
        // We are assumming that all our employees are subject to the rates of prsi
        // class A
        /*
         * According to class A prsi is charged at a rate of 4.1% as of October 1st
         * 2024 for a weekly income of 352.01 or greater We therefore can calculate
         * prsi by dividing our annual week by 52 and then applying the rate of 4.1%
         * to any additional income made over 352 weekly tapered PRSI credit of €12
         * is available for employees insured at Class A whose earnings are between
         * €352.01 and €424 in a week. The maximum PRSI credit of €12 per week
         * applies to gross weekly earnings of €352.01.
         *
         * From 1 October 2024, a person earning €352.01 pays €14.43 PRSI (4.1%).
         * After the €12 credit is deducted they will pay PRSI of €2.43. For people
         * earning between €352.01 and €424, the credit of €12 is reduced by
         * one-sixth of earnings over €352.01. There is no PRSI credit once earnings
         * exceed €424.
         */

        weeklyPay = Math.round(weeklyPay * 100.0) / 100.0;
        if (weeklyPay < 352.0) {
            prsi = 0;
        } else if (weeklyPay == 352.01) {
            credit = 12;
            prsi = ((weeklyPay) * 0.041) - credit;

        } else if (weeklyPay > 352.01 && weeklyPay <= 424.0) {
            credit = 12 - (weeklyPay - 352) / 6;

            prsi = ((weeklyPay) * 0.041) - credit;

        } else {
            credit = 0;
            prsi = ((weeklyPay) * 0.041) - credit;
        }
        prsi = Math.round(prsi * 100.0) / 100.0;

        return prsi;
    }

    /**
     * Calculates PAYE for a week
     * 
     * @param hours Number of hours worked in a week
     * @return returns the amount of PAYE to be paid in a week
     */

    public double calculatePAYE(float hours) {
        double HigherRate;

        double grosspay = getHourlyPay() * hours;
        double part1; // Tax on first 42000
        double part2; // Tax on over 42000
        /*
         * https://www.revenue.ie/en/personal-tax-credits-reliefs-and-exemptions/tax-
         * relief-charts/index.aspx
         * For the sake of ease I will be assuming that all employees are Single or
         * widowed or surviving civil partner without qualifying children
         * This means that 42000 of their income is subject to 20% and anything over
         * this is subject to 40%
         * All PAYE taxpayers are entitled to a tax credit known as the Employee Tax
         * Credit (formerly known as the PAYE tax credit). This is worth €1,875 in
         * 2024 (€1,775 in 2023). If you are married and taxed under joint
         * assessment, then you and your spouse may both claim an Employee Tax
         * Credit.
         * https://www.citizensinformation.ie/en/money-and-tax/tax/income-tax-credits-
         * and-reliefs/employment-tax-credits-and-reliefs/#:~:text=All%20PAYE%
         * 20taxpayers%20are%20entitled,claim%20an%20Employee%20Tax%20Credit.
         * The following tax credits will increase by €100 to €1,875:
         * Personal Tax Credit
         *
         */
        if (grosspay <= 42000 / 52) {
            HigherRate = 0;
            part1 = grosspay * 0.2;
            part2 = HigherRate * 0.4;
        } else {
            HigherRate = grosspay - 42000 / 52;
            part1 = (42000 / 52) * 0.2;
            part2 = HigherRate * 0.4;
        }
        double part3 = part1 + part2;
        double Paye = part3 -
                (1875 * 2) /
                        52; // sum of tax - employee tax credit and personal tax credit
        return Math.max(Paye, 0);
    }

    /**
     * This method calculates the amount of USC to be paid that week
     * 
     * @param hours Hours worked in a week
     * @return The USC to be paid that week
     */
    public double calculateUSC(float hours) {
        double USC = 0;
        double grosspay = (getHourlyPay()) * hours;
        double[] rates = { 0.005, 0.02, 0.04, 0.08 };
        double[] thresholds = { 12012 / 52, 25760 / 52, 70044 / 52 };
        /*
         * https://www.citizensinformation.ie/en/money-and-tax/tax/income-tax/universal-
         * social-charge/#:~:text=The%20Universal%20Social%20Charge%20is,do%20not%20pay%
         * 20any%20USC.
         * If your total income is €13,000 or less per year, you do not pay any USC.
         * If it is more than €13,000 per year, you pay USC on your full income.
         */
        // For ease, we will be assuming that our employees are not eligible to
        // receive reduced rates of USC

        if (grosspay <= 13000 / 52) {
            return USC;
        } else {
            USC += thresholds[0] * rates[0];
        }
        if (grosspay <= thresholds[1]) {
            USC += (grosspay - thresholds[0]) * rates[1];
            return USC;
        } else {
            USC += (thresholds[1] - thresholds[0]) * rates[1];
        }
        if (grosspay <= thresholds[2]) {
            USC += (grosspay - thresholds[1]) * rates[2];
            return USC;
        } else {
            USC += (thresholds[2] - thresholds[1]) * rates[2];
            USC += (grosspay - thresholds[2]) * rates[3];
        }
        return USC;
    }

    /**
     * Calculates hourly pay;
     * <p>
     * Hourly paid employees have a salary based on a 40 hour work week, we can
     * therefore estimate their hourly pay by dividing their hourly pay by 52(weeks
     * in a year) and then by 40(work week hours)
     * 
     * @return Returns hourly pay as a double
     */
    public double getHourlyPay() {
        double hourlypay = (double) ((getSalary() / 52.0) / 40.0);
        hourlypay = Math.round(hourlypay * 100.0) / 100.0;
        return hourlypay;
    }

    /**
     * To String method
     * 
     * @return Returns employee details
     */

    @Override
    public String toString() {
        return "Field : " + getField() + "\nEmployee : " + /* getEmployee() */
                "\nSalary : " + getSalary() + "\nScale : " + getScale();
    }
}
