/**
 * Class Volunteer
 * @author Chukwudalu Dumebi-Kachikwu
 * @created 10/17/2024
 */
public class Volunteer {
    private String id, fullName, dayAvailable, reasonForFailedConnect;
    private int age;
    private Location location;
    private TimeFrame timeAvailable;
    private double distanceAvailable;
    private boolean needsTransportation;
    private CommunityFoodOrg orgVolunteering;
    private double donation; // Added attribute to represent donation in pounds

    // Constructor
    public Volunteer(String id, String name, int age, Location loc, String dayAv, TimeFrame timeAv,
                     double distAv, boolean needTrans, double donation) {
        this.id = id;
        this.fullName = name;
        this.age = (age >= 18 && age <= 100) ? age : 18; // default 18
        this.location = loc;
        this.dayAvailable = dayAv;
        this.timeAvailable = timeAv;
        this.distanceAvailable = distAv;
        this.needsTransportation = needTrans;
        this.orgVolunteering = null;
        this.donation = Math.max(0, donation); // Ensure donation is non-negative
    }

    // Setters 
    public void setReasonForFailedConnect(String reason) {
        this.reasonForFailedConnect = reason; 
    }

    public void setAge(int age) {
        this.age = (age >= 18 && age <= 100) ? age : 18; 
    }

    public void setDayAvailable(String dayAvailable) {
        this.dayAvailable = dayAvailable; 
    }

    public void setLocation(Location location) {
        this.location = location; 
    }

    public void setTimeAvailable(TimeFrame timeAvailable) {
        this.timeAvailable = timeAvailable; 
    }

    public void setDistanceAvailable(double distanceAvailable) {
        this.distanceAvailable = distanceAvailable; 
    }

    public void setNeedsTransportation(boolean needsTransportation) {
        this.needsTransportation = needsTransportation; 
    }

    public void setDonation(double donation) {
        this.donation = Math.max(0, donation); // Ensure donation is non-negative
    }

    // Get the volunteer's time availability
    public TimeFrame getTimeAvailable() {
        return this.timeAvailable;
    }

    // Get volunteer's location
    public Location getLocation() {
        return this.location;
    }

    // Get maximum distance the volunteer is willing to travel
    public double getDistanceAvailable() {
        return this.distanceAvailable;
    }

    // Check if volunteer needs transportation
    public boolean needsTransportation() {
        return this.needsTransportation;
    }

    // Get donation amount
    public double getDonation() {
        return this.donation;
    }

    // Method to sign up for a community food organization
    public void signUp(CommunityFoodOrg org) {
        if (org instanceof FoodBank && this.donation > 0) {
            if (((FoodBank) org).signUpVolunteer(this, this.dayAvailable)) {
                this.orgVolunteering = org;
            }
        } else if (org instanceof FoodPantry && this.donation == 0) {
            if (org.signUpVolunteer(this, this.dayAvailable)) {
                this.orgVolunteering = org;
            }
        } else {
            setReasonForFailedConnect("Mismatch between volunteer interest and organization type.");
        }
    }

    // Method to cancel the current sign-up
    public void cancelSignup() {
        if (this.orgVolunteering != null) {
            if (this.orgVolunteering instanceof FoodBank && this.donation > 0) {
                ((FoodBank) this.orgVolunteering).cancelVolunteerSignup(this.dayAvailable, this.donation);
            } else {
                this.orgVolunteering.cancelVolunteerSignup(this.dayAvailable);
            }
            this.orgVolunteering = null;
        }
    }

    // Check if volunteer matches an organization
    public boolean orgMatch(CommunityFoodOrg org) {
        if (this.donation > 0 && !(org instanceof FoodBank)) {
            setReasonForFailedConnect("Volunteer wants to donate, but organization is not a Food Bank.");
            return false;
        }

        if (this.donation == 0 && !(org instanceof FoodPantry)) {
            setReasonForFailedConnect("Volunteer wants to help with packing/distribution, but organization is not a Food Pantry.");
            return false;
        }

        TimeFrame orgTime = org.getOpenHoursForDay(this.dayAvailable);

        if (orgTime == null) {
            setReasonForFailedConnect("Organization is closed on the volunteer's available day.");
            return false;
        }

        if (this.needsTransportation && !org.isOffersTransportation()) {
            setReasonForFailedConnect("Organization does not offer transport, but the volunteer needs transport.");
            return false;
        }

        if (!this.timeAvailable.timeFrameMatch(orgTime)) {
            setReasonForFailedConnect("Time availability does not match.");
            return false;
        }

        if (this.location.distance(org.getLocation()) > this.distanceAvailable) {
            setReasonForFailedConnect("Location is not within reachable distance.");
            return false;
        }

        return true;
    }

    // Getters
    public String getFullName() {
        return this.fullName;
    }

    public String getDayAvailable() {
        return this.dayAvailable;
    }

    public CommunityFoodOrg getOrgVolunteering() {
        return this.orgVolunteering;
    }

    public String getReasonForFailedConnect() {
        return reasonForFailedConnect; 
    }
}
