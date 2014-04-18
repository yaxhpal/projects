package com.techletsolutions.hulk.reader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techletsolutions.hulk.config.HulkConfig;
import com.techletsolutions.hulk.model.City;
import com.techletsolutions.hulk.model.Country;
import com.techletsolutions.hulk.model.State;
import com.techletsolutions.hulk.model.ZipCode;

/**
 * Data Reader
 * 
 * @author <a href="mailto:yashpal@techletsolutions.com">Yashpal Meena</a>
 * @version $Revision: 1 $
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
						
						State state 	= new State(parts[0], 1238L);
						
						City city   	= new City(parts[1], 0L);
						
						ZipCode zipCode = new ZipCode(parts[2], 0L);
						
						city.addZipCode(zipCode);
						
						state.addCity(city);
						
						country.addState(state);
					}
				}
			}
		}
		return country;
	}
	
	
	/*
	public static List<String> readLargerTextFile(String aFileName) throws IOException {
		Path path = Paths.get(aFileName);
		List<String> lines = new ArrayList<String>();
		try (Scanner scanner =  new Scanner(path, ENCODING.name())){
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				if (line != null && !line.trim().isEmpty()) {
					String[] parts = line.split(",");
					if (parts.length == 3) {
						State state = new State(parts[2], 1238L);
						City city = new City(parts[1], 0L);
						ZipCode zipCode = new ZipCode(parts[0], 0L);
						lines.add(state.getName()+","+city.getName()+","+zipCode.getName());
					}
				}
			}
		}
		return lines;
	}
	

	public static void writeLargerTextFile(String aFileName, List<String> aLines) throws IOException {
		Path path = Paths.get(aFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
			for(String line : aLines){
				writer.write(line);
				writer.newLine();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		String dataFile = HulkConfig.getProperty("hulk.app.datafilepath");
		String outputFile = "/home/yashpal/projects/hulk_cnd/zip_city_state_formatted.txt";
		writeLargerTextFile(outputFile, readLargerTextFile(dataFile));
	}
	*/
}
