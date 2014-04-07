package es.oeuvr.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

/**
 * This interceptor will <b>authenticate</b> and <b>authorize</b> incoming requests. 
 * 
 * @author <a href="mailto:haseena@techletsolutions.com">Haseena Rahmath</a>
 * @version $Revision: 1 $
 */
@Provider
@ServerInterceptor
@Consumes({ MediaType.APPLICATION_JSON })
public class SecurityInterceptor implements PreProcessInterceptor {

	/**
	 * Get EJB session bean
	 */
	@EJB
	private AppService appService;

	private static final Logger log = Logger.getLogger(SecurityInterceptor.class);
	
	private static final ServerResponse SUCCESS = new ServerResponse("", 200, new Headers<Object>());
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());
	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Unauthorized", 401, new Headers<Object>());
	private static final ServerResponse INTERNAL_ERROR = new ServerResponse("Internal Server Error", 500, new Headers<Object>());
	// Token name if it is present in request headers or cookies
	private static final String TOKEN_NAME = "sessionToken";
	// Token name if it is an Auth02 header
	private static final String AUTH_TOKEN_HEADER_NAME = "Authorization";
	
	  private static final Map<String, Boolean> filterMethods = new HashMap<String, Boolean>();
	  static {
		  filterMethods.put("login", true);
		  filterMethods.put("getAllEndpoints", true);
		  filterMethods.put("listAll", true);
	    }
	
	@Override
	public ServerResponse preProcess(HttpRequest httpRequest, ResourceMethod resourceMethod) throws Failure, WebApplicationException {
		
		log.debug("Inside SecurityInterceptor.preProcess");
		Method method = resourceMethod.getMethod();
		String methodName =  method.getName();
		String token = getToken(httpRequest);
		
		if (token == null) {
			return null;
		}
 		log.debug("Requested Method is " + methodName + ".");
		log.debug("Auth Token: " + token);
		
		//FIXME By pass for admin interface. Need to remove this
		HttpHeaders httpHeaders = httpRequest.getHttpHeaders();
		List<String> refererHeaders = httpHeaders.getRequestHeader("referer");
		if (refererHeaders != null) {
			String referer = refererHeaders.get(0);
			if (referer.contains("/admin/")) {
				return null;
			}
		}
			
		//FIXME Remove filtered method 'listAll' 
		try {
			if (!filterMethods.containsKey(methodName)) {
				if(token == null || token.isEmpty()) {
					return ACCESS_DENIED;
				} else {
					int status = appService.verify(token);
					if (status == TokenService.VALID) {
						if (methodName.equals("logout")) {
							appService.logout(TokenService.getSubjectFromToken(token));
							return SUCCESS;
						}
						return null;
					} else {
						return ACCESS_DENIED;
					}
				}
			}
		} catch (Exception e) {
			log.fatal(e.getMessage());
			return INTERNAL_ERROR;
		}
		// Access allowed for all
		if (method.isAnnotationPresent(PermitAll.class)) {
			return null;
		}
		// Access denied for all
		if (method.isAnnotationPresent(DenyAll.class)) {
			return ACCESS_FORBIDDEN;
		}
		// Return null to continue request processing
		return null;
	}
	
	
	private String getToken(HttpRequest request) {
        HttpHeaders httpHeaders = request.getHttpHeaders();
        List<String> tokenHeader = httpHeaders.getRequestHeader(AUTH_TOKEN_HEADER_NAME);
        String token = null;

        // Check Request Header
        if (tokenHeader != null && !tokenHeader.isEmpty()) {
            token = tokenHeader.get(0).replace("Bearer", "").trim();
        }
        
        // Check Authorization Header
        if (token == null) {
        	tokenHeader = httpHeaders.getRequestHeader(TOKEN_NAME);
        	 if (tokenHeader != null && !tokenHeader.isEmpty()) {
                 token = tokenHeader.get(0);
             }
        }
        
        // Check cookies
        if(token == null){
            Map<String,Cookie> cookies = httpHeaders.getCookies();
            if(cookies != null){
                Cookie cookie = cookies.get(TOKEN_NAME);
                if (cookie != null) {
                	 token = cookie.getValue();
                }
            }
            log.debug("Cokkies are : " + cookies);
        }
        return token;
    }
}