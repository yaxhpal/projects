package es.oeuvres.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUtils {

	public static Integer getIntegerFromString(String text) {
		try {
			Pattern pattern = Pattern.compile("([\\d]+)");
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				// System.out.println(matcher.group(1));
				return new Integer(matcher.group(1));
			}
			
		} catch (Exception e) {
			return -1;
		}
		return null;
	}
	
	public static Long getLongFromString(String text) {
		Pattern pattern = Pattern.compile("([\\d]+)");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			// System.out.println(matcher.group(1));
			return new Long(matcher.group(1));
		}
		return null;
	}
	
	public static void main(String[] args) {
		String[] literals = {"1999.0","1888.0","2006.0"};
		
		for (String string : literals) {
			///System.out.println(string);
			Integer idea = getIntegerFromString(string);
			System.out.println(idea);
		}
	}
}
