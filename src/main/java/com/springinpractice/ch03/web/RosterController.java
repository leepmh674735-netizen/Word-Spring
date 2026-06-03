package com.springinpractice.ch03.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;

@Controller
@RequestMapping("/roster")
public final class RosterController {

	private List<Member> members = new ArrayList<Member>();
	
	public RosterController() {
		members.add(new Member("John", "Lennon"));
		members.add(new Member("Paul", "McCartney"));
		members.add(new Member("George", "Harrison"));
		members.add(new Member("Ringo", "Starr"));
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("members", members);
		return "roster/list";
	}
	
	@RequestMapping("/member")
	public String member(@RequestParam("id") Integer id, Model model) {
		if (id >= 0 && id < members.size()) {
			model.addAttribute("member", members.get(id));
		}
		return "roster/member";
	}
}