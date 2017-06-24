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

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by ganesh.kumar on 6/19/17.
 */
public class CalendarService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CalendarService.class);

    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    public static String printPath() throws IOException {
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream input = classloader.getResourceAsStream("config.properties");
        properties.load(input);
        InputStream privateFileStream = classloader.getResourceAsStream(properties.getProperty("p12FileName"));
        File file = new File(String.valueOf(privateFileStream));
        return System.getProperty("user.home");
    }

    public static String insertNewEvent2(String eventName,String summary,String startTime,String endTime,String guests) throws GeneralSecurityException, IOException {
        return eventName+" : "+summary;
    }

    public static String insertNewEvent(EventInfo eventInfo) throws GeneralSecurityException, IOException {

        log.debug("Target 1");
        Properties properties = new Properties();
        ClassLoader classloader = CalendarService.class.getClassLoader();//Thread.currentThread().getContextClassLoader();
        InputStream input = classloader.getResourceAsStream("config.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();

        }

        log.debug("Target 2");
        File credentialsFile = new File(System.getProperty("user.dir")+"/src/main/resources/"+properties.getProperty("p12FileName"));


        GoogleCredential credentials = new GoogleCredential.Builder()
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(properties.getProperty("serviceAccountId"))
                .setServiceAccountScopes(Arrays.asList(CalendarScopes.CALENDAR))
                .setServiceAccountPrivateKeyFromP12File(credentialsFile)
                .build();

        log.debug("Target 3");

        Calendar client = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                credentials).setApplicationName(properties.getProperty("applicationName")).build();

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


        event = client.events().insert(properties.getProperty("calendar"), event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
        return event.getHtmlLink();
    }

    public static List<EventInfo> getEvents() throws GeneralSecurityException, IOException {

        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream input = classloader.getResourceAsStream("config.properties");
        properties.load(input);

        File credentialsFile = new File(String.valueOf(classloader.getResourceAsStream(properties.getProperty("p12FileName"))));

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
        }
        return eventList;
    }


    public static void insertEvent() throws GeneralSecurityException, IOException {

        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream input = classloader.getResourceAsStream("config.properties");
        properties.load(input);

        File credentialsFile = new File(String.valueOf(classloader.getResourceAsStream(properties.getProperty("p12FileName"))));

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

        Event event = new Event().setSummary("Test Event").setDescription("Testing event");

        DateTime startDateTime = new DateTime("2017-06-19T00:00:00+05:30");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime("2017-06-19T01:00:00+05:30");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(end);

        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("ganesh.kumar@payu.in"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);


        event = client.events().insert(properties.getProperty("calendar"), event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }


}
