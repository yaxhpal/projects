package es.oeuvr.model;

import es.oeuvr.domain.Exhibition;

public class ExhibitionModel {
	public ExhibitionModel(Exhibition e) {
		id = e.getId();
		date = e.getDate();
		name = e.getName();
	}

	public Long id;
	public String date;
	public String name;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExhibitionModel other = (ExhibitionModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
