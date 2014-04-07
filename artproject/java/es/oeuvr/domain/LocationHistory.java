package es.oeuvr.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.lang.Override;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.Digits;

//TODO
//@Entity
@XmlRootElement
@Cacheable
public class LocationHistory implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

	@Column
	private String comment;

	@Column
	private Location location;

   @Temporal(TemporalType.DATE)
   private Date requestDate;

   @Temporal(TemporalType.DATE)
   private Date relocationDate;

   @ManyToOne
   private Artwork artwork;

   @ManyToOne
   private User requester;

   @ManyToOne
   private User actioner;

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

   public void setLocation(Location location) {
		this.location = location;
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
         return id.equals(((LocationHistory) that).id);
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


	public Artwork getArtwork() {
		return artwork;
	}

	public void setArtwork(Artwork artwork) {
		this.artwork = artwork;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getRelocationDate() {
		return relocationDate;
	}

	public void setRelocationDate(Date relocationDate) {
		this.relocationDate = relocationDate;
	}

	public User getRequester() {
		return requester;
	}

	public void setRequester(User requester) {
		this.requester = requester;
	}

	public User getActioner() {
		return actioner;
	}

	public void setActioner(User actioner) {
		this.actioner = actioner;
	}

	public Location getLocation() {
		return location;
	}

	/*
	@Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (requester != null && !requester.trim().isEmpty())
         result += "country: " + country;
      if (town != null && !town.trim().isEmpty())
         result += ", town: " + town;
      if (house != null && !house.trim().isEmpty())
    	  result += ", house: " + house;
      if (location != null && !location.trim().isEmpty())
         result += ", location: " + location;
      return result;
   }
   */

}