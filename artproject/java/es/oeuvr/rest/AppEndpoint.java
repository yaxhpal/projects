package es.oeuvr.rest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.oeuvr.api.AddArtworkToSlideshowRequest;
import es.oeuvr.api.AddArtworkToTagRequest;
import es.oeuvr.api.LoginRequest;
import es.oeuvr.api.RemoveArtworkFromSlideshowRequest;
import es.oeuvr.api.RemoveArtworkFromTagRequest;
import es.oeuvr.api.SaveCommentRequest;
import es.oeuvr.api.SaveSlideshowRequest;
import es.oeuvr.domain.Artist;
import es.oeuvr.domain.Artwork;
import es.oeuvr.domain.Image;
import es.oeuvr.domain.Movement;
import es.oeuvr.domain.Style;
import es.oeuvr.domain.Comment;
import es.oeuvr.domain.Document;
import es.oeuvr.domain.Exhibition;
import es.oeuvr.domain.Location;
import es.oeuvr.domain.Provenance;
import es.oeuvr.domain.PurchaseHistory;
import es.oeuvr.domain.Slideshow;
import es.oeuvr.domain.Tag;
import es.oeuvr.domain.User;
import es.oeuvr.model.ArtistModel;
import es.oeuvr.model.ArtworkModel;
import es.oeuvr.model.ImageModel;
import es.oeuvr.model.MovementModel;
import es.oeuvr.model.StyleModel;
import es.oeuvr.model.CommentModel;
import es.oeuvr.model.DocumentModel;
import es.oeuvr.model.ExhibitionModel;
import es.oeuvr.model.LocationModel;
import es.oeuvr.model.ProvenanceModel;
import es.oeuvr.model.SlideshowModel;
import es.oeuvr.model.TagModel;
import es.oeuvr.model.UserModel;
import es.oeuvr.model.Wrapper;
import es.oeuvr.service.AppService;

@Stateless
@Path("/app")
@Produces({ MediaType.APPLICATION_JSON })
public class AppEndpoint {
	@PersistenceContext(unitName = "oeuvres-pu")
	private EntityManager em;

	@EJB
	private AppService appService;

	@PermitAll
	@Path("/login")
	@POST
	public Response login(LoginRequest request) {
		User user = appService.login(request);
		if (user == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		UserModel userModel = new UserModel(user);
		return Response.ok(userModel).build();
	}
	
	@PermitAll
	@Path("/logout")
	@POST
	public Response logout() {
		return Response.ok().build();
	}
	
	@PermitAll
	@POST
	@Path("/location/relocate")
	public CommentModel relocate(SaveCommentRequest request) {
		Comment c = null;
		if (request.id > 0 ) {
			//update
			c = appService.findCommentById(request.id);
			c.setMessage(request.message);
			c.setDate(new Date());
			c = em.merge(c);
		} else {
			//create
			c = new Comment();
			User u = appService.findUserById(request.authorId);
			c.setAuthor(u);
			c.setMessage(request.message);
			c.setDate(new Date());
			Artwork a = appService.findArtworkById(request.artworkId);
			c.setArtwork(a);
			em.persist(c);
		}
		return new CommentModel(c);
	}

	@PermitAll
	@POST
	@Path("/comment")
	public CommentModel saveComment(SaveCommentRequest request) {
		Comment c = null;
		if (request.id > 0 ) {
			//update
			c = appService.findCommentById(request.id);
			c.setMessage(request.message);
			c.setDate(new Date());
			c = em.merge(c);
		} else {
			//create
			c = new Comment();
			User u = appService.findUserById(request.authorId);
			c.setAuthor(u);
			c.setMessage(request.message);
			c.setDate(new Date());
			Artwork a = appService.findArtworkById(request.artworkId);
			c.setArtwork(a);
			em.persist(c);
		}
		return new CommentModel(c);
	}

	@PermitAll
	@POST
	@Path("/tag/addArtwork")
	public TagModel addArtworkToTag(AddArtworkToTagRequest request) {
		Tag t = null;
		Artwork a = appService.findArtworkById(request.artworkId);
		if (a!=null) {
			if (request.tagId > 0 ) {
				//update
				t = appService.findTagById(request.tagId);
			} else {
				t = appService.findTagByName(request.name);
				if (t==null)
				{
					//create
					t = new Tag();
					t.setName(request.name);
					User u = appService.findUserById(request.userId);
					t.setUser(u);
				}
			}
			if (t!=null){
				t.addArtwork(a);
				em.persist(t);
			}
		}
		return new TagModel(t);
	}

	@PermitAll
	@POST
	@Path("/tag/removeArtwork")
	public TagModel removeArtworkFromTag(RemoveArtworkFromTagRequest request) {
		Tag t = appService.findTagById(request.tagId);
		if (t!=null) {
			Artwork a = appService.findArtworkById(request.artworkId);
			if (a!=null) {
				t.removeArtwork(a);
				if (t.hasArtwork()){
					em.persist(t);
				} else {
					em.remove(t);
				}
			}
		}
		return new TagModel(t);
	}

	@PermitAll
	@POST
	@Path("/slideshow")
	public SlideshowModel saveSlideshow(SaveSlideshowRequest request) {
		Slideshow s = null;
		if (request.id > 0 ) {
			//update
			s = appService.findSlideshowById(request.id);
			s.setName(request.name);
			s = em.merge(s);
		} else {
			//create
			s = new Slideshow();
			s.setName(request.name);
			User u = appService.findUserById(request.userId);
			s.setUser(u);
			em.persist(s);
		}
		return new SlideshowModel(s);
	}

	@PermitAll
	@DELETE
	@Path("/slideshow/{id:[0-9][0-9]*}")
	public String deleteSlideshowById(@PathParam("id") Long id) {
		Slideshow entity = em.find(Slideshow.class, id);
		if (entity != null) {
			em.remove(entity);
			return "OK";
		}
		return "Not Found";
	}

	@PermitAll
	@POST
	@Path("/slideshow/addArtwork")
	public SlideshowModel addArtworkToSlideshow(AddArtworkToSlideshowRequest request) {
		Slideshow s = appService.findSlideshowById(request.slideshowId);
		if (s!=null) {
			Artwork a = appService.findArtworkById(request.artworkId);
			if (a!=null) {
				s.addArtwork(a);
				em.persist(s);
			}
		}
		return new SlideshowModel(s);
	}

	@PermitAll
	@POST
	@Path("/slideshow/removeArtwork")
	public SlideshowModel removeArtworkFromSlideshow(RemoveArtworkFromSlideshowRequest request) {
		Slideshow s = appService.findSlideshowById(request.slideshowId);
		if (s!=null) {
			Artwork a = appService.findArtworkById(request.artworkId);
			if (a!=null) {
				s.removeArtwork(a);
				em.persist(s);
			}
		}
		return new SlideshowModel(s);
	}

	// get all
	@PermitAll
	@GET
	@Path("/{uid:[0-9][0-9]*}")
	public List<Wrapper> listAll(@PathParam("uid") Long uid) {
		List<Wrapper> all = new LinkedList<Wrapper>();

		final List<Artwork> artworks = appService.findArtworksByUserId(uid);

		List<Image> imgs = null;
		TypedQuery<Image> imageQuery = em.createQuery(
				"SELECT i FROM Image i WHERE i.artwork = :artwork",
				Image.class);

		List<Document> docs = null;
		TypedQuery<Document> docQuery = em.createQuery(
				"SELECT d FROM Document d WHERE d.artwork = :artwork",
				Document.class);

		List<Comment> comments = null;
		TypedQuery<Comment> commentQuery = em.createQuery(
				"SELECT c FROM Comment c WHERE c.artwork = :artwork",
				Comment.class);

		List<Provenance> provenances = null;
		TypedQuery<Provenance> provenanceQuery = em.createQuery(
				"SELECT p FROM Provenance p WHERE p.artwork = :artwork",
				Provenance.class);

		PurchaseHistory lastestPurchase = null;

		List<ArtworkModel> artworkModels = new LinkedList<ArtworkModel>();
		ArtworkModel artworkModel = null;

		List<ArtistModel> artistModels = new LinkedList<ArtistModel>();
		ArtistModel artistModel = null;

		List<StyleModel> styleModels = new LinkedList<StyleModel>();
		StyleModel styleModel = null;

		List<MovementModel> movementModels = new LinkedList<MovementModel>();
		MovementModel movementModel = null;

		List<ExhibitionModel> exhibitionModels = new LinkedList<ExhibitionModel>();
		ExhibitionModel exhibitionModel = null;

		List<SlideshowModel> slideshowModels = new LinkedList<SlideshowModel>();
		SlideshowModel slideshowModel = null;

		List<TagModel> tagModels = new LinkedList<TagModel>();
		TagModel tagModel = null;

		List<LocationModel> locationModels = new LinkedList<LocationModel>();
		LocationModel locationModel = null;

		ImageModel imgModel = null;
		DocumentModel docModel = null;
		CommentModel commentModel = null;
		ProvenanceModel provenanceModel = null;

		for (Artwork artwork : artworks) {
			artworkModel = new ArtworkModel(artwork);

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
			if (lastestPurchase!=null)
			{
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

			artworkModels.add(artworkModel);

			Artist artist = artwork.getArtist();
			if (artist != null) {
				artistModel = new ArtistModel(artist);
			}
			if (!artistModels.contains(artistModel)) {
				artistModels.add(artistModel);
			}

			Set<Style> styles = artwork.getStyles();
			if (styles != null && !styles.isEmpty()) {
				for (Style style : styles) {
					styleModel = new StyleModel(style);
					if (!styleModels.contains(styleModel)) {
						styleModels.add(styleModel);
					}
				}
			}

			Set<Movement> movements = artwork.getMovements();
			if (movements != null && !movements.isEmpty()) {
				for (Movement movement : movements) {
					movementModel = new MovementModel(movement);
					if (!movementModels.contains(styleModel)) {
						movementModels.add(movementModel);
					}
				}
			}

			Set<Exhibition> exhibitions = artwork.getExhibitions();
			if (exhibitions != null && !exhibitions.isEmpty()) {
				for (Exhibition exhibition : exhibitions) {
					exhibitionModel = new ExhibitionModel(exhibition);
					if (!exhibitionModels.contains(exhibitionModel)) {
						exhibitionModels.add(exhibitionModel);
					}
				}
			}

			List<Slideshow> slideshows = appService.findSlideshowsByUserId(uid);
			if (slideshows != null && !slideshows.isEmpty()) {
				for (Slideshow slideshow : slideshows) {
					slideshowModel = new SlideshowModel(slideshow);
					if (!slideshowModels.contains(slideshowModel)) {
						slideshowModels.add(slideshowModel);
					}
				}
			}

			List<Tag> tags = appService.findTagsByUserId(uid);
			if (tags != null && !tags.isEmpty()) {
				for (Tag tag : tags) {
					tagModel = new TagModel(tag);
					if (!tagModels.contains(tagModel)) {
						tagModels.add(tagModel);
					}
				}
			}

			List<Location> locations = appService.findLocationsByUserId(uid);
			if (locations != null && !locations.isEmpty()) {
				for (Location location : locations) {
					locationModel = new LocationModel(location);
					if (!locationModels.contains(locationModel)) {
						locationModels.add(locationModel);
					}
				}
			}

		}

		all.add(new Wrapper("artworks", artworkModels));
		all.add(new Wrapper("styles", styleModels));
		all.add(new Wrapper("movements", movementModels));
		all.add(new Wrapper("artists", artistModels));
		all.add(new Wrapper("slideshows", slideshowModels));
		all.add(new Wrapper("tags", tagModels));
		all.add(new Wrapper("locations", locationModels));
		all.add(new Wrapper("exhibitions", exhibitionModels));

		return all;
	}
}