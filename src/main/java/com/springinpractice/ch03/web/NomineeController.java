package com.springinpractice.ch03.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springinpractice.ch03.model.Member;

@Controller
@RequestMapping("/nominees")
public final class NomineeController {

	private static final Logger log = LoggerFactory.getLogger(NomineeController.class);
	private String thanksViewName = "thanks";
	private String formViewName = "nomineeForm";

	public void setThanksViewName(String thanksViewName) {
		this.thanksViewName = thanksViewName;
	}

	public void setFormViewName(String formViewName) {
		this.formViewName = formViewName;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String form(Model model) { 
		model.addAttribute("member", new Member());
		return formViewName;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processFormData(@ModelAttribute("member") Member member) {
		log.info("Processing nominee: {}", member);
		return thanksViewName;
	}
}
