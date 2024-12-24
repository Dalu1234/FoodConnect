public class FoodPantry extends CommunityFoodOrg {

	/**
	 * Class FoodPantry
	 * @author : Chukwudalu Dumebi-Kachikwu
	 * @created 12/10/2024
	 */
    private int[] dailyVolunteersNeeded;
    private int[] dailyVolunteerSignups;

    /**
     * Constructor for FoodPantry
     * @param id a String representing the unique identifier for the pantry
     * @param name a String representing the name of the pantry
     * @param loc a Location object representing the geographic location of the pantry
     * @param dailyOH a TimeFrame array representing the daily open hours for the pantry
     * @param dailyVN an integer array representing the volunteers needed each day
     * @param dailyVS an integer array representing the current volunteer signups each day
     * @param offersT a boolean indicating whether the pantry offers transportation
     */
    public FoodPantry(String id, String name, Location loc, TimeFrame[] dailyOH,
                      int[] dailyVN, int[] dailyVS, boolean offersT) {
        super(id, name, loc, dailyOH, null, null, offersT);
        this.dailyVolunteersNeeded = (isValidDailyArray(dailyVN)) ? dailyVN : new int[7];
        this.dailyVolunteerSignups = (isValidDailyArray(dailyVS)) ? dailyVS : new int[7];
    }

    /**
     * Overridden signUpVolunteer method
     * @param volunteer a Volunteer object representing the person signing up
     * @param dayName a String representing the day of the week
     * @return true if successfully signed up, false otherwise
     */
    @Override
    public boolean signUpVolunteer(Volunteer volunteer, String dayName) {
        System.out.println("Checking the necessary information for signing up " + volunteer.getFullName()
                + " for helping " + getName() + " on " + dayName);
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1 && dailyVolunteerSignups[dayIndex] < dailyVolunteersNeeded[dayIndex]) {
            dailyVolunteerSignups[dayIndex]++;
            return true;
        }
        return false;
    }

    /**
     * Overridden cancelVolunteerSignup method
     * @param dayName a String representing the day of the week
     */
    @Override
    public void cancelVolunteerSignup(String dayName) {
        System.out.println("Canceling volunteer signup for " + getName() + " on " + dayName);
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1 && dailyVolunteerSignups[dayIndex] > 0) {
            dailyVolunteerSignups[dayIndex]--;
        }
    }

    /**
     * Method to get the number of available volunteer spots for a specific day
     * @param dayName a String representing the day of the week
     * @return the number of volunteer spots still available
     */
    public int dailyVolunteerSpotsLeft(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            return dailyVolunteersNeeded[dayIndex] - dailyVolunteerSignups[dayIndex];
        }
        return 0;
    }

    /**
     * Method to set the number of volunteers needed for a specific day
     * @param volunteers an integer representing the number of volunteers needed
     * @param dayName a String representing the day of the week
     */
    public void setDailyVolunteersNeeded(int volunteers, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1 && volunteers >= 0) {
            dailyVolunteersNeeded[dayIndex] = volunteers;
        }
    }

    /**
     * Method to set the number of volunteer signups for a specific day
     * @param signups an integer representing the current volunteer signups
     * @param dayName a String representing the day of the week
     */
    public void setDailyVolunteerSignups(int signups, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1 && signups >= 0) {
            dailyVolunteerSignups[dayIndex] = signups;
        }
    }

    /**
     * Helper method to validate daily arrays
     * @param array the array to validate
     * @return true if the array is valid, false otherwise
     */
    private boolean isValidDailyArray(int[] array) {
        if (array == null || array.length != 7) return false;
        for (int num : array) {
            if (num < 0) return false;
        }
        return true;
    }

    // Getters for dailyVolunteersNeeded and dailyVolunteerSignups
    public int[] getDailyVolunteersNeeded() {
        return dailyVolunteersNeeded;
    }

    public int[] getDailyVolunteerSignups() {
        return dailyVolunteerSignups;
    }
}
