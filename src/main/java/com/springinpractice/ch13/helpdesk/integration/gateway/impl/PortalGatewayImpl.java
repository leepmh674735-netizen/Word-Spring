package com.springinpractice.ch13.helpdesk.integration.gateway.impl;

import java.util.Collection;
import org.springframework.web.client.RestTemplate;
import com.springinpractice.ch13.helpdesk.integration.gateway.PortalGateway;
import com.springinpractice.ch13.helpdesk.integration.resource.CustomerResource;
import com.springinpractice.ch13.helpdesk.integration.resource.CustomerResources;

public class PortalGatewayImpl implements PortalGateway {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    
    public PortalGatewayImpl(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }
     
    @Override
    public CustomerResource findCustomerByUsername(String username) {
        String url = baseUrl + "/users/search/find-by-username?username={username}";
        CustomerResources customers = restTemplate.getForObject(url, CustomerResources.class, username);
        
        if (customers != null && customers.getContent() != null && customers.getContent().iterator().hasNext()) {
            return customers.getContent().iterator().next();
        }
        return null;
    }
    
    @Override
    public Collection<CustomerResource> findCustomersByUsernameIn(Collection<String> usernames) {
        StringBuilder builder = new StringBuilder(baseUrl + "/users/search/find-by-username-in?");
        for (String user : usernames) {
            builder.append("username=").append(user).append("&");
        }
        String url = builder.substring(0, builder.length() - 1);
        CustomerResources customers = restTemplate.getForObject(url, CustomerResources.class);
        
        return customers != null ? customers.getContent() : null;
    }
}