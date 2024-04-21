package edu.remad.learnical4j.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static net.fortuna.ical4j.model.Parameter.*;

import org.threeten.extra.PackedFields;

import edu.remad.learnical4j.InterchangeCalendarProdId;
import edu.remad.learnical4j.constants.InterchangeCalendarConstants;
import edu.remad.learnical4j.exceptions.InterChangeCalendarUtilitiesException;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Email;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;

public class InterchangeCalendarUtilities {
	
	/**
	 * private Constructor not to instantiate
	 */
	private InterchangeCalendarUtilities() {
	}
	
	private static final RandomUidGenerator UID_GENERATOR = new RandomUidGenerator();
	
	private static final String GERMAN_TIME_PATTERN = "dd.MM.yyyy HH:mm";
	
	private static final String INTERNATIONAL_TIME_PATTERN = "yyyy-MM-dd HH:mm";
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(GERMAN_TIME_PATTERN, Locale.GERMANY);
	
	public static final DateTimeFormatter DATE_AND_TIME_FORMATTER = DateTimeFormatter.ofPattern(INTERNATIONAL_TIME_PATTERN);
	
	public static final String PRODUCTION_ID_KEY = "-//R.Meier Freelance Nachhilfe//Tutoring 2//EN";
	
	public static LocalDateTime convertStringDateToLocalDateTime(String meetingDate) {
		Calendar calendarDate = new GregorianCalendar();
		calendarDate.setTime(new Date(DATE_TIME_FORMATTER.parse(meetingDate).get(PackedFields.PACKED_DATE)));
		LocalDateTime meetingTime = LocalDateTime.ofInstant(calendarDate.toInstant(), calendarDate.getTimeZone().toZoneId());
		
		return meetingTime;		
	}
	
	public static Uid generateUid() {
		return UID_GENERATOR.generateUid();
	}
	
	public static InterchangeCalendarProdId createInterchangeCalendarProdId() {
		return new InterchangeCalendarProdId();
	}
	
	public static Map<String, List<Parameter>> createOrganizerMap(Map<String,String> organizerData) {
		if(organizerData.isEmpty()) {
			throw new InterChangeCalendarUtilitiesException("organizer data has to be poulated.");
		}
		
		List<Parameter> parameters = createParameterList(organizerData, Role.CHAIR);
		Map<String, List<Parameter>> organizer = new HashMap<>();
		organizer.put(InterchangeCalendarConstants.MAILTO_KEY + organizerData.get(EMAIL), parameters);
		
		return organizer;
	}

	private static List<Parameter> createParameterList(Map<String, String> organizerData, Role role) {
		List<Parameter> parameters = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : organizerData.entrySet()) {
			switch(entry.getKey()) {
			case EMAIL : {
				parameters.add(new Email(entry.getValue()));
				break;
			}
			case CN: {
				parameters.add(new Cn(entry.getValue()));
				break;
			}
			}
		}
		parameters.add(role);
		
		return parameters;
	}
	
	public static Map<String, List<Parameter>> createAttendeeMap(Map<String,String> attendeeData) {
		if(attendeeData.isEmpty()) {
			throw new IllegalStateException("attendee data has to be ppoulated.");
		}
		
		List<Parameter> parameters = createParameterList(attendeeData, Role.REQ_PARTICIPANT);
		Map<String, List<Parameter>> attendee = new HashMap<>();
		attendee.put(InterchangeCalendarConstants.MAILTO_KEY + attendeeData.get(EMAIL), parameters);
		
		return attendee;
	}
}
