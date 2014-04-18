package com.techletsolutions.hulk;

import java.io.IOException;

import com.techletsolutions.db.EntityManager;
import com.techletsolutions.hulk.config.HulkConfig;
import com.techletsolutions.hulk.model.Country;
import com.techletsolutions.hulk.reader.DataReader;

/**
 * Main Executor
 * 
 * @author <a href="mailto:yashpal@techletsolutions.com">Yashpal Meena</a>
 * @version $Revision: 1 $
 */
public class Main {

	public static void main(String[] args) throws IOException {
		String dataFile = HulkConfig.getProperty("hulk.app.datafilepath");
		DataReader reader = new DataReader();
		Country country = reader.parseCountryFromDataFile(dataFile);
		if (country != null) {
			EntityManager em = new EntityManager();
			em.open();
			country.save(em);
			em.close();
		}
	}
}
