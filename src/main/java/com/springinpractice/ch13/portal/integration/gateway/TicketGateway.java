package com.springinpractice.ch13.portal.integration.gateway;

import com.springinpractice.ch13.cdm.Ticket;

public interface TicketGateway {
    
    void createTicket(Ticket ticket);
    
    TicketStatus findOpenTicketStatus();
    
    TicketCategoryList findTicketCategories();
    
    TicketCategory findTicketCategory(Long id);
}