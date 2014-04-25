package com.techletsolutions.calc;

public class DistanceCalculator {

	
	/**
	 * Calculates the distance in km between two lat/long points using the haver-sine formula
	 */
	public static double haversine(double lat1, double lng1, double lat2, double lng2) {
	    int r = 6371; // average radius of the earth in km
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lng2 - lng1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double d = r * c;
	    return d;
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(haversine(41.6409, -73.209685, 41.520312, -73.363875) + " km");
		
	}
}
/*06751",41.6409,-73.209685,"Bethlehem","CT","Litchfield"
"06752",41.520312,-73.363875,"Bridgewater","CT","Litchfield"*/