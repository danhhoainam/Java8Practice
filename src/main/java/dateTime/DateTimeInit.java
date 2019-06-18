package dateTime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author: Nichol
 * @date: {date: yyyy-MM-dd}
 * @description:
 */
public class DateTimeInit {

	public static LocalDate getCurrentLocalDate() {

		return LocalDate.now();
	}

	public static LocalTime getCurrentLocalTime() {

		return LocalTime.now();
	}

	public static LocalDateTime getCurrentLocalDateTime() {

		return LocalDateTime.now();
	}

	public static long getEpochDaysForLocalDate(LocalDate localDate) {

		if (localDate == null) {
			throw new NullPointerException();
		}

		return localDate.toEpochDay();
	}

	public static long getEpochSecondsForLocalDateTime(LocalDateTime localDateTime) {

		if (localDateTime == null) {
			throw new NullPointerException();
		}

		return localDateTime.toEpochSecond(ZoneOffset.UTC);
	}

	public static LocalDate parseStringToLocalDate(String localDateStr, String pattern) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate date = LocalDate.parse(localDateStr, formatter);
		return date;
	}

	public static LocalDateTime parseStringToLocalDateTime(String localDateTimeStr, String pattern) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(localDateTimeStr, formatter);
		return dateTime;
	}

	public static LocalTime parseStringToLocalTime(String localTimeString, String pattern) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalTime time = LocalTime.parse(localTimeString, formatter);
		return time;
	}

	public static void testInitDateTime() {

		LocalDate localDate = LocalDate.of(2019, Month.APRIL, 29);
		LocalTime localTime = LocalTime.of(23, 59, 59, 100);
		LocalDateTime localDateTime = LocalDateTime.of(2019, Month.APRIL, 29, 23, 59, 59, 100);

		localDate = localDate.plusDays(2);
		localDate = localDate.plusWeeks(3);
		localDate = localDate.plusMonths(1);
		localDate = localDate.plusYears(3);

		localDateTime = localDateTime.minusDays(2);
		localDateTime = localDateTime.minusHours(3);
		localDateTime = localDateTime.minusSeconds(333);
		localDateTime = localDateTime.minusMonths(1);

		Period daily = Period.ofDays(1);
		Period weekly = Period.ofWeeks(1);
		Period monthly = Period.ofMonths(1);
		Period yearly = Period.ofYears(1);

		localDate = localDate.plus(daily);
		localDateTime = localDateTime.plus(yearly);

		localDate = localDate.minus(weekly);
		localDateTime = localDateTime.minus(monthly);

		Period everyYearAndAWeek = Period.of(1, 0, 7);
		localDateTime.plus(everyYearAndAWeek);


		System.out.println(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

		DateTimeFormatter shortLocalizeDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		DateTimeFormatter mediumLocalizeDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		DateTimeFormatter longLocalizeDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
		DateTimeFormatter fullLolalizeDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		System.out.println("localDate short format: " + localDate.format(shortLocalizeDateFormatter));
		System.out.println("localDate medium format: " + localDate.format(mediumLocalizeDateFormatter));
		System.out.println("localDate long format: " + localDate.format(longLocalizeDateFormatter));
		System.out.println("localDate full format: " + localDate.format(fullLolalizeDateFormatter));

		DateTimeFormatter shortLocalizeDateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		DateTimeFormatter mediumLocalizeDateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		DateTimeFormatter longLocalizeDateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
		DateTimeFormatter fullLolalizeDateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		System.out.println("localDateTime short format: " + localDateTime.format(shortLocalizeDateTimeFormatter));
		System.out.println("localDateTime medium format: " + localDateTime.format(mediumLocalizeDateTimeFormatter));
		System.out.println("localDateTime long format: " + localDateTime.format(longLocalizeDateTimeFormatter));
		System.out.println("localDateTime full format: " + localDateTime.format(fullLolalizeDateTimeFormatter));

		DateTimeFormatter shortLocalizeTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		DateTimeFormatter mediumLocalizeTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
//		DateTimeFormatter longLocalizeTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG); --> not work
//		DateTimeFormatter fullLolalizeTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL); --> not work
		System.out.println("localTime short format: " + localTime.format(shortLocalizeTimeFormatter));
		System.out.println("localTime medium format: " + localTime.format(mediumLocalizeTimeFormatter));
//		System.out.println("localTime long format: " + localTime.format(longLocalizeTimeFormatter)); --> not work
//		System.out.println("localTime full format: " + localTime.format(fullLolalizeTimeFormatter)); --> not work

		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
		System.out.println(localDateTime.format(customFormatter));
	}
}
