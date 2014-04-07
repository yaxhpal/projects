package es.oeuvr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import es.oeuvr.domain.Location;

/**
 *
 */
@Stateless
@Path("/locations")
public class LocationEndpoint
{
   @PersistenceContext(unitName = "oeuvres-pu")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Location entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(LocationEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Location entity = em.find(Location.class, id);
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
      TypedQuery<Location> findByIdQuery = em.createQuery("SELECT l FROM Location l LEFT JOIN FETCH l.owner WHERE l.id = :entityId", Location.class);
      findByIdQuery.setParameter("entityId", id);
      Location entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Location> listAll()
   {
      final List<Location> results = em.createQuery("SELECT l FROM Location l LEFT JOIN FETCH l.owner", Location.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, Location entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}