package edu.remad.learnical4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.validate.ValidationException;

public class Ical4JExample {

	public static void main(String[] args) {
		/* Event start and end time in milliseconds */
		String startTimeString = "13.11.2023 10:00";
		String endTimeString = "13.11.2023 11:00";
		String appointmentName = "Nachhilfe";

		InterchangeCalendarData calendarData = new InterchangeCalendarData();
		calendarData.setStartTimestamp(new Timestamp(6325635636535L));
		calendarData.setStartTimeString(startTimeString);
		calendarData.setEndTimestamp(new Timestamp(46283742378473274L));
		calendarData.setEndTimeString(endTimeString);
		calendarData.setAppointmentName(appointmentName);
		calendarData.setLocation("Location");
		Map<String, List<Parameter>> attendees = new HashedMap<>();
		attendees.put("pamela@hotty.de", new ArrayList<Parameter>());
		attendees.put("pamela2@hotty.de", new ArrayList<Parameter>());
		calendarData.setAttendees(attendees);
		Map<String, List<Parameter>> organizers = new HashMap<>();
		organizers.put("MAILTO:remad@web.de", new ArrayList<Parameter>());
		calendarData.setOrganizers(organizers);
		ProdId prodId = new ProdId("-//Ben Fortuna//iCal4j 1.0//EN");
		calendarData.setProdId(prodId);
		String filePath = "rmeier_freelace_nachhilfe_termin.ics";
		calendarData.setFilePath(filePath);

		byte[] icsFile = new InterchangeCalendarBuilder().setStartTimestamp(calendarData.getStartTimestamp()).setEndTimestamp(calendarData.getEndTimestamp()).setAppointmentName(calendarData.getAppointmentName()).setAttendees(calendarData.getAttendees()).setOrganizers(calendarData.getOrganizers()).setProdId(calendarData.getProdId()).setLocation(new Location(calendarData.getLocation()) ).build();
		
		try (FileOutputStream fis = new FileOutputStream(new File(calendarData.getFilePath()))) {
			fis.write(icsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
