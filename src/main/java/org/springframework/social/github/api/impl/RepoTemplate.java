package org.springframework.social.github.api.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class RepoTemplate implements RepoOperations {
    
    private final RestTemplate restTemplate;
    
    public RepoTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public List<GitHubUser> getWatchers(String user, String repo) {
        GitHubUser[] watchers = restTemplate.getForObject(
                "https://api.github.com/repos/{user}/{repo}/watchers",
                GitHubUser[].class,
                user,
                repo);
        
        return watchers != null ? Arrays.asList(watchers) : Arrays.asList();
    }
}