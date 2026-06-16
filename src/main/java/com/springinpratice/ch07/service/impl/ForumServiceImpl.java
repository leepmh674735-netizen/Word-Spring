package com.springinpratice.ch07.service.impl;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import com.springinpratice.ch07.domain.Forum;
import com.springinpratice.ch07.domain.Message;
import com.springinpratice.ch07.service.ForumService;
import com.springinpratice.ch07.dao.MessageDao;

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

        if (message.isVisible()) {
        	messageAcl.insertAce(
        			messageAcl.getEntries().size(),
        			BasePermission.READ,
        			new GrantedAuthoritySid("ROLE_USER"),
        			true);
        }
        
        messageAcl.insertAce(messageAcl.getEntries().size(), BasePermission.READ, author, true);
        messageAcl.insertAce(messageAcl.getEntries().size(), BasePermission.WRITE, author, true);
        messageAcl.insertAce(messageAcl.getEntries().size(), BasePermission.DELETE, author, true);
        
        aclService.updateAcl(messageAcl);
    }

    private void updateAcl(Message message) {
    	MutableAcl acl = (MutableAcl) aclService.readAclById(getMessageOid(message));
    	
    	int userReadIndex = -1;
    	List<AccessControlEntry> aces = acl.getEntries();
    	Sid userSid = new GrantedAuthoritySid("ROLE_USER");
    	for (int i = 0; i < aces.size(); i++) {
    		AccessControlEntry ace = aces.get(i);
    		if (userSid.equals(ace.getSid()) && BasePermission.READ.equals(ace.getPermission())) {
    			userReadIndex = i;
    			break;
    		}
    	}
    	
    	if (message.isVisible()) {
    		if (userReadIndex == -1) {
    			acl.insertAce(acl.getEntries().size(), BasePermission.READ, userSid, true);
    		}
    	} else {
    		if (userReadIndex != -1) {
    			acl.deleteAce(userReadIndex);
    		}
    	}
    	aclService.updateAcl(acl);
    }

    private void deleteAcl(Message message) {
    	aclService.deleteAcl(getMessageOid(message), true);
    }

    private ObjectIdentity getMessageOid(Message message) {
    	return new ObjectIdentityImpl(Message.class, message.getId());
    }
}