package com.hulk.jobs.core;

import java.io.IOException;
import java.net.URL;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.hulk.jobs.model.AAPCJob;

public class JacksonExample {
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// read from file, convert it to user class
			//AAPCJob[] jobs = mapper.readValue(new File("/home/yashpal/Projects/hulk/AAPCJobs/src/resources/apppcjobtest.json"), AAPCJob[].class);
			AAPCJob[] jobs = mapper.readValue(new URL("http://dev.public.webapi.aapc.com/api/JobListing"), AAPCJob[].class);
			for (AAPCJob aapcJob : jobs) {
				System.out.println(aapcJob.getJobtitle());
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}