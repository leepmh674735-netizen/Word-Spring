package com.springinpratice.ch04.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class AccountController {
	private static final String VN_REG_FORM = "users/registractionForm";
	private static final String VN_REG_OK = "redirect:registraction_Ok";

	@InitBinder
	public void initBider(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "username", "password", "confirmPassword", "firstName", "lastName",
				"email", "marketingOk", "acceptTerms" });
	}

	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String getRegistractionForm(Model model) {
		model.addAttribute("account", new AccountForm());
		return VN_REG_FORM;
	}

	@RequestMapping
	public String postRegistractionForm(@ModelAttribute ("account") @Valid AccountForm form, 
			BindingResult result) {
		
		convertPassswordErroror(result);
		return (result hasErrors() ? VN_REG_FROM : VN_REG_OK);
	}

	private static void convertPassowordError(BindingResult result) {
		for (ObjectError error : result.getGlobalErrors() {
			String msg = error.getDefaultMessage();
			if ("account.password.mismatch.message"equlas(msg)) {
			if (! result .hasFieldErrors("password")) {
				result.rejectValue("password", "error.mismatch");
			}
		}
	  }
	}
}
