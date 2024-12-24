import java.util.ArrayList;

/**
 * Class VolunteeringManager
 * @author :
 * @created 11/21/2024
 */
public class VolunteeringManager {
    private ArrayList<CommunityFoodOrg> orgs;
    private ArrayList<Volunteer> volunteers;

    /**
     * Constructor VolunteeringManager
     * @param orgsFile a String representing the file path to the community food organizations data
     *        (the file contains details about organizations, their locations, schedules, and requirements).
     * @param volunteersFile a String representing the file path to the volunteers data
     *        (the file contains details about volunteers, including their availability, distance, and preferences).
     * 
     * This constructor initializes the VolunteeringManager by reading data from the specified files.
     * It populates the list of organizations and the list of volunteers  
     */
    public VolunteeringManager(String orgsFile, String volunteersFile) {
        this.orgs = DataManager.readCommunityFoodOrgs(orgsFile);
        this.volunteers = DataManager.readVolunteers(volunteersFile);
    }

    /**
     * Method signUpVolunteerToPriorityOrg
     * @param volunteer a Volunteer object representing the individual seeking to sign up 
     *        (includes details about the volunteer's availability, distance, and preferences).
     * @return a boolean value indicating whether the volunteer was successfully signed up
     *         (true if successfully signed up, false if no suitable organization was found).
     * 
     * This method attempts to assign the given volunteer to the highest-priority organization:
     * - For FoodBank: priority is based on dailyDonationsNeeded for the given day.
     * - For FoodPantry: priority is based on the number of unfilled volunteer spots for the given day.
     * 
     * It considers the volunteer's availability and preferences to find a match.
     */
    public boolean signUpVolunteerToPriorityOrg(Volunteer volunteer) {
        int dayIndex = getDayIndex(volunteer.getDayAvailable());
        CommunityFoodOrg bestOrg = null;
        double highestPriority = 0; // Track highest priority (max dailyDonationsNeeded or unfilled spots)

        for (CommunityFoodOrg org : orgs) {
            // Skip if the organization is closed on the volunteer's available day
            if (org.getDailyOpenHours()[dayIndex] == null) continue;

            double priority = 0;

            // Determine priority based on organization type
            if (org instanceof FoodBank) {
                FoodBank foodBank = (FoodBank) org;
                priority = foodBank.getDailyDonationsNeeded(volunteer.getDayAvailable());
            } else if (org instanceof FoodPantry) {
                FoodPantry foodPantry = (FoodPantry) org;
                int unfilledSpots = foodPantry.dailyVolunteerSpotsLeft(volunteer.getDayAvailable());
                priority = unfilledSpots;
            }

            // Check if this organization has a higher priority and matches the volunteer
            if (priority > highestPriority && volunteer.orgMatch(org)) {
                bestOrg = org;
                highestPriority = priority;
            }
        }

        // If a suitable organization is found, sign up the volunteer
        if (bestOrg != null) {
            volunteer.signUp(bestOrg);
            return true;
        }

        return false; // No suitable organization found
    }

    // Get day index
    private int getDayIndex(String day) {
        switch (day.toLowerCase()) {
            case "monday": return 0;
            case "tuesday": return 1;
            case "wednesday": return 2;
            case "thursday": return 3;
            case "friday": return 4;
            case "saturday": return 5;
            case "sunday": return 6;
            default: throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

    // Getters
    public ArrayList<CommunityFoodOrg> getOrgs() {
        return orgs;
    }

    public ArrayList<Volunteer> getVolunteers() {
        return volunteers;
    }
}
