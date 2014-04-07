package es.oeuvr.model;

import java.util.LinkedList;
import java.util.List;

import es.oeuvr.domain.Artwork;
import es.oeuvr.domain.Slideshow;
import es.oeuvr.domain.Tag;
public class TagModel {
	public TagModel () {}
	public TagModel (Tag t) {
		id = t.getId();
		name = t.getName();
		userId = t.getUser().getId();
		for (Artwork a : t.getArtworks()) {
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
		TagModel other = (TagModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
