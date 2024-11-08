import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Employee extends User {
    private String name;
    private String employeeID;
    private int salary;
    private String field;
    private String role;
    private int scale;
    private int promotion;

    public Employee(String name, String employeeID, String field, String role,
            int scale) {
        super("t" + employeeID, employeeID); // Username is t + employee id, password is employeeid + 8 digits of
                                             // current UNIX time
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

    public String getName() {
        return name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getSalary() {
        return salary;
    }

    public void promote() {
        this.promotion = 1;
    }

    private int getSalaryFromCSV(String field, String role, int scale)
            throws FileNotFoundException {
        File csvFile = new File("OODPROJ/src/salary_scales.csv");
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
                scanner.close();
                return csvSalary;
            }
        }

        scanner.close();

        // If no matching salary found, throw an exception or handle as needed
        throw new IllegalArgumentException(
                "Salary not found for given field, role, and scale.");
    }

    @Override
    public void displayOptions() {
        System.out.println("Employee Options:");
        System.out.println("1. View Personal Details");
        System.out.println("2. View Payslip");
        System.out.println("3. Logout");

        // Add any other options specific to employees
        Scanner sc = new Scanner(System.in);
        int command = sc.nextInt();
        switch (command) {
            case 1:
                // TODO: personal details
                displayOptions();
            case 2:
                viewPayslip();
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
        sc.close();
    }

    public void viewPayslip() {
        // TODO: Implementation for viewing payslip
    }

    public void acceptPromotion() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("OODPROJ/src/PendingPromotions.csv"));

        // SETTING THE DELIMITER
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();

            line = line.trim();
            String[] lines = line.split(",");
            String name = lines[0];
            int id = Integer.parseInt(lines[1]);
            String field = lines[2];
            String position = lines[3];
            int scale = Integer.parseInt(lines[4]);
            int promotion = Integer.parseInt(lines[5]);
            if (promotion == 1) {
                System.out.println("Congratulations you have been selected for a promotion");
                System.out.println("Would you like to accept this promotion Y/N ?");
                String response = sc.nextLine().toUpperCase();
                if (response.equals("Y")) {
                    System.out.println("You have been promoted congratulations");
                } else if (response.equals("N")) {
                    System.out.println("Promotion has been rejected you will remain as ");
                } else {
                    System.out.println("Invalid response");
                }

            }
        }
        sc.close();
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

    // public double calculatePRSI() {
    // double prsi = 0;
    // double grosspay = getSalary();
    // double credit = 0;
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
    // double weeklyPay = (double) grosspay / 52;
    // weeklyPay = Math.round(weeklyPay * 100.0) / 100.0;
    // if (weeklyPay < 352.0) {
    // prsi = 0;
    // } else if (weeklyPay == 352.01) {
    // credit = 12;
    // prsi = ((weeklyPay) * 0.041) - credit;
    // prsi = prsi * 52;
    // } else if (weeklyPay > 352.01 && weeklyPay <= 424.0) {
    // credit = 12 - (weeklyPay - 352) / 6;

    // prsi = ((weeklyPay) * 0.041) - credit;
    // prsi = prsi * 52;
    // } else {
    // credit = 0;
    // prsi = ((weeklyPay) * 0.041) - credit;
    // prsi = prsi * 52;
    // }
    // prsi = Math.round(prsi * 100.0) / 100.0;

    // return prsi;
    // }

    // public double calculatePAYE() {
    // double HigherRate;
    // double grosspay = getSalary();
    // double part1;// Tax on first 42000
    // double part2;// Tax on over 42000
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
    // if (grosspay <= 42000) {
    // HigherRate = 0;
    // part1 = grosspay * 0.2;
    // part2 = HigherRate * 0.4;
    // } else {
    // HigherRate = grosspay - 42000;
    // part1 = 42000 * 0.2;
    // part2 = HigherRate * 0.4;
    // }
    // double part3 = part1 + part2;
    // double Paye = part3 - 1875 * 2; // sum of tax - employee tax credit and
    // personal tax credit
    // return Math.max(Paye, 0);
    // }

    // public double calculateUSC() {
    // double USC = 0;
    // double grosspay = getSalary();
    // double[] rates = { 0.005, 0.02, 0.04, 0.08 };
    // double[] thresholds = { 12012, 25760, 70044 };
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
    // double rate;
    // if (grosspay <= 13000) {
    // return USC;
    // } else {
    // USC += thresholds[0] * rates[0];
    // }
    // if (grosspay <= thresholds[1]) {
    // USC += (grosspay - thresholds[0]) * rates[1];
    // return USC;
    // } else {
    // USC += (thresholds[1] - thresholds[0]) * rates[1];
    // }
    // if (grosspay <= thresholds[2]) {
    // USC += (grosspay - thresholds[1]) * rates[2];
    // return USC;
    // } else {
    // USC += (thresholds[2] - thresholds[1]) * rates[2];
    // USC += (grosspay - thresholds[2]) * rates[3];
    // }
    // return USC;
    // }

    // public double calculateNetPay() {
    // double NetPay = getSalary() - calculateUSC() - calculatePAYE() -
    // calculatePRSI();
    // NetPay = Math.round(NetPay * 100.0) / 100.0;

    // return NetPay;
    // }
    // public String promotiondue(){
    // if (this.promotion == 0){
    // return "Promotion not due";
    // }
    // return "Promotion due!!!";
    // }

    // @Override
    // public String toString() {
    // return "Field : " + getField() + "\n Employee : " + getEmployee() + "\n
    // Salary : " + getSalary() + "\n Scale : "
    // + getScale() + "\n Net Pay :" + calculateNetPay() + " \nPromotion due: "
    // + promotiondue() + "\n";
    // }
}
