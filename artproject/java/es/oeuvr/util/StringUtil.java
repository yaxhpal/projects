package es.oeuvr.util;

public class StringUtil {
	private static String salt = "ABGDGW03MhOC0Fpnaqs84SJ02l9Q10";

	public static final String hash(String username, String password) {
		try {
			StringBuilder sb = new StringBuilder(username.toLowerCase());
			sb.append(password);
			sb.append(salt);
			return Hash.hash(sb.toString());
		} catch (Exception uex) {
			// Ignore
		}
		return null;
	}

	public static boolean isEmpty(String string) {
		
		if (string == null) {
			return true;
		}
		
		if (string.trim().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	
    public static void main(String[] args)
    {
        // for (int i = 0; i != 10000000; i++) hash(args[0]);
        //System.out.println(hash(args[0]));
    	System.out.println(hash("a","b"));
    }
}