package es.oeuvr.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import es.oeuvr.domain.Artist;
import es.oeuvr.model.ArtworkModel;

/**
 * 
 */
@Stateless
@Path("/artists")
public class ArtistEndpoint
{
	@PersistenceContext(unitName = "oeuvres-pu")
	private EntityManager em;

	@EJB
	private ArtworkEndpoint artworkService;

	@POST
	@Consumes("application/json")
	public Response create(Artist entity) {
		em.persist(entity);
		return Response.created(UriBuilder.fromResource(ArtistEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Artist entity = em.find(Artist.class, id);
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
		TypedQuery<Artist> findByIdQuery = em.createQuery("SELECT a FROM Artist a WHERE a.id = :entityId", Artist.class);
		findByIdQuery.setParameter("entityId", id);
		Artist entity = findByIdQuery.getSingleResult();
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Artist> listAll() {
		final List<Artist> results = em.createQuery("SELECT a FROM Artist a", Artist.class).getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Artist entity) {
		entity.setId(id);
		entity = em.merge(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}/artworks")
	@Produces("application/json")
	public List<ArtworkModel> listAllArtworks(@PathParam("id") Long id) {
		return artworkService.findArtworksByArtistId(id);
	}
}