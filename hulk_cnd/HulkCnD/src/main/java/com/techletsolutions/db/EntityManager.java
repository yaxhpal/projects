package com.techletsolutions.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techletsolutions.hulk.config.HulkConfig;
import com.techletsolutions.hulk.model.City;
import com.techletsolutions.hulk.model.CnDEntity;
import com.techletsolutions.hulk.model.State;
import com.techletsolutions.hulk.model.ZipInfo;

public class EntityManager {
	final static Logger logger = LoggerFactory.getLogger(EntityManager.class);
	
	final static String DB_URL = HulkConfig.getProperty("hulk.db.url");
	final static String USER   = HulkConfig.getProperty("hulk.db.user");
	final static String PASS   = HulkConfig.getProperty("hulk.db.password");
	
	private static int stateSequence;
	
	private static int citySequence;
	
	private static int zipCodeSequence;
	
	private static  Connection conn = null;
	
	private static  PreparedStatement preparedStatementCnD 	 = null;
	private static  PreparedStatement preparedStatementZipcode 	 = null;

	public EntityManager() {}
	
	public void open(){
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			String sql = HulkConfig.getProperty("hulk.db.cndQuery");
			preparedStatementCnD = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			sql = HulkConfig.getProperty("hulk.db.zipcodesQuery");
			preparedStatementZipcode = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			//sequence = getLastSequenceNo();
			stateSequence = 100;
			citySequence = 10;
			zipCodeSequence = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			preparedStatementCnD.close();
		} catch (Exception e) {
			logger.error("Error while closing the CnD prepared statement.", e);
		}
		
		try {
			preparedStatementZipcode.close();
		} catch (Exception e) {
			logger.error("Error while closing the CnD prepared statement.", e);
		}
		
		try {
			conn.close();
		} catch (Exception e) {
			logger.error("Error while closing database connection.", e);
		}
	}
	
	public Long saveCnDEntity(CnDEntity entity) {
		Long entityId = -1L; 
		try {
			preparedStatementCnD.setString(1, entity.getName());
			preparedStatementCnD.setString(2, entity.getDescription());
			preparedStatementCnD.setString(3, entity.getGroup_name());
			preparedStatementCnD.setLong(4, entity.getParent());
			
			if (entity instanceof State) {
				preparedStatementCnD.setInt(5, stateSequence);
				stateSequence += 100;
			} else if (entity instanceof City) {
				preparedStatementCnD.setInt(5, citySequence);
				citySequence += 10;
			} else {
				preparedStatementCnD.setInt(5, zipCodeSequence++);
			}
			
			preparedStatementCnD.setString(6, entity.getOption1());
			preparedStatementCnD.setString(7, entity.getOption2());
			preparedStatementCnD.setString(8, entity.getDeleted()+"");
			preparedStatementCnD.setLong(9, System.currentTimeMillis()/1000L);
			preparedStatementCnD.setLong(10, System.currentTimeMillis()/1000L);
			preparedStatementCnD.setLong(11, 1000L);
			preparedStatementCnD.setLong(12, 1000L);
			preparedStatementCnD.executeUpdate();
			ResultSet rs = preparedStatementCnD.getGeneratedKeys();
			if (rs.next()) {
				entityId = rs.getLong(1);
			}
			logger.info("Entity '{}' in group '{}' has been inserted.",entity.getName(),entity.getGroup_name());
		} catch (Exception e) {
			logger.error("Can not update/insert into jobs table.", e);
		}
		return entityId;
	}
	
	
	// (zipcode, county, city, state, statecode, latitude, longitude)
	public void saveZipInfo(ZipInfo zipInfo) {
		logger.warn(zipInfo.toString());
		Long zipInfoId = -1L; 
		try {
			preparedStatementZipcode.setString(1, zipInfo.zipcode);
			preparedStatementZipcode.setString(2, zipInfo.county);
			preparedStatementZipcode.setString(3, zipInfo.city);
			preparedStatementZipcode.setString(4, zipInfo.state);
			preparedStatementZipcode.setString(5, zipInfo.statecode);
			preparedStatementZipcode.setFloat(6, zipInfo.latitude);
			preparedStatementZipcode.setFloat(7, zipInfo.longitude);
			preparedStatementZipcode.executeUpdate();
			ResultSet rs = preparedStatementZipcode.getGeneratedKeys();
			if (rs.next()) {
				zipInfoId = rs.getLong(1);
			}
			logger.info("Entity '{}' with id '{}' has been inserted.",zipInfo.zipcode, zipInfoId);
		} catch (Exception e) {
			logger.error("Can not update/insert into jobs table.", e);
		}
	}
	
	public int getLastSequenceNo() {
		int result = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select sequence from cndtest order by sequence desc limit 1");
			if (rs.next()) {
				result = rs.getInt(1);
			}	
		} catch (SQLException sqle) {
			logger.error("Error while getting last sequence no.", sqle);
		} catch (Exception e) {
			logger.error("Perhaps connection object is not intialized.", e);
		}
		return result;
	}
}
