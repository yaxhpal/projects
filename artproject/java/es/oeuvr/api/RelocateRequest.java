package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/*
[06/11/2013 02:01:41] Joel Baker: — Current location.location
— Current location.country
— Current location.wall
— Current location.room
— New location
— To field; email address that the email is sent to
— Message field; any comments
[06/11/2013 02:02:59] Joel Baker: would be great, thanks Sean
[06/11/2013 02:03:29] Joel Baker: but any of these may be left blank
[06/11/2013 02:03:37] Joel Baker: apart from the email address
*/
public class RelocateRequest {
	public Long id;
	public Long artworkId;
	public String message;
	public Long authorId;
}
