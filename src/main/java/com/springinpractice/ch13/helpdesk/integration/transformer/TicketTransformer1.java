package com.springinpractice.ch13.helpdesk.integration.transformer;

import java.io.IOException;
import java.sql.Date;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

import com.springinpractice.ch13.cdm.Ticket;
import com.springinpractice.ch13.cdm.TicketCategory;
import com.springinpractice.ch13.cdm.TicketStatus;
import com.springinpractice.ch13.helpdesk.integration.resource.CustomerResource;

@Component
public class TicketTransformer1 {
    @Inject private TicketCategoryRepository ticketCategoryRepo;
    @Inject private TicketStatusRepository ticketStatusRepo;
    @Inject private TicketCategoryTransformer ticketCategoryTransformer;
    @Inject private TicketStatusTransformer ticketStatusTransformer;
    
    private TicketCategory generalCategoryDto;
    private TicketStatus openStatusDto;
    
    @PostConstruct
    public void postConstruct() {
        TicketCategoryEntity generalCategoryEntity = ticketCategoryRepo.findByKey("general");
        this.generalCategoryDto = ticketCategoryTransformer.toDto(generalCategoryEntity);
        
        TicketStatusEntity openStatusEntity = ticketStatusRepo.findByKey("open");
        this.openStatusDto = ticketStatusTransformer.toDto(openStatusEntity);
    }
    
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
    
    public Ticket toDto(MimeMessage email) throws MessagingException, IOException {
        InternetAddress from = (InternetAddress) email.getFrom()[0];
        MimeMultipart content = (MimeMultipart) email.getContent();
        BodyPart body = content.getBodyPart(0);
        
        Ticket ticketDto = new Ticket();
        ticketDto.setCategory(generalCategoryDto);
        
        ticketDto.setCreatedBy(from.getAddress());
        
        if (email.getSentDate() != null) {
            ticketDto.setDateCreated(new Date(email.getSentDate().getTime()));
        }
        
        ticketDto.setDescription("[" + email.getSubject() + "] " + body.getContent());
        ticketDto.setStatus(openStatusDto);
        
        return ticketDto;
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