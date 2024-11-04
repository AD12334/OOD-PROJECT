public class User {
    private String username;
    private String password;
    private String type;

    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
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
