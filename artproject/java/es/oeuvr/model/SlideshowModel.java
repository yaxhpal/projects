package es.oeuvr.model;

import java.util.LinkedList;
import java.util.List;

import es.oeuvr.domain.Artwork;
import es.oeuvr.domain.Slideshow;
public class SlideshowModel {
	public SlideshowModel () {}
	public SlideshowModel (Slideshow s) {
		id=s.getId();
		name = s.getName();
		userId = s.getUser().getId();
		for (Artwork a : s.getArtworks()) {
			artworks.add(a.getId());
		}
	}
	public Long id;
	public String name;
	public Long userId;
	public List<Long> artworks = new LinkedList<Long>();

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
		SlideshowModel other = (SlideshowModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}