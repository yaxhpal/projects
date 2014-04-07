package es.oeuvr.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.annotations.providers.jaxb.Formatted;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.ResourceInvoker;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ResourceMethodRegistry;

import es.oeuvr.domain.JaxRsResource;

@Stateless
@Path("/meta")
public class MetaServiceEndpoint {
   
	@Context Dispatcher dispatcher;
    @GET
    @Path("/")
    @Wrapped(element = "resources")
    @Formatted
    public Set<JaxRsResource> getAllEndpoints(){
        Set<JaxRsResource> resources = new HashSet<JaxRsResource>();
        ResourceMethodRegistry registry = (ResourceMethodRegistry) dispatcher.getRegistry();
        for (Map.Entry<String, List<ResourceInvoker>> entry : registry.getRoot().getBounded().entrySet()) {
            for (ResourceInvoker invoker : entry.getValue()) {
                ResourceMethod method = (ResourceMethod) invoker;
                if(method.getMethod().getDeclaringClass() == getClass()){
                    continue;
                }
                for (String verb : method.getHttpMethods()) {
                    String uri = entry.getKey();
                    resources.add(new JaxRsResource(verb, uri));
                }
            }
        }
        return resources;
    }

}
