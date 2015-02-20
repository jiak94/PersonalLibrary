package library.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleBookInfo {
	private final static String apiKey = "AIzaSyD0sKZb2tQ3vzWYO5d-poGqQHENyeSV5ws";
	private final static String requestUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
	
	public static String getJSONString(String isbn) throws Exception{
		URL url = new URL(requestUrl + isbn + "&key=" + apiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream inputStream = connection.getInputStream();

        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);

        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            sb.append(str);
        }
        reader.close();
        connection.disconnect();
        return sb.toString();
	}
	
	public static String getMediumImage(String isbn) throws Exception {
		String jsonString = getJSONString(isbn);
		if (jsonString == null) {
    		return "file:Resources/images/no_book_cover.jpg";
    	}
    	String imageAddress = "file:Resources/images/no_book_cover.jpg";
    	JSONObject jsonObject = new JSONObject(jsonString);
    	JSONArray item = jsonObject.getJSONArray("items");
    	try {
    		for (int i = 0; i < item.length(); i++) {
    			imageAddress = item.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
    		}
    	} catch(Exception e) {
    		imageAddress = "file:Resources/images/no_book_cover.jpg";
    	}
    	return imageAddress;
	}
	
	public static String getSmallImage(String isbn) throws Exception {
		String jsonString = getJSONString(isbn);
		if (jsonString == null) {
    		return "file:Resources/images/no_book_cover.jpg";
    	}
    	String imageAddress = "file:Resources/images/no_book_cover.jpg";
    	JSONObject jsonObject = new JSONObject(jsonString);
    	JSONArray item = jsonObject.getJSONArray("items");
    	try {
    		for (int i = 0; i < item.length(); i++) {
    			imageAddress = item.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("smallThumbnail");
    		}
    	} catch(Exception e) {
    		imageAddress = "file:Resources/images/no_book_cover.jpg";
    	}
    	return imageAddress;
	}
	
	public static String getPublisher(String isbn) throws Exception {
		String jsonString = getJSONString(isbn);
		if (jsonString == null) {
			return "";
		}
		
		String publisher = "";
		JSONObject obj = new JSONObject(jsonString);
		JSONArray item = obj.getJSONArray("items");
		try {
			for (int i = 0; i < item.length(); i++) {
				publisher = item.getJSONObject(i).getJSONObject("volumeInfo").getString("publisher");
			}
		} catch(Exception e) {
			publisher = "";
		}
		return publisher;
	}
	
	public static String getAuthor(String isbn) throws Exception {
		String jsonString = getJSONString(isbn);
			if(jsonString == null) {
				return "";
			}
			
			String author = "";
			JSONObject obj = new JSONObject(jsonString);
			JSONArray item = obj.getJSONArray("items");
			int length = item.length();
			try {
				for (int i = 0; i < length; i++) {
					item = item.getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("authors");
					for (int j = 0; j < item.length(); j++) {
						author += item.getString(i) + " ";
					}
				}
			} catch (Exception e) {
				author = "";
			}
			return author;
	}
}
