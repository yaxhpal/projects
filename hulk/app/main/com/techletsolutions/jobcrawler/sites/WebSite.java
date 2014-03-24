package com.techletsolutions.jobcrawler.sites;

public class WebSite {
	private String name;
	private String url;
	public WebSite(String name, String url) {
		this.setName(name);
		this.setUrl(url);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}