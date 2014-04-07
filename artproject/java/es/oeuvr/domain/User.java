package es.oeuvr.domain;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.oeuvr.domain.Organisation;

import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.Past;

@Entity
@XmlRootElement
@Cacheable
public class User implements Serializable
{
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @NotNull
   @Size(min = 1, max = 100)
   private String firstName;

   @Column
   @NotNull
   @Size(min = 1, max = 100)
   private String lastName;

   @Temporal(TemporalType.DATE)
   @Past
   private Date dateOfBirth;

   @Column
   private boolean active;

   @Column(unique = true)
   @NotNull
   @Size(min = 3, max = 100)
   private String email;

   @Column
   private String password;

   @Column
   private String securityCode;

   @Column
   private String permissions;

   @Column
   private String sessionToken;
   
   @ManyToOne
   private Organisation organisation;

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
         return id.equals(((User) that).id);
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

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName(final String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return this.lastName;
   }

   public void setLastName(final String lastName)
   {
      this.lastName = lastName;
   }

   public Date getDateOfBirth()
   {
      return this.dateOfBirth;
   }

   public void setDateOfBirth(final Date dateOfBirth)
   {
      this.dateOfBirth = dateOfBirth;
   }

   public boolean isActive()
   {
      return this.active;
   }

   public void setActive(final boolean active)
   {
      this.active = active;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public String getSecurityCode()
   {
      return this.securityCode;
   }

   public void setSecurityCode(final String securityCode)
   {
      this.securityCode = securityCode;
   }

   public String getPermissions()
   {
      return this.permissions;
   }

   public void setPermissions(final String permissions)
   {
      this.permissions = permissions;
   }

   public Organisation getOrganisation()
   {
      return this.organisation;
   }

   public void setOrganisation(final Organisation organisation)
   {
      this.organisation = organisation;
   }
   
   public String getSessionToken() {
		return sessionToken;
	}

   public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (firstName != null && !firstName.trim().isEmpty())
         result += "firstName: " + firstName;
      if (lastName != null && !lastName.trim().isEmpty())
         result += ", lastName: " + lastName;
      result += ", active: " + active;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (securityCode != null && !securityCode.trim().isEmpty())
         result += ", securityCode: " + securityCode;
      if (permissions != null && !permissions.trim().isEmpty())
         result += ", permissions: " + permissions;
      return result;
   }
}