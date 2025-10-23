package com.gym.GoldenGym.utils;

public class LocationsUtils {
    public static boolean isLocationWithinRadius(double latitude1, double longitude1,
            double latitude2, double longitude2,
            double radiusInKm) {
        // Radius of the Earth in kilometers
        final double EARTH_RADIUS = 6371.0;

        // Convert degrees to radians
        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        // Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance between the two points
        double distance = EARTH_RADIUS * c;

        // Check if within radius (assuming radius is in kilometers)
        return distance <= radiusInKm;
    }

}
