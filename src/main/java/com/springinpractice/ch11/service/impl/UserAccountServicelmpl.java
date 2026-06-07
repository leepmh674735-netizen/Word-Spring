package com.springinpractice.ch11.service.impl;

import org.springframework.stereotype.Service;
import java.sql.Connection;
import org.springframework.social.connect.ConnectionRepository;
import jakarta.inject.Inject;

@Service
public class UserAccountServiceImpl extends AbstractCIService implements UserAccountService {
    
    @Inject 
    private ConnectionRepository connectionRepo;
    
    public GitHubUserProfile getCurrentUserProfile() {
        GitHub gitHub = gitHub();
        if (gitHub != null && gitHub.isAuthorized()) {
            return gitHub.userOperations().getUserProfile();
        } else {
            return null;
        }
    }
    
    private GitHub gitHub() {
        Connection<GitHub> conn = connectionRepo.findPrimaryConnection(GitHub.class);
        return conn != null ? conn.getApi() : null;
    }
}