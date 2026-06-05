package com.springinpractice.ch08.web;

import java.util.Date;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mailinglist")
public class MailingListController {
    
    private static final String SUBSCRIBER = "subscriber";

    @Inject
    private MailingListService mailingListService;

    @InitBinder(SUBSCRIBER)
    public void initSubscriberBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setAllowedFields(new String[] { "firstName", "lastName", "email" });
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public String getSubscribeForm(Model model) {
        model.addAttribute(SUBSCRIBER, new Subscriber());
        return getFullViewName("subscribeForm");
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public String postSubscribeForm(
            HttpServletRequest request,
            @ModelAttribute(SUBSCRIBER) @Valid Subscriber subscriber,
            BindingResult result) {
        
        if (result.hasErrors()) {
            return getFullViewName("subscribeForm");
        }
        
        subscriber.setIpAddress(request.getRemoteAddr());
        subscriber.setDateCreated(new Date());
        mailingListService.addSubscriber(subscriber);
        return "redirect:/mailinglist/subscribe-preconfirm.html";
    }

    @RequestMapping(value = "/subscribe-preconfirm", method = RequestMethod.GET)
    public String getConfirmSubscriptionPage() {
        return getFullViewName("subscribePreconfirm");
    }
    
    @RequestMapping(value = "/subscribe-confirm", method = RequestMethod.GET)
    public String confirmSubscription(
            @RequestParam("s") Long subscriberId,
            @RequestParam("d") String digest,
            Model model) {
        
        try {
            mailingListService.confirmSubscriber(subscriberId, digest);
            return getFullViewName("subscribeSuccess");
        } catch (ConfirmationExpiredException e) {
            model.addAttribute("expired", true);
        } catch (ConfirmationFailedException e) {
            model.addAttribute("failed", true);
        }
        
        model.addAttribute(SUBSCRIBER, new Subscriber());
        return getFullViewName("subscribeForm");
    }

    private String getFullViewName(String viewName) {
        return "mailingList/" + viewName;
    }
}