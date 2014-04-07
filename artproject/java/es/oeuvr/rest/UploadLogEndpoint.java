package es.oeuvr.rest;


import java.util.List;

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

import org.jboss.logging.Logger;

import es.oeuvr.domain.UploadLog;

/**
 * Home object for domain model class UploadLog.
 * @see UploadLog
 * @author <a href="mailto:haseena@techletsolutions.com">Haseena Rahmath</a>
 * @version $Revision: 1 $
 */
@Stateless
@Path("/upload")
public class UploadLogEndpoint {

	private static final Logger log = Logger.getLogger(UploadLogEndpoint.class);

	@PersistenceContext(unitName = "oeuvres-pu")
	private EntityManager em;
	   
	   @POST
	   @Consumes("application/json")
	   public Response create(UploadLog entity)
	   {
		   entity.setUploadDate((int) (System.currentTimeMillis() / 1000L));
		   em.persist(entity);
	      return Response.created(UriBuilder.fromResource(CategoryEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
	   }

	   @DELETE
	   @Path("/{id:[0-9][0-9]*}")
	   public Response deleteById(@PathParam("id") Long id)
	   {
	      UploadLog entity = em.find(UploadLog.class, id);
	      if (entity == null)
	      {
	         return Response.status(Status.NOT_FOUND).build();
	      }
	      em.remove(entity);
	      return Response.noContent().build();
	   }

	   @GET
	   @Path("/{id:[0-9][0-9]*}")
	   @Produces("application/json")
	   public Response findById(@PathParam("id") Long id)
	   {
	      TypedQuery<UploadLog> findByIdQuery = em.createQuery("SELECT c FROM UploadLog c WHERE c.id = :entityId", UploadLog.class);
	      findByIdQuery.setParameter("entityId", id);
	      UploadLog entity = findByIdQuery.getSingleResult();
	      if (entity == null)
	      {
	         return Response.status(Status.NOT_FOUND).build();
	      }
	      return Response.ok(entity).build();
	   }
	   @GET
	   @Produces("application/json")
	   public List<UploadLog> listAll()
	   {
	      log.debug("Listing all UploadLog entries...");
		  final List<UploadLog> results = em.createQuery("SELECT c FROM UploadLog c", UploadLog.class).getResultList();
	      return results;
	   }

	   @PUT
	   @Path("/{id:[0-9][0-9]*}")
	   @Consumes("application/json")
	   public Response update(@PathParam("id") Long id, UploadLog entity)
	   {
	      entity.setId(id);
	      entity = em.merge(entity);
	      return Response.noContent().build();
	   }
}
