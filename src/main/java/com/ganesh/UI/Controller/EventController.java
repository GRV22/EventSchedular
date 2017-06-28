package com.ganesh.UI.Controller;

import com.ganesh.UI.DTO.EventInfo;
import com.ganesh.UI.Service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by ganesh.kumar on 6/19/17.
 */
@Controller
public class EventController {

    CalendarService calendarService;

    @Autowired
    @Qualifier(value = "calendarService")
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(value = "/dynamicButton",method = RequestMethod.GET)
    public String dynamicPage(){
        return "dynamicButton";
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String homePage(){
        return "home";
    }

    @RequestMapping(value = "/events",method = RequestMethod.GET)
    public String getEvents(Model model) throws GeneralSecurityException, IOException {
        List<EventInfo> events = this.calendarService.getEvents();
        model.addAttribute("title","All Events");
        model.addAttribute("events",events);
        return "ViewEvents";
    }


    @RequestMapping(value = "/events",method = RequestMethod.POST)
    public String addEvent(@ModelAttribute EventInfo event, Model model) throws IOException, GeneralSecurityException {
        String message = this.calendarService.insertNewEvent(event);
        model.addAttribute("message", message);
        return "Response";
    }


    @RequestMapping(value = "/events/{eventId}/edit",method = RequestMethod.GET)
    public String editEventByGET(@PathVariable String eventId , Model model) throws GeneralSecurityException, IOException {
        EventInfo eventInfo = this.calendarService.getEventById(eventId);
        model.addAttribute("eventInfo",eventInfo);
        return "editEvent";
    }

    @RequestMapping(value = "/events/{eventId}/delete",method = RequestMethod.POST)
    public String deleteEventByPOST(@PathVariable String eventId , Model model) throws GeneralSecurityException, IOException {
        String message = this.calendarService.deleteEvent(eventId);
        model.addAttribute("message",message);
        model.addAttribute("title","Delete Request Status");
        return "Response";
    }

    @RequestMapping(value = "/events/{eventId}",method = RequestMethod.POST)
    public String updateAnEvent(@PathVariable String eventId,@ModelAttribute EventInfo event, Model model) throws GeneralSecurityException, IOException {

        System.out.println(event.getAttendee());
        EventInfo eventInfo = this.calendarService.updateEvent(event);
        model.addAttribute("event",eventInfo);
        return "viewAnEvent";
    }


    @RequestMapping(value = "/showAddEventPage",method = RequestMethod.GET)
    public String showAddEventPage(){
        return "addEvent";
    }

    @RequestMapping(value = "/editEventPage",method = RequestMethod.GET)
    public String editEventPage(@RequestParam(required = true)String eventId){
        return "editEvent";
    }


}
