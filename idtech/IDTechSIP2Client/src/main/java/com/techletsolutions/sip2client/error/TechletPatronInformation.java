package com.techletsolutions.sip2client.error;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PatronInformation")
//@XmlType(propOrder = { "name", "email", "homeAddress" , "finesAndCharges" , "chargedItems" })
public class TechletPatronInformation {

	private String id;
	String name;
	String email;
	String homeAddress;
	String finesAndCharges;
	String patronStatus;
	String language;
	String transactionDate;
	int holdItemsCount;
	int overdueItemsCount;
	int chargedItemsCount;
	int fineItemsCount;
	int recallItemsCount;
	int unavailableHoldsCount;
	List<TechletItemInformation> chargedItems;

	public TechletPatronInformation() {
		// Initialize the list
		chargedItems = new ArrayList<TechletItemInformation>();
	}
	
	@XmlAttribute (name = "identifier")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name = "homeAdress")
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@XmlElement(name = "finesAndCharges")
	public String getFinesAndCharges() {
		return finesAndCharges;
	}

	public void setFinesAndCharges(String finesAndCharges) {
		this.finesAndCharges = finesAndCharges;
	}

	@XmlElement(name = "patronStatus")
	public String getPatronStatus() {
		return patronStatus;
	}

	public void setPatronStatus(String patronStatus) {
		this.patronStatus = patronStatus;
	}

	@XmlElement(name = "language")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@XmlElement(name = "transactionDate")
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	@XmlElement(name = "holdItemsCount")
	public int getHoldItemsCount() {
		return holdItemsCount;
	}

	public void setHoldItemsCount(int holdItemsCount) {
		this.holdItemsCount = holdItemsCount;
	}

	@XmlElement(name = "overdueItemsCount")
	public int getOverdueItemsCount() {
		return overdueItemsCount;
	}

	public void setOverdueItemsCount(int overdueItemsCount) {
		this.overdueItemsCount = overdueItemsCount;
	}

	@XmlElement(name = "chargedItemsCount")
	public int getChargedItemsCount() {
		return chargedItemsCount;
	}

	public void setChargedItemsCount(int chargedItemsCount) {
		this.chargedItemsCount = chargedItemsCount;
	}

	@XmlElement(name = "fineItemsCount")
	public int getFineItemsCount() {
		return fineItemsCount;
	}

	public void setFineItemsCount(int fineItemsCount) {
		this.fineItemsCount = fineItemsCount;
	}

	@XmlElement(name = "recallItemsCount")
	public int getRecallItemsCount() {
		return recallItemsCount;
	}

	public void setRecallItemsCount(int recallItemsCount) {
		this.recallItemsCount = recallItemsCount;
	}

	@XmlElement(name = "unavailableHoldsCount")
	public int getUnavailableHoldsCount() {
		return unavailableHoldsCount;
	}

	public void setUnavailableHoldsCount(int unavailableHoldsCount) {
		this.unavailableHoldsCount = unavailableHoldsCount;
	}

	@XmlElementWrapper(name = "issuedItems")
	@XmlElement(name = "issuedItem")
	public List<TechletItemInformation> getChargedItems() {
		return chargedItems;
	}

	public void setChargedItems(List<TechletItemInformation> chargedItems) {
		this.chargedItems = chargedItems;
	}
	
	public void addChargedItem(TechletItemInformation chargedItem) {
		chargedItems.add(chargedItem);
	}

	@Override
	public String toString() {
		return "TechletPatronInformation [name=" + name + ", email=" + email
				+ ", homeAddress=" + homeAddress + ", finesAndCharges="
				+ finesAndCharges + ", patronStatus=" + patronStatus
				+ ", language=" + language + ", transactionDate="
				+ transactionDate + ", holdItemsCount=" + holdItemsCount
				+ ", overdueItemsCount=" + overdueItemsCount
				+ ", chargedItemsCount=" + chargedItemsCount
				+ ", fineItemsCount=" + fineItemsCount + ", recallItemsCount="
				+ recallItemsCount + ", unavailableHoldsCount="
				+ unavailableHoldsCount + ", chargedItems="
				+ chargedItems.toString() + "]";
	}
}