package com.springinpractice.ch10.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.spring.cho2.model.Contact;
import com.springinpratice.ch02.web.ContactController;

import jakarta.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/beans-datasource-it.xml",
    "classpath:/spring/beans-service.xml",
    "classpath:/spring/beans-web.xml" 
})
@Transactional
public class ContextControllerIT {
    
    @Inject 
    private ContactController controller;
    
    @Value("#{viewName.contactForm}")
    private String expectedContactFormViewName;
    
    private MockHttpServletRequest request;
    private Model model;
    
    @Before
    public void setUp() throws Exception {
        this.request = new MockHttpServletRequest();
        this.model = new ExtendedModelMap();
    }
    
    @After
    public void tearDown() throws Exception {
        this.request = null;
        this.model = null;
    }
    
    @Test
    public void testGetContactHappyPath() {
        String viewName = controller.getContact(request, 1L, model);
        assertEquals(expectedContactFormViewName, viewName);
        
        Contact contact = (Contact) model.asMap().get("contact");
        assertNotNull(contact);
        
        assertEquals(Long.valueOf(1L), contact.getId());
        assertEquals("Robert", contact.getFirstName());
        assertEquals("A", contact.getMiddleInitial());
        assertEquals("Zimmerman", contact.getLastName());
        assertEquals("bodylan@example.com", contact.getEmail());
    }
}