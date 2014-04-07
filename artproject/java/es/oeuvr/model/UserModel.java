package es.oeuvr.model;

import java.util.HashMap;
import java.util.Map;

import es.oeuvr.domain.User;

public class UserModel {

	public Long id;
	public String email;
	public Map<String, String> name;
	public String token;

	public UserModel(User u) {
		if (u == null) {
			id = 0L;
		} else {
			id = u.getId();
			email = u.getEmail();
			name = new HashMap<String, String>();
			name.put("first", u.getFirstName());
			name.put("last", u.getLastName());
			token = u.getSessionToken();
		}
	}
	
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
		UserModel other = (UserModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
