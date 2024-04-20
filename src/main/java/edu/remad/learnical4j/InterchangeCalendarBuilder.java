package edu.remad.learnical4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.validate.ValidationException;

public class InterchangeCalendarBuilder {

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private String appointmentName;

	private Map<String, List<Parameter>> attendees;

	private Map<String, List<Parameter>> organizers;

	private Property prodId;

	private Location location;

	public InterchangeCalendarBuilder setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;

		return this;
	}

	public InterchangeCalendarBuilder setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;

		return this;
	}

	public InterchangeCalendarBuilder setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;

		return this;
	}

	public InterchangeCalendarBuilder setAttendees(Map<String, List<Parameter>> attendees) {
		this.attendees = attendees;

		return this;
	}

	public InterchangeCalendarBuilder setOrganizers(Map<String, List<Parameter>> organizers) {
		this.organizers = organizers;

		return this;
	}

	public InterchangeCalendarBuilder setProdId(Property prodId) {
		this.prodId = prodId;

		return this;
	}

	public InterchangeCalendarBuilder setLocation(Location location) {
		this.location = location;

		return this;
	}

	public byte[] build() {
		VEvent event = new VEvent(startTime, endTime, appointmentName);
		event.add(InterchangeCalendarUtilities.generateUid());
		addAttendees(event);
		addOrganizers(event);
		event.add(location);
		event.add(new Method(Method.VALUE_REQUEST));
		event.add(new Status(Status.VALUE_CONFIRMED));

		Calendar icsCalendar = new Calendar();
		icsCalendar.add(prodId);
		icsCalendar.add(event);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(icsCalendar, out);

			return out.toByteArray();
		} catch (ValidationException | IOException e) {
			return new byte[0];
		}
	}

	private void addOrganizers(VEvent event) {
		for (Entry<String, List<Parameter>> organizerEntry : organizers.entrySet()) {
			URI uri = URI.create(organizerEntry.getKey());
			Organizer organizer = new Organizer(uri);

			for (Parameter parameter : organizerEntry.getValue()) {
				organizer.add(parameter);
			}

			event.add(organizer);
		}
	}

	private void addAttendees(VEvent event) {
		for (Entry<String, List<Parameter>> attendeeEntry : attendees.entrySet()) {
			URI uri = URI.create(attendeeEntry.getKey());
			Attendee attendee = new Attendee(uri);

			for (Parameter parameter : attendeeEntry.getValue()) {
				attendee.add(parameter);
			}

			event.add(attendee);
		}
	}
}
