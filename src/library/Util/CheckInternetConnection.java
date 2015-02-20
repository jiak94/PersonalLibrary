package library.Util;

import java.io.InputStream;
import java.net.URL;

public class CheckInternetConnection {
	public static boolean checkConnection() {
		URL url = null;
		InputStream in;
		try {
			url = new URL("https://google.com");
			in = url.openStream();
			in.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
