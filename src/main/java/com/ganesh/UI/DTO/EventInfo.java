package com.ganesh.UI.DTO;

import java.util.List;

/**
 * Created by ganesh.kumar on 6/20/17.
 */
public class EventInfo {

    String eventId;
    String eventName;
    String summary;
    String startTime;
    String endTime;
    List<String> attendee;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getAttendee() {
        return attendee;
    }

    public void setAttendee(List<String> attendee) {
        this.attendee = attendee;
    }

    public String toString(){
        return "Event Name : "+eventName+"\n"
                +"Event Description : "+summary+"\n"
                +"Start Time : "+startTime+"\n"
                +"End Time : "+endTime+"\n"
                +"Guests : "+attendee+"\n";
    }
}
