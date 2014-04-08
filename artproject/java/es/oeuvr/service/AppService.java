package es.oeuvr.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import es.oeuvr.api.LoginRequest;
import es.oeuvr.domain.Artist;
import es.oeuvr.domain.Artwork;
import es.oeuvr.domain.Comment;
import es.oeuvr.domain.Location;
import es.oeuvr.domain.PurchaseHistory;
import es.oeuvr.domain.Slideshow;
import es.oeuvr.domain.Tag;
import es.oeuvr.domain.User;
import es.oeuvr.util.StringUtil;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@Stateless
public class AppService {
	// final Logger logger = LoggerFactory.getLogger( this.getClass() );

	@PersistenceContext(unitName = "oeuvres-pu")
	private EntityManager em;

	public User login(LoginRequest request) {
		String username = request.username.toLowerCase();
		User user = findUserByEmail(username);
		String passwordHash = StringUtil.hash(request.password, username);
		if (user != null) {
			String token = user.getSessionToken();
			if (token == null || token.isEmpty() || isTokenExpired(token)) {
				if (user.getPassword().equals(passwordHash)) {
					int isUpdated = 0;
					try {
						user.setSessionToken(TokenService.creatToken(user.getEmail()));
						isUpdated = saveUserToken(user.getId(),user.getSessionToken());
					} catch (Exception e) {
						new Exception(e.getMessage());
					}
					if (isUpdated > 0) {
						return user;
					}
				}
			}
		} 
		return null;
	}

	public int verify(String serializedJWT) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(serializedJWT);
		JWSVerifier verifier = new MACVerifier(TokenService.KEY.getBytes());
		boolean verifiedSignature = signedJWT.verify(verifier);
		if (verifiedSignature) {
			User user = findUserByEmail(signedJWT.getJWTClaimsSet().getSubject());
			if (user != null && serializedJWT.equals(user.getSessionToken())) {
				Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
				if (expirationDate.getTime() > System.currentTimeMillis()) {
					return TokenService.VALID;
				} 
			}
		}
		return TokenService.INVALID;
	}
	
	public boolean isTokenExpired(String serializedJWT) {
		SignedJWT signedJWT;
		Date expirationDate;
		boolean isExpired = false;
		try {
			signedJWT = SignedJWT.parse(serializedJWT);
			expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
			isExpired = (expirationDate.getTime() < System.currentTimeMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isExpired;
	}

	public boolean logout(String username) {
		User user = findUserByEmail(username);
		if (user != null && user.getSessionToken() != null) {
			int isUpdated = saveUserToken(user.getId(), null);
			if (isUpdated > 0) {
				return true;
			}
		}
		return false;
	}


	public int saveUserToken(Long id, String token) {
		Query updateUserQuery = em.createNativeQuery("UPDATE User u SET u.sessionToken= :token WHERE u.id = :id");
		updateUserQuery.setParameter("token", token);
		updateUserQuery.setParameter("id", id);
		int updates = updateUserQuery.executeUpdate();
		return updates;
	}
	
	public User findUserByEmail(String email) {
		TypedQuery<User> findByEmailQuery = em.createQuery(
				"SELECT u FROM User u WHERE u.email = :email", User.class);
		findByEmailQuery.setParameter("email", email);
		User entity = null;
		try {
			entity = findByEmailQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public User findUserById(Long id) {
		TypedQuery<User> findByIdQuery = em.createQuery(
				"SELECT u FROM User u WHERE u.id = :entityId", User.class);
		findByIdQuery.setParameter("entityId", id);
		User entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public Artist findArtistById(Long id) {
		TypedQuery<Artist> findByIdQuery = em.createQuery("SELECT a FROM Artist a WHERE a.id = :entityId", Artist.class);
		findByIdQuery.setParameter("entityId", id);
		Artist entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
		}
		return entity;
	}

	public void updatePassword(User user, String newPassword,
			String exsitingPassword) {
		// logger.info("Received call to update password " + user.getEmail());
		// newPassword=StringUtil.hash(newPassword,user.getEmail());
		// if (checkPassword(newPassword))
		{
			// reset Failedlogon
			// user.setFailedLogons(0);
			// saveUser(user);
		}
	}

	public Artwork findArtworkById(Long id) {
		TypedQuery<Artwork> findByIdQuery = em
				.createQuery("SELECT a FROM Artwork a WHERE a.id = :entityId",
						Artwork.class);
		findByIdQuery.setParameter("entityId", id);
		Artwork entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public List<Artwork> findArtworksByUserId(Long uid) {
		return em.createQuery("SELECT a FROM Artwork a where a.user.id = :uid",
						Artwork.class).setParameter("uid", uid).getResultList();
	}

	public Tag findTagById(Long id) {
		TypedQuery<Tag> findByIdQuery = em
				.createQuery("SELECT t FROM Tag t WHERE t.id = :entityId",
						Tag.class);
		findByIdQuery.setParameter("entityId", id);
		Tag entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public Tag findTagByName(String name) {
		TypedQuery<Tag> findByIdQuery = em
				.createQuery("SELECT t FROM Tag t WHERE UPPER(t.name) = UPPER(:name)",
						Tag.class);
		findByIdQuery.setParameter("name", name);
		Tag entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public List<Tag> findTagsByUserId(Long uid) {
		return em.createQuery("SELECT t FROM Tag t where t.user.id = :uid ORDER BY t.name",
						Tag.class).setParameter("uid", uid).getResultList();
	}

	public List<Location> findLocationsByUserId(Long uid) {
		return em.createQuery("SELECT l FROM Location l where l.owner.id = :uid ORDER BY l.location, l.room",
				Location.class).setParameter("uid", uid).getResultList();
	}

	public Slideshow findSlideshowById(Long id) {
		TypedQuery<Slideshow> findByIdQuery = em
				.createQuery("SELECT s FROM Slideshow s WHERE s.id = :entityId",
						Slideshow.class);
		findByIdQuery.setParameter("entityId", id);
		Slideshow entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public List<Slideshow> findSlideshowsByUserId(Long uid) {
		return em.createQuery("SELECT s FROM Slideshow s where s.user.id = :uid",
						Slideshow.class).setParameter("uid", uid).getResultList();
	}

	public Comment findCommentById(Long id) {
		TypedQuery<Comment> findByIdQuery = em
				.createQuery("SELECT c FROM Comment c WHERE c.id = :entityId",
						Comment.class);
		findByIdQuery.setParameter("entityId", id);
		Comment entity = null;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

	public PurchaseHistory findArtworkLatestPurchase(Artwork artwork) {
		TypedQuery<PurchaseHistory> query = em
				.createQuery("SELECT p FROM PurchaseHistory p WHERE p.artwork = :artwork ORDER BY p.purchaseDate DESC", PurchaseHistory.class);
		query.setParameter("artwork", artwork);
		PurchaseHistory entity = null;
		try {
			entity = query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore
		}
		return entity;
	}

}
