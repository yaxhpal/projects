package com.techletsolutions.hulk.model;

import java.util.Set;
import java.util.TreeSet;

import com.techletsolutions.db.EntityManager;

public class City extends CnDEntity {

	protected Set<ZipCode> zipcodes;
	
	public City (String name, Long parent) {
		this.id = 0L;
		this.group_name = "City";
		this.name = name;
		this.description = name;
		this.parent = parent;
		this.option1 = null;
		this.option2 = null;
		this.deleted = 'N';
		zipcodes = new TreeSet<ZipCode>();
	}
	
	public Set<ZipCode> getAllZipCodes() {
		return zipcodes;
	}
	
	public void addZipCode(ZipCode zipCode) {
		zipcodes.add(zipCode);		
	}
	
	public ZipCode getZipCode(String zipCodeName) {
		for (ZipCode zipCode : zipcodes) {
			if (zipCode.getName().equalsIgnoreCase(zipCodeName)){
				return zipCode;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.toString()+": ");
		for (ZipCode zipCode : zipcodes) {
			stringBuilder.append(zipCode.toString()+", ");
		}
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
	
	@Override
	public Long save(EntityManager em) {
		Long cityId = super.save(em);
		if (cityId > 0L) {
			for (ZipCode zipCode : zipcodes) {
				zipCode.setParent(cityId);
				zipCode.save(em);
			}
		}
		return cityId;
	}
}
