package com.ganesh.UI.Controller;

import com.ganesh.UI.DTO.EventInfo;
import com.ganesh.UI.Service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by ganesh.kumar on 6/19/17.
 */
@Controller
public class UIController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UIController.class);

    @RequestMapping(value = "/testpage",method = RequestMethod.GET)
    public @ResponseBody String testPage(){
        return "Test page is Working";
    }

    @RequestMapping(value = "/firstpage",method = RequestMethod.GET)
    public String samplePage(Model model) throws IOException {
        model.addAttribute("message", CalendarService.printPath());
        return "sample";
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

    @RequestMapping(value = "/addEvent",method = RequestMethod.GET)
    public String newEvent(Model model){
        model.addAttribute("default","Test Event");
        return "addEvent";
    }

    @RequestMapping(value = "/addEvent",method = RequestMethod.POST)
    public String addEventResponse(@ModelAttribute EventInfo event, Model model) throws IOException, GeneralSecurityException {
        log.debug("B Target 1");
        String message = CalendarService.insertNewEvent(event);
        log.debug("B Target 2");
        model.addAttribute("message", message);
        return "Response";
    }

    @RequestMapping(value = "/EventSubmit",method = RequestMethod.GET)
    public String eventSubmit(Model model){
        model.addAttribute("default","Test Event");
        return "EventSubmit";
    }

}
