package com.techletsolutions.jobcrawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

import com.techletsolutions.jobcrawler.feeds.JobFeed;
import com.techletsolutions.jobcrawler.feeds.JobFeedParser;
import com.techletsolutions.jobcrawler.feeds.JobItem;
import com.techletsolutions.jobcrawler.similarity.ScoreItem;
import com.techletsolutions.jobcrawler.sites.Categories;
import com.techletsolutions.jobcrawler.sites.JobSites;
import com.techletsolutions.jobcrawler.sites.WebSite;

public class Main {
	final static Logger logger = LoggerFactory.getLogger(Main.class);
	// Database URL
	static final String DB_URL = "jdbc:mysql://localhost/hulk";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	// Database connection and statements
	static Connection conn = null;
	static PreparedStatement preparedStatement = null;
	static Statement statement = null;


	public static void main(String[] args) throws Exception {
		// checks for the expected user input
		if(args.length == 0) {
			// provides usage information to inform the user of the correct way
			// to work
			usage();
		}
		logger.info("Entering into main...");
		openDBConnection();
		readAllFeeds();
		// calculateScores();
		closeDBConnection();
		logger.info("exiting from main...");
	}

	private static void usage() {
		// TODO Auto-generated method stub
		
	}

	public static void readAllFeeds() {
		JobFeedParser parser = null;
		JobFeed jobFeed = null;
		List<String> categories = (new Categories()).getCategories();
		List<WebSite> websites = (new JobSites()).getJobsites();
		for (WebSite webSite : websites) {
			for (String category: categories) {
				parser = new JobFeedParser(webSite.getUrl().replace("<CATEGORY>", category), category);
				jobFeed = parser.readFeed();
				for (JobItem jobItem : jobFeed.getJobItems()) {
					saveJobItem(jobItem, webSite.getName());

				}
			}
		}
	}

	public static void saveJobItem(JobItem jobItem, String source) {
		preparedStatement = null;
		try {
			if (preparedStatement == null) {
				String sql = "INSERT INTO jobfeeds (jobid, source, title, description, link, pubdate, publisher, category)"
						+ " VALUES (?,?,?,?,?,?,?,?);";
				preparedStatement = conn.prepareStatement(sql);
			}
			preparedStatement.setString(1, jobItem.getGuid());
			preparedStatement.setString(2, source);
			preparedStatement.setString(3, jobItem.getTitle());
			preparedStatement.setString(4, jobItem.getDescription());
			preparedStatement.setString(5, jobItem.getLink());
			try {
				SimpleDateFormat pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date date = pubDate.parse(jobItem.getPubDate());
				preparedStatement.setTimestamp(6, new Timestamp(date.getTime()));
			} catch (IllegalArgumentException  exp) {
				exp.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			preparedStatement.setString(7, jobItem.getSource());
			preparedStatement.setString(8, jobItem.getCategory());
			preparedStatement.execute();
		} catch (SQLException e) {
			if (e.getLocalizedMessage().contains("jobid_UNIQUE")) {
				logger.info("Job with id: "+jobItem.getGuid()+" already exists.");
			} else {
				e.printStackTrace();
			}
		}
	}


	@SuppressWarnings("unused")
	private static void calculateScores() throws Exception {
		logger.info("Entering into main...");
		List <ScoreItem> scoreItemsList = new ArrayList<ScoreItem>();
		String sql = "SELECT id, title FROM jobfeeds;";
		if (statement == null) {
			statement = conn.createStatement();
		}
		ResultSet rs = statement.executeQuery(sql);
		int id;
		String title;
		while(rs.next()){
			id  = rs.getInt("id");
			title = rs.getString("title");
			scoreItemsList.add( new ScoreItem(id,title));
		}

		AbstractStringMetric metric = new Levenshtein();
		float result;
		for(ScoreItem item:scoreItemsList) {
			for (ScoreItem cItems: scoreItemsList) {
				result = metric.getSimilarity(item.getTitle(), cItems.getTitle());
				if (result > 0.8f && result < 1.0f && item.getId() != cItems.getId() ) {
					System.out.println("Title-1: "+ item.getTitle()+" is "+ (result * 100 / 1) + "%" +" similar to Title-2: " + cItems.getTitle());
				}
			}
		}
	}

	public static Connection openDBConnection() throws SQLException {
		if (conn == null ||  conn.isClosed()){
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} 
		return conn;
	}

	public static void closeDBConnection() throws SQLException {
		if (preparedStatement != null && !preparedStatement.isClosed()) {
			preparedStatement.close();
		}

		if (conn != null && !conn.isClosed()){
			conn.close();
		}
	}
}

