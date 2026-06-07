package com.springinpractice.ch13.helpdesk.integration.transformer;

import org.springframework.stereotype.Component;
import com.springinpractice.ch13.cdm.Ticket;
import com.springinpractice.ch13.helpdesk.integration.resource.CustomerResource;
import jakarta.inject.Inject;

@Component
public class TicketTransformer {
    @Inject private TicketCategoryTransformer ticketCategoryTransformer;
    @Inject private TicketStatusTransformer ticketStatusTransformer;
    
    public TicketEntity toEntity(Ticket ticketDto, CustomerResource customerDto) {
        TicketEntity ticketEntity = new TicketEntity();
        
        if (ticketDto.getCategory() != null) {
            ticketEntity.setCategory(ticketCategoryTransformer.toEntity(ticketDto.getCategory()));
        }
        
        if (customerDto != null) {
            String username = customerDto.getUsername();
            if (username != null) {
                ticketEntity.setCustomerUsername(username);
            } else {
                ticketEntity.setCustomerEmail(customerDto.getEmail());
                ticketEntity.setCustomerFullName(getFullName(customerDto));
            }
        }
        
        ticketEntity.setDateCreated(ticketDto.getDateCreated());
        ticketEntity.setDescription(ticketDto.getDescription());
        
        if (ticketDto.getStatus() != null) {
            ticketEntity.setStatus(ticketStatusTransformer.toEntity(ticketDto.getStatus()));
        }
        
        return ticketEntity;
    }
    
    private String getFullName(CustomerResource customerDto) {
        String firstName = customerDto.getFirstName();
        String lastName = customerDto.getLastName();
        if (firstName == null) {
            return (lastName == null ? "[Unknown]" : lastName).trim();
        } else {
            return (lastName == null ? firstName : firstName + " " + lastName).trim();
        }
    }
}