
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Userbase {
    private String username;
    private String password;
    private HashMap<String, User> usersbase;//our userbase with key usernames and values user
    private String role;
    public Userbase () {
        usersbase = new HashMap<>();
    }
    public void addUser(User user) {
       username = user.getUsername();
       password = user.getPassword();
      role = user.getRole();

      usersbase.put(username, new User(username, password, role));
    }
    public String getRole() {
        return role;
    }
    public String roleMeaning(User user) {
      String c =  user.getRole();
        if(c.toUpperCase().equals("EMPLOYEE")){
            return "Employee";
        }else if(c.toUpperCase().equals("ADMIN")){
            return "Admin";
        }else if(c.toUpperCase().equals("HUMAN RESOURCES")){
            return "Human Resources";
        }
        return "";
    }


    public void checkLogin(String username) throws LoginException {
        if (usersbase.keySet().contains(username)) {
            System.out.println("Username is valid");
        }else{
            throw new LoginException("username is not valid");
        }
    }
    public void Login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        username = sc.nextLine();
        System.out.println("Please enter your password: ");
        password = sc.nextLine();
        try{
            checkLogin(username);
            try{
                checkPassword(password);

            } catch (InputMismatchException e) {
                System.out.println("Password is incorrect");


            }

        }catch(LoginException e){
            System.out.println(e);
            System.out.println("Username is not valid");
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Username is not valid");
        }
        if (usersbase.get(username).getRole() == "Employee"){
            System.out.println("View latest payslip");
        }else if (usersbase.get(username).getRole()  == "Admin"){
            System.out.println("View all payslips");
        }else if (usersbase.get(username).getRole()  == "HumanResources"){
            System.out.println("do hr stuff");
        }

    }
    public void checkPassword(String password) throws Exception{
        User user = usersbase.get(username);
        if (user.getPassword().equals(password)) {
            System.out.println("Password is valid");
            System.out.println("Logged in successfully as " + roleMeaning(usersbase.get(username)));
        }else{
            throw new Exception();
        }
    }
}
