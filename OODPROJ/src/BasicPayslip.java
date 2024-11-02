import java.time.LocalDate;
import java.util.Date;

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
}
