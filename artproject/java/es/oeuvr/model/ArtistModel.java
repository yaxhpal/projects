package es.oeuvr.model;

import es.oeuvr.domain.Artist;
public class ArtistModel {
	public ArtistModel(Artist a) {
		id = a.getId();
		firstName = a.getFirstName();
		lastName = a.getLastName();
		birthPlace = a.getBirthPlace();
		about = a.getAbout();
		yearOfBirth = a.getYearOfBirth();
		yearOfDeath = a.getYearOfDeath();
		hasPhoto = a.isHasPhoto();
	}

	public Long id;
	public String firstName;
	public String lastName;
	public String birthPlace;
	public String about;
	public int yearOfBirth;
	public int yearOfDeath;
	public boolean hasPhoto;
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
		ArtistModel other = (ArtistModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
