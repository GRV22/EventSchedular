package com.ganesh.UI.Service;

import com.ganesh.UI.DTO.EventInfo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created by ganesh.kumar on 6/24/17.
 */
public interface CalendarService {
    public String insertNewEvent(EventInfo eventInfo) throws GeneralSecurityException, IOException;
    public String deleteEvent(String eventId) throws IOException, GeneralSecurityException;
    public List<EventInfo> getEvents() throws GeneralSecurityException, IOException;
    public EventInfo updateEvent(EventInfo eventInfo);
}
