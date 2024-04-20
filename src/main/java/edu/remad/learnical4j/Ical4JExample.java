package edu.remad.learnical4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Email;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.validate.ValidationException;

public class Ical4JExample {

	public static void main(String[] args) {
		/* Event start and end time in milliseconds */
		String startTimeString = "2024-04-20 10:00";
		String endTimeString = "2024-04-20 11:00";
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String appointmentName = "Nachhilfe";
		String stringProdId = "-//R.Meier Freelance Nachhilfe//Tutoring 2//EN";
		String filePath = "rmeier_freelace_nachhilfe_termin.ics";
		ProdId prodId = new ProdId(stringProdId);
		String email = "remad@web.de";
		String attendeeEmail = "pamela@hotty.de";
		String attendeeEmail2 = "pamela2@hotty.de";
		String userName = "Remy" + " " + "Meier";
		String mailTo = "mailto:";
		String location = "Remote Teams";
		String attendeeUsername1 = "Pamela 1";
		String attendeeUsername2 = "Pamela 2";

		List<Parameter> organizerParameters = new ArrayList<Parameter>();
		organizerParameters.add(new Email(email));
		organizerParameters.add(new Cn(userName));
		organizerParameters.add(Role.CHAIR);
		Map<String, List<Parameter>> organizers = new HashMap<>();
		organizers.put(mailTo + email, organizerParameters);
		
		List<Parameter> attendeeParameters = new ArrayList<Parameter>();
		attendeeParameters.add(new Email(attendeeEmail));
		attendeeParameters.add(new Cn(attendeeUsername1));
		attendeeParameters.add(Role.REQ_PARTICIPANT);
		List<Parameter> attendeeParameters2 = new ArrayList<Parameter>();
		attendeeParameters2.add(new Email(attendeeEmail));
		attendeeParameters2.add(new Cn(attendeeUsername2));
		attendeeParameters2.add(Role.REQ_PARTICIPANT);
		Map<String, List<Parameter>> attendees = new HashedMap<>();
		attendees.put(mailTo + attendeeEmail, attendeeParameters);
		attendees.put(mailTo + attendeeEmail2, attendeeParameters2);
		attendees.put(mailTo + email, organizerParameters);

		InterchangeCalendarData calendarData = new InterchangeCalendarData();
		calendarData.setStartTime(LocalDateTime.parse(startTimeString, timeFormatter));
		calendarData.setEndTime(LocalDateTime.parse(endTimeString, timeFormatter));
		calendarData.setAppointmentName(appointmentName);
		calendarData.setLocation(location);
		calendarData.setAttendees(attendees);
		calendarData.setOrganizers(organizers);
		calendarData.setProdId(prodId);
		calendarData.setFilePath(filePath);

		byte[] icsFile = new InterchangeCalendarBuilder().setStartTime(calendarData.getStartTime())
				.setEndTime(calendarData.getEndTime()).setAppointmentName(calendarData.getAppointmentName())
				.setAttendees(calendarData.getAttendees()).setOrganizers(calendarData.getOrganizers())
				.setProdId(calendarData.getProdId()).setLocation(new Location(calendarData.getLocation())).build();

		try (FileOutputStream fis = new FileOutputStream(new File(calendarData.getFilePath()))) {
			fis.write(icsFile);
		} catch (ValidationException | IOException e) {
			// TODO
		}
	}
}
