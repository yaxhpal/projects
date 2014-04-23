package es.oeuvres.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUtils {

	public static Integer getIntegerFromString(String text) {
		try {
			Pattern pattern = Pattern.compile("([\\d]+)");
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				return new Integer(matcher.group(1));
			}
		} catch (Exception e) {
			return -1;
		}
		return -1;
	}
	
	
	public static Float getFloatFromString(String text) {
		try {
			Pattern pattern = Pattern.compile("(\\d+\\.?\\d+)");
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				return Float.parseFloat(matcher.group(1));
			}
		} catch (Exception e) {
			// What to do?
		}
		return 0F;
	}
	
	public static Long getLongFromString(String text) {
		Pattern pattern = Pattern.compile("([\\d]+)");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			// System.out.println(matcher.group(1));
			return new Long(matcher.group(1));
		}
		return -1L;
	}
	
	public static void main(String[] args) {
		String[] literals = {"1999.0","1888.0","2006.5"};
		
		for (String string : literals) {
			///System.out.println(string);
			//Integer idea = getIntegerFromString(string);
			Float idea2 =  getFloatFromString(string);
			System.out.println(idea2*100);
		}
	}
}
