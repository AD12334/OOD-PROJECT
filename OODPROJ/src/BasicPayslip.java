import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class BasicPayslip {
    private int monthIndex;
     private int dayofpayment;

    public BasicPayslip(Employee Employee) {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the year which you wish to view a payslip for");
        int year = sc.nextInt();
        while(year > LocalDate.now().getYear()){   
            System.out.println("No payslip available for specified year please enter a valid year");
            year = sc.nextInt();

        }
        sc.nextLine(); //Int does not leave a newline character so we gotta go to the next line 
        System.out.println("Select which month you would like to view a payslip for..... " + monthoptions(year));
        String month = sc.nextLine().toUpperCase();
        while (!monthoptions(year).contains(month)){
            System.out.println("Please select a valid month for which you wish to view a payslip for");
            month = sc.nextLine().toUpperCase();
        }
        System.out.println("Fetching payslip for " + month);
        



        /*System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Date: " + LocalDate.now());
        System.out.println("Name: John Doe");
        System.out.println("PRSI CLASS: A");
        System.out.println("Field: " + Employee.getField());
        System.out.println("Employee: " + Employee.getEmployee());
        System.out.println("Scale: " + Employee.getScale());
        System.out.println("Gross annual pay: " + Employee.getSalary());
        System.out.println("Gross monthly pay: " + Employee.getSalary() / 12);
        System.out.println("PAYE: " + Employee.calculatePAYE());
        System.out.println("PRSI: " + Employee.calculatePRSI());
        System.out.println("USC: " + Employee.calculateUSC());
        System.out.println(
                "Total Deductions: " + (Employee.calculatePAYE() + Employee.calculatePRSI() + Employee.calculateUSC()));
        System.out.println("Net pay: " + Employee.calculateNetPay());
        System.out.println("Pay method: PayPath");
        System.out.println("----------------------------------------------------------------------------------------");
*/
    }

    public BasicPayslip(HourlyEmployee Employee) {
        float hoursworked1;
        float hoursworked2;
        float hoursworked3;
        float hoursworked4;
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Enter your total amount of hours worked during the first week of the current working period ");

        try {
            hoursworked1 = scanner.nextFloat();
            checkHour(hoursworked1);
        } catch (HoursException e) {
            System.out.println("Invalid hours worked during the first week of the current working period" + e);
            hoursworked1 = 0;
        } catch (InputMismatchException e) {
            System.out.println("Invalid hours worked during the first week of the current working period");
            hoursworked1 = 0;
            scanner.nextLine();
        }
        double paye1 = Employee.calculatePAYE(hoursworked1);
        double prsi1 = Employee.calculatePRSI(hoursworked1);
        double usc1 = Employee.calculateUSC(hoursworked1);
        System.out.println(
                "Enter your total amount of hours worked during the second week of the current working period");

        try {
            hoursworked2 = scanner.nextFloat();
            checkHour(hoursworked2);
        } catch (HoursException e) {
            System.out.println("Invalid hours worked during the second week of the current working period" + e);
            hoursworked2 = 0;

        } catch (InputMismatchException e) {
            System.out.println("Invalid hours worked during the second week of the current working period");
            hoursworked2 = 0;
            scanner.nextLine();
        }
        double paye2 = Employee.calculatePAYE(hoursworked2);
        double prsi2 = Employee.calculatePRSI(hoursworked2);
        double usc2 = Employee.calculateUSC(hoursworked2);
        System.out
                .println("Enter your total amount of hours worked during the third week of the current working period");

        try {
            hoursworked3 = scanner.nextFloat();
            checkHour(hoursworked3);
        } catch (HoursException e) {
            System.out.println("Invalid hours worked during the third week of the current working period" + e);
            hoursworked3 = 0;
        } catch (InputMismatchException e) {
            System.out.println("Invalid hours worked during the third week of the current working period");
            hoursworked3 = 0;
            scanner.nextLine();
        }
        double paye3 = Employee.calculatePAYE(hoursworked3);
        double prsi3 = Employee.calculatePRSI(hoursworked3);
        double usc3 = Employee.calculateUSC(hoursworked3);
        System.out
                .println("Enter your total amount of hours worked during the final week of the current working period");

        try {
            hoursworked4 = scanner.nextFloat();
            checkHour(hoursworked4);
        } catch (HoursException e) {
            System.out.println("Invalid hours worked during the final week of the current working period" + e);
            hoursworked4 = 0;
        } catch (InputMismatchException e) {
            System.out.println("Invalid hours worked during the final week of the current working period");
            hoursworked4 = 0;
            scanner.nextLine();
        }
        double paye4 = Employee.calculatePAYE(hoursworked4);
        double prsi4 = Employee.calculatePRSI(hoursworked4);
        double usc4 = Employee.calculateUSC(hoursworked4);
        double totalworked = hoursworked1 + hoursworked2 + hoursworked3 + hoursworked4;
        totalworked = Math.round(totalworked * 100) / 100;
        double totalpaye = paye1 + paye2 + paye3 + paye4;
        double totalprsi = prsi1 + prsi2 + prsi3 + prsi4;
        double totalusc = usc1 + usc2 + usc3 + usc4;
        totalusc = Math.round(totalusc * 100.0) / 100.0;
        double totaldeductions = totalpaye + totalprsi + totalusc;
        totaldeductions = Math.round(totaldeductions * 100.0) / 100.0;
        double netpay = Employee.getHourlyPay() * totalworked - totaldeductions;
        netpay = Math.round(netpay * 100.0) / 100.0;

        // Make it continuously prompt the user for a valid input

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Date: " + LocalDate.now());
        System.out.println("Name: John Doe");
        System.out.println("Hours Worked: " + totalworked);
        System.out.println("PRSI CLASS: A");
        System.out.println("Field: " + Employee.getField());
        System.out.println("Employee: ");
        System.out.println("Scale: " + Employee.getScale());
        System.out.println("Gross hourly pay: " + Employee.getHourlyPay());
        System.out.println("PAYE: " + totalpaye);
        System.out.println("PRSI: " + totalprsi);
        System.out.println("USC: " + totalusc);
        System.out.println("Gross pay: " + Employee.getHourlyPay() * totalworked);
        System.out.println("Total Deductions: " + (totaldeductions));
        System.out.println("Net pay: " + (netpay));
        System.out.println("Pay method: PayPath");
        System.out.println("----------------------------------------------------------------------------------------");

    }

    public float checkHour(float hour) throws HoursException {
        if ((hour) < 0 || hour > 100) {

            throw new HoursException("Hour is not valid");

        }
        return hour;
    }
    public ArrayList<String> monthoptions(int year){
        ArrayList<String> monthoptions = new ArrayList<>();
        LinkedHashMap<Integer,String> months = new LinkedHashMap<>();
        months.put(1,"JANUARY");
        months.put(2,"FEBUARY");
        months.put(3,"MARCH");
        months.put(4,"APRIL");
        months.put(5,"MAY");
        months.put(6,"JUNE");
        months.put(7,"JULY");
        months.put(8,"AUGUST");
        months.put(9,"SEPTEMBER");
        months.put(10,"OCTOBER");
        months.put(11,"NOVEMBER");
        months.put(12,"DECEMBER");
        if(year < LocalDate.now().getYear()){
            for (int i = 1;i < 13;i++){
                if(LocalDate.now().isAfter(LocalDate.of(year,i,25))){
                    monthoptions.add(months.get(i));
                }
                
            }
            return monthoptions; //If we want to view all payslips for a previous year then all months are available to view
        }else if (year == LocalDate.now().getYear()){//If the year we want to view payslips for is the current year
            String Month = LocalDate.now().getMonth().toString(); //Find the current month
           
            for (int i = 1;i<13;i++){
                if(months.get(i).equals(Month)){ //Iterate through our hashmap keys and check if their value is equal to the current month
                    monthIndex = i; // If the value of the month is the current month then we have our month index 
                    //The month index just corresponds to the number of months that have passed so far in the year
                }
            }
            for(int i = 1;i<monthIndex + 1; i++){
                LocalDate date = LocalDate.of(year, i, 25);
                String day = date.getDayOfWeek().toString();
                //All Monthly Staff, Hourly, Retired Members of Staff and Scholarships are paid on the 25th of the month. 
                //(Please note if payment date falls on a Saturday then pay day is Friday (24th) and if falls on a Sunday then pay day is Monday(26th)).
                if (day.equals("SATURDAY")){
                      dayofpayment = 24;
                 }
                else if(day.equals("SUNDAY")){
                     dayofpayment = 26;
                 }
                 else{
                    dayofpayment = 25;
                 }
                if(LocalDate.now().isAfter(LocalDate.of(year,i,dayofpayment))){ // If the current day is past the 25th of a certain month then we can print a payslip (the day has past)
                    monthoptions.add(months.get(i));
                }
                
            }
            return monthoptions;
        }
        return monthoptions; 
    }

}
