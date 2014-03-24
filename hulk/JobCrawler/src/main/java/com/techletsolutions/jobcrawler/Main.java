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
	// static final String DB_URL = "jdbc:mysql://localhost/hulk";
	static final String DB_URL = "jdbc:mysql://192.168.1.21/hulk";
	// Database credentials
//	static final String USER = "root";
//	static final String PASS = "";
	static final String USER = "aapc";
	static final String PASS = "aapcpwd";
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
		//calculateScores();
		closeDBConnection();
		logger.info("exiting from main...");
	}

	private static void usage() {
		// TODO Auto-generated method stub
		
	}
	

	public static long updateUsers (String email, long createdby, long updatedby) {
		// user_type = R (Recruiter)
		// status = I
		// password = "default"
		// deleted = 'N'
		// created = Timestamp.getTime();
		// updated = Timestamp.getTime();
		// email = TBP
		// createdby = TBP
		// updatedby = TBP
		try {
			String sql = "INSERT into users (email, user_type, status, password, deleted, created, updated, createdby, updatedby) "
					+ "value(?,?,?,?,?,?,?,?,?);";
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, "R");
			preparedStatement.setString(3, "I");
			preparedStatement.setString(4, "default");
			preparedStatement.setString(5, "N");
			preparedStatement.setLong(6, (new Date()).getTime());
			preparedStatement.setLong(7, (new Date()).getTime());
			preparedStatement.setLong(8, createdby);
			preparedStatement.setLong(9, updatedby);
			preparedStatement.executeUpdate();
			preparedStatement.getGeneratedKeys();
			ResultSet rs = preparedStatement.getGeneratedKeys();
		    rs.next();
		    return rs.getLong(1);
		} catch (Exception e) {
			logger.error("Can not update/insert into users table.", e);
		}
		return 0L;
	}
	
	public static boolean updateLocation(String name, long createdby, long updatedby) {
		// deleted = 'N'
		// created = Timestamp.getTime();
		// updated = Timestamp.getTime();
		// name    = TBP
		// createdby = TBP
		// updatedby = TBP
		try {
			String sql = "INSERT into cnd (name, group_name, deleted, created, updated, createdby, updatedby) "
					+ "values (?,?,?,?,?,?,?);";
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, "City");
			preparedStatement.setString(3, "N");;
			preparedStatement.setLong(4, (new Date()).getTime());
			preparedStatement.setLong(5, (new Date()).getTime());
			preparedStatement.setLong(6, createdby);
			preparedStatement.setLong(7, updatedby);
			return preparedStatement.execute();
		} catch (Exception e) {
			logger.error("Can not update/insert into cnd table.", e);
		}
		return false;
	}
	
	public static boolean updateJobs (String title, String description, String source, long user_id, long createdby, long updatedby) {
		// user_type = R (Recruiter)
		// status = I
		// deleted = 'N'
		// created = Timestamp.getTime();
		// updated = Timestamp.getTime();
		// title = TBP
		// source = TBP
		// description = TBP
		// createdby = TBP
		// updatedby = TBP
		try {
			String sql = "INSERT into jobs (user_id, title, location, description, status, source, deleted, created, updated, createdby, updatedby) "
					+ "value(?,?,?,?,?,?,?,?,?,?,?);";
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			preparedStatement = conn.prepareStatement(sql);
			// preparedStatement.setLong(1, user_id);
			 preparedStatement.setLong(1, 1007L);
			preparedStatement.setString(2, title);
			// preparedStatement.setLong(3, location);
			preparedStatement.setLong(3, 5010);
			preparedStatement.setString(4, description);
			preparedStatement.setString(5, "I");
			preparedStatement.setString(6, source);
			preparedStatement.setString(7, "N");
			preparedStatement.setLong(8, 1393491914L);
			preparedStatement.setLong(9, 1393491914L);
//			preparedStatement.setLong(10, createdby);
//			preparedStatement.setLong(11, updatedby);
			preparedStatement.setLong(10, 1000L);
			preparedStatement.setLong(11, 1000L);
			return preparedStatement.execute();
		} catch (Exception e) {
			logger.error("Can not update/insert into jobs table.", e);
		}
		return false;
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
					saveJobItem(jobItem, webSite.getName());
					boolean status = updateJobs(jobItem.getTitle(), jobItem.getDescription(), webSite.getName(), 0L, 0L, 0L);
					if (status) {
						logger.info("Jobs table updated successfully");
					} else {
						logger.info("Jobs table couldn't be updated. :((((");
					}
				}
			}
		}
	}

	public static void saveJobItem(JobItem jobItem, String source) {
		preparedStatement = null;
		try {
			if (preparedStatement == null) {
				String sql = "INSERT INTO jobfeeds"
							+ " (jobid, source, title, description, "
						    + "link, pubdate, publisher, location, category)"
							+ " VALUES (?,?,?,?,?,?,?,?,?);";
				preparedStatement = conn.prepareStatement(sql);
			}
			preparedStatement.setString(1, jobItem.getGuid());
			preparedStatement.setString(2, source);
			preparedStatement.setString(3, jobItem.getTitle());
			preparedStatement.setString(4, jobItem.getDescription());
			preparedStatement.setString(5, jobItem.getLink());
			preparedStatement.setString(7, jobItem.getPublisher());
			preparedStatement.setString(8, jobItem.getLocation());
			preparedStatement.setString(9, jobItem.getCategory());
			try {
				SimpleDateFormat pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date date = pubDate.parse(jobItem.getPubDate());
				preparedStatement.setTimestamp(6, new Timestamp(date.getTime()));
			} catch (IllegalArgumentException  exp) {
				exp.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
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

