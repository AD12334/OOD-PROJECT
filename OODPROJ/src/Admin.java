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
        // Logic for adding a new employee to the system
    }
}
