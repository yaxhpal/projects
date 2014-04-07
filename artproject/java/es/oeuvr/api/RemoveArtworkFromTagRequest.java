package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RemoveArtworkFromTagRequest {
	public Long artworkId;
	public Long tagId;
}
