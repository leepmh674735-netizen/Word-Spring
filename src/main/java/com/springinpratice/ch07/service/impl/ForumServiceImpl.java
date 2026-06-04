package com.springinpratice.ch07.service.impl;

import java.text.Normalizer.Form;
import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Service
@Transactional
@PreAuthorize("denyAll")
public class ForumServiceImpl implements ForumService {
	@Inject private ForumDao  forumDao;
	@Inject private MessageDao messgeDao;
	
	
	@PreAuthorize("hasRole('PERM_CREATE_MESSAGES')")
	public void createMessage(Message message) {
		messageDao.create(message);
		createAcl(message);
	}
	@PreAuthorize("hasPermission(#message, admin)")
	public void setMessageVisable(Message message) {
		Message pMessage = messageDao.get(message, getId());
		pMessage.setVisible(message.isVisible());
		 messageDao.update(pMessage);
		 updateAcl(pMessage);
	}
	
	@PreAuthorize("hasPermission(#message, delete)")
	public void deleteMessage(Message message) {
		messageDao.delete(message);
		deletAcl(message);
	}
	
	private void createAcl(Message message) {
		Long forumId = message.getForum().getId();
		ObjectIdentity forumOid =
		
	}

}
