package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaveCommentRequest {
	public Long id;
	public Long artworkId;
	public String message;
	public Long authorId;
}
