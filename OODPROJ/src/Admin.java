import java.time.LocalDate;
import java.util.Scanner;
import java.util.Date;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayOptions() {
        System.out.println("Admin Options:");
        System.out.println("1. Add New Employee");
        System.out.println("2. View Employee List");
        // Other admin-specific options
    }

    public void addEmployee(Employee employee) {
        System.out.println("Input employee details:");
        // Get employee details from user input
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee name:");
        String name = sc.nextLine();
        Date now = new Date();
        Long longTime = now.getTime() / 1000;
        String id = longTime.toString();
        System.out.println("Enter employee field:");
        String field = sc.nextLine();
        System.out.println("Enter employee role:");
        String role = sc.nextLine();
        System.out.println("Enter employee scale:");
        int scale = sc.nextInt();
        sc.close();

        // Create a new Employee object with the details
        Employee newEmployee = new Employee(name, id, field, role, scale);
        // Add the new Employee object to the list of employees (into
        // employee_database.csv)

    }
}
