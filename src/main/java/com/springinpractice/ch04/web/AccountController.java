package com.springinpractice.ch04.web;

import java.lang.System.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class AccountController {
	private static final Logger log = 
			LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String getRegistionForm(Model model) {
		model.addAttribute("account", new AccountFrom());
		return "users/registrationForm";
	}
	
	@RequestMapping(value= "", method = RequestMethod.POST)
	public String postRegistractionForm(AccountForm form) {
		log.info("Created registration: {}", form);
		return "redirect:registraction_Ok";
		}
	}


