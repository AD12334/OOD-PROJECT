import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Main {
    private static ArrayList<Employee> Employees;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("OODCSV.csv"));
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
            String Employee = lines[1];
            int salary = Integer.parseInt(lines[2]);
            int scale = Integer.parseInt(lines[3]);
            Employee p = new Employee(field, Employee, salary, scale);
            Employees.add(p);

            // System.out.println(Employees);

        }
        /*
         * for (Employee p : Employees) {
         * BasicPayslip payslip = new BasicPayslip(p);
         * }
         */
        Scanner sc2 = new Scanner(new File("HourlyPaidEmployees(Sheet1).csv"));
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
            HourlyEmployee p = new HourlyEmployee(field, Employee, salary, scale);
            Employees2.add(p);

            // System.out.println(Employees2);

        }
        // for (HourlyEmployee p : Employees2) {
        // BasicPayslip basicPayslip = new BasicPayslip(p);
        User user1 = new User("Joe Biden", "MAGA", "Employee");
        User user2 = new User("Jill Biden", "MABA", "Admin");
        User user3 = new User("Donald Biden", "MACA", "Human Resources");

        Userbase userbase = new Userbase();
        userbase.addUser(user1);
        userbase.addUser(user2);
        userbase.addUser(user3);
        userbase.Login();
    }

}
