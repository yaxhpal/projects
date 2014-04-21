package com.techletsolutions.hulk.model;

public class ZipCode extends CnDEntity {

	public ZipCode (String name, Long parent) {
		this.id = 0L;
		this.group_name = "Zipcode";
		this.name = name;
		this.description = name;
		this.parent = parent;
		this.option1 = null;
		this.option2 = null;
		this.deleted = 'N';
	}
}
