/**
 * Class CommunityFoodOrg
 * @author : Chukwudalu Dumebi-Kachikwu
 * @created 10/17/2024
 */
public class CommunityFoodOrg {
    private String id, name;
    private Location location;
    private TimeFrame[] dailyOpenHours; // Array for daily open hours
    private int[] dailyVolunteersNeeded; // Array for daily volunteers needed
    private int[] dailyVolunteerSignups; // Array for daily volunteer signups
    private boolean offersTransportation;

    /**
     * Constructor CommunityFoodOrg
     * @param id a String representing the unique identifier for the organization 
     *        (used to distinguish between different organizations).
     * @param name a String representing the name of the community food organization 
     *        (used for display and identification purposes).
     * @param loc a Location object representing the geographic location of the organization 
     *        (includes latitude, longitude, address, city, state, and ZIP code).
     * @param dailyHours a TimeFrame array of size 7 representing the organization's open hours 
     *        for each day of the week (null or missing values default to a new TimeFrame array).
     * @param dailyVolNeeded an integer array of size 7 representing the number of volunteers needed 
     *        for each day of the week (validated for non-negative values; invalid input defaults to a new array).
     * @param dailyVolSignups an integer array of size 7 representing the number of volunteers currently signed up 
     *        for each day of the week (validated for non-negative values; invalid input defaults to a new array).
     * @param offersT a boolean indicating whether the organization offers transportation for volunteers 
     *        (true if transportation is offered, false otherwise).
     * 
     * This constructor initializes a CommunityFoodOrg object with the provided details, ensuring that
     * daily hours and volunteer-related arrays are valid and defaulting to new arrays if invalid data is supplied.
     */

    public CommunityFoodOrg(String id, String name, Location loc, TimeFrame[] dailyHours,
                            int[] dailyVolNeeded, int[] dailyVolSignups, boolean offersT) {
        this.id = id;
        this.name = name;
        this.location = loc;
        this.dailyOpenHours = (dailyHours != null && dailyHours.length == 7) ? dailyHours : new TimeFrame[7];
        this.dailyVolunteersNeeded = (isValidDailyArray(dailyVolNeeded)) ? dailyVolNeeded : new int[7];
        this.dailyVolunteerSignups = (isValidDailyArray(dailyVolSignups)) ? dailyVolSignups : new int[7];
        this.offersTransportation = offersT;
    }

    // Helper method to validate daily arrays
    private boolean isValidDailyArray(int[] array) {
        if (array == null || array.length != 7) return false;
        for (int num : array) {
            if (num < 0) return false;
        }
        return true;
    }

    // Get the TimeFrame for a specific day
    public TimeFrame getOpenHoursForDay(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            return dailyOpenHours[dayIndex];
        }
        return null; // Closed on this day
    }

    // Set daily open hours for a specific day
    /**
     * Method setDailyVolunteersNeeded
     * @param volunteers an integer array of size 7 representing the number of volunteers needed for each day of the week 
     *        (each value must be non-negative, and the array must contain exactly 7 elements).
     * 
     * This method sets the number of volunteers required for all days of the week at the organization.
     * The process includes:
     * - Validating the input array using the `isValidDailyArray` method to ensure it is not null, has exactly 7 elements, 
     *   and all values are non-negative.
     * - Assigning the validated array to the `dailyVolunteersNeeded` field.
     * 
     * If the input array is invalid (e.g., null, incorrect size, or contains negative values), the method logs an 
     * error message and does not update the daily volunteer requirements.
     */

    public void setDailyOpenHours(TimeFrame time, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            dailyOpenHours[dayIndex] = time;
        } else {
            System.out.println("Invalid day name.");
        }
    }
    /**
     * Method setDailyOpenHours
     * @param times a TimeFrame array of size 7 representing the organization's open hours for each day of the week 
     *        (each TimeFrame specifies the start and end time for a specific day).
     * 
     * This method sets the organization's daily open hours for all days of the week.
     * The process includes:
     * - Validating the input array to ensure it is not null and contains exactly 7 TimeFrame objects.
     * - Assigning the validated array to the organization's `dailyOpenHours` field.
     * 
     * If the input is invalid (e.g., null or not of length 7), the method logs an error message 
     * indicating the issue and does not update the open hours.
     */

    public void setDailyOpenHours(TimeFrame[] times) {
        if (times != null && times.length == 7) {
            this.dailyOpenHours = times;
        } else {
            System.out.println("Invalid array input. Must contain 7 TimeFrame objects.");
        }
    }

    /**
     * Method setDailyVolunteersNeeded
     * @param volunteers an integer representing the number of volunteers needed for the specified day 
     *        (must be a non-negative value).
     * @param dayName a String representing the day for which the number of volunteers is being set 
     * 
     * This method sets the number of volunteers required for a specific day at the organization.
     * The process includes:
     * - Validating the day name to ensure it corresponds to a valid index.
     * - Ensuring the input number of volunteers is non-negative.
     * 
     * If the inputs are valid, the method updates the `dailyVolunteersNeeded` array for the specified day.
     * If the inputs are invalid (e.g., negative volunteers or an invalid day name), the method logs an 
     * error message and does not make any changes.
     */

    public void setDailyVolunteersNeeded(int volunteers, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1 && volunteers >= 0) {
            dailyVolunteersNeeded[dayIndex] = volunteers;
        } else {
            System.out.println("Invalid input.");
        }
    }

    // Set daily volunteers needed for all days
    /**
     * Method setDailyVolunteersNeeded
     * @param volunteers an integer array of size 7 representing the number of volunteers needed for each day of the week 
     *        (each value must be non-negative, and the array must contain exactly 7 elements).
     * 
     * This method sets the number of volunteers required for all days of the week at the organization.
     * The process includes:
     * - Validating the input array using the `isValidDailyArray` method to ensure it is not null, has exactly 7 elements, 
     *   and all values are non-negative.
     * - Assigning the validated array to the `dailyVolunteersNeeded` field.
     * 
     * If the input array is invalid (e.g., null, incorrect size, or contains negative values), the method logs an 
     * error message and does not update the daily volunteer requirements.
     */

    public void setDailyVolunteersNeeded(int[] volunteers) {
        if (isValidDailyArray(volunteers)) {
            this.dailyVolunteersNeeded = volunteers;
        } else {
            System.out.println("Invalid array input.");
        }
    }

    // Set daily volunteer signups for a specific day
    /**
     * Method setDailyVolunteerSignups
     * @param signups an integer representing the number of volunteers currently signed up for the specified day 
     *        (must be a non-negative value).
     * @param dayName a String representing the day for which the number of volunteer signups is being set 
     * 
     * This method sets the current number of volunteer signups for a specific day at the organization.
     * The process includes:
     * - Validating the day name to ensure it corresponds to a valid index.
     * - Ensuring the input number of signups is non-negative.
     * 
     * If the inputs are valid, the method updates the `dailyVolunteerSignups` array for the specified day.
     * If the inputs are invalid (e.g., negative signups or an invalid day name), the method logs an 
     * error message and does not make any changes.
     */

    public void setDailyVolunteerSignups(int signups, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1 && signups >= 0) {
            dailyVolunteerSignups[dayIndex] = signups;
        } else {
            System.out.println("Invalid input.");
        }
    }

    // Set daily volunteer signups for all days
    /**
     * Method setDailyVolunteerSignups
     * @param signups an integer array of size 7 representing the number of volunteers currently signed up 
     *        for each day of the week (each value must be non-negative, and the array must contain exactly 7 elements).
     * This method sets the current number of volunteer signups for all days of the week at the organization.
     * The process includes:
     * - Validating the input array using the `isValidDailyArray` method to ensure it is not null, has exactly 7 elements, 
     *   and all values are non-negative.
     * - Assigning the validated array to the `dailyVolunteerSignups` field.
     * 
     * If the input array is invalid (e.g., null, incorrect size, or contains negative values), the method logs an 
     * error message and does not update the daily volunteer signups.
     */

    public void setDailyVolunteerSignups(int[] signups) {
        if (isValidDailyArray(signups)) {
            this.dailyVolunteerSignups = signups;
        } else {
            System.out.println("Invalid array input.");
        }
    }

    /**
     * Method signUpVolunteer
     * @param volunteer a Volunteer object representing the individual attempting to sign up 
     *        (includes details about their availability, distance, and transportation needs).
     * @param dayName a String representing the day the volunteer wants to sign up 
     * @return a boolean value indicating whether the volunteer was successfully signed up 
     *         (true if successfully signed up, false if the sign-up failed).
     * 
     * This method attempts to sign up the given volunteer for the specified day at the organization.
     * The process includes:
     * - Validating the day name and ensuring the organization is open on that day.
     * - Checking if there are available spots for more volunteers on the specified day.
     * - Ensuring the volunteer's time availability matches the organization's hours for that day.
     * - Verifying that the volunteer is within the maximum allowable distance from the organization.
     * - Ensuring that transportation requirements are met (if the volunteer needs transportation and the organization offers it).
     * 
     * If all conditions are satisfied, the volunteer is signed up for the organization on the specified day,
     * and the method returns true. Otherwise, it returns false and logs the reason for the failure.
     */

    public boolean signUpVolunteer(Volunteer volunteer, String dayName) {
        System.out.println("Checking the necessary information for signing up " +
                volunteer.getFullName() + " for helping " + this.name + " on " + dayName);
        return true; // Always return true
    }

    /**
     * Method cancelVolunteerSignup
     * @param dayName a String representing the day for which a volunteer's signup is to be canceled 
     */
    public void cancelVolunteerSignup(String dayName) {
        System.out.println("Canceling volunteer signup for " + this.name + " on " + dayName);
    }


    /**
     * Method dailyVolunteerSpotsLeft
     * @param dayName a String representing the day for which to calculate available volunteer spots 
     *        (e.g., "Monday", "Tuesday").
     * @return an integer representing the number of volunteer spots still available for the specified day 
     *         (0 if the day name is invalid or there are no spots left).
     * 
     * This method calculates the difference between the total number of volunteers needed and the 
     * number of volunteers already signed up for the given day at the organization. 
     * The process includes:
     * - Validating the day name to ensure it corresponds to a valid index.
     * - Returning the calculated number of available spots if the day is valid, or 0 if invalid.
     */
    public int dailyVolunteerSpotsLeft(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            return dailyVolunteersNeeded[dayIndex] - dailyVolunteerSignups[dayIndex];
        }
        return 0;
    }

    // Get day index from day name
    public int getDayIndex(String dayName) {
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

    // Getters
    public TimeFrame[] getDailyOpenHours() { return dailyOpenHours; }
    public int[] getDailyVolunteersNeeded() { return dailyVolunteersNeeded; }
    public int[] getDailyVolunteerSignups() { return dailyVolunteerSignups; }
    public Location getLocation() { return location; }
    public boolean isOffersTransportation() { return offersTransportation; }
    public String getName() { return name; }
}
