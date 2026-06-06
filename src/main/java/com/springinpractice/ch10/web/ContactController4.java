package com.springinpractice.ch10.web;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jakarta.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:/spring/beans-datasource-it.xml",
    "classpath:/spring/beans-service.xml",
    "classpath:/spring/beans-web.xml" 
})
@Transactional
public class ContactController4 {

    @Ignore("Problem getting current session")
    @Test
    public void testGetContactWithBadSessionFactory() {
        // other tests
    }
}