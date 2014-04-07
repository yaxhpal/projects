package es.oeuvr.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.lang.Override;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Cacheable
public class PurchaseHistory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@NotNull
	private String price;

	@Column
	private String vat;

	@Column
	private String valuation;

	@Column
	private String valuationDate;

	@Column
	private String valuedBy;

	@Column
	private String insurance;

	@Column
	private String source;

	@Column
	private String commission;

	@Column
	private String purchasedFrom;

	@Column
	private String artistRR;

	@Column
	private String importTax;

	@Column
	@NotNull
	private String purchaseDate;

	@ManyToOne
	private Artwork artwork;

	@ManyToOne
	private User owner;

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
			return id.equals(((PurchaseHistory) that).id);
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

	public String getPrice() {
		return this.price;
	}

	public void setPrice(final String price) {
		this.price = price;
	}

	public String getValuation() {
		return this.valuation;
	}

	public void setValuation(final String valuation) {
		this.valuation = valuation;
	}

	public String getInsurance() {
		return this.insurance;
	}

	public void setInsurance(final String insurance) {
		this.insurance = insurance;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getPurchasedFrom() {
		return purchasedFrom;
	}

	public void setPurchasedFrom(String purchasedFrom) {
		this.purchasedFrom = purchasedFrom;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getValuedBy() {
		return valuedBy;
	}

	public void setValuedBy(String valuedBy) {
		this.valuedBy = valuedBy;
	}

	public String getArtistRR() {
		return artistRR;
	}

	public void setArtistRR(String artistRR) {
		this.artistRR = artistRR;
	}

	public String getImportTax() {
		return importTax;
	}

	public void setImportTax(String importTax) {
		this.importTax = importTax;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (price != null && !price.trim().isEmpty())
			result += "price: " + price;
		if (vat != null && !vat.trim().isEmpty())
			result += ", vat: " + vat;
		if (valuation != null && !valuation.trim().isEmpty())
			result += ", valuation: " + valuation;
		if (valuationDate != null && !valuationDate.trim().isEmpty())
			result += ", valuationDate: " + valuationDate;
		if (insurance != null && !insurance.trim().isEmpty())
			result += ", insurance: " + insurance;
		if (source != null && !source.trim().isEmpty())
			result += ", source: " + source;
		if (commission != null && !commission.trim().isEmpty())
			result += ", commission: " + commission;
		return result;
	}
}