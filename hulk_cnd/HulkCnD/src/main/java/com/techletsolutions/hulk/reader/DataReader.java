package com.techletsolutions.hulk.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techletsolutions.hulk.model.City;
import com.techletsolutions.hulk.model.Country;
import com.techletsolutions.hulk.model.State;
import com.techletsolutions.hulk.model.ZipCode;
import com.techletsolutions.hulk.model.ZipInfo;

/**
 * Data Reader
 * 
 * @author <a href="mailto:yashpal@techletsolutions.com">Yashpal Meena</a>
 * @version $Revision: 1 $
 */
/**
 * @author yashpal
 *
 */
public class DataReader {

	final static Logger logger = LoggerFactory.getLogger(DataReader.class);

	final static Charset ENCODING = StandardCharsets.UTF_8;

	public Country parseCountryFromDataFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		Country country = null;
		try (Scanner scanner =  new Scanner(path, ENCODING.name())){
			country = new Country("United States", null);
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				if (line != null && !line.trim().isEmpty()) {
					String[] parts = line.split(",");
					if (parts.length == 3) {
						State state 	= new State(parts[0].trim(), 1238L);
						City city   	= new City(parts[1].trim(), 0L);
						ZipCode zipCode = new ZipCode(parts[2].trim(), 0L);
						city.addZipCode(zipCode);
						state.addCity(city);
						country.addState(state);
					}
				}
			}
		}
		return country;
	}

	//#"zip_code","latitude","longitude","city","state","county"
	public List<ZipInfo> parseZipcodeData(String zipDataFile, String stateCodeDataFile) throws IOException {
		Path path = Paths.get(zipDataFile);
		List<ZipInfo> zipinfoList = new ArrayList<ZipInfo>();
		Map<String, String> stateCodes = parseStateCodes(stateCodeDataFile);
		try (Scanner scanner =  new Scanner(path, ENCODING.name())){
			while (scanner.hasNextLine()){
				ZipInfo zipInfo = new ZipInfo();
				String line = scanner.nextLine();
				//logger.info(line);
				if (line == null || line.startsWith("#")) {
					continue;
				} else if (!line.trim().isEmpty()) {
					String[] parts = line.split(",");
					if (parts.length == 6 && !parts[1].isEmpty() && !parts[2].isEmpty()) {
						zipInfo.zipcode   = parts[0].replaceAll("\"", "").trim();
						zipInfo.county    = parts[5].replaceAll("\"", "").trim();
						zipInfo.city      = parts[3].replaceAll("\"", "").trim();
						zipInfo.statecode = parts[4].replaceAll("\"", "").trim();
						zipInfo.state = stateCodes.get(parts[4].replaceAll("\"", "").trim());
						zipInfo.latitude  = new Float(parts[1].replaceAll("\"", "").trim());
						zipInfo.longitude = new Float(parts[2].replaceAll("\"", "").trim());
						zipinfoList.add(zipInfo);
					}
				}
			}
		}
		return zipinfoList;
	}

	private Map<String, String> parseStateCodes(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		Map<String, String> stateCodesMap = new HashMap<String, String>(); 
		try (Scanner scanner =  new Scanner(path, ENCODING.name())){
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				if (line == null || line.startsWith("#")) {
					continue;
				} else if (!line.trim().isEmpty()) {
					String[] parts = line.split(",");
					if (parts.length == 2) {
						stateCodesMap.put(parts[0].replaceAll("\"", "").trim(),parts[1].replaceAll("\"", "").trim());
					}
				}
			}
		}
		return stateCodesMap;
	}

//	public static void main(String[] args) {
//		DataReader reader = new DataReader();
//		try {
//			List<ZipInfo>  list = reader.parseZipcodeData("/home/yashpal/projects/hulk_cnd/HulkCnD/src/main/resources/data/zip_codes_states.csv");
//			for (ZipInfo zipInfo : list) {
//				System.out.println(zipInfo.toString());
//			}
//
//			System.out.println("#############################################################");
//			System.out.println("Size of the list is: " + list.size());
//
//		} catch (IOException e) {
//			logger.error("Error while reading file: zip_codes_states.csv", e);
//		}
//	}
}
