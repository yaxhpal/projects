package es.oeuvr.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Cacheable
public class Category implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String description;
	private String cndGroup;
	private Long parent;
	private Integer sequence;
	private Character status;
	private Integer created;
	private Integer updated;

	public Category() {
	}

	public Category(long id) {
		this.id = id;
	}

	public Category(long id, String name, String description, String cndGroup,
			Long parent, Integer sequence, Character status, Integer created,
			Integer updated) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.cndGroup = cndGroup;
		this.parent = parent;
		this.sequence = sequence;
		this.status = status;
		this.created = created;
		this.updated = updated;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "cnd_group", length = 64)
	public String getCndGroup() {
		return this.cndGroup;
	}

	public void setCndGroup(String cndGroup) {
		this.cndGroup = cndGroup;
	}

	@Column(name = "parent")
	public Long getParent() {
		return this.parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	@Column(name = "sequence")
	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Column(name = "status", length = 1)
	public Character getStatus() {
		return this.status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Column(name = "created")
	public Integer getCreated() {
		return this.created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	@Column(name = "updated")
	public Integer getUpdated() {
		return this.updated;
	}

	public void setUpdated(Integer updated) {
		this.updated = updated;
	}

}