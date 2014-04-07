package es.oeuvr.model;

import java.util.Date;

import es.oeuvr.domain.Comment;
import es.oeuvr.domain.User;

public class CommentModel {
	public CommentModel(Comment c) {
		id=c.getId();
		message = c.getMessage();
		date = c.getDate();
		User a = c.getAuthor();
		if (a!=null) {
			authorId = a.getId();
			author = a.getFirstName()+" "+a.getLastName();
		}
	}

	public Long id;
	public String message;
	public Date date;
	public Long authorId;
	public String author;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentModel other = (CommentModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
