package com.techletsolutions.hulk.model;

import java.util.Set;
import java.util.TreeSet;

import com.techletsolutions.db.EntityManager;

public class State extends CnDEntity {

	protected Set<City> cities;

	public State (String name, Long parent) {
		this.id = 0L;
		this.group_name = "US_STATE";
		this.name = name;
		this.description = "State of "+name;
		this.parent = parent;
		this.option1 = null;
		this.option2 = null;
		this.deleted = 'N';
		cities = new TreeSet<City>();
	}

	public  Set<City> getAllCities() {
		return cities;
	}

	public void addCity(City city) {
		City thisCity = getCity(city.getName());
		if (thisCity == null) {
			cities.add(city);
		} else {
			for (ZipCode zipCode : city.getAllZipCodes()) {
				thisCity.addZipCode(zipCode);
			}
		}
	}

	public City getCity(String cityName) {
		for (City city : cities) {
			if (city.getName().equalsIgnoreCase(cityName)){
				return city;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.toString()+"\n");
		for (City city : cities) {
			stringBuilder.append("\t\t"+city.toString()+"\n");
		}
		return stringBuilder.toString();
	}

	@Override
	public Long save(EntityManager em) {
		Long stateId = super.save(em);
		if (stateId > 0L) {
			for (City city : cities) {
				city.setGroup_name("US_"+name.substring(0,3).toUpperCase()+"_CITY");
				city.setParent(stateId);
				city.save(em);
			}
		}
		return stateId;
	}
}
