package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddArtworkToTagRequest {
	public Long artworkId;
	public Long tagId;
	public String name;
	public Long userId;
}
