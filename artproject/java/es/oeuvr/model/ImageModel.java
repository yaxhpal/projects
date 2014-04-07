package es.oeuvr.model;

import es.oeuvr.domain.Image;

public class ImageModel {

	public ImageModel(Image i) {
		id = i.getId();
		width = i.getWidth();
		height = i.getHeight();
	}

	public Long id;
	public int width;
	public int height;

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
		
		ImageModel other = (ImageModel) obj;
		
		if (id == null && other.id != null) {
			return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
