package es.oeuvr.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import java.lang.Override;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.Digits;

@Entity
@XmlRootElement
@Cacheable
public class Artist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@NotNull
	@Size(min = 1, max = 100)
	private String firstName;

	@Column
	@NotNull
	@Size(min = 1, max = 100)
	private String lastName;

	@Column
	private String birthPlace;

	@Column(columnDefinition = "TEXT")
	private String about;

	@Column
	@Digits(integer = 4, fraction = 0)
	private int yearOfBirth;

	@Column
	@Digits(integer = 4, fraction = 0)
	private int yearOfDeath;

	@Column
	private boolean hasPhoto;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Movement> movements = new HashSet<Movement>();

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Artist) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(final String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(final String about) {
		this.about = about;
	}

	public int getYearOfBirth() {
		return this.yearOfBirth;
	}

	public void setYearOfBirth(final int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public int getYearOfDeath() {
		return this.yearOfDeath;
	}

	public void setYearOfDeath(final int yearOfDeath) {
		this.yearOfDeath = yearOfDeath;
	}

	public boolean isHasPhoto() {
		return this.hasPhoto;
	}

	public void setHasPhoto(final boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (firstName != null && !firstName.trim().isEmpty())
			result += "firstName: " + firstName;
		if (lastName != null && !lastName.trim().isEmpty())
			result += ", lastName: " + lastName;
		if (birthPlace != null && !birthPlace.trim().isEmpty())
			result += ", country: " + birthPlace;
		if (about != null && !about.trim().isEmpty())
			result += ", about: " + about;
		result += ", yearOfBirth: " + yearOfBirth;
		result += ", yearOfDeath: " + yearOfDeath;
		result += ", hasPhoto: " + hasPhoto;
		return result;
	}
}