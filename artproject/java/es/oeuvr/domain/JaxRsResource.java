package es.oeuvr.domain;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "resource")
public class JaxRsResource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute String method;
	@XmlValue String uri;

	public JaxRsResource() {}

	public JaxRsResource(String method, String uri) {
		this.method = method;
		this.uri = uri;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JaxRsResource that = (JaxRsResource) o;

		if (method != null ? !method.equals(that.method) : that.method != null) return false;
		if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = method != null ? method.hashCode() : 0;
		result = 31 * result + (uri != null ? uri.hashCode() : 0);
		return result;
	}
}