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
import es.oeuvr.domain.PurchaseHistory;

/**
 *
 */
@Stateless
@Path("/purchasehistorys")
public class PurchaseHistoryEndpoint
{
   @PersistenceContext(unitName = "oeuvres-pu")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(PurchaseHistory entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(PurchaseHistoryEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      PurchaseHistory entity = em.find(PurchaseHistory.class, id);
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
      TypedQuery<PurchaseHistory> findByIdQuery = em.createQuery("SELECT p FROM PurchaseHistory p LEFT JOIN FETCH p.artwork LEFT JOIN FETCH p.owner WHERE p.id = :entityId", PurchaseHistory.class);
      findByIdQuery.setParameter("entityId", id);
      PurchaseHistory entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<PurchaseHistory> listAll()
   {
      final List<PurchaseHistory> results = em.createQuery("SELECT p FROM PurchaseHistory p LEFT JOIN FETCH p.artwork LEFT JOIN FETCH p.owner", PurchaseHistory.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, PurchaseHistory entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}