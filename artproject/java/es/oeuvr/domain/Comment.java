package es.oeuvr.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.lang.Override;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import es.oeuvr.domain.Artwork;

import javax.persistence.ManyToOne;

@Entity
@XmlRootElement
@Cacheable
public class Comment implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column(length = 1024)   
   private String message;
   
   @Temporal(TemporalType.TIMESTAMP)
   private Date date;
   
   @ManyToOne
   private User author;

   @ManyToOne
   private Artwork artwork;

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

   public Artwork getArtwork()
   {
      return this.artwork;
   }

   public void setArtwork(final Artwork artwork)
   {
      this.artwork = artwork;
   }

   public Date getDate() {
       return date;
   }

   public void setDate(Date date) {
       this.date = date;
   }

   public User getAuthor() {
       return author;
   }

   public void setAuthor(User author) {
       this.author = author;
   }

   public String getMessage() {
       return message;
   }

   public void setMessage(final String message) {
       this.message = message;
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
         return id.equals(((Comment) that).id);
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

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (message != null && !message.trim().isEmpty())
         result += "message: " + message;
      return result;
   }

}