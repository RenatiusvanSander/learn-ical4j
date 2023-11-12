package edu.remad.learnical4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

public class Ical4JExample {

	public static void main(String[] args) {
		/* Event start and end time in milliseconds */
		String dateStartString = "13.11.2023 10:00";
		String dateEndString = "13.11.2023 11:00";

		/* Create the event */
		String eventSummary = "New Year";
		LocalDateTime startTime = InterchangeCalendarUtilities.convertStringDateToLocalDateTime(dateStartString);
		LocalDateTime endTime = InterchangeCalendarUtilities.convertStringDateToLocalDateTime(dateEndString);

		VEvent event = new VEvent(startTime, endTime, eventSummary);
		/* Generate unique identifier */
		UidGenerator ug = new RandomUidGenerator();
		Uid uid = ug.generateUid();
		event.add(uid);

		// own addAttendees
		// Add email addresses as attendees
		Attendee attendee1 = new Attendee("danny@example.com");
		Attendee attendee2 = new Attendee("jenifer@example.com");
		event.add(attendee1);
		event.add(attendee2);

		// own method
		// Create an Organizer
		Organizer organizer = new Organizer();
		organizer.setValue("MAILTO:remad@web.de");
		event.add(organizer);

		/* Create calendar */
		Calendar icsCalendar = new Calendar();
		icsCalendar.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));

		// Set the location
		Location location = new Location("Conference Room");

		// Add the Location to the event
		event.add(location);

		/* Add event to calendar */
		icsCalendar.add(event);

		/* Create a file */
		String filePath = "my_meeting.ics";
		try (FileOutputStream out = new FileOutputStream(filePath))  {
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(icsCalendar, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
