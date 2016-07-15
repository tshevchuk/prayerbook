package com.tshevchuk.calendar_validation.google_calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by taras on 08.07.16.
 */
public class GoogleCalendar {
    private static final String CLIENT_SECRET = "{\"installed\":{\"client_id\":\"795355244345-7me1ckjm7c1ohgrbd7250f49b83oncr0.apps.googleusercontent.com\",\"project_id\":\"gifted-healer-136623\",\"auth_uri\":\"https://accounts.google.com/o/oauth2/auth\",\"token_uri\":\"https://accounts.google.com/o/oauth2/token\",\"auth_provider_x509_cert_url\":\"https://www.googleapis.com/oauth2/v1/certs\",\"client_secret\":\"7i8_UHvX1MYjOFwdpH3J_Q9Q\",\"redirect_uris\":[\"urn:ietf:wg:oauth:2.0:oob\",\"http://localhost\"]}}";

    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart.json");
    /**
     * 1
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     * <p/>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart.json
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    private final Calendar calendarService;

    public GoogleCalendar() throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        calendarService = getCalendarService();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = new ByteArrayInputStream(CLIENT_SECRET.getBytes());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     *
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<CalendarListEntry> getCalendars() throws IOException {
        return calendarService.calendarList().list().execute().getItems();
    }

    public CalendarListEntry getCalendarByName(String name) throws IOException {
        List<CalendarListEntry> items = calendarService.calendarList().list().execute().getItems();
        for (CalendarListEntry cal : items) {
            if (name.equals(cal.getSummary())) {
                return cal;
            }
        }
        return null;
    }

    public void addEvent(String calendarId, GoogleCalendarEvent calendarEvent) throws IOException {
        Event event = new Event()
                .setSummary(calendarEvent.getSummary())
                .setDescription(calendarEvent.getDescription());
        event.setStart(new EventDateTime().setDate(new DateTime(calendarEvent.getDate())));
        event.setEnd(new EventDateTime().setDate(new DateTime(calendarEvent.getDate())));
        event.setVisibility("public");
        event.setColorId(String.valueOf(calendarEvent.getColor()));

        event = calendarService.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    public void removeCalendarEvent(String calendarId, Event event) throws IOException {
        calendarService.events().delete(calendarId, event.getId()).execute();
    }

    public List<Event> getAllEventsFromCalendar(String calendarId) throws IOException {
        List<Event> eventsList = new ArrayList<>();
        String pageToken = null;
        do {
            Events events = calendarService.events().list(calendarId).setPageToken(pageToken).execute();
            eventsList.addAll(events.getItems());
            pageToken = events.getNextPageToken();
        } while (pageToken != null);
        return eventsList;
    }
}
