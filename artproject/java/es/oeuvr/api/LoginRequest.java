package es.oeuvr.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginRequest {

    public String username;

    public String password;

    public LoginRequest(){}
}
