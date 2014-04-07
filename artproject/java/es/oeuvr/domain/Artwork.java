package es.oeuvr.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@Cacheable

public class Artwork implements Serializable {

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
	private String name;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String artBookBio;

	@Column
	@Digits(integer = 4, fraction = 0)
	private int year;

	@Column
	private String yearNote;

	@Column
	private String period;

	@Column
	private String origin;

	@Column
	private String medium;

	@Column
	private String signed;
	
	@Column
	private String externalLink;
	
	@Column
	private int height;

	@Column
	private int width;

	@Column
	private int depth;

	@Column
	private int framedHeight;

	@Column
	private int framedWidth;

	@Column
	private int framedDepth;

	@Column
	private String conditionDescription;

	@Column
	private String importRestriction;

	@Column
	private String exportRestriction;

	@Column
	private String tagNumber;

	@Column
	private String editionNo;

	@OneToOne
	private Category category;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Category> categories = new HashSet<Category>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Exhibition> exhibitions = new HashSet<Exhibition>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Style> styles = new HashSet<Style>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Movement> movements = new HashSet<Movement>();

	@ManyToOne
	private Artist artist;

	@ManyToOne
	private User user;

	@ManyToOne
	private Location location;

	public String getExternalLink() {
		return externalLink;
	}

	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getFramedHeight() {
		return this.framedHeight;
	}

	public void setFramedHeight(final int framedHeight) {
		this.framedHeight = framedHeight;
	}

	public int getFramedWidth() {
		return this.framedWidth;
	}

	public void setFramedWidth(final int framedWidth) {
		this.framedWidth = framedWidth;
	}

	public int getFramedDepth() {
		return this.framedDepth;
	}

	public void setFramedDepth(final int framedDepth) {
		this.framedDepth = framedDepth;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@XmlTransient
	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getArtBookBio() {
		return this.artBookBio;
	}

	public void setArtBookBio(final String artBookBio) {
		this.artBookBio = artBookBio;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public String getPeriod() {
		return this.period;
	}

	public void setPeriod(final String period) {
		this.period = period;
	}

	public Set<Style> getStyles() {
		return this.styles;
	}

	public void setStyles(final Set<Style> styles) {
		this.styles = styles;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Artist getArtist() {
		return this.artist;
	}

	public void setArtist(final Artist artist) {
		this.artist = artist;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getYearNote() {
		return yearNote;
	}

	public void setYearNote(String yearNote) {
		this.yearNote = yearNote;
	}

	public Set<Exhibition> getExhibitions() {
		return exhibitions;
	}

	public void setExhibitions(Set<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}

	public String getImportRestriction() {
		return importRestriction;
	}

	public void setImportRestriction(String importRestriction) {
		this.importRestriction = importRestriction;
	}

	public String getExportRestriction() {
		return exportRestriction;
	}

	public void setExportRestriction(String exportRestriction) {
		this.exportRestriction = exportRestriction;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

	public Set<Movement> getMovements() {
		return movements;
	}

	public void setMovements(Set<Movement> movements) {
		this.movements = movements;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

	public String getEditionNo() {
		return editionNo;
	}

	public void setEditionNo(String editionNo) {
		this.editionNo = editionNo;
	}
	
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
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
			return id.equals(((Artwork) that).id);
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
	
	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (id != null)
			result += "id: " + id;
		if (name != null && !name.trim().isEmpty())
			result += ", name: " + name;
		if (artBookBio != null && !artBookBio.trim().isEmpty())
			result += ", artBookBio: " + artBookBio;
		if (period != null && !period.trim().isEmpty())
			result += ", period: " + period;
		result += ", year: " + year;
		return result;
	}
}