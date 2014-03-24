package com.techletsolutions.jobcrawler.feeds;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

/*
 * Represents one RSS message
 */
public class JobItem {
	
	private String category;
	private String title;
	private String description;
	private String link;
	private String author;
	private String guid;
	private String pubDate;
	private String publisher;
	private String location;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title =  new HtmlToPlainText().getPlainText(Jsoup.parse(title));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = new HtmlToPlainText().getPlainText(Jsoup.parse(description));
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public String toString() {
		return "FeedMessage [title=" + title + ", description=" + description
				+ ", link=" + link + ", author=" + author + ", guid=" + guid
				+ ", pubDate =" + pubDate + ", source=" + getPublisher() + "]";
	}
}
