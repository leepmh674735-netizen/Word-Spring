package com.springinpractice.ch13.helpdesk.integration.transformer;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class TicketTransformer {

	@Value("${confirmation.from}")
	private String confirmationFrom;

	@Value("${confirmation.subject}")
	private String confirmationSubject;

	public MailMessage toConfirmationEmail(Ticket ticketDto) {
		MailMessage msg = new SimpleMailMessage();

		Customer customerDto = ticketDto.getCreatedBy();
		String customerFullName = getFullName(customerDto);
		String customerEmail = customerDto.getEmail();
		
		String to = customerFullName + " <" + customerEmail + ">";
		msg.setTo(to);

		msg.setFrom(confirmationFrom);
		msg.setSubject(confirmationSubject);
		msg.setSentDate(new Date());

		String desc = "Thank you for reporting this issue. We will contact you "
				+ "within one business day.\n\nYour message:\n\n"
				+ ticketDto.getDescription();
		msg.setText(desc);

		return msg;
	}

	private String getFullName(Customer customer) {
		if (customer == null) {
			return "";
		}
		return customer.getFirstName() + " " + customer.getLastName();
	}
}