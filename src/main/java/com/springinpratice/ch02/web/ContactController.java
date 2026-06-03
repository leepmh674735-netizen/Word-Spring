package com.springinpratice.ch02.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.cho2.model.Contact;
import com.springinpractice.ch02.service.impl.ContactService;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/contacts")
public class ContactController {
	private static final Logger log = LoggerFactory.getLogger(ContactController.class);

	@Inject
	private ContactService contactService;

	@Value("#{viewNames.contactList}")
	private String contactListViewName;

	@Value("#{viewNames.contactForm}")
	private String contactFormViewName;

	@Value("#{viewNames.updateContactSuccces}")
	private String updateContactSuccessViewName;

	@Value("#{viewNames.deleteContactSucces}")
	private String deleteContactSuccessViewName;

	@Value("#{viewNames.contactSerp}")
	private String contactSerpViewName;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "firstName", "middleInitial", "lastName", "email" });
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createContactForm(HttpServletRequest req, Model model) {
		prepareNewContactForm(req);
		model.addAttribute(new Contact());
		return contactFormViewName;
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String createContact(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute @Valid Contact contact, BindingResult result) {

		if (!result.hasErrors()) {
			contactService.createContact(contact);

			res.setStatus(HttpServletResponse.SC_CREATED);
			String location = req.getRequestURL() + "/" + contact.getId();
			log.debug("Setting Location={}", location);
			res.setHeader("Location", location);

			return updateContactSuccessViewName;
		} else {
			prepareNewContactForm(req);
			result.reject("global.error");
			return contactFormViewName;
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getContacts(Model model) {
		model.addAttribute(contactService.getContacts());
		return contactListViewName;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getContact(HttpServletRequest req, @PathVariable("id") long id, Model model) {
		Contact contact = contactService.getContact(id);
		if (contact != null) {
			prepareExistingContactForm(req, id);
			model.addAttribute(contact);
			return contactFormViewName;
		} else {
			throw new IllegalArgumentException("No such contact: " + id);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String updateContact(HttpServletRequest req, @PathVariable("id") Long id,
			@ModelAttribute @Valid Contact contact, BindingResult result) {
		
		contact.setId(id);
		
		if (!result.hasErrors()) {
			contactService.updateContact(contact);
			return updateContactSuccessViewName;
		} else {
			prepareExistingContactForm(req, id);
			result.reject("global.error");
			return contactFormViewName;
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteContact(@PathVariable("id") long id) {
		contactService.deleteContact(id);
		return deleteContactSuccessViewName;
	}

	private void prepareNewContactForm(HttpServletRequest req) {
		setActionAndMethod(req, "/contacts.html", "POST");
	}

	private void prepareExistingContactForm(HttpServletRequest req, long id) {
		setActionAndMethod(req, "/contacts/" + id + ".html", "PUT");
	}

	private void setActionAndMethod(HttpServletRequest req, String action, String method) {
		req.setAttribute("action", action);
		req.setAttribute("method", method);
	}
}