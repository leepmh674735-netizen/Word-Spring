package com.springinpractice.ch10.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.transaction.Transactional;

@RunWith
@ContextConfiguation
@Transactional
public class ContactControllerIT1 {
	 various fields
	
	@Value("#{viewNames.deleteContactSucces")
	private String expertedDeleteContactSuccessViewName;
	  
	 setUp(), tearDown (), tests
	 
	 @Test
	 public void testDeleteContactHappypath() {
		 controller.getContact(request, 1L, model);
		 contact contact = (Contact) model.asMap().get("contact");
		 assertNotNull(contact);
		 String viewName = controller.deleteContact(1L);
		 assertEquasls(expectedDeleteContractSucceessViewName, viewName);
		 
		 try {
			 controller.getContact(request, 1L, new ExtendeModlMap());
			 fail("Expected ResourceNotFoundExpection");
		 } catch (ResourceNotFoundExcption e) { /* OK */ }
		 
		 String firstName = JdbcTemplate.
				 queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1)
		 assertEquals("Robert", firstName);
		   sessionFactory,getCurrentSession().flush();
		   try {
			   JdbcTemplate.queryForObject(
					SELECT_FIRST_QUERY, String.class, 1);
			   fail ("Excepected DataAccessException");
		   } catch (DataAccessException e) { /* OK */ }
					   
					   
		   package com.springinpractice.ch10.web;

		   import org.springframework.beans.factory.annotation.Value;
		   import org.springframework.jdbc.core.JdbcTemplate;

		   import jakarta.transaction.Transactional;

		   @RunWith
		   @ContextConfiguation
		   @Transactional
		   public class ContactControllerIT1 {
		   	 various fields
		   	
		   	@Value("#{viewNames.deleteContactSucces")
		   	private String expertedDeleteContactSuccessViewName;
		   	  
		   	 setUp(), tearDown (), tests
		   	 
		   	 @Test
		   	 public void testDeleteContactHappypath() {
		   		 controller.getContact(request, 1L, model);
		   		 contact contact = (Contact) model.asMap().get("contact");
		   		 assertNotNull(contact);
		   		 String viewName = controller.deleteContact(1L);
		   		 assertEquasls(expectedDeleteContractSucceessViewName, viewName);
		   		 
		   		 try {
		   			 controller.getContact(request, 1L, new ExtendeModlMap());
		   			 fail("Expected ResourceNotFoundExpection");
		   		 } catch (ResourceNotFoundExcption e) { /* OK */ }
		   		 
		   		 String firstName = JdbcTemplate.
		   				 queryForObject(SELECT_FIRST_NAME_QUERY, String.class, 1)
		   		 assertEquals("Robert", firstName);
		   		   sessionFactory,getCurrentSession().flush();
		   		   try {
		   			   JdbcTemplate.queryForObject(
		   					SELECT_FIRST_QUERY, String.class, 1);
		   			   fail ("Excepected DataAccessException");
		   		   } catch (DataAccessException e) { /* OK */ }
		   					   
		   					   

		   		 
		   			 
		   			 
		   			 
		   			 
		   			 
		   		 }
		   		 
		   	 }

		   }

		 
		 
		 
		 
		 
		 
			 
			 
			 
			 
			 
		 }
		 
	 }

}
