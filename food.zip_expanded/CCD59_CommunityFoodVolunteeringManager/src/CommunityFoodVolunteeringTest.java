import java.util.ArrayList;

/**
 * Class CommunityFoodVolunteeringTest
 * Author: Chukwudalu Dumebi-Kachikwu
 * Created: 10/17/2024
 */
public class CommunityFoodVolunteeringTest {

    
    public static void checkVolunteerMatches(ArrayList<Volunteer> volunteers, ArrayList<CommunityFoodOrg> organizations) {
        for (Volunteer volunteer : volunteers) {
            for (CommunityFoodOrg organization : organizations) {
                boolean match = volunteer.orgMatch(organization); // Check if the volunteer matches the organization
                if (match) {
                    System.out.println(volunteer.getFullName() + " can volunteer at " + organization.getName() + " on " + volunteer.getDayAvailable());
                } else {
                    System.out.println(volunteer.getFullName() + " cannot volunteer at " + organization.getName() + " on " + volunteer.getDayAvailable() + " because " + volunteer.getReasonForFailedConnect());
                }
            }
        }
    }

    public static void main(String[] args) {
        VolunteeringManager volManager = new VolunteeringManager("src/data/community_food_organizations.txt", "src/data/volunteers.txt");
        ArrayList<Volunteer> volunteers = volManager.getVolunteers();
        ArrayList<CommunityFoodOrg> orgs = volManager.getOrgs();

        // Check matches between volunteers and organizations
        System.out.println("Matching Volunteers to Organizations:");
        checkVolunteerMatches(volunteers, orgs);

        // Assign each volunteer to the highest-priority organization
        System.out.println("\nSigning up volunteers to priority organizations...");
        for (Volunteer volunteer : volunteers) {
            volManager.signUpVolunteerToPriorityOrg(volunteer);
        }

        // Cancel the sign-up for the first volunteer
        if (!volunteers.isEmpty() && volunteers.get(0).getOrgVolunteering() != null) {
        	System.out.println("BOB");
            System.out.println("\nCanceling sign-up for the first volunteer...");
            volunteers.get(0).cancelSignup();
            System.out.println(volunteers.get(0).getFullName() + " has canceled their sign-up.");
        }

        // Display for available spots for each organization
        System.out.println("\nChecking remaining spots per day for each organization:");
        for (CommunityFoodOrg org : orgs) {
            System.out.println("Organization: " + org.getName());
            for (int dayIndex = 0; dayIndex < 7; dayIndex++) {
                int spotsLeft = org.dailyVolunteerSpotsLeft(getDayName(dayIndex));
                System.out.println(getDayName(dayIndex) + ": " + spotsLeft + " spots left");
            }
        }

        // The summary of who signed up where and who couldn't sign up
        System.out.println("\nSummary of Volunteer Sign-ups:");
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getOrgVolunteering() != null) {
                System.out.println(volunteer.getFullName() + " successfully signed up for " +
                        volunteer.getOrgVolunteering().getName() + " on " + volunteer.getDayAvailable());
            } else {
                System.out.println(volunteer.getFullName() + " is not signed up anywhere.");
            }
        }
    }

    // Map day indices to day names
    private static String getDayName(int index) {
        switch (index) {
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thursday";
            case 4: return "Friday";
            case 5: return "Saturday";
            case 6: return "Sunday";
            default: return "Invalid Day";
        }
    }
}
