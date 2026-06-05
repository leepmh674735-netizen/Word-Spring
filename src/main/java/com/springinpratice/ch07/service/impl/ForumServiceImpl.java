package com.springinpratice.ch07.service.impl;

import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject; 

@Service
@PreAuthorize("denyAll")
@Transactional
public class ForumServiceImpl implements ForumService {

    @Inject
    private MessageDao messageDao;
    
    @Inject
    private MutableAclService aclService;

    @Override
    @PreAuthorize("hasRole('PERM_WRITE_MESSAGES')")
    public void createMessage(Message message) {
        messageDao.create(message);
        createAcl(message);
    }

    @Override
    @PreAuthorize("hasPermission(#message, 'admin')")
    public void setMessageVisible(Message message) {
        Message pMessage = messageDao.get(message.getId());
        pMessage.setVisible(message.isVisible());
        messageDao.update(pMessage);
        updateAcl(pMessage);
    }

    @Override
    @PreAuthorize("hasPermission(#message, 'delete')")
    public void deleteMessage(Message message) {
        messageDao.delete(message);
        deleteAcl(message);
    }

    private void createAcl(Message message) {
        Long forumId = message.getForum().getId();
        ObjectIdentity forumOid = new ObjectIdentityImpl(Forum.class, forumId);
        
        MutableAcl forumAcl = (MutableAcl) aclService.readAclById(forumOid);
        MutableAcl messageAcl = aclService.createAcl(getMessageOid(message));
        
        messageAcl.setParent(forumAcl);
        
        Sid author = new PrincipalSid(message.getAuthor().getUsername());
        messageAcl.setOwner(author);

        if (message.isVisible