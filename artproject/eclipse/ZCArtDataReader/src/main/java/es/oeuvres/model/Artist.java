package es.oeuvres.model;

public class Artist {
	public String artistsNo;
	public String Type; ; 
	public String about; 
	public String link; 
	public String movements; 
	public String artistCountry; 
	public String firstName; 
	public String lastName; 
	public String yearOfBirth; 
	public String yearOfDeath; 
	public String hasPhoto;
	
	public Artist() {
		// TODO Auto-generated constructor stub
	}
	/*
	@Override
	public String toString() {
		StringBuilder object = new StringBuilder();
		object.append("artistsNo: ");
		object.append(artistsNo + "\n");
		object.append("Type: ");
		object.append(Type + "\n");
		object.append("about: ");
		object.append(about + "\n");
		object.append("link: ");
		object.append(link + "\n");
		object.append("movements: ");
		object.append(movements + "\n");
		object.append("country: ");
		object.append(country  + "\n");
		object.append("firstName: ");
		object.append(firstName + "\n");
		object.append("lastName: ");
		object.append(lastName  + "\n");
		object.append("yearOfBirth: ");
		object.append(yearOfBirth + "\n");
		object.append("yearOfDeath: ");
		object.append(yearOfDeath  + "\n");
		object.append("hasPhoto: ");
		object.append(hasPhoto + "\n");
		return object.toString();
	}*/
	
	@Override
	public String toString() {
		return firstName+" "+lastName +" "+artistCountry+" "+yearOfBirth+" "+yearOfDeath;
	}
}