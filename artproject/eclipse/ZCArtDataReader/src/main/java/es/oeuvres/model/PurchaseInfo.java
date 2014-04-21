package es.oeuvres.model;

public class PurchaseInfo {
	public String commission; 
	public String purchaseDate; 
	public String insurance; 
	public String price; 
	public String purchasedFrom; 
	public String source; 
	public String artistRR; 
	public String vat; 
	public String importTax; 
	public String seymoursfeeVat; 
	public String valuation; 
	public String valuationBy; 
	public String valuationDate;
	
	public PurchaseInfo() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		StringBuilder object = new StringBuilder();
		object.append("commission: ");
		object.append(commission + "\n");
		object.append("purchaseDate: ");
		object.append(purchaseDate + "\n");
		object.append("insurance: ");
		object.append(insurance + "\n");
		object.append("price: ");
		object.append(price + "\n");
		object.append("purchasedFrom: ");
		object.append(purchasedFrom + "\n");
		object.append("source: ");
		object.append(source  + "\n");
		object.append("artistRR: ");
		object.append(artistRR  + "\n");
		object.append("vat: ");
		object.append(vat + "\n");
		object.append("importTax: ");
		object.append(importTax  + "\n");
		object.append("seymoursfeeVat: ");
		object.append(seymoursfeeVat + "\n");
		object.append("valuation: ");
		object.append(valuation + "\n");
		object.append("valuationBy: ");
		object.append(valuationBy + "\n");
		object.append("valuationDate: ");
		object.append(valuationDate + "\n");
		return object.toString();
	}
}
