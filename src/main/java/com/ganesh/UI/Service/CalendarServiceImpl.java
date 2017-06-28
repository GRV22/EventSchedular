package com.ganesh.UI.Service;

import com.ganesh.UI.DTO.EventInfo;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by ganesh.kumar on 6/19/17.
 */
@Service
public class CalendarServiceImpl implements CalendarService{


    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    private Properties loadProperties(){
        Properties properties = new Properties();
        ClassLoader classloader = CalendarServiceImpl.class.getClassLoader();
        InputStream input = classloader.getResourceAsStream("config.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return properties;
    }

    private Calendar loadCredentials() throws GeneralSecurityException, IOException {

        Properties properties = loadProperties();

        File credentialsFile = new File(System.getProperty("user.dir")+"/src/main/resources/"+properties.getProperty("p12FileName"));


        GoogleCredential credentials = new GoogleCredential.Builder()
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(properties.getProperty("serviceAccountId"))
                .setServiceAccountScopes(Arrays.asList(CalendarScopes.CALENDAR))
                .setServiceAccountPrivateKeyFromP12File(credentialsFile)
                .build();


        Calendar client = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                credentials).setApplicationName(properties.getProperty("applicationName")).build();
        return client;
    }


    @Transactional
    public String insertNewEvent(EventInfo eventInfo) throws GeneralSecurityException, IOException {

        Calendar client = loadCredentials();

        Event event = new Event().setSummary(eventInfo.getEventName()).setDescription(eventInfo.getSummary());

        DateTime startDateTime = new DateTime(eventInfo.getStartTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventInfo.getEndTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
        for (String emailId: eventInfo.getAttendee()) {
            attendees.add(new EventAttendee().setEmail(emailId));
        }

        event.setAttendees(attendees);

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        Properties properties = loadProperties();
        event = client.events().insert(properties.getProperty("calendar"), event).execute();

        System.out.printf("Event created: %s\n", event.getHtmlLink());
        return event.getStatus();
    }

    @Transactional
    public String deleteEvent(String eventId) throws IOException, GeneralSecurityException {

        Calendar client = loadCredentials();
        Properties properties = loadProperties();
        client.events().delete(properties.getProperty("calendar"), eventId).execute();
        return "Successfully Deleted";
    }


    public EventInfo getEventById(String eventId) throws GeneralSecurityException, IOException {
        Calendar client = loadCredentials();
        Properties properties = loadProperties();

        Event event = client.events().get(properties.getProperty("calendar"), eventId).execute();
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventId(event.getId());
        eventInfo.setStartTime(event.getStart().getDateTime().toString());
        eventInfo.setEndTime(event.getEnd().getDateTime().toString());
        eventInfo.setSummary(event.getDescription());
        eventInfo.setEventName(event.getSummary());
        List<String> guests = new ArrayList<String>();
        for (EventAttendee attendee : event.getAttendees()){
            guests.add(attendee.getEmail());
        }
        eventInfo.setAttendee(guests);

        return eventInfo;
    }

    @Transactional
    public List<EventInfo> getEvents() throws GeneralSecurityException, IOException {

        Calendar client = loadCredentials();
        Properties properties = loadProperties();

        List<Event> events = client.events().list(properties.getProperty("calendar")).execute().getItems();

        ArrayList<EventInfo> eventList = new ArrayList<EventInfo>();

        for(Event event : events){
            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventId(event.getId());
            eventInfo.setStartTime(event.getStart().getDateTime().toString());
            eventInfo.setEndTime(event.getEnd().getDateTime().toString());
            eventInfo.setSummary(event.getDescription());
            eventInfo.setEventName(event.getSummary());
            List<String> guests = new ArrayList<String>();
            for (EventAttendee attendee : event.getAttendees()){
                guests.add(attendee.getEmail());
            }
            eventInfo.setAttendee(guests);
            eventList.add(eventInfo);
        }
        return eventList;
    }

    @Transactional
    public EventInfo updateEvent(EventInfo eventInfo) throws GeneralSecurityException, IOException {

        Calendar client = loadCredentials();
        Properties properties = loadProperties();

        Event event = new Event().setSummary(eventInfo.getEventName()).setDescription(eventInfo.getSummary());

        DateTime startDateTime = new DateTime(eventInfo.getStartTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(eventInfo.getEndTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
        for (String emailId: eventInfo.getAttendee()) {
            attendees.add(new EventAttendee().setEmail(emailId));
        }

        event.setAttendees(attendees);

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        event = client.events().update(properties.getProperty("calendar"), eventInfo.getEventId(), event).execute();

        eventInfo.setEventId(event.getId());
        eventInfo.setStartTime(event.getStart().getDateTime().toString());
        eventInfo.setEndTime(event.getEnd().getDateTime().toString());
        eventInfo.setSummary(event.getDescription());
        eventInfo.setEventName(event.getSummary());
        List<String> guests = new ArrayList<String>();
        for (EventAttendee attendee : event.getAttendees()){
            guests.add(attendee.getEmail());
        }
        eventInfo.setAttendee(guests);

        return eventInfo;
    }

}
