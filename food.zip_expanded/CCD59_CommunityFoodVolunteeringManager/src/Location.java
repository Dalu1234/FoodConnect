	    /**
	    * Class Location
	    * author: Chukwudalu Dumebi-Kachikwu
	    * created: 10/17/2024
	    */

public class Location {
    public static final double EARTH_RADIUS = 3958.8; // in miles
    private double latitude, longitude;
    private String address, city, state, zipCode;

    // Constructor
    public Location(double lat, double lon, String addr, String city, String state, String zip) {
        if (lat >= -90 && lat <= 90) this.latitude = lat; else this.latitude = 0;
        if (lon >= -180 && lon <= 180) this.longitude = lon; else this.longitude = 0;
        this.address = addr;
        this.city = city;
        this.state = state;
        this.zipCode = zip;
    }

    // Method to calculate distance using Haversine formula
    public double distance(Location loc) {
        double latDistance = Math.toRadians(loc.latitude - this.latitude);
        double lonDistance = Math.toRadians(loc.longitude - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                   Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(loc.latitude)) *
                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
