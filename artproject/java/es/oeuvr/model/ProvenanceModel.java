package es.oeuvr.model;

import es.oeuvr.domain.Provenance;

public class ProvenanceModel {
	
	public Long id;
	public String date;
	public String owner;
	
	public ProvenanceModel(Provenance p) {
		this.id = p.getId();
		this.date = p.getDate();
		this.owner = p.getOwner();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}

		ProvenanceModel other = (ProvenanceModel) obj;

		if (id == null && other.id != null) {
			return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
