package com.springinpractice.ch09.comment.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "/spring/beans-service-richtext.xml")
public class RichTextFilterTests extends AbstractJUnit4SpringContextTests {
    
    private RichTextFilter filter;
    
    @Before
    public void setUp() throws Exception {
        this.filter = applicationContext.getBean(RichTextFilter.class);
    }
    
    @After
    public void tearDown() throws Exception {
        this.filter = null;
    }
    
    @Test
    public void testWithJavaScriptUrls() {
        String in = "<a href=\"javascript:alert('hi')\">Hi</a>";
        String out = "<p>Hi</p>";
        assertEquals(out, filter.filter(in));
    }
}