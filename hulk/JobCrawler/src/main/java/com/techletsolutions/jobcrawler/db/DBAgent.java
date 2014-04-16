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
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techletsolutions.jobcrawler.JobConfig;
import com.techletsolutions.jobcrawler.feeds.JobItem;

public class DBAgent {
	
	private final static Logger logger = LoggerFactory.getLogger(DBAgent.class);
	final static String DB_URL = JobConfig.getProperty("jobcrawler.database.url");
	final static String USER   = JobConfig.getProperty("jobcrawler.database.username");
	final static String PASS   = JobConfig.getProperty("jobcrawler.database.password");
	private static  Connection conn = null;
	private static  PreparedStatement preparedStatementFeeds 	 = null;
	private static  PreparedStatement preparedStatementJobs 	 = null;
	private static  PreparedStatement updateJobFeedPreparedStmt  = null;


	public DBAgent() {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql = JobConfig.getProperty("jobcrawler.database.jobfeedquery");
			preparedStatementFeeds = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sql = JobConfig.getProperty("jobcrawler.database.jobquery");
			preparedStatementJobs = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sql = JobConfig.getProperty("jobcrawler.database.jobfeedupdatequery");
			updateJobFeedPreparedStmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			logger.error("Sql Error while initializing DB components", e);
		}
	}

	public void saveJobItem(JobItem jobItem, String source) {
		try {
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
				logger.error("Not valid argument", exp);
			} catch (ParseException e) {
				logger.error("Can not parse the date from given feed", e);
			}
			preparedStatementFeeds.executeUpdate();
			ResultSet rs = preparedStatementFeeds.getGeneratedKeys();
			int jobFeedId = -1;
			if (rs.next()) {
				jobFeedId = rs.getInt(1);
			} 
			// Also, save the job into jobs table
			int jobId = updateJobs(jobItem.getTitle(), jobItem.getDescription(), source, 0L, 0L, 0L);

			if (jobId != -1) {
				updateJobFeedPreparedStmt.setInt(1, jobId);
				updateJobFeedPreparedStmt.setInt(2, jobFeedId);
				updateJobFeedPreparedStmt.execute();
			}
		} catch (SQLException e) {
			if (e.getLocalizedMessage().contains("jobid_UNIQUE")) {
				logger.info("Job with id: "+jobItem.getGuid()+" already exists.");
			} else {
				logger.error("Can not perform database operations.", e);
			}
		}
	}

	public int updateJobs (String title, String description, String source, long user_id, long createdby, long updatedby) {
		int jobId = -1;
		try {
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
			preparedStatementJobs.executeUpdate();
			ResultSet rs = preparedStatementJobs.getGeneratedKeys();
			if (rs.next()) {
				jobId = rs.getInt(1);
			} 
			logger.info("Jobs table updated successfully");
		} catch (Exception e) {
			logger.error("Can not update/insert into jobs table.", e);
		}
		return jobId;
	}

	public static void close() throws SQLException {
		try {
			preparedStatementFeeds.close();
		} catch (Exception e) {
			logger.error("Error while closing Feed's prepared statement.", e);
		}

		try {
			preparedStatementJobs.close();
		} catch (Exception e) {
			logger.error("Error while closing Job's prepared statement.", e);
		}
		
		try {
			updateJobFeedPreparedStmt.close();
		} catch (Exception e) {
			logger.error("Error while closing update JobFeed's prepared statement.", e);
		}

		try {
			conn.close();
		} catch (Exception e) {
			logger.error("Error while closing database connection.", e);
		}
	}
}
