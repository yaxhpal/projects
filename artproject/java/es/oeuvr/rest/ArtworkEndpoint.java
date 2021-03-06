package es.oeuvr.rest;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.oeuvr.domain.Artwork;
import es.oeuvr.domain.Comment;
import es.oeuvr.domain.Document;
import es.oeuvr.domain.Image;
import es.oeuvr.domain.Provenance;
import es.oeuvr.domain.PurchaseHistory;
import es.oeuvr.model.ArtworkModel;
import es.oeuvr.model.CommentModel;
import es.oeuvr.model.DocumentModel;
import es.oeuvr.model.ImageModel;
import es.oeuvr.model.ProvenanceModel;
import es.oeuvr.service.AppService;

@Stateless
@Path("/artworks")
public class ArtworkEndpoint {
	@PersistenceContext(unitName = "oeuvres-pu")
	private EntityManager em;

	@EJB
	private AppService appService;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response create(Artwork entity) {
		try {
			em.persist(entity);
		} catch (ConstraintViolationException e) {

		}
		ArtworkModel newNodel = getArtWorkModelFromArtWork(entity);
		return Response.ok(newNodel).status(Status.CREATED).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Artwork entity = em.find(Artwork.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		String query = "SELECT a FROM Artwork a LEFT JOIN FETCH a.category LEFT JOIN "
				     + "FETCH a.user LEFT JOIN FETCH a.artist WHERE a.id = :entityId";
		
		TypedQuery<Artwork> findByIdQuery = em.createQuery(query, Artwork.class);
		findByIdQuery.setParameter("entityId", id);
		
		Artwork entity = findByIdQuery.getSingleResult();
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		ArtworkModel model = getArtWorkModelFromArtWork(entity);
		return Response.ok(model).build();
	}
	
	@GET
	@Produces("application/json")
	public List<ArtworkModel> listAll(
			@DefaultValue("0")   @QueryParam("page") int page,
			@DefaultValue("10")  @QueryParam("per-page") int per_page,
			@DefaultValue("ASC") @QueryParam("order") String order,
			@DefaultValue("id")  @QueryParam("sort") List<String> orderBy) {

		String query = "SELECT a FROM Artwork a LEFT JOIN FETCH a.category LEFT JOIN FETCH  a.user LEFT JOIN FETCH a.artist ORDER BY ";
		
		for (String field : orderBy) {
			query += "a."+field + " ";
		}
		
		query += order;

		TypedQuery<Artwork> artworkQuery = em.createQuery(query, Artwork.class);
		
		if (page > 0) {
			int offset = (page - 1) * per_page;
			artworkQuery.setFirstResult(offset);
			artworkQuery.setMaxResults(per_page);
		}
		
		final List<Artwork> artworks = artworkQuery.getResultList();

		List<ArtworkModel> artworkModels = new LinkedList<ArtworkModel>();
		
		for (Artwork artwork : artworks) {
			artworkModels.add(getArtWorkModelFromArtWork(artwork));
		}
		
		return artworkModels;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Artwork entity) {
		entity.setId(id);
		entity = em.merge(entity);
		return Response.noContent().build();
	}
	
	public List<ArtworkModel> findArtworksByArtistId(Long aid) {
		String query = "SELECT a FROM Artwork a where a.artist.id = :aid";
		TypedQuery<Artwork> artworkQuery = em.createQuery(query, Artwork.class);
		artworkQuery.setParameter("aid", aid);
		List<Artwork> artworks = artworkQuery.getResultList();
		List<ArtworkModel> artworkModels = new LinkedList<ArtworkModel>();
		for (Artwork artwork : artworks) {
			artworkModels.add(getArtWorkModelFromArtWork(artwork));
		}
		return artworkModels;
	}
	
	private ArtworkModel getArtWorkModelFromArtWork(Artwork artwork) {
		
		if (artwork == null) {
			return null;
		}
		
		ArtworkModel artworkModel = new ArtworkModel(artwork);
		
		TypedQuery<Image> imageQuery = em.createQuery("SELECT i FROM Image i WHERE i.artwork = :artwork", Image.class);
		TypedQuery<Document> docQuery = em.createQuery("SELECT d FROM Document d WHERE d.artwork = :artwork", Document.class);
		TypedQuery<Comment> commentQuery = em.createQuery("SELECT c FROM Comment c WHERE c.artwork = :artwork", Comment.class);
		TypedQuery<Provenance> provenanceQuery = em.createQuery("SELECT p FROM Provenance p WHERE p.artwork = :artwork", Provenance.class);
		
		List<Image> imgs = null;
		List<Document> docs = null;
		List<Comment> comments = null;
		List<Provenance> provenances = null;
		
		PurchaseHistory lastestPurchase = null;
		ImageModel imgModel = null;
		DocumentModel docModel = null;
		CommentModel commentModel = null;
		ProvenanceModel provenanceModel = null;
		
		imageQuery.setParameter("artwork", artwork);
		imgs = imageQuery.getResultList();
		for (Image i : imgs) {
			imgModel = new ImageModel(i);
			artworkModel.addImage(imgModel);
		}

		docQuery.setParameter("artwork", artwork);
		docs = docQuery.getResultList();
		for (Document d : docs) {
			docModel = new DocumentModel(d);
			artworkModel.addDocument(docModel);
		}

		commentQuery.setParameter("artwork", artwork);
		comments = commentQuery.getResultList();
		for (Comment c : comments) {
			commentModel = new CommentModel(c);
			artworkModel.addComment(commentModel);
		}

		provenanceQuery.setParameter("artwork", artwork);
		provenances = provenanceQuery.getResultList();
		for (Provenance p : provenances) {
			provenanceModel = new ProvenanceModel(p);
			artworkModel.addProvenance(provenanceModel);
		}

		lastestPurchase = appService.findArtworkLatestPurchase(artwork);
		if (lastestPurchase!=null) {
			artworkModel.price = lastestPurchase.getPrice();
			artworkModel.vat = lastestPurchase.getVat();
			artworkModel.valuation = lastestPurchase.getValuation();
			artworkModel.valuationDate = lastestPurchase.getValuationDate();
			artworkModel.insurance = lastestPurchase.getInsurance();
			artworkModel.source = lastestPurchase.getSource();
			artworkModel.commission = lastestPurchase.getCommission();
			artworkModel.purchasedFrom = lastestPurchase.getPurchasedFrom();
			artworkModel.purchasedDate = lastestPurchase.getPurchaseDate();
			artworkModel.valuedBy = lastestPurchase.getValuedBy();
			artworkModel.artistRR = lastestPurchase.getArtistRR();
			artworkModel.importTax = lastestPurchase.getImportTax();
		}
		return artworkModel;
	}
	
	private Artwork getArtworkFromArtworkModel(ArtworkModel artworkModel) {
		Artwork artwork = new Artwork();
		artwork.setUser(appService.findUserById(artworkModel.user));
		artwork.setArtist(appService.findArtistById(artworkModel.artist));
		artwork.setName(artworkModel.name);
		artwork.setArtBookBio(artworkModel.artBookBio);
		artwork.setOrigin(artworkModel.origin);
		return artwork;
//		origin = a.getOrigin();
//		yearNote = a.getYearNote();
//		medium = a.getMedium();
//		conditionDescription = a.getConditionDescription();
//		year = a.getYear();
//		period = a.getPeriod();
//		heightCm = (float) a.getHeight() / 100;
//		widthCm = (float) a.getWidth() / 100;
//		depthCm = (float) a.getDepth() / 100;
//		heightInch = (float) a.getHeight() * cmToInc10k / 1000000;
//		widthInch = (float) a.getWidth() * cmToInc10k / 1000000;
//		depthInch = (float) a.getDepth() * cmToInc10k / 1000000;
//		framedHeightCm = (float) a.getFramedHeight() / 100;
//		framedWidthCm = (float) a.getFramedWidth() / 100;
//		framedDepthCm = (float) a.getFramedDepth() / 100;
//		framedHeightInch = (float) a.getFramedHeight() * cmToInc10k / 1000000;
//		framedWidthInch = (float) a.getFramedWidth() * cmToInc10k / 1000000;
//		framedDepthInch = (float) a.getFramedDepth() * cmToInc10k / 1000000;
//		tagNumber = a.getTagNumber();
//		signed = a.getSigned();
//		editionNo = a.getEditionNo();
//		
//		externalLink = a.getExternalLink();
//		
//		Location location = a.getLocation();
//		if (location != null) {
//			locationId = location.getId();
//		}
//		
//		importRestriction = a.getImportRestriction();
//		
//		exportRestriction = a.getExportRestriction();
//		
//		if (a.getCategory() != null) {
//			category = a.getCategory().getId();
//		}
//		
//		if (a.getUser() != null) {
//			user = a.getUser().getId();
//		}
//		
//		if (a.getArtist() != null) {
//			artist = a.getArtist().getId();
//		}
//		
//		for (Style c : a.getStyles()) {
//			styles.add(c.getId());
//		}
//		
//		for (Exhibition e : a.getExhibitions()) {
//			exhibitions.add(e.getId());
//		}
	}
}