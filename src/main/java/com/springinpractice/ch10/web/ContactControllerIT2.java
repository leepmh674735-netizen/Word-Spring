package com.springinpractice.ch10.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.springinpratice.ch02.web.ContactController;
import com.springinpractice.dao.hbn.HbnContactDao;

import jakarta.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/beans-datasource-it.xml",
    "classpath:/spring/beans-service.xml",
    "classpath:/spring/beans-web.xml" 
})
@Transactional
public class ContactControllerIT2 {

    @Inject private ContactController controller;
    @Inject private HbnContactDao contactDao;
    
    private SessionFactory badSessionFactory;
    private MockHttpServletRequest request;
    private Model model;
    
    @Before
    public void setUp() throws Exception {
        this.badSessionFactory = mock(SessionFactory.class);
        when(badSessionFactory.getCurrentSession())
            .thenThrow(new HibernateException("Problem getting current session"));
            
        this.request = new MockHttpServletRequest();
        this.model = new ExtendedModelMap();
    }
    
    @After
    public void tearDown() throws Exception {
        this.badSessionFactory = null;
        this.request = null;
        this.model = null;
    }
    
    @Test(expected = HibernateException.class)
    @DirtiesContext
    public void testContactWithBadSessionFactory() {
        ReflectionTestUtils.setField(contactDao, "sessionFactory", badSessionFactory);
        controller.getContact(request, 1L, model);
    }
}