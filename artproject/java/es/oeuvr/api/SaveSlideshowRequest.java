package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaveSlideshowRequest {
	public Long id;
	public String name;
	public Long userId;
}
