package library.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	//define the date pattern
	private static final String DATE_PATTERN = "MM/dd/yyyy";
	//Date formatter
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/**
	 * Return the given date as a well formatted String.
	 * 
	 * @param date as LocalDate
	 * @return date as String
	 */
	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		else {
			return DATE_FORMATTER.format(date);
		}
	}
	
	/**
	 * Convert a String date to LocalDate
	 * 
	 * @param date as String
	 * @return date as LocalDate Object
	 */
	public static LocalDate convert(String date) {
		try {
			return DATE_FORMATTER.parse(date, LocalDate::from);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * check the string whether it is a valid date
	 */
	public static boolean validDate(String date) {
		if (convert(date) != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
