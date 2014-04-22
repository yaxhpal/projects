package es.oeuvres.main;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.oeuvres.config.ZCArtConfig;
import es.oeuvres.data.Reader;
import es.oeuvres.db.DBManager;
import es.oeuvres.model.ArtInfo;

public class Main {

	private final static Logger logger = LoggerFactory.getLogger(DBManager.class);

	public static void main(String[] args) throws SQLException {
		logger.debug("Commencing the operation. Fasten your seat belts :)");

		// Initialize Reader
		Reader reader = new Reader();

		// Initialize DBManager
		DBManager dbManager = new DBManager();

		// Read the data from data files
		List<ArtInfo> artInfoList = reader.readData();

		// Open DB connection 
		dbManager.open();

		// Default user id
		Long userId = 1L;
		Long artistId = 0L;
		Long artworkId = 0L;
		Long movementId = 0L;
		Long styleId = 0L;
		Long categoryId = 0L;
		Long purchaseInfoId = 0L;
		Long locationId = 0L;
		Long tagId = 0L;

		for (ArtInfo artInfo : artInfoList) {
			userId = 1L;
			artistId = dbManager.saveArtist(artInfo.getArtist());
			categoryId = dbManager.saveCategory(artInfo.getCategory(), null);
			movementId = dbManager.saveMovement(artInfo.getMovement());
			styleId = dbManager.saveStyle(artInfo.getStyle());
			locationId = dbManager.saveLocation(artInfo.getLocation(), userId);
			tagId = dbManager.saveTag(artInfo.getTag(), userId);
			artworkId = dbManager.saveArtwork(artInfo.getArtwork(), artistId, userId, locationId, categoryId, artInfo.getTag().tagName);
			purchaseInfoId = dbManager.savePurchaseHistory(artInfo.getPurchaseInfo(), artworkId, userId);

			
			// Save ids into interlinking tables
			dbManager.insertIntoLinkTable(ZCArtConfig.getProperty("zcart.artwork.category.preparedstatement"), artworkId, categoryId);
			dbManager.insertIntoLinkTable(ZCArtConfig.getProperty("zcart.artwork.movement.preparedstatement"), artworkId, movementId);
			dbManager.insertIntoLinkTable(ZCArtConfig.getProperty("zcart.artwork.style.preparedstatement"), artworkId, styleId);
			dbManager.insertIntoLinkTable(ZCArtConfig.getProperty("zcart.artwork.tag.preparedstatement"), artworkId, tagId);
			dbManager.insertIntoLinkTable(ZCArtConfig.getProperty("zcart.artist.movement.preparedstatement"), artistId, movementId);
			
			logger.info("Artist: {}, Category: {}, Movement: {}, Style: {}, Location: {}, Tag: {}, Atrwork: {}, PurchaseInfo: {}", 
					artistId, categoryId, movementId, styleId, locationId, tagId, artworkId, purchaseInfoId);
		}
		dbManager.close();
	}
}
