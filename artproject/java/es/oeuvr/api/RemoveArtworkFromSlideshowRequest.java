package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RemoveArtworkFromSlideshowRequest {
	public Long artworkId;
	public Long slideshowId;
}
