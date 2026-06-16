package com.springinpractice.ch08.web;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springinpractice.ch08.service.ContactService;
import com.springinpractice.ch08.domain.UserMessage;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Inject
    private ContactService contactService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(new String[] { "name", "email", "text", "referer" });
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getContactForm(HttpServletRequest req, Model model) {
        UserMessage userMsg = new UserMessage();
        userMsg.setReferer(req.getHeader("Referer"));
        model.addAttribute("userMessage", userMsg);
        return getFullViewName("contactForm");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postContactForm(HttpServletRequest req,
            @ModelAttribute("userMessage") @Valid UserMessage userMessage,
            BindingResult result) {
         
        if (result.hasErrors()) {
            return getFullViewName("contactForm");
        }
         
        userMessage.setIpAddress(req.getRemoteAddr());
        userMessage.setAcceptLanguage(req.getHeader("Accept-Language"));
        userMessage.setUserAgent(req.getHeader("User-Agent"));
         
        contactService.saveMessage(userMessage);
         
        return "redirect:/contact/thanks.html";
    }

    @RequestMapping(value = "/thanks", method = RequestMethod.GET)
    public String getThanksPage() {
        return getFullViewName("thanks");
    }

    private String getFullViewName(String viewName) {
        return "contact/" + viewName;
    }
}