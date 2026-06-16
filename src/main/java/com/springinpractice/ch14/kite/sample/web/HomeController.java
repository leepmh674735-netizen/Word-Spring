package com.springinpractice.ch14.kite.sample.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springinpractice.ch14.kite.sample.service.MessageService;

import jakarta.inject.Inject;

@Controller
public class HomeController {
	private static final Logger log = 
			LoggerFactory.getLogger(HomeController.class);
	
	@Inject 
	private MessageService messageService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getHome(Model model) {
		loadMotd(model);
		loadImportantMessages(model);
		return "home";
	}
	
	private void loadMotd(Model model) {
		try {
			model.addAttribute("motd", messageService.getMotd());
		} catch (Exception e) {
			log.error("Unable to load MOTD", e);
		}
	}
	
	private void loadImportantMessages(Model model) {
		try {
			model.addAttribute("importantMessage",
					messageService.getImportantMessages());
		} catch (Exception e) {
			log.error("Unable to load important messages", e);
		}
	}
}
