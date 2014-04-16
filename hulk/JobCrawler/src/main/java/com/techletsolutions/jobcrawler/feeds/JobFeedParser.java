package com.techletsolutions.jobcrawler.feeds;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobFeedParser {
	final static Logger logger = LoggerFactory.getLogger(JobFeedParser.class);
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String SOURCE = "source";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	final URL url;
	final String category;
	final String source;

	public JobFeedParser(String feedUrl, String category, String source) {
		try {
			this.url = new URL(feedUrl);
			this.category = category;
			this.source = source;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public JobFeed readFeed() {
		JobFeed feed = null;
		try {
			boolean isFeedHeader = true;
			// Set header values initial to the empty string
			String description = "";
			String title = "";
			String source = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = read();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();
					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new JobFeed(title, link, description, language,copyright, pubdate);
						}
						event = eventReader.nextEvent();
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						break;
					case SOURCE:
						source = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						break;
					case AUTHOR:
						author = getCharacterData(event, eventReader);
						break;
					case PUB_DATE:
						pubdate = getCharacterData(event, eventReader);
						break;
					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						JobItem jobItem = new JobItem();
						jobItem.setAuthor(author);
						jobItem.setDescription(description);
						jobItem.setPublisher(source);
						jobItem.setGuid(guid);
						jobItem.setLink(link);
						jobItem.setTitle(title);
						jobItem.setPubDate(pubdate);
						jobItem.setCategory(category.toUpperCase());
						//setExtraDetails(jobItem);
						feed.getJobItems().add(jobItem);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setExtraDetails(JobItem jobItem) {
		logger.info("Getting extra details: Job title: {}",  jobItem.getTitle());
		Document doc = null;
		try {
			doc = Jsoup.connect(jobItem.getLink()).get();
		} catch (IOException e) {
			logger.error("There wan an error while getting url contents through jsoup.", e);
			return;
		}
		if (source.equalsIgnoreCase("INDEED.COM")) {
			jobItem.setTitle(getFirstElementText(doc, "b.jobtitle", jobItem.getTitle()));
			jobItem.setPublisher(getFirstElementText(doc, "span.company", jobItem.getPublisher()));
			jobItem.setLocation(getFirstElementText(doc, "span.location", jobItem.getLocation()));
			jobItem.setDescription(getFirstElementText(doc, "span.summary", jobItem.getDescription()));
		} else if (source.equalsIgnoreCase("SIMPLYHIRED.COM")) {
			jobItem.setTitle(getFirstElementText(doc, "span.title", jobItem.getTitle()));
			jobItem.setPublisher(getFirstElementText(doc, "p.company", jobItem.getPublisher()).replace("Company:", "").trim());
			jobItem.setLocation(getFirstElementText(doc, "p.location", jobItem.getLocation()).replace("Location:", "").trim());
			jobItem.setDescription(getFirstElementText(doc, "p.description", jobItem.getDescription()));
		} else {
			logger.info("Defaulting to doing nothing...");
		}
	}
	
	private String getFirstElementText(Document doc, String cssQuery, String oldValue) {
		Elements els;
		els = doc.select(cssQuery);
		if (els != null && !els.isEmpty()) {
			Element el = els.get(0);
			return el.text().trim();
		}
		return oldValue;
	}
}