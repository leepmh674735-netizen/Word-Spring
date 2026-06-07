package com.springinpractice.ch13.helpdesk.integration.gateway;

import java.util.Collection;

import com.springinpractice.ch13.helpdesk.integration.reposource.CustomerResource;

public interface PortalGateway {
     CustomerResource findCustomerByUsername(String username);
     
     Collection<CustomerResource> findCustomersByUsernameIn(
    	Collection<String> usernames);
     
}
