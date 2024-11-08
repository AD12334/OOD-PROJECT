import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

    // myWriter.write(name + "," + id + "," + field + "," + role + "," + scale
    // +"\n");
    // JOE,1731007147,PRESIDENTIAL,PRESIDENT,1
  }

  public void setpromotion() throws Exception {
    Scanner sc = new Scanner(System.in);
    ArrayList<Integer> IDS = new ArrayList<>();
    System.out.println(
        "Enter the ID of the employee which you wish to promote");
    int DesiredId = Integer.parseInt(sc.next());

    // We are asking the human resources person the id of the person they want
    // to promote An employee can only be promoted if they are at the max scale
    // We need to check if they are at the max scale
    Scanner sc2 = new Scanner(new File("OODPROJ/src/employee_database.csv"));
    sc2.useDelimiter(",");
    sc2.useDelimiter("\n");
    while (sc2.hasNext()) {
      String line = sc2.next();
      line = line.trim();
      String[] lines = line.split(",");
      // Iterate through every entry in our employees database

      String name = lines[0];
      int id = Integer.parseInt(lines[1]);

      String field = lines[2];
      String position = lines[3];
      int scale = Integer.parseInt(lines[4]);
      // If the id entry for a specific row belongs to the person we want to
      // promote
      if (id == DesiredId) {
        if (hashMap.containsKey(
                position)) { // If our hashmap has their position
          if (hashMap.get(position) == scale) { // If they are at the max scale
           String newposition = changePosition(position);

            FileWriter myWriter =
                new FileWriter("OODPROJ/src/PendingPromotions.csv",
                               true); // Promote them by
                                      // putting them onto our
                                      // promotable employees
                                      // csv
            myWriter.write(name + "," + id + "," + field + "," + newposition +
                           "," + scale + ",1\n");
            myWriter.close();
          }
        }
      }
    }
  }

  @Override
  public void displayOptions() {
    Scanner input = new Scanner(System.in);
    System.out.println("Human Resources Options:");
    System.out.println("1. View Employee Details");
    System.out.println("2. Promote Employee");
    System.out.println("3. Logout");
    int command = input.nextInt();
    switch (command) {
    case 1:
      System.out.println("List of Employees");
      displayOptions();
      break;
    case 2:
      try {
        setpromotion();
      } catch (Exception e) {
        System.out.println("Selected Employee cannot be promoted");
      }
      displayOptions();
      break;
    case 3:
      System.out.println("Logging out");
      System.exit(0);
      break;
    default:
      System.out.println("Incorrect Input");
      displayOptions();
      break;
    }
  }
  //WE currently have logic in place to detect when an employee is promotable i.e if they are at the max scale
  //Now we just need to make logic to actually promote them
  //We could do this by saving all employee info deleting all the instances of the promoted employee from our database
  //Then we could just place our new promoted employee to our database
  public String changePosition(String position){
    Scanner sc = new Scanner(System.in);
    ArrayList<String> keys = new ArrayList<>(hashMap.keySet());
    ArrayList<String> nonpromotable = new ArrayList<>(Arrays.asList("PRESIDENT","FULL PROFESSOR","SENIOR ADMINISTRATIVE OFFICER 3","EPS PORFOLIO MANAGER","SUB LIBRARIAN","ANALYST PROGRAMMER 3","CHIEF TECHNICAL OFFICER","SEN PORTER/ATTENDANT","TEACHING FELLOW","THERAPIES REGIONAL SUPERVISORS REGIONAL PLACEMENT FACILITATOR"));
    //We now have our list of positions and our list of non promotable positions
    //If our employee position is contained by non promotable then they have reached the top of their field
    //If an employee is not nonpromotable then they can be promoted
    
    if(nonpromotable.contains(position) && keys.contains(position)){
        System.out.println("Employee has already reached the top of their respective field therefore they cannot be promoted further");
    }
    if(!nonpromotable.contains(position) && keys.contains(position)){
        System.out.println("Employee can be promoted to " + keys.get(keys.indexOf(position) - 1));
        System.out.println("Would you like to promote this employee Y/N ?");
        String response = sc.nextLine().toUpperCase();
        if (response.equals("Y")){
            System.out.println("Employee has been offered promotion, pending employee acceptance");
            return keys.get(keys.indexOf(position) - 1);
        }else if (response.equals("N")){
            System.out.println("Employee has not been promoted");
            

        }else{
            System.out.println("Invalid input");
            
        }
      

    }
    
    return keys.get(keys.indexOf(position));//If the employee isnt promotable or we dont want to promote them then we just keep their position the same
  }

}
