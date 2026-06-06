package com.springinpractice.ch10.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.mock.web.MockHttpServletRequest;

import com.springinpractice.ch08.web.ContactController;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/beans-datasource-it.xml",
    "classpath:/spring/beans-service.xml",
    "classpath:/spring/beans-web.xml" 
})
@Transactional
public class ContactControllerIT3 {

    @Inject 
    private ContactController controller;
    
    private MockHttpServletRequest request = new MockHttpServletRequest();

    @Test(timeout = 200L)
    @Repeat(20)
    public void testGetContactPerformanceSingleCall() {
        controller.getContact(request, 1L, new ExtendedModelMap());
    }

    @Test
    @IfProfileValue(name = "environment", value = "ci")
    @Repeat(20)
    @Timed(millis = 2000)
    public void testGetContactPerformanceMultipleCalls() {
        controller.getContact(request, 1L, new ExtendedModelMap());
    }
}