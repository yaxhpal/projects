package com.techletsolutions.hulk.model;

import com.techletsolutions.db.EntityManager;

public class CnDEntity implements Comparable<Object> {
	
	protected Long id;
	
	protected String name;
	
	protected Long parent;
	
	protected String group_name;
	
	protected String description;
	
	protected long sequence;
	
	protected String option1;
	
	protected String option2;
	
	protected char deleted;
	
	protected long created;
	
	protected long updated;
	
	protected long createdby;
	
	protected long updatedby;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public char getDeleted() {
		return deleted;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public long getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(long updatedby) {
		this.updatedby = updatedby;
	}
	
	public Long save(EntityManager em) {
		Long result = -1L;
		if (parent != null && parent != 0L) {
			result = em.saveCnDEntity(this);
			id = result;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		if (id != null && id != 0L) {
			return id.hashCode();
		}
		if (name != null) {
			return name.hashCode();
		}
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((CnDEntity) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int compareTo(Object o) {
		CnDEntity entity = (CnDEntity)o;
			
		if (this.id == null || entity.getId() == null) {
			return -1;
		}
		
		int res = String.CASE_INSENSITIVE_ORDER.compare(name, entity.name);
        if (res == 0) {
            res = name.compareTo(entity.name);
        }
        
        return res;
	}
	
	@Override
	public String toString() {
			return name;
	}
}
