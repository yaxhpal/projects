package es.oeuvr.model;

import es.oeuvr.domain.Document;


public class DocumentModel {
	public DocumentModel(Document d) {
		id=d.getId();
		name = d.getName();
		file = d.getFile();
		type = d.getType();
	}

	public Long id;
	public String name;
	public String file;
	public String type;

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
		DocumentModel other = (DocumentModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
