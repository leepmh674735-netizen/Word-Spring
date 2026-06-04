package com.springinpractice.ch06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springinpractice.ch04.web.AccountForm;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    // 뷰 이름 상수가 정의되어 있지 않아 클래스 내부에 선언했습니다.
    private static final String VN_REG_FORM = "registrationForm";
    private static final String VN_REG_OK = "registrationSuccess";

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authMgr;

    // accountService와 관련 메서드(convertPasswordError, toAccount)가 선언되어 있어야 합니다.
    private AccountService accountService; 

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegistrationForm(@ModelAttribute("account") @Valid AccountForm form, BindingResult result) {
        
        convertPasswordError(result);
        
        String password = form.getPassword();
        accountService.registerAccount(toAccount(form), password, result);
        
        if (result.hasErrors()) {
            return VN_REG_FORM;
        }

        Authentication authRequest = new UsernamePasswordAuthenticationToken(form.getUsername(), password);
        Authentication authResult = authMgr.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        
        return VN_REG_OK;
    }

    // 아래 메서드들은 기존 코드 흐름상 필요한 메서드의 예시 껍데기입니다.
    private void convertPasswordError(BindingResult result) {}
    private Object toAccount(AccountForm form) { return null; }
}