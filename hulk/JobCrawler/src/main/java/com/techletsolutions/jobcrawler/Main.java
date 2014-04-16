package com.techletsolutions.jobcrawler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techletsolutions.jobcrawler.db.DBAgent;
import com.techletsolutions.jobcrawler.feeds.JobFeed;
import com.techletsolutions.jobcrawler.feeds.JobFeedParser;
import com.techletsolutions.jobcrawler.feeds.JobItem;
import com.techletsolutions.jobcrawler.sites.Categories;
import com.techletsolutions.jobcrawler.sites.JobSites;
import com.techletsolutions.jobcrawler.sites.WebSite;

public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	final static DBAgent dbAgent = new DBAgent();

	public static void main(String[] args) throws Exception {
		// checks for the expected user input
		if(args.length == 0) {
			usage(); // provides usage information to inform the user of the correct way to work
		}
		logger.info("Entering into main...");
		readAllFeeds();
		logger.info("exiting from main...");
	}

	private static void usage() {
		logger.info("This is Jobcrawler.");
		logger.info("You need not to provide any argument to this program.");
		logger.info("Anyways, you did. So, I say Hello :");
	}

	public static void readAllFeeds() {
		JobFeedParser parser = null;
		JobFeed jobFeed = null;
		List<String> categories = (new Categories()).getCategories();
		List<WebSite> websites = (new JobSites()).getJobsites();
		for (WebSite webSite : websites) {
			for (String category: categories) {
				parser = new JobFeedParser(webSite.getUrl().replace("<CATEGORY>", category), category, webSite.getName());
				jobFeed = parser.readFeed();
				for (JobItem jobItem : jobFeed.getJobItems()) {
					dbAgent.saveJobItem(jobItem, webSite.getName());
				}
			}
		}
	}
}

