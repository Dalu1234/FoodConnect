import java.io.*;
import java.util.ArrayList;

/**
 * Class DataManager
 * @author : Chukwudalu Dumebi-Kachikwu
 * @created 11/21/2024
 */
public class DataManager {

    // Static method to read community food organizations from a file
    public static ArrayList<CommunityFoodOrg> readCommunityFoodOrgs(String filename) {
        ArrayList<CommunityFoodOrg> organizations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 9) continue; // Skip invalid lines
          //code to check to see if Datamanager is correctly parsing the file
                /*  for (int i = 0; i < parts.length; i++) {
                System.out.println("Index " + i + ": " + parts[i]);
            } */
                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                double latitude = Double.parseDouble(parts[3].trim());
                double longitude = Double.parseDouble(parts[4].trim());
                String address = parts[5];
                String city = parts[6];
                String state = parts[7];
                String zip = parts[8];
                boolean offersTransportation = parts[9].equalsIgnoreCase("yes");
                Location location = new Location(latitude, longitude, address, city, state, zip);

                if (type.equalsIgnoreCase("Food Bank")) {
                    double maxCapacity = Double.parseDouble(parts[10]);
                    TimeFrame[] dailyOpenHours = new TimeFrame[7];
                    double[] dailyDonationsNeeded = new double[7];

                    for (int i = 11; i < parts.length; i++) {
                        String[] dayInfo = parts[i].split("@");
                        if (dayInfo.length == 3) {
                            int dayIndex = getDayIndex(dayInfo[0]);
                            if (dayIndex != -1) {
                                String[] startTimeParts = dayInfo[1].split(":");
                                String[] endTimeParts = dayInfo[2].split(":");

                                int startHour = Integer.parseInt(startTimeParts[0]);
                                int startMinute = Integer.parseInt(startTimeParts[1]);
                                int endHour = Integer.parseInt(endTimeParts[0]);
                                int endMinute = Integer.parseInt(endTimeParts[1]);

                                dailyOpenHours[dayIndex] = new TimeFrame(startHour, startMinute, endHour, endMinute);
                                dailyDonationsNeeded[dayIndex] = maxCapacity;
                            }
                        }
                    }
                    organizations.add(new FoodBank(id, name, location, dailyOpenHours, maxCapacity, dailyDonationsNeeded));

                } else if (type.equalsIgnoreCase("Food Pantry")) {
                    TimeFrame[] dailyOpenHours = new TimeFrame[7];
                    int[] dailyVolunteersNeeded = new int[7];
                    int[] dailyVolunteerSignups = new int[7];

                    for (int i = 10; i < parts.length; i++) {
                        String[] dayInfo = parts[i].split("@");
                        if (dayInfo.length == 4) {
                            int dayIndex = getDayIndex(dayInfo[0]);
                            if (dayIndex != -1) {
                                String[] startTimeParts = dayInfo[1].split(":");
                                String[] endTimeParts = dayInfo[2].split(":");

                                int startHour = Integer.parseInt(startTimeParts[0]);
                                int startMinute = Integer.parseInt(startTimeParts[1]);
                                int endHour = Integer.parseInt(endTimeParts[0]);
                                int endMinute = Integer.parseInt(endTimeParts[1]);

                                dailyOpenHours[dayIndex] = new TimeFrame(startHour, startMinute, endHour, endMinute);
                                dailyVolunteersNeeded[dayIndex] = Integer.parseInt(dayInfo[3]);
                            }
                        }
                    }
                    organizations.add(new FoodPantry(id, name, location, dailyOpenHours, dailyVolunteersNeeded, dailyVolunteerSignups, offersTransportation));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading community food organizations: " + e.getMessage());
        }
        return organizations;
    }

    // Static method to read volunteers from a file
    public static ArrayList<Volunteer> readVolunteers(String filename) {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 15) continue; // Skip invalid lines
              /*  for (int i = 0; i < parts.length; i++) {
                    System.out.println("Index " + i + ": " + parts[i]);
                } */

                String id = parts[0];
                String firstName = parts[1];
                String lastName = parts[2];
                int age = Integer.parseInt(parts[3]);
                double latitude = Double.parseDouble(parts[4].trim());
                double longitude = Double.parseDouble(parts[5].trim());
                String address = parts[6];
                String city = parts[7];
                String state = parts[7];
                String zip = parts[8];
                String dayAvailable = parts[9];

                String[] startTimeParts = parts[10].split(":");
                String[] endTimeParts = parts[11].split(":");
                int startHour = Integer.parseInt(startTimeParts[0]);
                int startMinute = Integer.parseInt(startTimeParts[1]);
                int endHour = Integer.parseInt(endTimeParts[0]);
                int endMinute = Integer.parseInt(endTimeParts[1]);

                double distanceAvailable = Double.parseDouble(parts[12]);
                double donation = Double.parseDouble(parts[14]); // Corrected index
                boolean needsTransportation = parts[13].trim().equalsIgnoreCase("yes");

                Location location = new Location(latitude, longitude, address, city, state, zip);
                TimeFrame timeAvailable = new TimeFrame(startHour, startMinute, endHour, endMinute);
                volunteers.add(new Volunteer(id, firstName + " " + lastName, age, location, dayAvailable, timeAvailable, distanceAvailable, needsTransportation, donation));
            }
        } catch (IOException e) {
            System.err.println("Error reading volunteers: " + e.getMessage());
        }
        return volunteers;
    }


    // Map day names to indices
    private static int getDayIndex(String dayName) {
        switch (dayName.toLowerCase()) {
            case "monday": return 0;
            case "tuesday": return 1;
            case "wednesday": return 2;
            case "thursday": return 3;
            case "friday": return 4;
            case "saturday": return 5;
            case "sunday": return 6;
            default: return -1;
        }
    }
}
