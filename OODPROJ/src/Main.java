import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Employee> Employees;

    public static void main(String[] args) throws IOException, Exception {
        //System.out.println("Current working directory: " + System.getProperty("user.dir"));
        Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));
        ArrayList<Employee> Employees = new ArrayList<>();
        // SETTING THE DELIMITER
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();

            line = line.trim();
            String[] lines = line.split(",");

            // System.out.println((lines[3].getClass().getName()));
            String field = lines[0];
            String role = lines[1];
            int salary = Integer.parseInt(lines[2]);
            int scale = Integer.parseInt(lines[3]);
            int promotion = Integer.parseInt(lines[4]);
            int employeeID = Integer.parseInt(lines[5]);
           // Employee p = new Employee(field,role,scale);
            //String name, String employeeID, String field, String role, int scale

        }
        /*
         * for (Employee p : Employees) {
         * BasicPayslip payslip = new BasicPayslip(p);
         * }
         */
        Scanner sc2 = new Scanner(new File("OODPROJ/src/HourlyPaidEmployees.csv"));
        ArrayList<HourlyEmployee> Employees2 = new ArrayList<>();
        // Hourly paid employees have salaries based on 40 hour work weeks
        // We can get the hourly rate by dividing by 52 and then dividing by 40
        // SETTING THE DELIMITER
        sc2.useDelimiter(",");
        sc2.useDelimiter("\n");
        while (sc2.hasNext()) {
            String line = sc2.next();
            line = line.trim();
            String[] lines = line.split(",");

            // System.out.println((lines[3].getClass().getName()));
            String field = lines[0];
            String Employee = lines[1];
            int salary = Integer.parseInt(lines[2]);
            int scale = Integer.parseInt(lines[3]);
           // HourlyEmployee p = new HourlyEmployee(field, Employee, salary, scale);
            //Employees2.add(p);

            // System.out.println(Employees2);

        }
        // for (HourlyEmployee p : Employees2) {
        // BasicPayslip basicPayslip = new BasicPayslip(p);
        /*
        User user1 = new User("Joe Biden", "MAGA", "Employee", 2);
        User user2 = new User("Jill Biden", "MABA", "Admin", 3);
        User user3 = new User("Donald Biden", "MACA", "Human Resources", 5);
*/
        // Userbase userbase = new Userbase();
        // userbase.addUser(user1);
        // userbase.addUser(user2);
        // userbase.addUser(user3);
        //userbase.Login();
        Admin admin1 = new Admin("k","m");

     //   admin1.addEmployee();
        HumanResources humanResources = new HumanResources();
       // humanResources.setpromotion();
      // Userbase userbase = new Userbase();
      // userbase.Login();
      Employee employee = new Employee("ADAM", "181881818", "ACADEMIC", "PROFESSOR", 5);
      BasicPayslip payslip = new BasicPayslip(employee);

        
        


    }
    

}
