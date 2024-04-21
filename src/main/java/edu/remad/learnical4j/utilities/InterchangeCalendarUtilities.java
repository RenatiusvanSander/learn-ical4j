package edu.remad.learnical4j.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.fortuna.ical4j.model.Parameter.*;

import edu.remad.learnical4j.constants.InterchangeCalendarConstants;
import edu.remad.learnical4j.exceptions.InterChangeCalendarUtilitiesException;
import edu.remad.learnical4j.models.InterchangeCalendarProdId;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Email;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Uid;

public class InterchangeCalendarUtilities {
	
	/**
	 * private Constructor not to instantiate
	 */
	private InterchangeCalendarUtilities() {
	}
	
	public static Uid generateUid() {
		return InterchangeCalendarConstants.UID_GENERATOR.generateUid();
	}
	
	public static InterchangeCalendarProdId createInterchangeCalendarProdId() {
		return new InterchangeCalendarProdId();
	}
	
	public static Map<String, List<Parameter>> createOrganizerMap(Map<String,String> organizerData) {
		if(organizerData.isEmpty()) {
			throw new InterChangeCalendarUtilitiesException("organizer data has to be populated.");
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
			throw new IllegalStateException("attendee data has to be populated.");
		}
		
		List<Parameter> parameters = createParameterList(attendeeData, Role.REQ_PARTICIPANT);
		Map<String, List<Parameter>> attendee = new HashMap<>();
		attendee.put(InterchangeCalendarConstants.MAILTO_KEY + attendeeData.get(EMAIL), parameters);
		
		return attendee;
	}
}
