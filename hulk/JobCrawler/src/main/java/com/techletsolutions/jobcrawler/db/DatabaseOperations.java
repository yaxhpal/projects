package com.techletsolutions.jobcrawler.db;

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

import com.techletsolutions.jobcrawler.JobConfig;
import com.techletsolutions.jobcrawler.feeds.JobItem;
import com.techletsolutions.jobcrawler.similarity.ScoreItem;

public class DatabaseOperations {
	private final static Logger logger = LoggerFactory.getLogger(DatabaseOperations.class);
	private static Connection conn = null;
	private static  PreparedStatement preparedStatementFeeds 	 = null;
	private static  PreparedStatement preparedStatementJobs 	 = null;
	private static  PreparedStatement preparedStatementUsers 	 = null;
	private static  PreparedStatement preparedStatementLocations = null;
	private static  Statement statement = null;

	public void saveJobItem(JobItem jobItem, String source) {
		try {
			if (preparedStatementFeeds == null) {
				String sql = "INSERT INTO jobfeeds"
						+ " (jobid, source, title, description, "
						+ "link, pubdate, publisher, location, category)"
						+ " VALUES (?,?,?,?,?,?,?,?,?);";
				preparedStatementFeeds = conn.prepareStatement(sql);
			}
			preparedStatementFeeds.setString(1, jobItem.getGuid());
			preparedStatementFeeds.setString(2, source);
			preparedStatementFeeds.setString(3, jobItem.getTitle());
			preparedStatementFeeds.setString(4, jobItem.getDescription());
			preparedStatementFeeds.setString(5, jobItem.getLink());
			preparedStatementFeeds.setString(7, jobItem.getPublisher());
			preparedStatementFeeds.setString(8, jobItem.getLocation());
			preparedStatementFeeds.setString(9, jobItem.getCategory());
			try {
				SimpleDateFormat pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date date = pubDate.parse(jobItem.getPubDate());
				preparedStatementFeeds.setTimestamp(6, new Timestamp(date.getTime()));
			} catch (IllegalArgumentException  exp) {
				exp.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			preparedStatementFeeds.execute();

			// Also, save the job into jobs table
			updateJobs(jobItem.getTitle(), jobItem.getDescription(), source, 0L, 0L, 0L);

		} catch (SQLException e) {
			if (e.getLocalizedMessage().contains("jobid_UNIQUE")) {
				logger.info("Job with id: "+jobItem.getGuid()+" already exists.");
			} else {
				e.printStackTrace();
			}
		}
	}



	/**
	 * 	// user_type = R (Recruiter)
		// status = I
		// deleted = 'N'
		// created = Timestamp.getTime();
		// updated = Timestamp.getTime();
		// title = TBP
		// source = TBP
		// description = TBP
		// createdby = TBP
		// updatedby = TBP
	 * @param title
	 * @param description
	 * @param source
	 * @param user_id
	 * @param createdby
	 * @param updatedby
	 */
	public static void updateJobs (String title, String description, String source, long user_id, long createdby, long updatedby) {

		try {
			String sql = "INSERT into jobs (user_id, title, location, description, "
					+ "status, source, deleted, created, updated, createdby, updatedby) "
					+ "value(?,?,?,?,?,?,?,?,?,?,?);";
			if (!(preparedStatementJobs == null || preparedStatementJobs.isClosed())) {
				preparedStatementJobs.close();
			}
			preparedStatementJobs = conn.prepareStatement(sql);
			// preparedStatement.setLong(1, user_id);
			preparedStatementJobs.setLong(1, 1007L);
			preparedStatementJobs.setString(2, title);
			// preparedStatement.setLong(3, location);
			preparedStatementJobs.setLong(3, 5010);
			preparedStatementJobs.setString(4, description);
			preparedStatementJobs.setString(5, "I");
			preparedStatementJobs.setString(6, source);
			preparedStatementJobs.setString(7, "N");
			preparedStatementJobs.setLong(8, 1393491914L);
			preparedStatementJobs.setLong(9, 1393491914L);
			// preparedStatement.setLong(10, createdby);
			// preparedStatement.setLong(11, updatedby);
			preparedStatementJobs.setLong(10, 1000L);
			preparedStatementJobs.setLong(11, 1000L);
			preparedStatementJobs.execute();
			logger.info("Jobs table updated successfully");
		} catch (Exception e) {
			logger.error("Can not update/insert into jobs table.", e);
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

	/**
	 * 		// user_type = R (Recruiter)
		// status = I
		// password = "default"
		// deleted = 'N'
		// created = Timestamp.getTime();
		// updated = Timestamp.getTime();
		// email = TBP
		// createdby = TBP
		// updatedby = TBP
	 * @param email
	 * @param createdby
	 * @param updatedby
	 * @return
	 */
	public static long updateUsers (String email, long createdby, long updatedby) {

		try {
			String sql = "INSERT into users (email, user_type, status, password, deleted,"
					+ " created, updated, createdby, updatedby) value(?,?,?,?,?,?,?,?,?);";
			if (preparedStatementUsers != null && !preparedStatementUsers.isClosed()) {
				preparedStatementUsers.close();
			}
			preparedStatementUsers = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatementUsers.setString(1, email);
			preparedStatementUsers.setString(2, "R");
			preparedStatementUsers.setString(3, "I");
			preparedStatementUsers.setString(4, "default");
			preparedStatementUsers.setString(5, "N");
			preparedStatementUsers.setLong(6, (new Date()).getTime());
			preparedStatementUsers.setLong(7, (new Date()).getTime());
			preparedStatementUsers.setLong(8, createdby);
			preparedStatementUsers.setLong(9, updatedby);
			preparedStatementUsers.executeUpdate();
			preparedStatementUsers.getGeneratedKeys();
			ResultSet rs = preparedStatementUsers.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (Exception e) {
			logger.error("Can not update/insert into users table.", e);
		}
		return 0L;
	}


	/**
	 * 		// deleted = 'N'
		// created = Timestamp.getTime();
		// updated = Timestamp.getTime();
		// name    = TBP
		// createdby = TBP
		// updatedby = TBP
	 * @param name
	 * @param createdby
	 * @param updatedby
	 * @return
	 */
	public static boolean updateLocation(String name, long createdby, long updatedby) {
		try {
			String sql = "INSERT into cnd (name, group_name, deleted, created, updated, "
					+ "createdby, updatedby) values (?,?,?,?,?,?,?);";
			if (preparedStatementLocations == null) {
				preparedStatementLocations = conn.prepareStatement(sql);
			}
			preparedStatementLocations.setString(1, name);
			preparedStatementLocations.setString(2, "City");
			preparedStatementLocations.setString(3, "N");;
			preparedStatementLocations.setLong(4, (new Date()).getTime());
			preparedStatementLocations.setLong(5, (new Date()).getTime());
			preparedStatementLocations.setLong(6, createdby);
			preparedStatementLocations.setLong(7, updatedby);
			return preparedStatementLocations.execute();
		} catch (Exception e) {
			logger.error("Can not update/insert into cnd table.", e);
		}
		return false;
	}


	public static Connection openDBConnection() throws SQLException {
		if (conn == null ||  conn.isClosed()){
			conn = DriverManager.getConnection(JobConfig.getProperty("database.url"),
					JobConfig.getProperty("dbuser"), JobConfig.getProperty("dbpassword"));
		} 
		return conn;
	}

	public static void closeDBConnection() throws SQLException {

		if (preparedStatementFeeds != null && !preparedStatementFeeds.isClosed()) {
			preparedStatementFeeds.close();
		}

		if (preparedStatementJobs != null && !preparedStatementJobs.isClosed()) {
			preparedStatementJobs.close();
		}

		
		if (preparedStatementUsers != null && !preparedStatementUsers.isClosed()) {
			preparedStatementUsers.close();
		}
		
		if (preparedStatementLocations != null && !preparedStatementLocations.isClosed()) {
			preparedStatementLocations.close();
		}

		if (conn != null && !conn.isClosed()){
			conn.close();
		}
	}
}
