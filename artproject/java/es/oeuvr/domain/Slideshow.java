package es.oeuvr.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import java.lang.Override;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import es.oeuvr.domain.User;

import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Cacheable
public class Slideshow implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column(unique = true)
   @NotNull
   private String name;

   @ManyToOne
   private User user;

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<Artwork> artworks = new HashSet<Artwork>();

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Slideshow) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      return result;
   }
   
   public User getUser()
   {
      return this.user;
   }

   public void setUser(final User user)
   {
      this.user = user;
   }

   public Set<Artwork> getArtworks()
   {
      return this.artworks;
   }

   public void setArtworks(final Set<Artwork> artworks)
   {
      this.artworks = artworks;
   }

	public void addArtwork(Artwork a) {
		this.artworks.add(a);
	}

	public void removeArtwork(Artwork a) {
		this.artworks.remove(a);
	}
}