package com.techletsolutions.jobcrawler.sites;

import java.util.ArrayList;
import java.util.List;

public class JobSites {
	private final List<WebSite> jobsites = new ArrayList<WebSite>();

	public JobSites() {
		jobsites.add(new WebSite("INDEED.COM", "http://rss.indeed.com/rss?q=<CATEGORY>&sort=date"));
		// jobsites.add(new WebSite("SIMPLYHIRED.COM", "http://www.simplyhired.com/a/job-feed/rss/q-<CATEGORY>/fdb-1"));
	}
	
	public List<WebSite> getJobsites() {
		return jobsites;
	}
}
