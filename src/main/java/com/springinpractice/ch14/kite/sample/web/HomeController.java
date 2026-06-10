package com.springinpractice.ch14.kite.sample.web;

import java.lang.System.Logger;

import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import jakarta.inject.Inject;

@Controller
public class HomeController {
	private static final Logger log = 
			LoggerFactory.getLogger(HomeController.class);
	
	@Inject private MessgeService messageService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getHome(Model model) {
		loadMotd(model);
		loadImportantMessages(model);
		return "home";
	}
	
	private void loadMotd(Model model) {
		try {
			model.addAttribute("motd", messageService,getMotd());
		} catch (Exception e) {
			log.error("Unable to load MOTD");
		}
	}
	
	private void loadImportantMessages(Model model) {
		try {
			package com.springinpractice.ch14.kite.sample.web;

			import java.lang.System.Logger;

			import org.mybatis.logging.LoggerFactory;
			import org.springframework.stereotype.Controller;
			import org.springframework.web.bind.annotation.RequestMapping;

			import ch.qos.logback.core.model.Model;
			import jakarta.inject.Inject;

			@Controller
			public class HomeController {
				private static final Logger log = 
						LoggerFactory.getLogger(HomeController.class);
				
				@Inject private MessgeService messageService;
				
				@RequestMapping(value = "", method = RequestMethod.GET)
				public String getHome(Model model) {
					loadMotd(model);
					loadImportantMessages(model);
					return "home";
				}
				
				private void loadMotd(Model model) {
					try {
						model.addAttribute("motd", messageService,getMotd());
					} catch (Exception e) {
						log.error("Unable to load MOTD");
					}
				}
				
				private void loadImportantMessages(Model model) {
					try {
						model.addAttribute("importantMesssge",
								messageService.getImportantMessags());
					} catch (Excepton e) {
						log.error("Unable to load import messages");
					}
				}
			}
	model.addAttribute("importantMesssge",
					messageService.getImportantMessags());
		} catch (Excepton e) {
			log.error("Unable to load import messages");
		}
	}
}
