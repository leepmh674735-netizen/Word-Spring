package com.springinpratice.ch04.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class AccountController {
    
    private static final String VN_REG_FORM = "users/registrationForm";
    private static final String VN_REG_OK = "redirect:registration_Ok";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(new String[] { 
            "username", "password", "confirmPassword", "firstName", "lastName",
            "email", "marketingOk", "acceptTerms" 
        });
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getRegistrationForm(Model model) {
        model.addAttribute("account", new AccountForm());
        return VN_REG_FORM;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String postRegistrationForm(@ModelAttribute("account") @Valid AccountForm form, BindingResult result) {
        convertPasswordError(result);
        return result.hasErrors() ? VN_REG_FORM : VN_REG_OK;
    }

    private void convertPasswordError(BindingResult result) {
        for (ObjectError error : result.getGlobalErrors()) {
            String code = error.getCode();
            if ("invalidPassword".equals(code)) {
                result.rejectValue("password", "invalidPassword", error.getDefaultMessage());
            } else if ("passwordsDoNotMatch".equals(code)) {
                result.rejectValue("confirmPassword", "passwordsDoNotMatch", error.getDefaultMessage());
            }
        }
    }
}