package mypackage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class simulates our system
 */
public class Main {
    private static ArrayList<Employee> Employees;

    // Reading in the file with our full time employees
    public static void main(String[] args) throws IOException, Exception {
        // Scanner sc = new Scanner(newadminadm
        // File("OODPROJ/src/mypackage/salary_scales.csv"));
        // ArrayList<Employee> Employees = new ArrayList<>();
        Userbase userbase = new Userbase();
        userbase.Login();
        // HourlyEmployee hourlyEmployee = new HourlyEmployee("WALTER WHITE",
        // "t17316811", "t17316811", 10, "ULAC",
        // "ASSISTANT INSTRUCTOR",
        // 1);
        // hourlyEmployee.submithours("JULY", 2024, "t17316811");
        // Package pkg = Main.class.getPackage();
        // System.out.println("Current package: " + pkg.getName());

        // sc.useDelimiter(",");
        // sc.useDelimiter("\n");
        // while (sc.hasNext()) {
        // String line = sc.next();
        // line = line.trim();
        // String[] lines = line.split(",");

        // // Split the line in accordance to commas and put the split string onto
        // // an array

        // // System.out.println((lines[3].getClass().getName()));
        // String field = lines[0];
        // String role = lines[1];
        // int salary = Integer.parseInt(lines[2]);
        // int scale = Integer.parseInt(lines[3]);
        // int promotion = Integer.parseInt(lines[4]);
        // int employeeID = Integer.parseInt(lines[5]);
        // // Employee p = new Employee(field,role,scale);
        // // String name, String employeeID, String field, String role, int scale
        // }
        /*
         * for (Employee p : Employees) {
         * BasicPayslip payslip = new BasicPayslip(p);
         * }
         */

        // for (HourlyEmployee p : Employees2) {
        // BasicPayslip basicPayslip = new BasicPayslip(p);
        /*
         * User user1 = new User("Joe Biden", "MAGA", "Employee", 2);
         * User user2 = new User("Jill Biden", "MABA", "Admin", 3);
         * User user3 = new User("Donald Biden", "MACA", "Human Resources", 5);
         */
        // Userbase userbase = new Userbase();
        // userbase.addUser(user1);
        // userbase.addUser(user2);
        // userbase.addUser(user3);
        // userbase.Login();
        // Admin admin1 = new Admin("k", "m");
        // admin1.displayOptions();
        // admin1.addEmployee();
        // HumanResources humanResources = new HumanResources();
        // humanResources.setPromotion();

        // Userbase userbase = new Userbase();
        // userbase.Login();

        // "ACADEMIC", "PROFESSOR", 5);
        // BasicPayslip payslip = new BasicPayslip("t17316811", "17316811");
        // BasicPayslip payslip = new BasicPayslip("t17316802","17316802");

        // System.out.println((lines[3].getClass().getName()));

        // Employee p = new Employee(field,role,scale);
        // String name, String employeeID, String field, String role, int scale
    }
    /*
     * for (Employee p : Employees) {
     * BasicPayslip payslip = new BasicPayslip(p);
     * }
     */

    // for (HourlyEmployee p : Employees2) {
    // BasicPayslip basicPayslip = new BasicPayslip(p);

    // Userbase userbase = new Userbase();
    // userbase.addUser(user1);
    // userbase.addUser(user2);
    // userbase.addUser(user3);
    // userbase.Login();

    // HumanResources humanResources = new HumanResources();
    // humanResources.setPromotion();

    // Userbase userbase = new Userbase();
    // userbase.Login();
    // Employee employee = new
    // Employee("adam","t123","124","ACADEMIC","PROFESSOR",1);
    // employee.HandlePromotion();
    // "ACADEMIC", "PROFESSOR", 5);

}
