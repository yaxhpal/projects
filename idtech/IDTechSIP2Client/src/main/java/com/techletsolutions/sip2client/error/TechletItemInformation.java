package com.techletsolutions.sip2client.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ItemInformation")
//@XmlType(propOrder = { "itemIdentifier", "titleIdentifier", "dueDate" })
public class TechletItemInformation {

	String itemIdentifier;
	String titleIdentifier;
	String dueDate;
	double feeAmount;
	String owner;
	String permanentLocation;
	String currentLocation;
	String screenMessage;
	String mediaType;
	String itemProperties;

	@XmlElement(name = "id")
	public String getItemIdentifier() {
		return itemIdentifier;
	}

	public void setItemIdentifier(String itemIdentifier) {
		this.itemIdentifier = itemIdentifier;
	}
	
	@XmlElement(name = "title")
	public String getTitleIdentifier() {
		return titleIdentifier;
	}

	public void setTitleIdentifier(String titleIdentifier) {
		this.titleIdentifier = titleIdentifier;
	}

	@XmlElement(name = "dueDate")
	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	@XmlElement(name = "feeAmount")
	public double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}

	@XmlElement(name = "owner")
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@XmlElement(name = "permanentLocation")
	public String getPermanentLocation() {
		return permanentLocation;
	}

	public void setPermanentLocation(String permanentLocation) {
		this.permanentLocation = permanentLocation;
	}

	@XmlElement(name = "currentLocation")
	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	@XmlElement(name = "screenMessage")
	public String getScreenMessage() {
		return screenMessage;
	}

	public void setScreenMessage(String screenMessage) {
		this.screenMessage = screenMessage;
	}

	@XmlElement(name = "mediaType")
	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	@XmlElement(name = "itemProperties")
	public String getItemProperties() {
		return itemProperties;
	}

	public void setItemProperties(String itemProperties) {
		this.itemProperties = itemProperties;
	}

	@Override
	public String toString() {
		return "TechletItemInformation [itemIdentifier=" + itemIdentifier
				+ ", titleIdentifier=" + titleIdentifier + ", dueDate="
				+ dueDate + ", feeAmount=" + feeAmount + ", owner=" + owner
				+ ", permanentLocation=" + permanentLocation
				+ ", currentLocation=" + currentLocation + ", screenMessage="
				+ screenMessage + ", mediaType=" + mediaType
				+ ", itemProperties=" + itemProperties + "]";
	}
}
