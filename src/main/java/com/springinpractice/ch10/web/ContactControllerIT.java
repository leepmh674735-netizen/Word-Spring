package com.springinpractice.ch10.web;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

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
public class ContactControllerIT {

    private static final String SELECT_FIRST_NAME_QUERY = "select first_name from contact where id = ?";
    
    @Inject private ContactController controller;
    @Inject private SessionFactory sessionFactory;
    @Inject private DataSource dataSource;
    
    @Value("#{viewNames.updateContactSuccess}")
    private String expectedUpdateContactSuccessViewName;
    
    private JdbcTemplate jdbcTemplate;
    private MockHttpServletRequest request;
    private org.springframework.ui.Model model;
         
    @Before
    public void setUp() throws Exception {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.request = new MockHttpServletRequest();
        this.model = new org.springframework.ui.ExtendedModelMap();
    }
    
    @After
    public void tearDown() throws Exception {
        this.jdbcTemplate = null;
        this.request = null;
        this.model = null;
    }
       
    @Test
    public void testUpdateContactHappyPath() {
        Contact contact = new Contact();
        contact.setFirstName("Bob");
        contact.setLastName("Dylan");
        contact.setEmail("bodylan@example.com");
       
        BindingResult result = new BeanPropertyBindingResult(contact, "contact");
       
        String viewName = controller.updateContact(request, 1L, contact, result);
        assertEquals(expectedUpdateContactSuccessViewName, viewName);
        
        org.springframework.ui.Model anotherModel = new org.springframework.ui.ExtendedModelMap();
        controller.getContact(request, 1L, anotherModel);
        Contact updatedContact = (Contact) anotherModel.asMap().get("contact");
        assertEquals("Bob", updatedContact.getFirstName());
            
        String firstName = jdbcTemplate.queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1L);
        assertEquals("Robert", firstName);
        
        sessionFactory.getCurrentSession().flush();
            
        String updatedFirstName = jdbcTemplate.queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1L);
        assertEquals("Bob", updatedFirstName);
    }
}