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
import es.oeuvres.model.PurchaseInfo;
import es.oeuvres.model.Style;
import es.oeuvres.model.Tag;

public class Reader {

	Logger logger = LoggerFactory.getLogger(Reader.class);

	private final Map<String, Integer> map = new HashMap<String, Integer>();

	private final String dataFile = "/home/yashpal/projects/artproject/data/Data_03.03.14/Master.xls";

	public Reader() {
		logger.debug("Data file is {} \n", dataFile);
		initializeMap();
	}

//	public static void main(String[] args) {
//		Reader reader = new Reader();
//		List<ArtInfo>  list = reader.ReadIt();
//
//		System.out.println(list.size());
//	}

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

	private Artist readArtist(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Artist artist = new Artist();
		boolean flag = false;
		Field[] artistFields = Artist.class.getDeclaredFields();
		for (Field field : artistFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(map.get(fieldName));
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
			HSSFCell cell = row.getCell(map.get(fieldName));
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
			HSSFCell cell = row.getCell(map.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(movement, cell.toString().trim());
			}
		}
		return flag?movement:null;
	}

	private PurchaseInfo readPurchaseInfo(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		PurchaseInfo purchaseInfo = new PurchaseInfo();
		boolean flag = false;
		Field[] purchaseInfoFields = PurchaseInfo.class.getDeclaredFields();
		for (Field field : purchaseInfoFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(map.get(fieldName));
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
			HSSFCell cell = row.getCell(map.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(style, cell.toString().trim());
			}
		}
		return flag?style:null;
	}

	private Category readCategory(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Category category = new Category();
		boolean flag = false;
		Field[] categoryFields = Category.class.getDeclaredFields();
		for (Field field : categoryFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(map.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(category, cell.toString().trim());
			}
		}
		return flag?category:null;
	}
	
	private Location readLocation(HSSFRow row) throws IllegalArgumentException, IllegalAccessException {
		Location location = new Location();
		boolean flag = false;
		Field[] locationFields = Location.class.getDeclaredFields();
		for (Field field : locationFields) {
			String fieldName = field.getName();
			HSSFCell cell = row.getCell(map.get(fieldName));
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
			HSSFCell cell = row.getCell(map.get(fieldName));
			if (cell != null) {
				flag = true;
				field.set(tag, cell.toString().trim());
			}
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
		 map.put("artistsNo", 0);
		 map.put("Type", 1);
		 map.put("about", 2);
		 map.put("link", 3);
		 map.put("movements", 4);
		 map.put("artistCountry", 5); // Original name country
		 map.put("firstName", 6);
		 map.put("lastName", 7);
		 map.put("yearOfBirth", 8);
		 map.put("yearOfDeath", 9);
		 map.put("hasPhoto", 10);
		 map.put("artworkNo", 11);
		 map.put("categoryName", 12); // Original category
		 map.put("subCategoryName", 13); // Original subCategory
		 map.put("movementName", 14); // Original name movement
		 map.put("styleName", 15); // Original name styles
		 map.put("artBookBio", 16);
		 map.put("name", 17);
		 map.put("yearNote", 18);
		 map.put("integer", 19);
		 map.put("editionNo", 20);
		 map.put("unframedDepth", 21);
		 map.put("unframedHeight", 22);
		 map.put("unframedWidth", 23);
		 map.put("framedDepth", 24);
		 map.put("framedHeight", 25);
		 map.put("framedWidth", 26);
		 map.put("medium", 27);
		 map.put("signed", 28);
		 map.put("location", 29);
		 map.put("locationCountry", 30); // Original country
		 map.put("room", 31);
		 map.put("wall", 32);
		 map.put("conditionDescription", 33);
		 map.put("period", 34);
		 map.put("exportRestriction", 35);
		 map.put("importRestriction", 36);
		 map.put("origin", 37);
		 map.put("tagName", 38); // Original tagNo
		 map.put("commission", 39);
		 map.put("purchaseDate", 40);
		 map.put("insurance", 41);
		 map.put("price", 42);
		 map.put("purchasedFrom", 43);
		 map.put("source", 44);
		 map.put("artistRR", 45);
		 map.put("vat", 46);
		 map.put("importTax", 47);
		 map.put("seymoursfeeVat", 48);
		 map.put("valuation", 49);
		 map.put("valuationBy", 50);
		 map.put("valuationDate", 51);
	 }
}