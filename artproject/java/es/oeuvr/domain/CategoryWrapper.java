package es.oeuvr.domain;

import java.io.Serializable;
import java.util.List;

public class CategoryWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private List<CategoryWrapper> subCategory;

	public void setSubCategory(List<CategoryWrapper> subCategory) {
		this.subCategory = subCategory;
	}

	public List<CategoryWrapper> getSubCategory() {
		return this.subCategory;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}