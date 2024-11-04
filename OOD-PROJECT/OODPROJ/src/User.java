import java.io.FileNotFoundException;

public class User extends Employee {
    private String username;
    private String password;
    private String type;
    private String position;
    private String role;
    private int salary;
    private int scale;
    private int promotionid;

    public User(String username, String password, String type,int employment_id) throws FileNotFoundException {
        super(employment_id);
        this.username = username;
        this.password = password;
        this.type = type;
        this.position = getEmployee();

        this.salary = getSalary();
        this.scale = getScale();
        this.role = getEmployee();
        this.promotionid = getpromotionid();

        // 'Employee' = Employee
        // 'Admin' = Admin
        // 'Human resources' = Human resources
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;

    }

    public String getType() {
        return type;
    }
}
