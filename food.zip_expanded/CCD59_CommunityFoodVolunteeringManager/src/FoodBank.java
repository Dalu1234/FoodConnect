public class FoodBank extends CommunityFoodOrg {
	/**
	 * Class FoodBank
	 * @author : Chukwudalu Dumebi-Kachikwu
	 * @created 12/10/2024
	 */

    private double maxCapacity = 500;
    private double[] dailyDonationsNeeded;

    /**
     * Constructor for FoodBank
     * @param id a String representing the unique identifier for the organization
     * @param name a String representing the name of the food bank
     * @param loc a Location object representing the geographic location of the food bank
     * @param dailyHours a TimeFrame array representing the daily open hours
     * @param maxCapacity the maximum capacity of the food bank in pounds
     */
    public FoodBank(String id, String name, Location loc, TimeFrame[] dailyHours, double maxCapacity, double [] dailyDN) {
        super(id, name, loc, dailyHours, null, null, false);
        this.maxCapacity = maxCapacity;
        this.dailyDonationsNeeded = dailyDN;
        for (int i = 0; i < 7; i++) {
            this.dailyDonationsNeeded[i] = maxCapacity;
        }
    }

    // Getter for maxCapacity
    public double getMaxCapacity() {
        return maxCapacity;
    }

    // Setter for maxCapacity
    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Getter for dailyDonationsNeeded
    public double[] getDailyDonationsNeeded() {
        return dailyDonationsNeeded;
    }

    // Method to get the donations needed for a specific day
    public double getDailyDonationsNeeded(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            return dailyDonationsNeeded[dayIndex];
        } else {
            throw new IllegalArgumentException("Invalid day name: " + dayName);
        }
    }

    // Method to set the donations needed for a specific day
    public void setDailyDonationsNeeded(double donations, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            if (donations < 0 || donations > maxCapacity) {
                throw new IllegalArgumentException("Donations must be between 0 and maxCapacity.");
            }
            dailyDonationsNeeded[dayIndex] = donations;
        } else {
            throw new IllegalArgumentException("Invalid day name: " + dayName);
        }
    }

    // Override signUpVolunteer method
    @Override
    public boolean signUpVolunteer(Volunteer volunteer, String dayName) {
        System.out.println("Checking the necessary information for signing up " + volunteer.getFullName()
                + " for helping " + getName() + " on " + dayName);

        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            double donation = volunteer.getDonation();
            if (donation <= dailyDonationsNeeded[dayIndex]) {
                dailyDonationsNeeded[dayIndex] -= donation;
                return true;
            } else {
                System.out.println("Not enough space for donations on " + dayName);
                return false;
            }
        } else {
            throw new IllegalArgumentException("Invalid day name: " + dayName);
        }
    }

    // Overloaded cancelVolunteerSignup method
    public void cancelVolunteerSignup(String dayName, double donation) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex != -1) {
            dailyDonationsNeeded[dayIndex] += donation;
            if (dailyDonationsNeeded[dayIndex] > maxCapacity) {
                dailyDonationsNeeded[dayIndex] = maxCapacity;
            }
        } else {
            throw new IllegalArgumentException("Invalid day name: " + dayName);
        }
    }

 
}
