package es.oeuvres.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.oeuvres.config.ZCArtConfig;
import es.oeuvres.model.Artist;
import es.oeuvres.model.Artwork;
import es.oeuvres.model.Category;
import es.oeuvres.model.Location;
import es.oeuvres.model.Movement;
import es.oeuvres.model.PurchaseInfo;
import es.oeuvres.model.Style;
import es.oeuvres.model.Tag;
import es.oeuvres.utils.ConvertUtils;


public class DBManager {

	private final static Logger logger = LoggerFactory.getLogger(DBManager.class);
	final static String DB_URL = ZCArtConfig.getProperty("zcart.db.url");
	final static String USER   = ZCArtConfig.getProperty("zcart.db.user");
	final static String PASS   = ZCArtConfig.getProperty("zcart.db.pssword");

	private static  Connection conn = null;

	private static  PreparedStatement preparedStatementArtwork	 		= null;

	private static  PreparedStatement preparedStatementArtist	 		= null;

	private static  PreparedStatement preparedStatementPurchaseHistory	= null;

	private static  PreparedStatement preparedStatementMovement			= null;

	private static  PreparedStatement preparedStatementStyle			= null;
	
	private static  PreparedStatement preparedStatementCategory			= null;

	private static  PreparedStatement preparedStatementLocation			= null;
	
	private static  PreparedStatement preparedStatementTag				= null;
	

	public void open() {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String sql = ZCArtConfig.getProperty("zcart.artwork.preparedstatement");
			preparedStatementArtwork = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			sql = ZCArtConfig.getProperty("zcart.artist.preparedstatement");
			preparedStatementArtist = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			sql = ZCArtConfig.getProperty("zcart.purchaseinfo.preparedstatement");
			preparedStatementPurchaseHistory = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			sql = ZCArtConfig.getProperty("zcart.movement.preparedstatement");
			preparedStatementMovement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			sql = ZCArtConfig.getProperty("zcart.style.preparedstatement");
			preparedStatementStyle = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			sql = ZCArtConfig.getProperty("zcart.category.preparedstatement");
			preparedStatementCategory = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			sql = ZCArtConfig.getProperty("zcart.location.preparedstatement");
			preparedStatementLocation = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			sql = ZCArtConfig.getProperty("zcart.tag.preparedstatement");
			preparedStatementTag = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
		} catch (SQLException e) {
			logger.error("Sql Error while initializing DB components", e);
		}

	}

	public void close() {
		try {
			preparedStatementArtwork.close();
		} catch (Exception e) {
			logger.error("Error while closing the Artwork prepared statement.", e);
		}

		try {
			preparedStatementArtist.close();
		} catch (Exception e) {
			logger.error("Error while closing the Artist prepared statement.", e);
		}

		try {
			preparedStatementPurchaseHistory.close();
		} catch (Exception e) {
			logger.error("Error while closing the PurchaseHistory prepared statement.", e);
		}

		try {
			preparedStatementMovement.close();
		} catch (Exception e) {
			logger.error("Error while closing the Movement prepared statement.", e);
		}

		try {
			preparedStatementStyle.close();
		} catch (Exception e) {
			logger.error("Error while closing the Style prepared statement.", e);
		}
		
		try {
			preparedStatementCategory.close();
		} catch (Exception e) {
			logger.error("Error while closing the Category prepared statement.", e);
		}

		try {
			preparedStatementLocation.close();
		} catch (Exception e) {
			logger.error("Error while closing the Location prepared statement.", e);
		}
		
		try {
			preparedStatementTag.close();
		} catch (Exception e) {
			logger.error("Error while closing the Tag prepared statement.", e);
		}
		
		try {
			conn.close();
		} catch (Exception e) {
			logger.error("Error while closing database connection.", e);
		}

	}

	public Long saveArtist(Artist artist) throws SQLException {
		// Check if this artist already exists?
		PreparedStatement stmt = conn.prepareStatement("select * from Artist where firstName=? and lastName=? and yearOfBirth=? and birthPlace=?;");
		stmt.setString(1, artist.firstName);
		stmt.setString(2, artist.lastName);
		stmt.setInt(3, ConvertUtils.getIntegerFromString(artist.yearOfBirth));
		stmt.setString(4, artist.artistCountry);
		ResultSet rs = stmt.executeQuery();
		Long artistId = 0L;
		if (rs.next()) {
			artistId = rs.getLong(1);
		}

		if (artistId == 0) {
			preparedStatementArtist.setString(1, artist.about);
			preparedStatementArtist.setString(2, artist.firstName);
			preparedStatementArtist.setString(3, artist.lastName);
			preparedStatementArtist.setString(4, artist.artistCountry);
			preparedStatementArtist.setInt(5, ConvertUtils.getIntegerFromString(artist.yearOfBirth));
			preparedStatementArtist.setInt(6, ConvertUtils.getIntegerFromString(artist.yearOfDeath));
			preparedStatementArtist.setInt(7, ConvertUtils.getIntegerFromString(artist.hasPhoto));
			preparedStatementArtist.setInt(8, 1);
			preparedStatementArtist.executeUpdate();
			rs = preparedStatementArtist.getGeneratedKeys();
			if (rs.next()) {
				artistId = rs.getLong(1);
			}
		}
		return artistId;
	}

	public Long saveMovement(Movement movement) throws SQLException {
		// Check if this movement already exists?
		PreparedStatement stmt = conn.prepareStatement("select * from Movement where name=?;");
		stmt.setString(1, movement.movementName);
		ResultSet rs = stmt.executeQuery();
		Long movementId = 0L;
		if (rs.next()) {
			movementId = rs.getLong(1);
		}

		if (movementId == 0) {
			preparedStatementMovement.setString(1, movement.movementName);
			preparedStatementMovement.setInt(2, 1);
			preparedStatementMovement.executeUpdate();
			rs = preparedStatementMovement.getGeneratedKeys();
			if (rs.next()) {
				movementId = rs.getLong(1);
			}
		}
		return movementId;
	}

	public Long saveStyle(Style style) throws SQLException {
		// Check if this Style already exists?
		PreparedStatement stmt = conn.prepareStatement("select * from Style where name=?;");
		stmt.setString(1, style.styleName);
		ResultSet rs = stmt.executeQuery();
		Long styleId = 0L;

		if (rs.next()) {
			styleId = rs.getLong(1);
		}

		if (styleId == 0) {
			preparedStatementStyle.setString(1, style.styleName);
			preparedStatementStyle.setInt(2, 1);
			preparedStatementStyle.executeUpdate();
			rs = preparedStatementStyle.getGeneratedKeys();
			if (rs.next()) {
				styleId = rs.getLong(1);
			}
		}
		return styleId;
	}
	
	public Long saveCategory(Category category, Long parentId) throws SQLException {
		// Check if this Style already exists?
		PreparedStatement stmt = conn.prepareStatement("select * from Category where name=?;");
		stmt.setString(1, category.categoryName);
		ResultSet rs = stmt.executeQuery();
		Long categoryId = 0L;

		if (rs.next()) {
			categoryId = rs.getLong(1);
		}

		if (categoryId == 0) {
			preparedStatementCategory.setString(1, category.categoryName);
			preparedStatementCategory.setString(2, category.categoryName);
			preparedStatementCategory.setString(3, (parentId == null)?"Category":"SubCategory");
			preparedStatementCategory.setLong(4, (parentId == null)?0L:parentId);
			preparedStatementCategory.setString(5, "A");
			preparedStatementCategory.setLong(6, System.currentTimeMillis()/1000);
			preparedStatementCategory.setLong(7, System.currentTimeMillis()/1000);
			preparedStatementCategory.executeUpdate();
			rs = preparedStatementCategory.getGeneratedKeys();
			if (rs.next()) {
				categoryId = rs.getLong(1);
			}
		}
		
		String subcategoryName = category.subCategoryName;
		if ( subcategoryName!= null && !subcategoryName.trim().isEmpty()) {
			Category subcat = new Category();
			subcat.categoryName = subcategoryName;
			subcat.subCategoryName = null;
			categoryId = saveCategory(subcat, categoryId);
		}
		
		return categoryId;
	}

	public Long savePurchaseHistory(PurchaseInfo purchaseInfo, Long artworkId, Long ownerId) {
		Long purchaseInfoId = 0L; 
		try {
			preparedStatementPurchaseHistory.setString(1, purchaseInfo.commission);
			preparedStatementPurchaseHistory.setString(2, purchaseInfo.insurance);
			preparedStatementPurchaseHistory.setString(3, purchaseInfo.price);
			preparedStatementPurchaseHistory.setString(4, purchaseInfo.purchasedFrom);
			preparedStatementPurchaseHistory.setString(5, purchaseInfo.source);
			preparedStatementPurchaseHistory.setString(6, purchaseInfo.valuation);
			preparedStatementPurchaseHistory.setString(7, purchaseInfo.valuationDate);
			preparedStatementPurchaseHistory.setString(8, purchaseInfo.vat);
			preparedStatementPurchaseHistory.setString(9, purchaseInfo.artistRR);
			preparedStatementPurchaseHistory.setString(10, purchaseInfo.importTax);
			preparedStatementPurchaseHistory.setString(11, purchaseInfo.valuationBy);
			preparedStatementPurchaseHistory.setString(12, purchaseInfo.purchaseDate);
			preparedStatementPurchaseHistory.setLong(13, artworkId);
			preparedStatementPurchaseHistory.setLong(14, ownerId);
			preparedStatementPurchaseHistory.setInt(15, 1);
			preparedStatementPurchaseHistory.executeUpdate();
			ResultSet rs = preparedStatementPurchaseHistory.getGeneratedKeys();
			if (rs.next()) {
				purchaseInfoId = rs.getLong(1);
			}
		} catch (Exception e) {
			logger.error("Can not update/insert into PurchaseHistory table.", e);
		}
		return purchaseInfoId;
	}

	public Long saveArtwork(Artwork artwork, Long artistId, Long userId, Long locationId, Long categoryId, String tagNo) {
		Long artworkId = 0L; 
		try {
			preparedStatementArtwork.setString(1, artwork.artBookBio);
			preparedStatementArtwork.setString(2, artwork.name);
			preparedStatementArtwork.setInt(3, ConvertUtils.getIntegerFromString(artwork.yearNote));
			preparedStatementArtwork.setInt(4, ConvertUtils.getIntegerFromString(artwork.yearNote));
			preparedStatementArtwork.setString(5, artwork.signed);
			preparedStatementArtwork.setString(6, artwork.period);
			preparedStatementArtwork.setInt(7, ConvertUtils.getIntegerFromString(artwork.unframedWidth));
			preparedStatementArtwork.setString(8, artwork.medium);
			preparedStatementArtwork.setString(9, artwork.origin);
			preparedStatementArtwork.setString(10, tagNo);
			preparedStatementArtwork.setString(11, artwork.editionNo);
			preparedStatementArtwork.setInt(12, ConvertUtils.getIntegerFromString(artwork.unframedDepth));
			preparedStatementArtwork.setInt(13, ConvertUtils.getIntegerFromString(artwork.unframedHeight));
			preparedStatementArtwork.setInt(14, ConvertUtils.getIntegerFromString(artwork.framedDepth));
			preparedStatementArtwork.setInt(15, ConvertUtils.getIntegerFromString(artwork.framedHeight));
			preparedStatementArtwork.setInt(16, ConvertUtils.getIntegerFromString(artwork.framedWidth));
			preparedStatementArtwork.setString(17, artwork.exportRestriction);
			preparedStatementArtwork.setString(18, artwork.importRestriction);
			preparedStatementArtwork.setString(19, artwork.conditionDescription);
			preparedStatementArtwork.setString(20, null);
			preparedStatementArtwork.setLong(21, artistId);
			preparedStatementArtwork.setLong(22, userId);
			preparedStatementArtwork.setLong(23, locationId);
			preparedStatementArtwork.setLong(24, categoryId);
			preparedStatementArtwork.setInt(25, 1);
			preparedStatementArtwork.executeUpdate();
			ResultSet rs = preparedStatementArtwork.getGeneratedKeys();
			if (rs.next()) {
				artworkId = rs.getLong(1);
			}
		} catch (Exception e) {
			logger.error("Can not update/insert into Artwork table.", e);
		}
		return artworkId;
	}
	
	//(location, room, country, wall, owner_id, version)
	public Long saveLocation(Location location, Long userId) throws SQLException {
		Long locationId = 0L;
		// Check if this artist already exists?
		PreparedStatement stmt = conn.prepareStatement("select * from Location where location=? and room=? and wall=? and country=?;");
		stmt.setString(1, location.location);
		stmt.setString(2, location.room);
		stmt.setString(3, location.wall);
		stmt.setString(4, location.locationCountry);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			locationId = rs.getLong(1);
		}
		if (locationId == 0) {
			preparedStatementLocation.setString(1, location.location);
			preparedStatementLocation.setString(2, location.room);
			preparedStatementLocation.setString(3, location.locationCountry);
			preparedStatementLocation.setString(4, location.wall);
			preparedStatementLocation.setLong(5, userId);
			preparedStatementLocation.setInt(6, 1);
			preparedStatementLocation.executeUpdate();
			rs = preparedStatementLocation.getGeneratedKeys();
			if (rs.next()) {
				locationId = rs.getLong(1);
			}
		}
		return locationId;
	}
	
	public Long saveTag(Tag tag, Long userId) throws SQLException {
		Long tagId = 0L;
		// Check if this artist already exists?
		PreparedStatement stmt = conn.prepareStatement("select * from Tag where name=? and user_id=?;");
		stmt.setString(1, tag.tagName);
		stmt.setLong(2, userId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			tagId = rs.getLong(1);
		}
		if (tagId == 0) {
			preparedStatementTag.setString(1, tag.tagName);
			preparedStatementTag.setLong(2, userId);
			preparedStatementTag.setInt(3, 1);
			preparedStatementTag.executeUpdate();
			rs = preparedStatementTag.getGeneratedKeys();
			if (rs.next()) {
				tagId = rs.getLong(1);
			}
		}
		return tagId;
	}
}
