package com.springinpractice.ch11.web.controller.application;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springinpractice.ch11.model.Application;
import com.springinpractice.ch11.model.ApplicationRepository;
import jakarta.inject.Inject;

@Controller
@RequestMapping("/applications")
public class ApplicationScmController {

    @Inject 
    private ApplicationRepository applicationRepository;
    
    @Inject 
    private GitHub gitHub;
    
    @RequestMapping(value = "/{id}/scm/watchers", method = RequestMethod.GET)
    public String getWatchers(@PathVariable Long id, Model model) {
        Application app = applicationRepository.findOne(id);
        
        String user = app.getScm().getUser();
        String repo = app.getScm().getRepo();
        
        List<GitHubUser> watchers = gitHub.repoOperations().getWatchers(user, repo);
        
        model.addAttribute("application", app);
        model.addAttribute("watchers", watchers);
        
        return "applicationScmWatchers";
    }
}