package com.techletsolutions.jobcrawler.similarity;

public class ScoreItem {
	
	private int id;
	private String title;
	private String similarItems;
	
	public ScoreItem(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSimilarItems() {
		return similarItems;
	}
	
	public void setSimilarItems(String similarItems) {
		this.similarItems = similarItems;
	}
}
