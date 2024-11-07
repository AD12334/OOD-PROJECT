import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class HumanResources extends User {
    // We want a particular employment to correspond to a scale
    private LinkedHashMap<String, Integer> hashMap;
    private ArrayList<Integer> scales;
    private ArrayList<String> positions;

    // The string can be our job titles
    // The array of ints can be all the scales associated with that specific job
    // title
    public HumanResources() {
        super("HR", "HR");
        hashMap = new LinkedHashMap<>();
        scales = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public void promote() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/OODCSV.csv"));

        // SETTING THE DELIMITER
        sc.useDelimiter(",");
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();

            line = line.trim();
            String[] lines = line.split(",");
            String field = lines[0];
            String Employee = lines[1];
            int salary = Integer.parseInt(lines[2]);
            int scale = Integer.parseInt(lines[3]);
            int promotion = Integer.parseInt(lines[4]);
            int employeeID = Integer.parseInt(lines[5]);
            scales.add(scale);
            positions.add(Employee);

        }
        for (int i = 0; i < scales.size(); i++) {

            hashMap.put(positions.get(i), scales.get(i));
        }
        System.out.println(hashMap);
    }
}
