package es.oeuvr.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
public class Wrapper {
	public Wrapper(String name, List values) {
		this.name=name;
		this.values=values;
	}
	public String name;
	public List values;
}
