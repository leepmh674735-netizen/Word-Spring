package com.springinpractice.ch13.helpdesk.integration.reposource;

import org.springframework.hateoas.RepresentationModel;

public class CustomerResource extends RepresentationModel<CustomerResource> {
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    
    public String getUsername() { 
        return username; 
    }
    
    public String getFirstName() { 
        return firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getFirstNameLastName() {
        return firstName + " " + lastName;
    }
}