package es.oeuvres.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.oeuvres.model.ArtInfo;
import es.oeuvres.model.Artist;
import es.oeuvres.model.Artwork;
import es.oeuvres.model.Category;
import es.oeuvres.model.Location;
import es.oeuvres.model.Movement;
import es.oeuvres.model.Provenance;
import es.oeuvres.model.PurchaseInfo;
import es.oeuvres.model.Style;
import es.oeuvres.model.Tag;
import es.oeuvres.utils.ConvertUtils;
import es.oeuvres.utils.StringUtils;

public class Reader {

	Logger logger = LoggerFactory.getLogger(Reader.class);

	private final Map<String, Integer> masterMap = new HashMap<String, Integer>();

	private final Map<String, Integer> provenanceMap = new HashMap<String, Integer>();

	private final String dataFile = "/home/yashpal/projects/artproject/data/Data_03.03.14/Master.xls";

	private final String dataProvenancesFile = "/home/yashpal/projects/artproject/data/Data_03.03.14/Provenance.xls";

	public Reader() {
		logger.debug("Data file is {} \n", dataFile);
		initializeMap();
	}

	public static void main(String[] args) {
		Reader reader = new Reader();
		//List<ArtInfo>  list = reader.ReadIt();

		reader.readProvenancesData();

		//System.out.println(list.size());
	}


	public List<ArtInfo> readData() {
		List<ArtInfo> artInfoList = new ArrayList<ArtInfo>();
		try {
			HSSFWorkbook wb = Reader.readFile(dataFile);		
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				HSSFSheet sheet = wb.getSheetAt(k);
				int rows = sheet.getPhysicalNumberOfRows();
				for (int r = 0; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row != null &&  row.getRowNum() > 2) {

						ArtInfo artInfo = new ArtInfo();

						Artist artist = readArtist(row);
						if (artist != null) {
							artInfo.setArtist(artist);
						}

						Artwork artwork = readArtwork(row);
						if (artwork != null) {
							artInfo.setArtwork(artwork);
						}

						Movement movement = readMovement(row);
						if (movement != null) {
							artInfo.setMovement(movement);
						}

						PurchaseInfo purchaseInfo = readPurchaseInfo(row);
						if (purchaseInfo != null) {
							artInfo.setPurchaseInfo(purchaseInfo);
						}

						Style style = readStyle(row);
						if (style != null) {
							artInfo.setStyle(style);
						}

						Category category = readCategory(row);
						if (category != null) {
							artInfo.setCategory(category);
						}

						Location location = readLocation(row);
						if (location != null) {
							artInfo.setLocation(location);
						}

						Tag tag = readTag(row);
						if (tag != null) {
							artInfo.setTag(tag);
						}

						artInfoList.add(artInfo);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return artInfoList;
	}

	public List<Provenance> readProvenancesData() {
		List<Provenance> provenanceList = new ArrayList<Provenance>();
		try {
			HSSFWorkbook wb = Reader.readFile(dataProvenancesFile);		
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				HSSFSheet sheet = wb.getSheetAt(k);
				int rows = sheet.getPhysicalNumberOfRows();
				for (int r = 0; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row != null &&  row.getRowNum() > 0) {
						Provenance provenance = readProvenance(row);
						if (provenance != null) {
							provenanceList.add(provenance);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String provenanceId = "";
		for (Provenance provenance : provenanceList) {
			if (StringUtils.isEmpty(provenance.artworkId)) {
				provenance.artworkId = provenanceId;
			} else {
				provenanceId = provenance.artworkId;
			}
			
			System.out.println(provenance.artworkId +"\t\t\t"+ provenance.date+"\t\t\t"+provenance.owner);
		}
		return provenanceList;
	}

	private Provenance readProvenance(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Provenance provenance = new Provenance();
		boolean flag = false;
		Field[] artistFields = Provenance.class.getDeclaredFields();
		for (Field field : artistFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(provenanceMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(provenance, cell.toString().trim());
			}
		}

		if (StringUtils.isEmpty(provenance.artworkId) && StringUtils.isEmpty(provenance.date) && StringUtils.isEmpty(provenance.owner) ) {
			flag = false;
		}
		return flag?provenance:null;
	}

	private Artist readArtist(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Artist artist = new Artist();
		boolean flag = false;
		Field[] artistFields = Artist.class.getDeclaredFields();
		for (Field field : artistFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(artist, cell.toString().trim());
			}
		}
		return flag?artist:null;
	}

	private Artwork readArtwork(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Artwork artwork = new Artwork();
		boolean flag = false;
		Field[] artworkFields = Artwork.class.getDeclaredFields();
		for (Field field : artworkFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(artwork, cell.toString().trim());
			}
		}
		return flag?artwork:null;
	}

	private Movement readMovement(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Movement movement = new Movement();
		boolean flag = false;
		Field[] movementFields = Movement.class.getDeclaredFields();
		for (Field field : movementFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(movement, cell.toString().trim());
			}
		}

		if (StringUtils.isEmpty(movement.movementName)) {
			movement.movementName = "Unknown";
		}
		return flag?movement:null;
	}

	private PurchaseInfo readPurchaseInfo(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		PurchaseInfo purchaseInfo = new PurchaseInfo();
		boolean flag = false;
		Field[] purchaseInfoFields = PurchaseInfo.class.getDeclaredFields();
		for (Field field : purchaseInfoFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(purchaseInfo, cell.toString().trim());
			}
		}
		return flag?purchaseInfo:null;
	}

	private Style readStyle(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Style style = new Style();
		boolean flag = false;
		Field[] styleFields = Style.class.getDeclaredFields();
		for (Field field : styleFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(style, cell.toString().trim());
			}
		}

		if (StringUtils.isEmpty(style.styleName)) {
			style.styleName = "Unknown";
		}

		return flag?style:null;
	}

	private Category readCategory(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Category category = new Category();
		boolean flag = false;
		Field[] categoryFields = Category.class.getDeclaredFields();
		for (Field field : categoryFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(category, cell.toString().trim());
			}
		}

		if (StringUtils.isEmpty(category.categoryName)) {
			category.categoryName = "Unknown";
		}
		return flag?category:null;
	}

	private Location readLocation(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Location location = new Location();
		boolean flag = false;
		Field[] locationFields = Location.class.getDeclaredFields();
		for (Field field : locationFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(location, cell.toString().trim());
			}
		}
		return flag?location:null;
	}

	private Tag readTag(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Tag tag = new Tag();
		boolean flag = false;
		Field[] tagFields = Tag.class.getDeclaredFields();
		for (Field field : tagFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(masterMap.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(tag, cell.toString().trim());
			}
		}

		if (StringUtils.isEmpty(tag.tagName)) {
			tag.tagName = "Unknown";
		} else {
			tag.tagName = ConvertUtils.getIntegerFromString(tag.tagName)+"";
		}
		return flag?tag:null;
	}

	/**
	 * creates an {@link HSSFWorkbook} the specified OS filename.
	 */
	private static HSSFWorkbook readFile(String filename) throws IOException {
		return new HSSFWorkbook(new FileInputStream(filename));
	}

	public void initializeMap () {

		// Initialize master map
		masterMap.put("artistsNo", 0);
		masterMap.put("Type", 1);
		masterMap.put("about", 2);
		masterMap.put("link", 3);
		masterMap.put("movements", 4);
		masterMap.put("artistCountry", 5); // Original name country
		masterMap.put("firstName", 6);
		masterMap.put("lastName", 7);
		masterMap.put("yearOfBirth", 8);
		masterMap.put("yearOfDeath", 9);
		masterMap.put("hasPhoto", 10);
		masterMap.put("artworkNo", 11);
		masterMap.put("categoryName", 12); // Original category
		masterMap.put("subCategoryName", 13); // Original subCategory
		masterMap.put("movementName", 14); // Original name movement
		masterMap.put("styleName", 15); // Original name styles
		masterMap.put("artBookBio", 16);
		masterMap.put("name", 17);
		masterMap.put("yearNote", 18);
		masterMap.put("integer", 19);
		masterMap.put("editionNo", 20);
		masterMap.put("unframedDepth", 21);
		masterMap.put("unframedHeight", 22);
		masterMap.put("unframedWidth", 23);
		masterMap.put("framedDepth", 24);
		masterMap.put("framedHeight", 25);
		masterMap.put("framedWidth", 26);
		masterMap.put("medium", 27);
		masterMap.put("signed", 28);
		masterMap.put("location", 29);
		masterMap.put("locationCountry", 30); // Original country
		masterMap.put("room", 31);
		masterMap.put("wall", 32);
		masterMap.put("conditionDescription", 33);
		masterMap.put("period", 34);
		masterMap.put("exportRestriction", 35);
		masterMap.put("importRestriction", 36);
		masterMap.put("origin", 37);
		masterMap.put("tagName", 38); // Original tagNo
		masterMap.put("commission", 39);
		masterMap.put("purchaseDate", 40);
		masterMap.put("insurance", 41);
		masterMap.put("price", 42);
		masterMap.put("purchasedFrom", 43);
		masterMap.put("source", 44);
		masterMap.put("artistRR", 45);
		masterMap.put("vat", 46);
		masterMap.put("importTax", 47);
		masterMap.put("seymoursfeeVat", 48);
		masterMap.put("valuation", 49);
		masterMap.put("valuationBy", 50);
		masterMap.put("valuationDate", 51);

		// Initialize provenance map
		provenanceMap.put("artworkId", 0);
		provenanceMap.put("date", 1);
		provenanceMap.put("owner", 2);
	}
}