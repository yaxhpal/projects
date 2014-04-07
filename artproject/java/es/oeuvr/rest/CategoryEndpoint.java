package es.oeuvr.rest;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import es.oeuvr.domain.Category;
import es.oeuvr.domain.CategoryWrapper;

/**
 * 
 */
@Stateless
@Path("/categorys")
public class CategoryEndpoint
{
   @PersistenceContext(unitName = "oeuvres-pu")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Category entity)
   {
	   entity.setCreated((int) (System.currentTimeMillis() / 1000L));
	   entity.setUpdated((int) (System.currentTimeMillis() / 1000L));
	   if(entity.getParent()== null || entity.getParent().equals(""))
		   entity.setCndGroup("Category");
	   else
		   entity.setCndGroup("SubCategory");
	   entity.setDescription(entity.getName());
	   entity.setStatus('A');
	   entity.setSequence(1);
	   em.persist(entity);
      return Response.created(UriBuilder.fromResource(CategoryEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Category entity = em.find(Category.class, id);
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
      TypedQuery<Category> findByIdQuery = em.createQuery("SELECT c FROM Category c WHERE c.id = :entityId", Category.class);
      findByIdQuery.setParameter("entityId", id);
      Category entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<CategoryWrapper> listAll()
   {
      List<CategoryWrapper> res=new ArrayList<CategoryWrapper>();
      final List<Category> results = em.createQuery("SELECT c FROM Category c where c.cndGroup='Category' ", Category.class).getResultList();
      if(results != null){
    	  List<Category> subresults=null;
    	  List<CategoryWrapper> sub=null;
    	  CategoryWrapper cat=null;
    	  for(Category c : results){
    		  cat=new CategoryWrapper();
    		  cat.setId(c.getId());
    		  cat.setName(c.getName());
    		  TypedQuery<Category> findByIdQuery =em.createQuery("SELECT c FROM Category c where c.cndGroup='SubCategory' and c.parent=:id", Category.class);
    		  findByIdQuery.setParameter("id", c.getId());
    		  subresults = findByIdQuery.getResultList();
    		  if(subresults != null){
    			  sub=new ArrayList<CategoryWrapper>();
    			  CategoryWrapper subcat=null;
    			  for(Category s : subresults){
    				  subcat=new CategoryWrapper();
    				  subcat.setId(s.getId());
    				  subcat.setName(s.getName());
    				  sub.add(subcat);
    			  }
    		  }
    		  cat.setSubCategory(sub);
    		  res.add(cat);
    	  }
      }
      return res;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, Category entity)
   {
      entity.setId(id);
      if(entity.getParent()== null || entity.getParent().equals(""))
		   entity.setCndGroup("Category");
	   else
		   entity.setCndGroup("SubCategory");
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}