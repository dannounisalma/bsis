package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateTime;

public class CustomDateFormatter {

  private static String datePattern = "yyyy-MM-dd";
  private static String datePatternHumanReadable = "MM/dd/yyyy"; 

  private static String dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static String dateTimePatternHumanReadable = "MM/dd/yyyy hour:minute:second AM/PM";
  private static String timePattern = "hh:mm:ss a"; 
  
  private static DateFormat dateFormat;
  private static DateFormat dateTimeFormat;
  private static DateFormat timeFormat;

  static {
    dateFormat = new SimpleDateFormat(getDatePattern());
    dateFormat.setLenient(false);
    dateTimeFormat = new SimpleDateFormat(getDateTimePattern());
    dateTimeFormat.setLenient(false);
    timeFormat = new SimpleDateFormat(getTimePattern());
    timeFormat.setLenient(false);
  }

  public static Date getDateFromString(String dateString) throws ParseException {
    Date date = null;

    if (!isDateEmpty(dateString))
      date = new DateTime(dateString).toDate();

    return date;
  }

  public static Date getDateTimeFromString(String dateTimeString) throws ParseException {
    Date date = null;
    if (!isDateEmpty(dateTimeString))
      date = new DateTime(dateTimeString).toDate();
    return date;
  }
  
  public static Date getTimeFromString(String timeString) throws ParseException {
    Date date = null;
    if (!isDateEmpty(timeString))
      date = timeFormat.parse(timeString);
    return date;
  }

  public static boolean isDateEmpty(String dateString) {
    return (dateString == null || dateString.trim().isEmpty());
  }

  public static boolean isDateStringValid(String dateString) {
    boolean valid = false;
    if (isDateEmpty(dateString)) {
      valid = true;
    } else {
      try {
        dateFormat.parse(dateString);
        valid = true;
      } catch (ParseException ex) {
        ex.printStackTrace();
        valid = false;
      }
    }
    return valid;
  }

  public static boolean isDateTimeStringValid(String dateTimeString) {
    boolean valid = false;
    if (isDateEmpty(dateTimeString)) {
      valid = true;
    } else {
      try {
        dateTimeFormat.parse(dateTimeString);
        valid = true;
      } catch (ParseException ex) {
        ex.printStackTrace();
        valid = false;
      }
    }
    return valid;
  }
  
  public static boolean isTimeStringValid(String timeString) {
    boolean valid = false;
    if (isDateEmpty(timeString)) {
      valid = true;
    } else {
      try {
        timeFormat.parse(timeString);
        valid = true;
      } catch (ParseException ex) {
        ex.printStackTrace();
        valid = false;
      }
    }
    return valid;
  }

  public static String getDateErrorMessage() {
    return "Invalid Date specified.";
  }

  public static String getDateTimeErrorMessage() {
    return "Invalid Date specified.";
  }
  
  public static String getTimeErrorMessage() {
    return "Invalid Time specified.";
  }

  public static String getDateString(Date date) {
    if (date == null)
      return "";
    else
      return getISO8601StringForDate(date);
  }

  public static String getDateTimeString(Date date) {
    if (date == null)
      return "";
    else
      return getISO8601StringForDate(date);
  }
  
  public static String getTimeString(Date date) {
    if (date == null)
      return "";
    else
      return getISO8601StringForDate(date);
  }

  public static String getDatePattern() {
    return datePattern;
  }

  public static void setDatePattern(String datePattern) {
    CustomDateFormatter.datePattern = datePattern;
  }

  public static String getDatePatternHumanReadable() {
    return datePatternHumanReadable;
  }

  public static void setDatePatternHumanReadable(
      String datePatternHumanReadable) {
    CustomDateFormatter.datePatternHumanReadable = datePatternHumanReadable;
  }

  public static String getDateTimePattern() {
    return dateTimePattern;
  }

  public static String getDateTimePatternHumanReadable() {
    return dateTimePatternHumanReadable;
  }
  
  public static String getTimePattern() {
    return timePattern;
  }
  
  public static void setTimePattern(String timePattern) {
    CustomDateFormatter.timePattern = timePattern;
  }

  public static String format(Date date){
	  return getDateString(date);
  }
  
  public static Date parse(String dateStr) throws ParseException{
	  return getDateFromString(dateStr);
  }

  private static String getISO8601StringForDate(Date date) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat.format(date);
  }
}
