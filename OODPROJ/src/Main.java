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
            String[] lines =line.split(",");

            //System.out.println((lines[3].getClass().getName()));
       String field = lines[0];
       String position = lines[1];
        int salary = Integer.parseInt(lines[2]);
        int scale = Integer.parseInt(lines[3]);
        Position p = new Position(field, position, salary, scale);
        positions.add(p);
        System.out.println(positions);




        }

    }
}