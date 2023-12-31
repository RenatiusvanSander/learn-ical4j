package edu.remad.learnical4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.threeten.extra.PackedFields;

import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;

public class InterchangeCalendarUtilities {
	
	private static final RandomUidGenerator UID_GENERATOR = new RandomUidGenerator();
	
	private static final String GERMAN_TIME_PATTERN = "dd.MM.yyyy HH:mm";
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(GERMAN_TIME_PATTERN, Locale.GERMANY);
	
	public static LocalDateTime convertStringDateToLocalDateTime(String meetingDate) {
		Calendar calendarDate = new GregorianCalendar();
		calendarDate.setTime(new Date(DATE_TIME_FORMATTER.parse(meetingDate).get(PackedFields.PACKED_DATE)));
		LocalDateTime meetingTime = LocalDateTime.ofInstant(calendarDate.toInstant(), calendarDate.getTimeZone().toZoneId());
		
		return meetingTime;		
	}
	
	public static Uid generateUid() {
		return UID_GENERATOR.generateUid();
	}
}
