package com.techletsolutions.hulk;

import java.io.IOException;
import java.util.List;

import com.techletsolutions.db.EntityManager;
import com.techletsolutions.hulk.config.HulkConfig;
import com.techletsolutions.hulk.model.Country;
import com.techletsolutions.hulk.model.ZipInfo;
import com.techletsolutions.hulk.reader.DataReader;

/**
 * Main Executor
 * 
 * @author <a href="mailto:yashpal@techletsolutions.com">Yashpal Meena</a>
 * @version $Revision: 1 $
 */
public class Main {

	public static void main(String[] args) throws IOException {
		EntityManager em = new EntityManager();
		em.open();
		// saveCountry(em);
		saveZipInfo(em);
		em.close();
	}


	public static void saveCountry(EntityManager em) throws IOException {
		String dataFile = HulkConfig.getProperty("hulk.app.datafilepath");
		DataReader reader = new DataReader();
		Country country = reader.parseCountryFromDataFile(dataFile);
		if (country != null) {
			country.save(em);
		}
	}
	
	public static void saveZipInfo(EntityManager em) throws IOException {
		String zipDataFileName = HulkConfig.getProperty("hulk.app.zipdatafilepath");
		String stateCodeDataFile = HulkConfig.getProperty("hulk.app.statecodedatafilepath");
		DataReader reader = new DataReader();
		 List<ZipInfo> zipInfoList = reader.parseZipcodeData(zipDataFileName, stateCodeDataFile);
		if (zipInfoList != null) {
			for (ZipInfo zipInfo : zipInfoList) {
				zipInfo.saveMe(em);
			}
		}
	}
}
