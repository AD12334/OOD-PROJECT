package mypackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * This class is used to determine employees; an employee is a user of our
 * system
 */
public class Employee extends User {
    private String name;
    private String employeeID;
    private int salary;
    private String field;
    private String role;
    private LinkedHashMap<String, Integer> hashMap;
    private int scale;
    private int promotion;
    private ArrayList<Integer> scales;
    private ArrayList<String> positions;
    private LocalDate date = Time.getCurrentDate();

    /**
     * Constructor to make an employee
     * with a given name,username,employeeid,etc.
     * 
     * @param name
     * @param username
     * @param employeeID
     * @param field
     * @param role
     * @param scale
     */
    public Employee(String name, String username, String employeeID, String field,
            String role, int scale) {
        // Username: t(added in admin) + employee id,
        // password: employeeid UNIX time
        super(username, employeeID);
        this.name = name;
        this.employeeID = employeeID;

        try {
            this.salary = getSalaryFromCSV(field.toUpperCase(), role.toUpperCase(), scale);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.salary = 0; // or handle it appropriately
        }
        this.field = field.toUpperCase();
        this.role = role.toUpperCase();
        this.scale = scale;
        this.promotion = 0;
    }

    /**
     * Gets employee name
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets employeeID
     * 
     * @return
     */
    public String getEmployeeID() {
        return employeeID;
    }

    /**
     * Gets employee Field
     * 
     * @return
     */
    public String getField() {
        return field;
    }

    /**
     * Sets employee field
     * 
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Gets employee role
     * 
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets employee role
     * 
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets employee scale
     * 
     * @return
     */
    public int getScale() {
        return scale;
    }

    /**
     * Sets employee scale
     * 
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Gets employee salary
     * 
     * @return
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Changes an employees promotion ID
     * 
     */
    public void promote() {
        this.promotion = 1;
    }

    /**
     * Moves up the employees scale if applicable
     * 
     */
    public void checkDate() {
        if (date.getMonthValue() == 10 && date.getDayOfMonth() == 1) {
            setScale(getScale() + 1);
        }
    }

    /**
     * Gets the salary from the csv based on a specific scale, field and position
     * 
     * @param field
     * @param role
     * @param scale
     * @return
     * @throws FileNotFoundException
     */
    private int getSalaryFromCSV(String field, String role, int scale)
            throws FileNotFoundException {
        File csvFile = new File("OODPROJ/src/mypackage/salary_scales.csv");
        Scanner scanner = new Scanner(csvFile);

        // Process each line to find matching salary
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");

            // CSV format: field,role,salary,scale
            String csvField = values[0];
            String csvRole = values[1];
            int csvSalary = Integer.parseInt(values[2]);
            int csvScale = Integer.parseInt(values[3]);

            if (csvField.equals(field) && csvRole.equals(role) && csvScale == scale) {

                return csvSalary;
            }
        }

        // If no matching salary found, throw an exception or handle as needed
        throw new IllegalArgumentException(
                "Salary not found for given field, role, and scale.");
    }

    /**
     * UI options for an employee
     */
    @Override
    public void displayOptions() throws Exception {
        System.out.println("\nEmployee Options for " + getName());
        checkDate();
        System.out.println("1. View Personal Details");
        System.out.println("2. View Payslip");
        System.out.println("3. Check for Promotion");

        // Check if employee is hourly paid
       
        
            System.out.println("4. Logout");
            // Add any other options specific to employees
            Scanner sc = new Scanner(System.in);
            int command = sc.nextInt();
            switch (command) {
                case 1: // View Personal Details
                    System.out.println(this.toString());
                    displayOptions();
                case 2:
                    viewPayslip();
                    displayOptions();
                    break;
                case 3:
                    HandlePromotion(employeeID);
                    
                    displayOptions();
                case 4:
                    System.out.println("Logging out");
                    Userbase.Login();
                default:
                    System.out.println("Please enter a valid Command");
                    displayOptions();
                    break;
            }
        

    }

    /**
     * View the payslip of an employee
     * 
     * @throws FileNotFoundException
     */
    public void viewPayslip() throws FileNotFoundException {
        BasicPayslip payslip = new BasicPayslip(getEmployeeID(), getPassword());//Invokes the basicPayslip constructor
    }

    /**
     * If an employee has a promotion pending then this method bumps up their
     * position and changes their scale back to 1 if they accept the promotion
     * If the employee rejects the promotion it changes their promotion id back to
     * 0;
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void HandlePromotion(String targetid) throws FileNotFoundException, IOException {
        int counter = 0;
        Scanner sc = new Scanner(new File("OODPROJ/src/mypackage/employee_database.csv"));
        //If the promotion flag corresponding to the entry of a user in our employees database is set to 1, then they are eligible for a promotion
        // SETTING THE DELIMITER

        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();

            line = line.trim();
           
            String[] lines = line.split(",");
            String field = lines[3];
            String id = lines[2];
            int scale = Integer.parseInt(lines[5]);

            String position = lines[4];

            int promotion = Integer.parseInt(lines[6]);
            counter++;
            if (id.equals(targetid) ){
            if (promotion != 1) {
                System.out.println("No promotions available");
                System.out.println();
            } else if (promotion == 1) {
                System.out.println(
                        "Congratulations you have been selected for promotion");

                System.out.println("Your current position is " + position +
                        " and your current salary is " +
                        getSalaryFromCSV(field, position, scale));

                System.out.println(
                        "Your new position would be " + changePosition(position) +
                                " and your new salary would be " +
                                getSalaryFromCSV(field, changePosition(position), 1));
                System.out.println("Would you like to accept this promotion Y/N ?");
                Scanner sc2 = new Scanner(System.in);
                String response = sc2.nextLine().toUpperCase();
                while (!response.equals("Y") && !response.equals("N")) {
                    System.out.println("Please enter a valid response");
                    response = sc2.nextLine().toUpperCase();
                }
                if (response.equals("N")) {
                    System.out.println("Promotion has been rejected you will remain as " +
                            position);
                            System.out.println(employeeID);
                    RejectPromotion(counter);
                    
                } else if (response.equals("Y")) {
                    System.out.println(
                            "Congratulations on accepting your new promotion !!!!");

                    try{
                        AcceptPromotion(counter, changePosition(position));
                    }catch (Exception e){
                        System.out.println();
                    }
                }
            }
            
        }
    }
    }

    /**
     * This method determies the new position of a promotable employee
     * 
     * @param position
     * @return
     * @throws FileNotFoundException
     */
    public String changePosition(String position) throws FileNotFoundException {

        scales = new ArrayList<>();
        positions = new ArrayList<>();
        hashMap = new LinkedHashMap<>();
        Scanner sc = new Scanner(new File("OODPROJ/src/mypackage/salary_scales.csv"));
        // SETTING THE DELIMITER
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();

            line = line.trim();
            String[] lines = line.split(",");
            String field = lines[0];
            String Employee = lines[1];
    
            int scale = Integer.parseInt(lines[3]);
            
            scales.add(scale);
            positions.add(Employee);
        }
        for (int i = 0; i < scales.size(); i++) {

            hashMap.put(positions.get(i), scales.get(i));
        }
        // System.out.println(hashMap);
        ArrayList<String> keys = new ArrayList<>(hashMap.keySet());
        ArrayList<String> nonpromotable = new ArrayList<>(Arrays.asList(
                "PRESIDENT", "FULL PROFESSOR", "SENIOR ADMINISTRATIVE OFFICER 3",
                "EPS PORFOLIO MANAGER", "SUB LIBRARIAN", "ANALYST PROGRAMMER 3",
                "CHIEF TECHNICAL OFFICER", "SEN PORTER/ATTENDANT", "TEACHING FELLOW",
                "THERAPIES REGIONAL SUPERVISORS REGIONAL PLACEMENT FACILITATOR",
                "ASSISTANT SENIOR INSTRUCTOR", "SENIOR RESEARCH FELLOW",
                "CO-OP STUDENT"));//they have reached the top of their field
        // We now have our list of positions and our list of non promotable
        // positions If our employee position is contained by non promotable then
        // they have reached the top of their field If an employee is not
        // nonpromotable then they can be promoted

        if (!nonpromotable.contains(position) && keys.contains(position)) {
            return (keys.get(keys.indexOf(position) - 1));//If the employee currently has a position that is promotable and they are at the max scale for that position then we return the position corresponding to the key entry above
        }

        return keys.get(keys.indexOf(
                position)); // If the employee isnt promotable or we dont want to
                            // promote them then we just keep their position the same
    }

    /**
     * If an employee rejects their promotion we change their info in the
     * employee_database.csv
     * 
     * @param targetRow
     * @throws IOException
     */
    public void RejectPromotion(int targetRow) throws IOException {
     
        targetRow-=1;
        String filePath = "OODPROJ/src/mypackage/employee_database.csv";
        String newValue = "0";
        int targetCol = 6; // Change the promotion id back to 0

        // Step 1: Read all rows from the CSV
        ArrayList<String[]> csvData = new ArrayList<>();//Create an arraylist of String arrays
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] row = line.split(",");
                csvData.add(row);//Write each split row to our arraylist
            }
        }

        // Step 2: Edit the specific cell
        if (targetRow < csvData.size() &&
                targetCol < csvData.get(targetRow).length) { //If the target row and column is valid
            csvData.get(targetRow)[targetCol] = newValue;//Set the value of the array entry within our arraylist to the new value
        } else {
            System.out.println("Invalid row/column index.");
            return;
        }

        // Step 3: Write the updated data back to the CSV
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : csvData) {
                writer.write(String.join(",", row));
                writer.write("\n"); // Add a newline after each row
                //Write our arraylist back to our csv
            }
        }
    }

    /**
     * If an employee accepts their promotion we change their info in the
     * employee_database.csv
     * 
     * @param targetRow
     * @param newposition
     * @throws IOException
     */
    public void AcceptPromotion(int targetRow, String newposition)
            throws IOException {
              targetRow -= 1;
              
        String filePath = "OODPROJ/src/mypackage/employee_database.csv";
        String newValue = "0";
        int targetCol = 6; // Change the promotion id back to 0
        int targetCol2 = 5; // Update the scale to 1
        int targetCol3 = 4; // Update their position

        //  Read all rows from the CSV
        ArrayList<String[]> csvData = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] row = line.split(",");
                csvData.add(row);
            }
        }

        //  Edit the specific cell
        if (targetRow < csvData.size() && targetCol < csvData.get(targetRow).length) {
            try{
            csvData.get(targetRow)[targetCol] = newValue;
            csvData.get(targetRow)[targetCol2] = "1";
            csvData.get(targetRow)[targetCol3] = newposition;
            }
            catch (Exception e){

            }

        } else {
            System.out.println("Invalid row/column index.");
            return;
        }

        //  Write the updated data back to the CSV
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0 ; i< csvData.size();i++) {
                writer.write(String.join(",", csvData.get(i)));
                if (i != csvData.size()-1){
                    writer.write("\n"); // Add a newline after each row
                }
                
                
            }
        }catch (Exception e){
            System.out.println("");
        }
    }
    // myWriter.write(name + "," + id + "," + field + "," + position + "," +
    // scale
    // +",1\n");

    // public Employee( String Field, String Employee, int salary, int scale,int
    // promotionint) {
    // this.salary = salary;
    // this.scale = scale;
    // this.Field = Field;
    // this.Employee = Employee;
    // this.promotion = promotionint;

    // }
    // public Employee(int employment_id) throws FileNotFoundException {
    // Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));
    // // SETTING THE DELIMITER
    // sc.useDelimiter(",");
    // sc.useDelimiter("\n");
    // while (sc.hasNext()) {
    // String line = sc.next();

    // line = line.trim();
    // String[] lines = line.split(",");
    // String field = lines[0];
    // String Employee = lines[1];
    // int salary = Integer.parseInt(lines[2]);
    // int scale = Integer.parseInt(lines[3]);
    // int promotion = Integer.parseInt(lines[4]);
    // int employeeID = Integer.parseInt(lines[5]);

    // if(employeeID == employment_id){
    // this.Field = field;
    // this.EmployeeID = employeeID;

    // this.Employee = Employee;
    // this.salary = salary;
    // this.scale = scale;
    // this.promotion = promotion;
    // break;

    // }
    // }
    // }
    // public int getpromotionid(){
    // return promotion;
    // }
    // public int getEmployeeID(){
    // return EmployeeID;
    // }

    // public int getSalary() {
    // return salary;
    // }

    // public int getScale() {
    // return scale;
    // }

    // public String getEmployee() {
    // return Employee;
    // }

    // public String getField() {
    // return Field;
    // }
    /**
     * Calculates PRSI contributions per month for an annual employee
     */

    public double calculatePRSI() {
        double prsi = 0;
        double grosspay = getSalary();
        double credit = 0;
        // //
        // https://www.gov.ie/en/publication/14ecbe-the-different-classes-of-pay-related-social-insurance-prsi/#class-a
        // // We are assumming that all our employees are subject to the rates of
        // prsi
        // // class A
        // /*
        // * According to class A prsi is charged at a rate of 4.1% as of October
        // 1st 2024
        // * for a weekly income of 352.01 or greater
        // * We therefore can calculate prsi by dividing our annual week by 52 and
        // then
        // * applying the rate of 4.1% to any additional income made over 352
        // * weekly tapered PRSI credit of €12 is available for employees insured at
        // Class
        // * A whose earnings are between €352.01 and €424 in a week. The maximum
        // PRSI
        // * credit of €12 per week applies to gross weekly earnings of €352.01.
        // *
        // * From 1 October 2024, a person earning €352.01 pays €14.43 PRSI (4.1%).
        // After
        // * the €12 credit is deducted they will pay PRSI of €2.43. For people
        // earning
        // * between €352.01 and €424, the credit of €12 is reduced by one-sixth of
        // * earnings over €352.01. There is no PRSI credit once earnings exceed
        // €424.
        // */
        double weeklyPay = (double) grosspay / 52;
        weeklyPay = Math.round(weeklyPay * 100.0) / 100.0;
        if (weeklyPay < 352.0) {
            prsi = 0;
        } else if (weeklyPay == 352.01) {
            credit = 12;
            prsi = ((weeklyPay) * 0.041) - credit;
            prsi = prsi * 52;
        } else if (weeklyPay > 352.01 && weeklyPay <= 424.0) {
            credit = 12 - (weeklyPay - 352) / 6;

            prsi = ((weeklyPay) * 0.041) - credit;
            prsi = prsi * 52;
        } else {
            credit = 0;
            prsi = ((weeklyPay) * 0.041) - credit;
            prsi = prsi * 52;
        }
        prsi = Math.round(prsi * 100.0) / 100.0;

        return prsi;
    }

    /**
     * Calculates PAYE contributions per month for an annual employee
     */
    public double calculatePAYE() {
        double HigherRate;
        double grosspay = getSalary();
        double part1;// Tax on first 42000
        double part2;// Tax on over 42000
        // /*
        // * https://www.revenue.ie/en/personal-tax-credits-reliefs-and-exemptions/tax-
        // * relief-charts/index.aspx
        // * For the sake of ease I will be assuming that all employees are Single
        // or
        // * widowed or surviving civil partner without qualifying children
        // * This means that 42000 of their income is subject to 20% and anything
        // over
        // * this is subject to 40%
        // * All PAYE taxpayers are entitled to a tax credit known as the Employee
        // Tax
        // * Credit (formerly known as the PAYE tax credit). This is worth €1,875 in
        // 2024
        // * (€1,775 in 2023). If you are married and taxed under joint assessment,
        // then
        // * you and your spouse may both claim an Employee Tax Credit.
        // * https://www.citizensinformation.ie/en/money-and-tax/tax/income-tax-credits-
        // * and-reliefs/employment-tax-credits-and-reliefs/#:~:text=All%20PAYE%
        // * 20taxpayers%20are%20entitled,claim%20an%20Employee%20Tax%20Credit.
        // * The following tax credits will increase by €100 to €1,875:
        // * Personal Tax Credit
        // *
        // */
        if (grosspay <= 42000) {
            HigherRate = 0;
            part1 = grosspay * 0.2;
            part2 = HigherRate * 0.4;
        } else {
            HigherRate = grosspay - 42000;
            part1 = 42000 * 0.2;
            part2 = HigherRate * 0.4;
        }
        double part3 = part1 + part2;
        double Paye = part3 - 1875 * 2; // sum of tax - employee tax credit and
        // personal tax credit
        return Math.max(Paye, 0);
    }

    /**
     * Calculates USC contributions per month for an annual employee
     */
    public double calculateUSC() {
        double USC = 0;
        double grosspay = getSalary();
        double[] rates = { 0.005, 0.02, 0.04, 0.08 };
        double[] thresholds = { 12012, 25760, 70044 };
        // /*
        // *
        // https://www.citizensinformation.ie/en/money-and-tax/tax/income-tax/universal-
        // *
        // social-charge/#:~:text=The%20Universal%20Social%20Charge%20is,do%20not%20pay%
        // * 20any%20USC.
        // * If your total income is €13,000 or less per year, you do not pay any
        // USC. If
        // * it is more than €13,000 per year, you pay USC on your full income.
        // */
        // // For ease, we will be assuming that our employees are not eligible to
        // receive
        // // reduced rates of USC
        double rate;
        if (grosspay <= 13000) {
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
     * Calculates Net pay for an annual employee
     */
    public double calculateNetPay() {
        double NetPay = getSalary() - calculateUSC() - calculatePAYE() -
                calculatePRSI();
        NetPay = Math.round(NetPay * 100.0) / 100.0;

        return NetPay;
    }
/**
 * To string method
 */
    @Override
    public String toString() {
        return "Personal Details:\nName: " + name + ", EmployeeID: " + employeeID + ", Salary: " +
                salary + ", Field: " + field + ", Role: " + role + ", Scale: " + scale;
    }
}
