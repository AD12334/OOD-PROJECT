import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
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
    public HumanResources() throws FileNotFoundException {
        super("HR", "HR");
        hashMap = new LinkedHashMap<>();
        scales = new ArrayList<>();
        positions = new ArrayList<>();
        Scanner sc = new Scanner(new File("OODPROJ/src/salary_scales.csv"));

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

        //myWriter.write(name + "," + id + "," + field + "," + role + "," + scale +"\n");
        //JOE,1731007147,PRESIDENTIAL,PRESIDENT,1


    }


    public void setpromotion() throws Exception {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> IDS = new ArrayList<>();
        System.out.println("Enter the ID of the employee which you wish to promote");
        int DesiredId = Integer.parseInt(sc.next());

        //We are asking the human resources person the id of the person they want to promote
        //An employee can only be promoted if they are at the max scale
        //We need to check if they are at the max scale
        Scanner sc2 = new Scanner(new File("OODPROJ/src/employee_database.csv"));
        sc2.useDelimiter(",");
        sc2.useDelimiter("\n");
        while (sc2.hasNext()) {
            String line = sc2.next();
            line = line.trim();
            String[] lines = line.split(",");
            //Iterate through every entry in our employees database

            String name = lines[0];
            int id = Integer.parseInt(lines[1]);

            String field = lines[2];
            String position = lines[3];
            int scale = Integer.parseInt(lines[4]);
            //If the id entry for a specific row belongs to the person we want to promote
            if(id ==DesiredId){
            if (hashMap.containsKey(position)) { //If our hashmap has their position
                if (hashMap.get(position) == scale) { // If they are at the max scale

                    FileWriter myWriter = new FileWriter("OODPROJ/src/PromotableEmployees.csv", true); //Promote them by putting them onto our promotable employees csv
                    myWriter.write(name + "," + id + "," + field + "," + position + "," + scale + ",1\n");
                    myWriter.close();
                }
            }
            }
        }
    }

    @Override
    public void displayOptions() {

    }
}