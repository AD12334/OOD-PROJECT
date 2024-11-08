import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayOptions() {
        System.out.println("Admin Options:");
        System.out.println("1. Add New Employee");
        System.out.println("2. View Employee List");
        System.out.println("3. Logout");
        // Other admin-specific options

        Scanner input = new Scanner(System.in);
        int command = input.nextInt();
        switch (command) {
            case 1:
                try {
                    addEmployee();
                    displayOptions();
                } catch (Exception e) {
                    System.out.println("Please enter valid fields");
                    displayOptions();
                }
            case 2:
                System.out.println("Employees: ");
                displayOptions();
                break;
            case 3:
                System.out.println("Logging out");
                System.exit(0);
            default:
                System.out.println("Please enter a valid Command");
                displayOptions();
                break;
        }
    }

    public void addEmployee() throws IOException {
        System.out.println("Input employee details");
        System.out.println("-----------------------------------------------------"
                + "-----------------------------------");
        // Get employee details from user input
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee name:");
        String name = sc.nextLine().toUpperCase();
        Date now = new Date();
        Long longTime = now.getTime() / 1000;
        String id = longTime.toString().substring(0, 8);
        System.out.println("Enter employee field from options " + Fields());
        String field = sc.nextLine().toUpperCase();
        System.out.println("Enter employee role from options " + Positions(field));
        String role = sc.nextLine().toUpperCase();
        System.out.println("Enter employee scale from options " +
                Scales(field, role));
        int scale = sc.nextInt();
        System.out.println(
                "Employee has been successfully registered to the database ");
        sc.close();

        // Create a new Employee object with the details
        Employee newEmployee = new Employee(name, id, field, role, scale);
        FileWriter myWriter = new FileWriter("OODPROJ/src/employee_database.csv", true);
        myWriter.write(name + ",t" + id + "," + field + "," + role + "," + scale + "," + id +
                "\n");// The second instance of id is their password
        myWriter.close();

        // Add the new Employee object to the list of employees (into
        // employee_database.csv)
    }

    public ArrayList<String> Fields() throws FileNotFoundException {
        ArrayList<String> fields = new ArrayList<>();
        Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));

        // SETTING THE DELIMITER
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();

            line = line.trim();
            String[] lines = line.split(",");

            // Get each field name, if it isnt on our list then put it on our list
            String field = lines[0];
            if (fields.contains(field) == false) {
                fields.add(field);
            }
        }

        return fields;
    }

    public ArrayList<String> Positions(String Field)
            throws FileNotFoundException {
        ArrayList<String> positions = new ArrayList<>();
        Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();
            line = line.trim();
            String[] lines = line.split(",");
            // Get each field and the corresponding positions
            String position = lines[1];
            String field = lines[0];
            if (positions.contains(position) == false && field.equals(Field)) {
                positions.add(position);
            }
        }

        return positions;
    }

    public ArrayList<String> Scales(String Field, String Position)
            throws FileNotFoundException {
        ArrayList<String> Scales = new ArrayList<>();
        Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();
            line = line.trim();
            String[] lines = line.split(",");
            // Get each field and the corresponding positions
            String position = lines[1];
            String scale = lines[3];
            String field = lines[0];
            if (Scales.contains(position) == false && field.equals(Field) &&
                    position.equals(Position)) {
                Scales.add(scale);
            }
        }

        return Scales;
    }
}
