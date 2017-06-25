package com.ganesh.UI.Controller;

import com.ganesh.UI.DTO.EventInfo;
import com.ganesh.UI.Service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created by ganesh.kumar on 6/19/17.
 */
@Controller
public class UIController {

    CalendarService calendarService;

    @Autowired
    @Qualifier(value = "calendarService")
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(value = "/testpage",method = RequestMethod.GET)
    public @ResponseBody String testPage(){
        return "Test page is Working";
    }


    @RequestMapping(value = "/firstpage",method = RequestMethod.POST)
    public String samplePageRedirect(@ModelAttribute EventInfo event, Model model) throws IOException {
        model.addAttribute("message", event.toString());
        System.out.println(event.toString());
        return "sample";
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

    @RequestMapping(value = "/events/{eventId}",method = RequestMethod.PUT)
    public String updateEvent(@PathVariable String eventId,@ModelAttribute EventInfo event, Model model){
        EventInfo eventInfo = this.calendarService.updateEvent(event);
        return "Response";
    }

    @RequestMapping(value = "/events/{eventId}",method = RequestMethod.DELETE)
    public String deleteEvent(@PathVariable String eventId , Model model) throws GeneralSecurityException, IOException {
        String message = this.calendarService.deleteEvent(eventId);
        model.addAttribute("message",message);
        model.addAttribute("title","Delete Request Status");
        return "Response";
    }

    @RequestMapping(value = "/delete/events/{eventId}",method = RequestMethod.GET)
    public String deleteEventByGET(@PathVariable String eventId , Model model) throws GeneralSecurityException, IOException {
        String message = "Tmp";//this.calendarService.deleteEvent(eventId);
        model.addAttribute("message",message);
        model.addAttribute("title","Delete Request Status");
        return "Response";
    }


    @RequestMapping(value = "/showAddEventPage",method = RequestMethod.GET)
    public String showAddEventPage(){
        return "addEvent";
    }

    @RequestMapping(value = "/editEventPage",method = RequestMethod.GET)
    public String editEventPage(@RequestParam(required = true)String eventId){
        return "editEvent";
    }


    @RequestMapping(value = "/EventSubmit",method = RequestMethod.GET)
    public String eventSubmit(Model model){
        model.addAttribute("default","Test Event");
        return "EventSubmit";
    }

}
