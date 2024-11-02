import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BasicPayslip  {


    public BasicPayslip(Position position) {


        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Date: " + LocalDate.now() );
        System.out.println("Name: John Doe");
        System.out.println("PRSI CLASS: A");
        System.out.println("Field: " + position.getField());
        System.out.println("Position: " + position.getPosition());
        System.out.println("Scale: " + position.getScale());
        System.out.println("Gross annual pay: " + position.getSalary());
        System.out.println("Gross monthly pay: " + position.getSalary()/12);
        System.out.println("PAYE: " + position.calculatePAYE());
        System.out.println("PRSI: " + position.calculatePRSI());
        System.out.println("USC: " + position.calculateUSC());
        System.out.println("Total Deductions: " +(position.calculatePAYE() +position.calculatePRSI()+position.calculateUSC()));
        System.out.println("Net pay: " + position.calculateNetPay());
        System.out.println("Pay method: PayPath");
        System.out.println("----------------------------------------------------------------------------------------");

    }
    public BasicPayslip(HourlyPosition position) {
int hoursworked1;
int hoursworked2;
int hoursworked3;
int hoursworked4;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your total amount of hours worked during the first week of the current working period ");

        try{
             hoursworked1 = scanner.nextInt();
            checkHour(hoursworked1);
        }catch(HoursException e){
            System.out.println("Invalid hours worked during the first week of the current working period" + e);
            hoursworked1=0;
        }catch(InputMismatchException e){
            System.out.println("Invalid hours worked during the first week of the current working period");
            hoursworked1=0;
            scanner.nextLine();
        }
        double paye1 =  position.calculatePAYE(hoursworked1);
        double prsi1 = position.calculatePRSI(hoursworked1);
        double usc1 = position.calculateUSC(hoursworked1);
        System.out.println("Enter your total amount of hours worked during the second week of the current working period");


        try{
           hoursworked2 = scanner.nextInt();
            checkHour(hoursworked2);
        }catch(HoursException e){
            System.out.println("Invalid hours worked during the second week of the current working period" + e);
            hoursworked2=0;

        }catch(InputMismatchException e){
            System.out.println("Invalid hours worked during the second week of the current working period");
            hoursworked2=0;
            scanner.nextLine();
        }
        double paye2 =  position.calculatePAYE(hoursworked2);
        double prsi2 = position.calculatePRSI(hoursworked2);
        double usc2 = position.calculateUSC(hoursworked2);
        System.out.println("Enter your total amount of hours worked during the third week of the current working period");

        try{
            hoursworked3 = scanner.nextInt();
            checkHour(hoursworked3);
        }catch(HoursException e){
            System.out.println("Invalid hours worked during the third week of the current working period" + e);
            hoursworked3=0;
        }catch(InputMismatchException e){
            System.out.println("Invalid hours worked during the third week of the current working period");
            hoursworked3=0;
            scanner.nextLine();
        }
        double paye3 =  position.calculatePAYE(hoursworked3);
        double prsi3 = position.calculatePRSI(hoursworked3);
        double usc3 = position.calculateUSC(hoursworked3);
        System.out.println("Enter your total amount of hours worked during the final week of the current working period");

        try{
            hoursworked4 = scanner.nextInt();
            checkHour(hoursworked4);
        }catch(HoursException e){
            System.out.println("Invalid hours worked during the final week of the current working period" + e);
            hoursworked4=0;
        }catch(InputMismatchException e){
            System.out.println("Invalid hours worked during the final week of the current working period");
            hoursworked4=0;
            scanner.nextLine();
        }
        double paye4 =  position.calculatePAYE(hoursworked4);
        double prsi4 = position.calculatePRSI(hoursworked4);
        double usc4 = position.calculateUSC(hoursworked4);
        int totalworked = hoursworked1 + hoursworked2 + hoursworked3 + hoursworked4;
        double totalpaye = paye1 + paye2 + paye3 + paye4;
        double totalprsi = prsi1 + prsi2 + prsi3 + prsi4;
        double totalusc = usc1 + usc2 + usc3 + usc4;
        double totaldeductions= totalpaye +totalprsi+totalusc;
        double netpay = position.getHourlyPay()*totalworked - totaldeductions;

//Make it continuously prompt the user for a valid input

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Date: " + LocalDate.now() );
        System.out.println("Name: John Doe");
        System.out.println("Hours Worked: " + totalworked);
        System.out.println("PRSI CLASS: A");
        System.out.println("Field: " + position.getField());
        System.out.println("Position: " + position.getPosition());
        System.out.println("Scale: " + position.getScale());
        System.out.println("Gross hourly pay: " + position.getHourlyPay());
        System.out.println("PAYE: " + totalpaye);
        System.out.println("PRSI: " + totalprsi);
        System.out.println("USC: " + totalusc);
        System.out.println("Total Deductions: " +(totaldeductions));
        System.out.println("Net pay: " + (netpay));
        System.out.println("Pay method: PayPath");
        System.out.println("----------------------------------------------------------------------------------------");

    }
    public int checkHour(int hour) throws HoursException{
        if ((hour) <0 || hour > 100 ){

            throw new HoursException("Hour is not valid");

        }
        return hour;
    }


}
