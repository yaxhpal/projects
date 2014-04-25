package com.techletsolutions.hulk.model;

import com.techletsolutions.db.EntityManager;

public class ZipInfo {
	public String zipcode;
	public String county;
	public String city;
	public String state;
	public String statecode;
	public float  latitude;
	public float  longitude;

	public void saveMe(EntityManager em) {
		if (em != null) {
			em.saveZipInfo(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Zip: ");
		result.append(zipcode);
		result.append(", ");

		result.append("Country: ");
		result.append(county);
		result.append(", ");

		result.append("City: ");
		result.append(city);
		result.append(", ");

		result.append("State: ");
		result.append(state);
		result.append(", ");

		result.append("StateCode: ");
		result.append(statecode);
		result.append(", ");

		result.append("lat: ");
		result.append(latitude);
		result.append(", ");

		result.append("long: ");
		result.append(longitude);
		result.append(", ");

		return result.toString();
	}
}
