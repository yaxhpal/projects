package es.oeuvr.model;

import java.util.LinkedList;
import java.util.List;

import es.oeuvr.domain.Artwork;
import es.oeuvr.domain.Exhibition;
import es.oeuvr.domain.Location;
import es.oeuvr.domain.Style;

public class ArtworkModel {

	private static final int cmToInc10k = 3937;

	public Long id;
	public String name;
	public String artBookBio;
	public String yearNote;
	public String origin;
	public String conditionDescription;
	public String medium;
	public int year;
	public String period;
	public Long user;
	public Long artist;
	public Long category;
	public float heightCm;
	public float widthCm;
	public float depthCm;
	public float heightInch;
	public float widthInch;
	public float depthInch;
	public float framedHeightCm;
	public float framedWidthCm;
	public float framedDepthCm;
	public float framedHeightInch;
	public float framedWidthInch;
	public float framedDepthInch;
	public String tagNumber;
	public String editionNo;
	public String importRestriction;
	public String exportRestriction;
	public String price;
	public String vat;
	public String valuation;
	public String valuationDate;
	public String insurance;
	public String source;
	public String commission;
	public String purchasedFrom;
	public String purchasedDate;
	public Long	locationId;
	public String signed;
	public String externalLink;
	public String valuedBy;
	public String importTax;
	public String artistRR;
	
	public List<Long> styles = new LinkedList<Long>();
	public List<ImageModel> images = new LinkedList<ImageModel>();
	public List<Long> exhibitions = new LinkedList<Long>();

	public List<DocumentModel> documents = new LinkedList<DocumentModel>();
	public List<CommentModel> comments = new LinkedList<CommentModel>();
	public List<ProvenanceModel> provenances = new LinkedList<ProvenanceModel>();

	public ArtworkModel(Artwork a) {
		id = a.getId();
		name = a.getName();
		artBookBio = a.getArtBookBio();
		origin = a.getOrigin();
		yearNote = a.getYearNote();
		medium = a.getMedium();
		conditionDescription = a.getConditionDescription();
		year = a.getYear();
		period = a.getPeriod();
		heightCm = (float) a.getHeight() / 100;
		widthCm = (float) a.getWidth() / 100;
		depthCm = (float) a.getDepth() / 100;
		heightInch = (float) a.getHeight() * cmToInc10k / 1000000;
		widthInch = (float) a.getWidth() * cmToInc10k / 1000000;
		depthInch = (float) a.getDepth() * cmToInc10k / 1000000;
		framedHeightCm = (float) a.getFramedHeight() / 100;
		framedWidthCm = (float) a.getFramedWidth() / 100;
		framedDepthCm = (float) a.getFramedDepth() / 100;
		framedHeightInch = (float) a.getFramedHeight() * cmToInc10k / 1000000;
		framedWidthInch = (float) a.getFramedWidth() * cmToInc10k / 1000000;
		framedDepthInch = (float) a.getFramedDepth() * cmToInc10k / 1000000;
		tagNumber = a.getTagNumber();
		signed = a.getSigned();
		editionNo = a.getEditionNo();
		
		externalLink = a.getExternalLink();
		
		Location location = a.getLocation();
		if (location != null) {
			locationId = location.getId();
		}
		
		importRestriction = a.getImportRestriction();
		
		exportRestriction = a.getExportRestriction();
		
		if (a.getCategory() != null) {
			category = a.getCategory().getId();
		}
		
		if (a.getUser() != null) {
			user = a.getUser().getId();
		}
		
		if (a.getArtist() != null) {
			artist = a.getArtist().getId();
		}
		
		for (Style c : a.getStyles()) {
			styles.add(c.getId());
		}
		
		for (Exhibition e : a.getExhibitions()) {
			exhibitions.add(e.getId());
		}
	}

	public void addImage(ImageModel image) {
		this.images.add(image);
	}

	public void addDocument(DocumentModel doc) {
		this.documents.add(doc);
	}

	public void addComment(CommentModel comment) {
		this.comments.add(comment);
	}

	public void addProvenance(ProvenanceModel provenance) {
		this.provenances.add(provenance);
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

		ArtworkModel other = (ArtworkModel) obj;

		if (id == null && other.id != null) {
			return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
