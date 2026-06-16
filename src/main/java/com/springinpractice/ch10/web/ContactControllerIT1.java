package com.springinpractice.ch10.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.spring.cho2.model.Contact;
import com.springinpratice.ch02.web.ContactController;

import jakarta.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/beans-datasource-it.xml",
    "classpath:/spring/beans-service.xml",
    "classpath:/spring/beans-web.xml" 
})
@Transactional
public class ContactControllerIT1 {

    private static final String SELECT_FIRST_NAME_QUERY = "select first_name from contact where id = ?";
    
    @Inject private ContactController controller;
    @Inject private SessionFactory sessionFactory;
    @Inject private DataSource dataSource;
    
    @Value("#{viewNames.deleteContactSucces}")
    private String expectedDeleteContactSuccessViewName;
    
    private JdbcTemplate jdbcTemplate;
    private MockHttpServletRequest request;
    private Model model;
         
    @Before
    public void setUp() throws Exception {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.request = new MockHttpServletRequest();
        this.model = new ExtendedModelMap();
    }
    
    @After
    public void tearDown() throws Exception {
        this.jdbcTemplate = null;
        this.request = null;
        this.model = null;
    }
       
    @Test
    public void testDeleteContactHappypath() {
        controller.getContact(request, 1L, model);
        Contact contact = (Contact) model.asMap().get("contact");
        assertNotNull(contact);
        
        String viewName = controller.deleteContact(1L);
        assertEquals(expectedDeleteContactSuccessViewName, viewName);
        
        try {
            controller.getContact(request, 1L, new ExtendedModelMap());
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK
        }
        
        sessionFactory.getCurrentSession().flush();
        
        try {
            jdbcTemplate.queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1L);
            fail("Expected DataAccessException");
        } catch (DataAccessException e) {
            // OK
        }
    }
}
