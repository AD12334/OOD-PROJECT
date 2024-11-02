import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Main {
    private static ArrayList<Position> positions;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/OODCSV.csv"));
        ArrayList<Position> positions = new ArrayList<>();
        //SETTING THE DELIMITER
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();
            line = line.trim();
            String[] lines = line.split(",");

            //System.out.println((lines[3].getClass().getName()));
            String field = lines[0];
            String position = lines[1];
            int salary = Integer.parseInt(lines[2]);
            int scale = Integer.parseInt(lines[3]);
            Position p = new Position(field, position, salary, scale);
            positions.add(p);


            //System.out.println(positions);

        }
       /* for (Position p : positions) {
            BasicPayslip payslip = new BasicPayslip(p);
        } */
        Scanner sc2 = new Scanner(new File("src/HourlyPaidEmployees(Sheet1).csv"));
        ArrayList<HourlyPosition> positions2 = new ArrayList<>();
        //Hourly paid employees have salaries based on 40 hour work weeks
        //We can get the hourly rate by dividing by 52 and then dividing by 40
        //SETTING THE DELIMITER
        sc2.useDelimiter(",");
        sc2.useDelimiter("\n");
        while (sc2.hasNext()) {
            String line = sc2.next();
            line = line.trim();
            String[] lines = line.split(",");

            //System.out.println((lines[3].getClass().getName()));
            String field = lines[0];
            String position = lines[1];
            int salary = Integer.parseInt(lines[2]);
            int scale = Integer.parseInt(lines[3]);
            HourlyPosition p = new HourlyPosition(field, position, salary, scale);
            positions2.add(p);

            //System.out.println(positions2);


        }
       for (HourlyPosition p : positions2) {
           BasicPayslip basicPayslip = new BasicPayslip(p);
       }


    }
}